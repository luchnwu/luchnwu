package com.tedu.straw.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tedu.straw.portal.mapper.ClassInfoMapper;
import com.tedu.straw.portal.mapper.QuestionMapper;
import com.tedu.straw.portal.mapper.UserMapper;
import com.tedu.straw.portal.mapper.UserRoleMapper;
import com.tedu.straw.portal.model.*;
import com.tedu.straw.portal.service.IUserService;
import com.tedu.straw.portal.service.ServiceException;
import com.tedu.straw.portal.vo.RegisterVo;
import com.tedu.straw.portal.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tedu.com
 * @since 2022-10-04
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private ClassInfoMapper classInfoMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final List<User> masters = new CopyOnWriteArrayList<>();
    private Map<String, User> masterMap = new ConcurrentHashMap<>();
    private final Timer timer = new Timer();

    {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (masters) {
                    masters.clear();
                    masterMap.clear();
                }
            }
        }, 1000 * 60 * 60, 1000 * 60 * 60);
    }

    @Override
    public UserDetails getUserDetails(String username) {
        User user = userMapper.findUserByUsername(username);
        if (user == null) {
            return null;
        }
        List<Permission> permissions = userMapper.findUserPermissionsById(user.getId());
        String[] authorities = new String[permissions.size()];
        int i = 0;
        for (Permission p : permissions) {
            authorities[i++] = p.getAuthority();
        }
        UserDetails userDetail = org.springframework.security.core.userdetails
                .User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .accountLocked(user.getIsLocked() == 1)
                .disabled(user.getIsEnabled() == 0)
                .authorities(authorities)
                .build();
        return userDetail;
    }

    @Override
    public void registerStudent(RegisterVo registerVo) {
        if (registerVo == null) {
            log.info("方法參數為null");
            throw ServiceException.unprocessableEntity("參數為空");
        }
        log.debug("方法參數{}", registerVo);
        log.debug("驗證邀請碼{}", registerVo.getInviteCode());
        QueryWrapper<ClassInfo> query = new QueryWrapper<>();
        query.eq("invite_code", registerVo.getInviteCode());
        ClassInfo classInfo = classInfoMapper.selectOne(query);
        if (classInfo == null) {
            log.info("驗證邀請失敗");
            throw ServiceException.notFound("無效邀請碼,請聯繫任課老師");
        }
        log.debug("驗證手機號碼是否使用{}", registerVo.getPhone());
        User user = userMapper.findUserByUsername(registerVo.getPhone());
        if (user != null) {
            log.info("手機號已經註冊過!");
            throw ServiceException.unprocessableEntity("手機號已經註冊過");
        }
        user = new User().setUsername(registerVo.getPhone()).setNickName(registerVo.getNickname());
        String password = passwordEncoder.encode(registerVo.getPassword());
        user.setPassword("{bcrypt}" + password)
        .setPhone(registerVo.getPhone())
        .setIsLocked(0).setIsEnabled(1)
        .setGmtCreate(LocalDateTime.now()).setClassId(classInfo.getId())
        .setDayOfBirth(null);
        int rows = userMapper.insert(user);
        if (rows != 1) {
            log.info("保存用戶信息失敗");
            throw new ServiceException("數據庫繁忙,請稍後嘗試");
        }
        log.debug("保存用戶數據成功{}", user);
        log.debug("設置用戶是一個學生角色");
        //role id = 2
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId()).setRoleId(2);
        rows = userRoleMapper.insert(userRole);
        if (rows != 1) {
            log.info("保存用戶角色信息失敗");
            throw new ServiceException("數據庫繁忙,請稍後嘗試");
        }
        log.debug("設置用戶的角色{}", userRole);
    }

    @Override
    public String currentUsername() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            return username;
        }
        throw ServiceException.notFound("沒有找到當前登陸用戶");
    }

    @Override
    public List<User> getMasters() {
        if (masters.isEmpty()) {
            synchronized (masters) {
                if (masters.isEmpty()) {
                    QueryWrapper<User> query = new QueryWrapper<>();
                    query.eq("account_Type", 1);

                    List<User> list = userMapper.selectList(query);
                    masters.addAll(list);
                    masters.forEach(master -> masterMap.put(master.getNickName(), master));
                    masters.forEach(master -> master.setPassword(""));//保護老師密碼不被讀取
                }
            }
        }
        return masters;
    }

    @Override
    public Map<String, User> getMasterMap() {
        if(masters.isEmpty()){
            getMasters();
        }
        return masterMap;
    }

    @Override
    public Integer countQuestionsByUserId(Integer userId) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);

        Integer count = questionMapper.selectCount(queryWrapper).intValue();
        return count;
    }

    @Override
    public UserVo getCurrentUserVo() {
        String username = currentUsername();
        UserVo userVo = userMapper.getUserVoByUsername(username);
        Integer questions = countQuestionsByUserId(userVo.getId());
        userVo.setQuestions(questions);

        return userVo;
    }

}
