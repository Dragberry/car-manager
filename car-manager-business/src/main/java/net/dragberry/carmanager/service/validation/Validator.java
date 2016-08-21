package net.dragberry.carmanager.service.validation;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import net.dragberry.carmanager.to.GenericIssue;
import net.dragberry.carmanager.to.ValidationIssue;

public interface Validator<E> {
	
	Collection<ValidationIssue<E>> validate(E entity);
	
	default ValidationIssue<E> issue(E entity, int msgCode, Serializable... params) {
		return new GenericIssue<E>(entity, msgCode, params);
	}
	
	default Collection<ValidationIssue<E>> noIssues() {
		return Collections.emptyList();
	}
	
	default Collection<ValidationIssue<E>> issues(ValidationIssue<E> issue) {
		return Arrays.asList(issue);
	}

}
