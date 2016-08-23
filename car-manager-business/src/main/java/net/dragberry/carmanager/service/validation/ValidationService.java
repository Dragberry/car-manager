package net.dragberry.carmanager.service.validation;

import java.util.Collection;
import java.util.List;

public interface ValidationService<E> {
	
	Collection<ValidationIssue<E>> validate(List<E> entityList);
	
}
