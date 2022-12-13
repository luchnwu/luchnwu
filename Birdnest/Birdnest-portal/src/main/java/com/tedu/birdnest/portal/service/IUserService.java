package com.tedu.birdnest.portal.service;

import com.tedu.birdnest.portal.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tedu.birdnest.portal.vo.R;
import com.tedu.birdnest.portal.vo.RegisterVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tedu.com
 * @since 2022-11-02
 */
public interface IUserService extends IService<User> {

    R register(RegisterVo registerVo);
}
