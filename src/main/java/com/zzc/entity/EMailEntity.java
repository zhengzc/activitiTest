package com.zzc.entity;

import java.io.Serializable;

/**
 * 用作绑定发送email的模版
 * @author zhengzhichao
 *
 */
public class EMailEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 收件人
	 */
	private String to;
	/**
	 * 发件人
	 */
	private String from;
	/**
	 * 主题
	 */
	private String subject;
	/**
	 * 抄送
	 */
	private String cc;
	/**
	 * 暗抄送
	 */
	private String bcc;
	/**
	 * 字符编码
	 */
	private String charset;
	/**
	 * html格式
	 */
	private String htmlText;
	/**
	 * 非html格式
	 */
	private String nonHtmlText;
	
	
	private EMailEntity(String to,String from,String subject,String cc,String bcc,String charset,String htmlText,String nonHtmlText){
		this.to = to;
		this.from = from;
		this.subject = subject;
		this.cc = cc;
		this.bcc = bcc;
		this.charset = charset;
		this.htmlText = htmlText;
		this.nonHtmlText = nonHtmlText;
	}
	
	public static EMailEntity create(String to,String from,String subject,String cc,String bcc,String htmlText,String nonHtmlText){
		return new EMailEntity(to,from,subject,cc,bcc,"UTF-8",htmlText,nonHtmlText);
	}
	
	public static EMailEntity create(String to,String from,String subject,String htmlText,String nonHtmlText){
		return new EMailEntity(to,from,subject,null,null,"UTF-8",htmlText,nonHtmlText);
	}
	
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getBcc() {
		return bcc;
	}
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getHtmlText() {
		return htmlText;
	}
	public void setHtmlText(String htmlText) {
		this.htmlText = htmlText;
	}

	public String getNonHtmlText() {
		return nonHtmlText;
	}

	public void setNonHtmlText(String nonHtmlText) {
		this.nonHtmlText = nonHtmlText;
	}
}
