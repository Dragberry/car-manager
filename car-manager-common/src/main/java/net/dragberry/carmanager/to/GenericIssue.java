package net.dragberry.carmanager.to;

import java.io.Serializable;

public class GenericIssue<E> implements ValidationIssue<E> {
	
	private int msgNumber;
	private Serializable[] params;
	private E entity;
	
	public GenericIssue(E entity, int msgNumber, Serializable... params) {
		this.msgNumber = msgNumber;
		this.entity = entity;
		this.params = params;
	}

	public int getMsgNumber() {
		return msgNumber;
	}

	public E getEntity() {
		return entity;
	}

	public Serializable[] getParams() {
		return params;
	}
	
}
