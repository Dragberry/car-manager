package net.dragberry.carmanager.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import net.dragberry.carmanager.common.TransactionStatus;

@Converter
public class TransactionStatusConverter implements AttributeConverter<TransactionStatus, Character> {

	@Override
	public Character convertToDatabaseColumn(TransactionStatus attribute) {
		return attribute.getValue();
	}

	@Override
	public TransactionStatus convertToEntityAttribute(Character dbData) {
		return TransactionStatus.valueOf(dbData);
	}

}
