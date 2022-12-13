package com.tedu.birdnest.portal.service.impl;

import com.tedu.birdnest.portal.mapper.AccountMapper;
import com.tedu.birdnest.portal.mapper.UserAccountMapper;
import com.tedu.birdnest.portal.model.Account;
import com.tedu.birdnest.portal.model.User;
import com.tedu.birdnest.portal.mapper.UserMapper;
import com.tedu.birdnest.portal.model.UserAccount;
import com.tedu.birdnest.portal.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tedu.birdnest.portal.service.ServiceException;
import com.tedu.birdnest.portal.vo.R;
import com.tedu.birdnest.portal.vo.RegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tedu.com
 * @since 2022-11-02
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    UserMapper userMapper;
    @Resource
    AccountMapper accountMapper;
    @Resource
    UserAccountMapper userAccountMapper;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public R register(RegisterVo registerVo) {
        //先確定用戶有正確輸入
        if (registerVo == null) {
            log.info("方法參數為null");
            throw ServiceException.unprocessableEntity("參數為空");
        }
        if (!registerVo.getPassword().equals(registerVo.getConfirm())) {
            return R.unprocessableEntity("確認密碼不一致!!");
        }

        //確認用戶不存在
        Account account = accountMapper.findAccountByUsername(registerVo.getUsername());
        if (account != null) {
            log.info("用戶已經註冊過!");
            return R.unprocessableEntity("信箱已經註冊過");
        }

        //確認沒有再進行註冊
        String password = passwordEncoder.encode(registerVo.getPassword());
        account = new Account()
                .setUsername(registerVo.getUsername())
                .setPassword("{bcrypt}"+password);
        User user = new User()
                .setName(registerVo.getName())
                .setPhone(registerVo.getPhone())
                .setIsLocked(1)
                .setIsEnabled(1);
        accountMapper.insert(account);
        userMapper.insert(user);
        UserAccount userAccount = new UserAccount()
                .setAccountId(account.getId())
                .setUserId(user.getId());
        userAccountMapper.insert(userAccount);
        return R.created("註冊成功");
    }
}
