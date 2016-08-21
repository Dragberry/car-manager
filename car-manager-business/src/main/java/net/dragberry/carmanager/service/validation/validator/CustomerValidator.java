package net.dragberry.carmanager.service.validation.validator;

import java.util.Collection;

import net.dragberry.carmanager.domain.Transaction;
import net.dragberry.carmanager.to.ValidationIssue;

public class CustomerValidator extends AbstractValidator<Transaction> {

	@Override
	public Collection<ValidationIssue<Transaction>> validate(Transaction entity) {
		if (entity.getCustomer() == null) {
			return issues(issue(entity, 2));
		}
		return noIssues();
	}

}
