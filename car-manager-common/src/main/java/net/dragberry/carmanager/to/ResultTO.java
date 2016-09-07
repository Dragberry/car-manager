package net.dragberry.carmanager.to;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;

public class ResultTO<T extends TransferObject> implements TransferObject {

	private static final long serialVersionUID = -7018780169278859998L;
	
	private T object;
	
	private Collection<IssueTO> issues = new ArrayList<>();
	
	public ResultTO(T object) {
		this.object = object;
	}
	
	public ResultTO(T object, Collection<IssueTO> issues) {
		this.object = object;
		this.issues = issues;
	}

	public T getObject() {
		return object;
	}

	public Collection<IssueTO> getIssues() {
		return issues;
	}
	
	public boolean hasIssues() {
		return !CollectionUtils.isEmpty(issues);
	}

}
