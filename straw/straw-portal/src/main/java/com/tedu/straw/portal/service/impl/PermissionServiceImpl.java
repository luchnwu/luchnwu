package com.tedu.straw.portal.service.impl;

import com.tedu.straw.portal.model.Permission;
import com.tedu.straw.portal.mapper.PermissionMapper;
import com.tedu.straw.portal.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.com
 * @since 2022-10-04
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
