package net.dragberry.carmanager.service.validation.validator;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import net.dragberry.carmanager.domain.Transaction;
import net.dragberry.carmanager.service.validation.ValidationIssue;
import net.dragberry.carmanager.service.validation.ValidationIssues;

public class DescriptionValidator extends AbstractValidator<Transaction> {

	private static final int DESCRIPTION_LENGTH_LIMIT = 128;

	@Override
	public Collection<ValidationIssue<Transaction>> validate(Transaction entity) {
		if (StringUtils.isBlank(entity.getDescription())) {
			return issues(issue(entity, ValidationIssues.DESCRIPTION_IS_MANDATORY));
		}
		if (entity.getDescription().length() > DESCRIPTION_LENGTH_LIMIT) {
			return issues(issue(entity, ValidationIssues.DESCRIPTION_LENGTH_LIMIT_EXCEEDED, 128));
		}
		return noIssues();
	}

}
