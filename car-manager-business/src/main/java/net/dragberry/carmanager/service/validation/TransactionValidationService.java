package net.dragberry.carmanager.service.validation;

import java.util.ArrayList;
import java.util.Collection;
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
		Collection<ValidationIssue<Transaction>> issues = new ArrayList<>();
		entityList.forEach(entity -> {
			validationGroup.getValidators().forEach(validator -> issues.addAll(validator.validate(entity)));
		});
		
		return issues;
	}

}
