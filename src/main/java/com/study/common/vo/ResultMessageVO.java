package com.study.common.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ResultMessageVO {
	private boolean result;
	private String title;
	private String message;
	private String url;
	private String urlTitle;
	
	// 메서드 체이
	//기존 setter는 void를 반환하는데 void가 아닌 this(자기자신)을 반환하도록 수정한다.
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	public boolean isResult() {
		return result;
	}
	public ResultMessageVO setResult(boolean result) {
		this.result = result;
		return this;
	}
	public String getTitle() {
		return title;
	}
	public ResultMessageVO setTitle(String title) {
		this.title = title;
		return this;
	}
	public String getMessage() {
		return message;
	}
	public ResultMessageVO setMessage(String message) {
		this.message = message;
		return this;
	}
	public String getUrl() {
		return url;
	}
	public ResultMessageVO setUrl(String url) {
		this.url = url;
		return this;
	}
	public String getUrlTitle() {
		return urlTitle;
	}
	public ResultMessageVO setUrlTitle(String urlTitle) {
		this.urlTitle = urlTitle;
		return this;
	}
	
}
