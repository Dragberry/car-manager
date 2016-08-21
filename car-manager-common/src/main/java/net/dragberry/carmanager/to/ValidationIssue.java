package net.dragberry.carmanager.to;

import java.io.Serializable;

public interface ValidationIssue<E> {
	
	int getMsgNumber();

	E getEntity();

	Serializable[] getParams();

}
