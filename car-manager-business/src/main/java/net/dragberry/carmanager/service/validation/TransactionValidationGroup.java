package net.dragberry.carmanager.service.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import net.dragberry.carmanager.domain.Transaction;

public class TransactionValidationGroup implements ValidationGroup<Transaction> {
	
	private Collection<Validator<Transaction>> validators = new ArrayList<>();
	
	@Override
	public Collection<Validator<Transaction>> getValidators() {
		return Collections.unmodifiableCollection(validators);
	}

	@Override
	public void add(Validator<Transaction> validator) {
		validators.add(validator);
	}


}
