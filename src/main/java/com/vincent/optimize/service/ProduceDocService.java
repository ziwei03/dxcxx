package com.vincent.optimize.service;

import com.vincent.optimize.assist.SL_Busi;
import com.vincent.optimize.service.question.ParallerQstService;
import com.vincent.optimize.service.question.SingleQstService;
import com.vincent.optimize.vo.SrcDocVo;
import com.vincent.optimize.vo.TaskResultVo;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class ProduceDocService {

    /**
     * 上传文档到网络
     * @param docFileName 实际文档在本地的存储位置
     * @return 上传后的网络存储地址
     */
    public static String upLoadDoc(String docFileName){
        Random r = new Random();
        SL_Busi.buisness(9000+r.nextInt(400));
        return "http://www.xxxx.com/file/upload/"+docFileName;
    }

    /**
     * 将待处理文档处理为本地实际PDF文档
     * @param pendingDocVo 待处理文档
     * @return 实际文档在本地的存储位置
     */
    public static String makeDoc(SrcDocVo pendingDocVo){
        System.out.println("开始处理文档："+ pendingDocVo.getDocName());
        StringBuffer sb = new StringBuffer();
        //循环处理文档中的每个题目
        for(Integer questionId: pendingDocVo.getQuestionList()){
            sb.append(SingleQstService.makeQuestion(questionId));
        }
        return "complete_"+System.currentTimeMillis()+"_"
        	+ pendingDocVo.getDocName()+".pdf";
    }
    
    //异步化处理题目的方法
    public static String makeDocAsyn(SrcDocVo pendingDocVo) throws 
    InterruptedException, ExecutionException{
    	System.out.println("开始处理文档："+ pendingDocVo.getDocName());
    	
    	Map<Integer, TaskResultVo> qstResultMap = new HashMap<>();
        //循环处理文档中的每个题目,准备并行化处理题目
        for(Integer questionId: pendingDocVo.getQuestionList()){
        	qstResultMap.put(questionId, ParallerQstService.makeQuestion(questionId));
        }
        StringBuffer sb = new StringBuffer();
        for(Integer questionId: pendingDocVo.getQuestionList()){
        	TaskResultVo result = qstResultMap.get(questionId);
        	sb.append(result.getQuestionDetail()==null?
        			result.getQuestionFuture().get().getQuestionDetail()
        			:result.getQuestionDetail());
        }
        return "complete_"+System.currentTimeMillis()+"_"
    	+ pendingDocVo.getDocName()+".pdf";       
    	
    }


}
