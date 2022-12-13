package com.tedu.straw.portal.controller;


import com.tedu.straw.portal.model.User;
import com.tedu.straw.portal.service.IUserService;
import com.tedu.straw.portal.vo.R;
import com.tedu.straw.portal.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tedu.com
 * @since 2022-10-04
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private IUserService userService;

//    @GetMapping("/get")
//    @PreAuthorize("hasAuthority('/user/get')")
//    public User get(Integer id) {
//        return userService.getById(id);
//    }
//
//    @GetMapping("/list")
//    @PreAuthorize("hasAuthority('/user/list')")
//    public List<User> list() {
//        return userService.list();
//    }

    @GetMapping("/masters")
    public R<List<User>> masters() {
        List<User> masters = userService.getMasters();
        return R.ok(masters);
    }

    @GetMapping("/me")
    public R<UserVo> me() {
        UserVo userVo = userService.getCurrentUserVo();
        return R.ok(userVo);
    }

}
