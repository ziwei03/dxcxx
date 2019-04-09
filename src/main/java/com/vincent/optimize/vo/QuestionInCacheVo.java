package com.vincent.optimize.vo;


public class QuestionInCacheVo {
	
	private final String questionDetail;
	private final String questionSha;
	
	public QuestionInCacheVo(String questionDetail, String questionSha) {
		super();
		this.questionDetail = questionDetail;
		this.questionSha = questionSha;
	}
	public String getQuestionDetail() {
		return questionDetail;
	}
	public String getQuestionSha() {
		return questionSha;
	}
	
	

}
