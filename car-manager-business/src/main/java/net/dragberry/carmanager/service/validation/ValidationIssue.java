package net.dragberry.carmanager.service.validation;

import java.io.Serializable;

public interface ValidationIssue<E> {
	
	String getMsgNumber();

	E getEntity();

	Serializable[] getParams();

}
