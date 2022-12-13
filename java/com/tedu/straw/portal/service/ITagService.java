package com.tedu.straw.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tedu.straw.portal.model.Tag;

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
public interface ITagService extends IService<Tag> {

    List<Tag> getTags();

    Map<Integer, Tag> getId2TagMap();

}
