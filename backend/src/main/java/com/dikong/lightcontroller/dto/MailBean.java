package com.dikong.lightcontroller.dto;

import java.util.Map;

/**
 * 发送邮件的实体
 */
public class MailBean {

	  private String from;
      private String[] to;
      private String[] cc;
      private String subject;
      private String content;
      private String templateName;
      private Map<String, String> images;
      private Map<String, byte[]> files;
      private boolean html;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public String[] getCc() {
		return cc;
	}

	public void setCc(String[] cc) {
		this.cc = cc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Map<String, String> getImages() {
		return images;
	}

	public void setImages(Map<String, String> images) {
		this.images = images;
	}

	public Map<String, byte[]> getFiles() {
		return files;
	}

	public void setFiles(Map<String, byte[]> files) {
		this.files = files;
	}

	public boolean isHtml() {
		return html;
	}

	public void setHtml(boolean html) {
		this.html = html;
	}
}
