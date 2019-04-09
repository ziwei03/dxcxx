package com.vincent.optimize.service.question;


import com.vincent.optimize.assist.SL_QuestionBank;

public class SingleQstService {

    /**
     * 对题目进行处理
     * @param questionId 题目id
     * @return 题目解析后的文本
     */
    public static String makeQuestion(Integer questionId){
        return BaseQuestionProcessor.makeQuestion(questionId,
                SL_QuestionBank.getQuetion(questionId).getDetail());
    }

}
