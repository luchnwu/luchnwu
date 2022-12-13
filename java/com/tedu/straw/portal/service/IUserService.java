package com.tedu.straw.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tedu.straw.portal.model.User;
import com.tedu.straw.portal.vo.RegisterVo;
import com.tedu.straw.portal.vo.UserVo;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tedu.com
 * @since 2022-10-04
 */
public interface IUserService extends IService<User> {

    UserDetails getUserDetails(String username);

    void registerStudent(RegisterVo registerVo);

    String currentUsername();

    List<User> getMasters();

    Map<String, User> getMasterMap();

    Integer countQuestionsByUserId(Integer userId);

    UserVo getCurrentUserVo();

}
