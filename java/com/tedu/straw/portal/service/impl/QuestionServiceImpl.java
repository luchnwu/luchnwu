package com.tedu.straw.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tedu.straw.portal.mapper.*;
import com.tedu.straw.portal.model.*;
import com.tedu.straw.portal.service.IQuestionService;
import com.tedu.straw.portal.service.ITagService;
import com.tedu.straw.portal.service.IUserService;
import com.tedu.straw.portal.service.ServiceException;
import com.tedu.straw.portal.vo.QuestionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

    @Autowired
    private IUserService userService;

    @Autowired
    private ITagService tagService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private QuestionTagMapper questionTagMapper;

    @Autowired
    private UserQuestionMapper userQuestionMapper;

    @Override
    public PageInfo<Question> getMyQuestions(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageSize == null) {
            throw new ServiceException("翻頁參數錯誤");
        }

        String username = userService.currentUsername();
        log.debug("當前用戶名{}", username);
        User user = userMapper.findUserByUsername(username);
        log.debug("當前登陸用戶{}", user);
        if (user == null) {
            throw ServiceException.notFound("登陸用戶沒有找到");
        }
        QueryWrapper<Question> query = new QueryWrapper<>();
        query.eq("user_id", user.getId());
        query.orderByDesc("gmt_create");

        PageHelper.startPage(pageNum, pageSize);
        List<Question> questions = questionMapper.selectList(query);
        log.debug("查詢得到{}行數據", questions.size());

        for (Question q : questions) {
            List<Tag> tags = tagIdsToTags(q.getTagIds());
            q.setTags(tags);
        }
        return new PageInfo<>(questions);
    }

    /**
     *
     * @param tagIds
     * @return
     */
    private List<Tag> tagIdsToTags(String tagIds) {
        String[] ids = tagIds.split(",\\s?");
        Map<Integer, Tag> id2TagMap = tagService.getId2TagMap();
        List<Tag> tags = new ArrayList<>();
        for (String id : ids) {
            Tag tag = id2TagMap.get(Integer.parseInt(id));
            tags.add(tag);
        }
        return tags;
    }

    /**
     *
     * @param questionVo
     */
    @Override
    @Transactional
    public void saveQuestion(QuestionVo questionVo) {
        log.debug("問題信息{}", questionVo);

        String username = userService.currentUsername();

        User user = userMapper.findUserByUsername(username);
        log.debug("當前用戶{}", user);

        StringBuilder buf = new StringBuilder();
        for (String tagName : questionVo.getTagNames()) {
            Tag tag = new Tag();
            tag.setName(tagName);
            Integer id = tagMapper.selectOne(new QueryWrapper<>(tag)).getId();
            buf.append(id).append(",");
        }
        String tagIds = buf.deleteCharAt(buf.length() - 1).toString();
        log.debug("標籤ID列表{}", tagIds);

        Question question = new Question()
                .setTitle(questionVo.getTitle())
                .setContent(questionVo.getContent())
                .setStatus(0)
                .setHits(0)
                .setGmtCreate(LocalDateTime.now())
                .setGmtModified(LocalDateTime.now())
                .setUserId(user.getId())
                .setUserNickName(user.getNickName())
                .setTagIds(tagIds);

        int rows = questionMapper.insert(question);
        log.debug("問題信息{}", question);

        if (rows != 1) {
            throw new ServiceException("數據庫繁忙，請稍後再嘗試");
        }


        for (String tagName : questionVo.getTagNames()) {
            Tag tag = new Tag();
            tag.setName(tagName);
            if (tag == null) {
                throw ServiceException.unprocessableEntity("標籤名稱錯誤");
            }
            Integer id = tagMapper.selectOne(new QueryWrapper<>(tag)).getId();

            QuestionTag questionTag = new QuestionTag()
                    .setQuestionId(question.getId())
                    .setTagId(id)
                    .setGmtCreate(LocalDateTime.now())
                    .setGmtModified(LocalDateTime.now());
            rows = questionTagMapper.insert(questionTag);
            log.debug("問題標籤信息{}", questionTag);

            if (rows != 1) {
                throw new ServiceException("數據庫繁忙，請稍後再嘗試");
            }
        }

        Map<String, User> masterMap = userService.getMasterMap();
        for (String nickname : questionVo.getTeacherNicknames()) {
            User master = masterMap.get(nickname);
            if (master == null) {
                throw ServiceException.unprocessableEntity("講師名稱錯誤");
            }
            UserQuestion userQuestion = new UserQuestion()
                    .setQuestionId(question.getId())
                    .setUserId(master.getId())
                    .setGmtCreate(LocalDateTime.now())
                    .setGmtModified(LocalDateTime.now());
            rows = userQuestionMapper.insert(userQuestion);
            log.debug("問題標籤信息{}", userQuestion);

            if (rows != 1) {
                throw new ServiceException("數據庫繁忙，請稍後再嘗試");
            }
        }
    }

}
