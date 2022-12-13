package com.tedu.birdnest.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tedu.birdnest.portal.mapper.UserAccountMapper;
import com.tedu.birdnest.portal.model.UserAccount;
import com.tedu.birdnest.portal.service.IUserAccountService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.com
 * @since 2022-11-07
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements IUserAccountService {

}
