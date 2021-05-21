package com.study.member.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.study.common.valid.Step1;
import com.study.common.valid.Step2;
import com.study.common.valid.Step3;

public class MemberVO {
	@NotBlank(message = "이용약관은 필수입니다",groups =Step1.class )
	private String agreeYn;
	public String getAgreeYn() {
		return agreeYn;
	}

	public void setAgreeYn(String agreeYn) {
		this.agreeYn = agreeYn;
	}

	public String getPrivacyYn() {
		return privacyYn;
	}

	public void setPrivacyYn(String privacyYn) {
		this.privacyYn = privacyYn;
	}

	public String getEventYn() {
		return eventYn;
	}

	public void setEventYn(String eventYn) {
		this.eventYn = eventYn;
	}

	public String getMemPassConfirm() {
		return memPassConfirm;
	}

	public void setMemPassConfirm(String memPassConfirm) {
		this.memPassConfirm = memPassConfirm;
	}
	@NotBlank(message = "개인정보이용동의는  필수입니다",groups =Step1.class )
	private String privacyYn;
	
	private String eventYn;
	
	private String memPassConfirm;  //memPass랑 같은지 여부는 
									//자바스크립트로 하기때문에
									//따로 @validation 안해도 ok 
	//회원가입 step부분
	
	@NotBlank(message = "memId는 필수입니다",groups = {Default.class,Step2.class})
	private String memId;                   /*회원아이디*/
	@NotBlank(message = "memPass는 필수입니다",groups = {Default.class,Step2.class})
	private String memPass;                 /*회원비밀번호*/
	@NotBlank(message = "memName는 필수입니다",groups = {Default.class,Step2.class})
	private String memName;                 /*회원이름*/
	
	@NotBlank(message = "는 필수입니다",groups = {Default.class,Step3.class})
//	@Pattern(regexp = "/^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/",message =  "날짜형식이아닙니다")
	private String memBir;                  /*회원생일*/
	@NotBlank(message = "필수입니다",groups= {Default.class,Step3.class})
	private String memZip;                  /*회원우편번호*/
	@NotBlank(message = "필수입니다",groups= {Default.class,Step3.class})
	private String memAdd1;                 /*회원주소*/
	@NotBlank(message = "필수입니다",groups= {Default.class,Step3.class})
	private String memAdd2;                 /*회원상세주소*/
	@NotBlank(message = "필수입니다",groups= {Default.class,Step3.class})
	private String memHp;                   /*회원전화번호*/
	@NotBlank(message = "memMail는 필수입니다",groups = {Default.class,Step2.class})
	//@Email
	private String memMail;                 /*회원이메일*/
	@NotBlank(message = "필수입니다",groups= {Default.class,Step3.class})
	private String memJob;                  /*회원직업*/
	@NotBlank(message = "필수입니다",groups= {Default.class,Step3.class})
	private String memLike;                 /*회원취미*/
	private int memMileage;                 /*회원마일리지*/
	private String memDelYn;                /*회원삭제여부*/
	
	
	//codeVO 
	private String memJobNm;
	private String memLikeNm;
	
	public String getMemJobNm() {
		return memJobNm;
	}

	public void setMemJobNm(String memJobNm) {
		this.memJobNm = memJobNm;
	}

	public String getMemLikeNm() {
		return memLikeNm;
	}

	public void setMemLikeNm(String memLikeNm) {
		this.memLikeNm = memLikeNm;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getMemPass() {
		return memPass;
	}
	public void setMemPass(String memPass) {
		this.memPass = memPass;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public String getMemBir() {
		return memBir;
	}
	public void setMemBir(String memBir) {
		this.memBir = memBir;
	}
	public String getMemZip() {
		return memZip;
	}
	public void setMemZip(String memZip) {
		this.memZip = memZip;
	}
	public String getMemAdd1() {
		return memAdd1;
	}
	public void setMemAdd1(String memAdd1) {
		this.memAdd1 = memAdd1;
	}
	public String getMemAdd2() {
		return memAdd2;
	}
	public void setMemAdd2(String memAdd2) {
		this.memAdd2 = memAdd2;
	}
	public String getMemHp() {
		return memHp;
	}
	public void setMemHp(String memHp) {
		this.memHp = memHp;
	}
	public String getMemMail() {
		return memMail;
	}
	public void setMemMail(String memMail) {
		this.memMail = memMail;
	}
	public String getMemJob() {
		return memJob;
	}
	public void setMemJob(String memJob) {
		this.memJob = memJob;
	}
	public String getMemLike() {
		return memLike;
	}
	public void setMemLike(String memLike) {
		this.memLike = memLike;
	}
	public int getMemMileage() {
		return memMileage;
	}
	public void setMemMileage(int memMileage) {
		this.memMileage = memMileage;
	}
	public String getMemDelYn() {
		return memDelYn;
	}
	public void setMemDelYn(String memDelYn) {
		this.memDelYn = memDelYn;
	}
	
}
