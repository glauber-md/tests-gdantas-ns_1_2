/**
 * 
 */
package com.gdantas.data.ws.input;

import javax.ws.rs.core.Response;

import com.gdantas.data.enums.DefaultReplyCodes;

/**
 * @author Glauber M. Dantas glauber.md@gmail.com
 *
 */
public class ReplyMessage {
	
	private int code;
	private String message;
	private Object data;
	
	public ReplyMessage() {}
	
	public ReplyMessage(DefaultReplyCodes replyCode, Object data) {
		if(replyCode != null) { 
			this.code = replyCode.getCode().getStatusCode();
			this.message = replyCode.getDescription();
		}
		this.data = data;
	}
	
	public ReplyMessage(Response.Status code, String msg, Object data) {
		if(code != null)
			this.code = code.getStatusCode();
		this.message = msg;
		this.data = data;
	}
	
	public ReplyMessage(int code, String msg, Object data) {
		this.code = code;
		this.message = msg;
		this.data = data;
	}
	
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the body
	 */
	public Object getData() {
		return data;
	}
	/**
	 * @param data the body to set
	 */
	public void setData(Object data) {
		this.data = data;
	}
	
}
