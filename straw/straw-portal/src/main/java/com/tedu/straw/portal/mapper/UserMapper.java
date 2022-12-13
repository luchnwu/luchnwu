package com.tedu.straw.portal.mapper;

import com.tedu.straw.portal.model.Permission;
import com.tedu.straw.portal.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tedu.straw.portal.vo.UserVo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tedu.com
 * @since 2022-10-04
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     *
     * @param username
     * @return
     */

    @Select("SELECT * FROM user WHERE username=#{username}")
    User findUserByUsername(String username);

    /**
     *
     * @param id
     * @return
     */

    @Select("SELECT p.id,p.authority " +
            "FROM user u " +
            "LEFT JOIN user_role ur on u.id = ur.user_id " +
            "LEFT JOIN role r on r.id = ur.role_id " +
            "LEFT JOIN role_permission rp on r.id = rp.role_id " +
            "LEFT JOIN permission p on p.id = rp.permission_id " +
            "where u.id = #{id}")
    List<Permission> findUserPermissionsById(Integer id);

    @Select("SELECT id,username,nick_name " +
            "FROM user " +
            "WHERE username = #{username}")
    UserVo getUserVoByUsername(String username);

}
