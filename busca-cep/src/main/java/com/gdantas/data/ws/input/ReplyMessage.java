/**
 * 
 */
package com.gdantas.data.ws.input;

import com.gdantas.data.enums.DefaultReplyCodes;

/**
 * @author Glauber M. Dantas glauber.md@gmail.com
 *
 */
public class ReplyMessage {
	
	private int code;
	private String message;
	private Object body;
	
	public ReplyMessage() {}
	
	public ReplyMessage(DefaultReplyCodes replyCode, Object body) {
		if(replyCode != null) { 
			this.code = replyCode.getCode();
			this.message = replyCode.getDescription();
		}
		this.body = body;
	}
	
	public ReplyMessage(int code, String msg, Object body) {
		this.code = code;
		this.message = msg;
		this.body = body;
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
	public Object getBody() {
		return body;
	}
	/**
	 * @param body the body to set
	 */
	public void setBody(Object body) {
		this.body = body;
	}
	
}
