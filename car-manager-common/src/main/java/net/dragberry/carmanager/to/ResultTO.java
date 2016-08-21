package net.dragberry.carmanager.to;

import java.util.ArrayList;
import java.util.Collection;

public class ResultTO<T extends TransferObject> implements TransferObject {

	private static final long serialVersionUID = -7018780169278859998L;
	
	private T object;
	
	private Collection<ValidationIssue<T>> issues = new ArrayList<>();
	
	public ResultTO(T object) {
		this.object = object;
	}
	
	public ResultTO(T object, Collection<ValidationIssue<T>> issues) {
		this.object = object;
		this.issues = issues;
	}

	public T getObject() {
		return object;
	}

	public Collection<ValidationIssue<T>> getIssues() {
		return issues;
	}

}
