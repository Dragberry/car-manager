package net.dragberry.carmanager.service.validation;

import java.util.Collection;
import java.util.List;

import net.dragberry.carmanager.to.ValidationIssue;

public interface ValidationService<E> {
	
	Collection<ValidationIssue<E>> validate(List<E> entityList);
	
}
