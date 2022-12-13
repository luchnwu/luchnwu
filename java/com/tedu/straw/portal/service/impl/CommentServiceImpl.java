package com.tedu.straw.portal.service.impl;

import com.tedu.straw.portal.model.Comment;
import com.tedu.straw.portal.mapper.CommentMapper;
import com.tedu.straw.portal.service.ICommentService;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
