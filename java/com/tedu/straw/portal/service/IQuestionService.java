package com.tedu.straw.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.tedu.straw.portal.model.Question;
import com.tedu.straw.portal.vo.QuestionVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tedu.com
 * @since 2022-10-04
 */
public interface IQuestionService extends IService<Question> {

    PageInfo<Question> getMyQuestions(Integer pageNum, Integer pageSize);

    void saveQuestion(QuestionVo questionVo);

}
