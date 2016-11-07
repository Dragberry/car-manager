package net.dragberry.carmanager.to;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class IssueTO implements TransferObject {

	private static final long serialVersionUID = 3479635029841031940L;
	
	private String msgCode;
	
	private Serializable[] params;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public Serializable[] getParams() {
		return params;
	}

	public void setParams(Serializable[] params) {
		this.params = params;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof IssueTO) {
			IssueTO issue = (IssueTO) obj;
			return StringUtils.equals(msgCode, issue.msgCode);

		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return 31 + (msgCode != null ? msgCode.hashCode() : 0);
	}
	
}
