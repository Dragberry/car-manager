package net.dragberry.carmanager.service.validation.validator;

import java.math.BigDecimal;
import java.util.Collection;

import net.dragberry.carmanager.domain.Transaction;
import net.dragberry.carmanager.service.validation.ValidationIssue;
import net.dragberry.carmanager.service.validation.ValidationIssues;

public class AmountValidator extends AbstractValidator<Transaction> {

	@Override
	public Collection<ValidationIssue<Transaction>> validate(Transaction entity) {
		BigDecimal amount = entity.getAmount();
		if (amount == null) {
			return issues(issue(entity, ValidationIssues.AMOUNT_IS_MANDATORY));
		} else if (BigDecimal.ZERO.compareTo(amount) >= 0) {
			return issues(issue(entity, ValidationIssues.AMOUNT_IS_ZERO_OR_LESS));
		}
		return noIssues();
	}

}
