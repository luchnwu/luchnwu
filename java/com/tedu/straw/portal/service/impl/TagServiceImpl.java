package com.tedu.straw.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tedu.straw.portal.mapper.TagMapper;
import com.tedu.straw.portal.model.Tag;
import com.tedu.straw.portal.service.ITagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    private final List<Tag> tags = new CopyOnWriteArrayList<>();

    private final Map<Integer, Tag> Id2TagMap = new ConcurrentHashMap<>();

    @Override
    public List<Tag> getTags() {
        if (tags.isEmpty()) {
            synchronized (tags) {
                if (tags.isEmpty()) {
                    tags.addAll(list());
                    tags.forEach(tag -> Id2TagMap.put(tag.getId(), tag));
                    log.debug("load tags{}", tags);
                    log.debug("load map{}", Id2TagMap);
                }
            }
        }
        return tags;
    }

    @Override
    public Map<Integer, Tag> getId2TagMap() {
        if (tags.isEmpty()) {
            getTags();
        }
        return Id2TagMap;
    }
}
