package net.dragberry.carmanager.service.validation.validator;

import java.util.Collection;

import net.dragberry.carmanager.domain.Transaction;
import net.dragberry.carmanager.service.validation.ValidationIssue;
import net.dragberry.carmanager.service.validation.ValidationIssues;

public class CustomerValidator extends AbstractValidator<Transaction> {

	@Override
	public Collection<ValidationIssue<Transaction>> validate(Transaction entity) {
		if (entity.getCustomer() == null) {
			return issues(issue(entity, ValidationIssues.CUSTOMER_IS_MANDATORY));
		}
		return noIssues();
	}

}
