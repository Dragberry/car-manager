package net.dragberry.carmanager.service.validation;

import java.io.Serializable;

public class GenericIssue<E> implements ValidationIssue<E> {
	
	private String msgNumber;
	private Serializable[] params;
	private E entity;
	
	public GenericIssue(E entity, String msgNumber, Serializable... params) {
		this.msgNumber = msgNumber;
		this.entity = entity;
		this.params = params;
	}

	public String getMsgNumber() {
		return msgNumber;
	}

	public E getEntity() {
		return entity;
	}

	public Serializable[] getParams() {
		return params;
	}
	
}
