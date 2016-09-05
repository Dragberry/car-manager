package net.dragberry.carmanager.service.validation;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.dragberry.carmanager.domain.Transaction;

@Service("TransactionValidationService")
public class TransactionValidationService implements ValidationService<Transaction> {

	@Autowired
	private ValidationGroup<Transaction> validationGroup;
	
	@Override
	public Collection<ValidationIssue<Transaction>> validate(List<Transaction> entityList) {
		entityList.forEach(entity -> {
			validationGroup.getValidators().forEach(validator -> validator.validate(entity));
		});
		
		return Collections.emptyList();
	}

}
