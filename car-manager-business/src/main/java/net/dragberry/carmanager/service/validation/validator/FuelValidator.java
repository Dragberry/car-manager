package net.dragberry.carmanager.service.validation.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import net.dragberry.carmanager.domain.Fuel;
import net.dragberry.carmanager.domain.Transaction;
import net.dragberry.carmanager.domain.TransactionType;
import net.dragberry.carmanager.service.validation.ValidationIssue;
import net.dragberry.carmanager.service.validation.ValidationIssues;

public class FuelValidator extends AbstractValidator<Transaction> {

	@Override
	public Collection<ValidationIssue<Transaction>> validate(Transaction entity) {
		if (entity.getTransactionType() != null
				&& TransactionType.FUEL_KEY.equals(entity.getTransactionType().getEntityKey())) {
			Fuel fuel = entity.getFuel();
			if (fuel == null) {
				return issues(issue(entity, ValidationIssues.FUEL_IS_MANDATORY));
			}
			Collection<ValidationIssue<Transaction>> issues = new ArrayList<>();
			if (fuel.getCost() == null) {
				issues.add(issue(entity, ValidationIssues.FUEL_COST_IS_MANDATORY));
			} else if (fuel.getCost().compareTo(BigDecimal.ZERO) <= 0) {
				issues.add(issue(entity, ValidationIssues.FUEL_COST_IS_ZERO_OR_LESS));
			}
			if (fuel.getQuantity() == null) {
				issues.add(issue(entity, ValidationIssues.FUEL_QUANTITY_IS_MANDATORY));
			} else if (fuel.getQuantity() <= 0) {
				issues.add(issue(entity, ValidationIssues.FUEL_QUANTITY_IS_ZERO_OR_LESS));
			}
			if (StringUtils.isBlank(fuel.getType())) {
				issues.add(issue(entity, ValidationIssues.FUEL_TYPE_IS_MANDATORY));
			}
			return issues;
		}
		return noIssues();
	}

}
