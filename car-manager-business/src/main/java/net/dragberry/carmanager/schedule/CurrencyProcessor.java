package net.dragberry.carmanager.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import net.dragberry.carmanager.common.Currency;
import net.dragberry.carmanager.common.TransactionStatus;
import net.dragberry.carmanager.dao.TransactionDao;
import net.dragberry.carmanager.domain.Transaction;
import net.dragberry.carmanager.ws.client.CurrencyService;

@Service
public class CurrencyProcessor {
	
	@Autowired
	private CurrencyService currencyService;
	@Autowired
	private TransactionDao transactionDao;
	
	@Scheduled(cron = "0 0 2 * * *" )
	public void processTransactions() {
		List<Transaction> tnxList = transactionDao.fetchInactiveTransactions();
		tnxList.forEach(tnx -> {
			Double exRate = currencyService.getExchangeRate(Currency.USD, tnx.getExecutionDate());
			if (exRate != null) {
				tnx.setExchangeRate(exRate);
				tnx.setStatus(TransactionStatus.ACTIVE);
				transactionDao.update(tnx);
			}
		});
	}

}
