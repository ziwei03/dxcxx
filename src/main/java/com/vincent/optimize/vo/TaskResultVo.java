package com.vincent.optimize.vo;

import java.util.concurrent.Future;


public class TaskResultVo {
	
	private final String questionDetail;
	private final Future<QuestionInCacheVo> questionFuture;
	
	public TaskResultVo(String questionDetail) {
		this.questionDetail = questionDetail;
		this.questionFuture = null;
	}
	
	public TaskResultVo(Future<QuestionInCacheVo> questionFuture) {
		this.questionDetail = null;
		this.questionFuture = questionFuture;
	}
	
	public String getQuestionDetail() {
		return questionDetail;
	}
	public Future<QuestionInCacheVo> getQuestionFuture() {
		return questionFuture;
	}
	
	

}
