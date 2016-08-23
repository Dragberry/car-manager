package net.dragberry.carmanager.service.validation.validator;

import java.util.Collection;

import net.dragberry.carmanager.domain.Transaction;
import net.dragberry.carmanager.service.validation.ValidationIssue;
import net.dragberry.carmanager.service.validation.ValidationIssues;
import net.dragberry.carmanager.service.validation.Validator;

public class DateValidator implements Validator<Transaction> {

	@Override
	public Collection<ValidationIssue<Transaction>> validate(Transaction entity) {
		if (entity.getExecutionDate() == null) {
			return issues(issue(entity, ValidationIssues.DATE_IS_MANDATORY));
		}
		return noIssues();
	}
	
	

}
