package com.study.free.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.study.common.valid.ModifyType;



public class FreeBoardVO {
	// message는 검사를 통과하지 못했을 때 보여줌
	// form:errors 태그에 적용
	@NotNull(groups = ModifyType.class, message = "boNo 필수")
	private int boNo;                       /*글 번호*/
	@NotBlank(message = "글 제목은 필수입니다.")
	@Size(min = 2, message = "제목은 2글자 이상입니다.")
	private String boTitle;                 /*글 제목*/
	@NotBlank(message = "글 분류는 필수입니다.")
	private String boCategory;              /*글 분류 코드*/
	@NotBlank(message = "작성자는 필수입니다.")
	private String boWriter;                /*작성자명*/
	@NotBlank(message = "비밀번호는 필수입니다.")
	// @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message = "비밀번호는 8글자 이상")
	private String boPass;                  /*비밀번호*/
	@NotBlank(message = "글 내용은 필수입니다.")
	@Size(min = 2, message = "글 내용은 2자이상 작성해야합니다.")
	private String boContent;               /*글 내용*/
	private String boIp;                    /*등록자 IP*/
	private int boHit;                      /*조회수*/
	private String boRegDate;               /*등록 일자*/
	private String boModDate;               /*수정 일자*/
	private String boDelYn;                 /*삭제 여부*/
	
	//codeVO
	private String boCategoryNm;           /*글 분류 이름*/
	
	public String getBoCategoryNm() {
		return boCategoryNm;
	}


	public void setBoCategoryNm(String boCategoryNm) {
		this.boCategoryNm = boCategoryNm;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	
	public int getBoNo() {
		return boNo;
	}
	public void setBoNo(int boNo) {
		this.boNo = boNo;
	}
	public String getBoTitle() {
		return boTitle;
	}
	public void setBoTitle(String boTitle) {
		this.boTitle = boTitle;
	}
	public String getBoCategory() {
		return boCategory;
	}
	public void setBoCategory(String boCategory) {
		this.boCategory = boCategory;
	}
	public String getBoWriter() {
		return boWriter;
	}
	public void setBoWriter(String boWriter) {
		this.boWriter = boWriter;
	}
	public String getBoPass() {
		return boPass;
	}
	public void setBoPass(String boPass) {
		this.boPass = boPass;
	}
	public String getBoContent() {
		return boContent;
	}
	public void setBoContent(String boContent) {
		this.boContent = boContent;
	}
	public String getBoIp() {
		return boIp;
	}
	public void setBoIp(String boIp) {
		this.boIp = boIp;
	}
	public int getBoHit() {
		return boHit;
	}
	public void setBoHit(int boHit) {
		this.boHit = boHit;
	}
	public String getBoRegDate() {
		return boRegDate;
	}
	public void setBoRegDate(String boRegDate) {
		this.boRegDate = boRegDate;
	}
	public String getBoModDate() {
		return boModDate;
	}
	public void setBoModDate(String boModDate) {
		this.boModDate = boModDate;
	}
	public String getBoDelYn() {
		return boDelYn;
	}
	public void setBoDelYn(String boDelYn) {
		this.boDelYn = boDelYn;
	}
	
	
}
