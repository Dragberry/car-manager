package net.dragberry.carmanager.service.validation;

import java.util.Collection;

public interface ValidationGroup<E> {

	Collection<Validator<E>> getValidators();
	
	void add(Validator<E> validator);
	
}
