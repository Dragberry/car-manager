package net.dragberry.carmanager.application;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.RestTemplate;

import net.dragberry.carmanager.application.config.BusinessConfig;
import net.dragberry.carmanager.application.config.WSConfig;
import net.dragberry.carmanager.service.TransactionService;
import net.dragberry.carmanager.service.transfer.DataImporter;
import net.dragberry.carmanager.transferobject.Currency;
import net.dragberry.carmanager.transferobject.QueryListTO;
import net.dragberry.carmanager.transferobject.ResultList;
import net.dragberry.carmanager.transferobject.TransactionQueryListTO;
import net.dragberry.carmanager.transferobject.TransactionTO;
import net.dragberry.carmanager.ws.json.CurrencyExRate;

public class Launcher {

	public static void main(String[] args) throws Exception {
		
		/**/
			ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(BusinessConfig.class);
			TransactionService transactionService = context.getBean(TransactionService.class);
			TransactionQueryListTO query = new TransactionQueryListTO();
//			query.setDateFrom(LocalDate.of(2015, 1, 1));
//			query.setDateTo(LocalDate.of(2016, 1, 1));
//			query.setFuelQuantityFrom(30.0);
//			query.setAmountFrom(new BigDecimal("40"));
//			query.setCustomerKey(3L);
//			query.setCarKey(1L);
			query.setDisplayCurrency(Currency.USD);
			query.setTransactionTypeKeyList(Arrays.asList(1L, 2L, 3L, 4L));
			ResultList<TransactionTO> list = transactionService.fetchList(query);
			list.getResult().forEach(tnx -> {
				System.out.println(tnx.getTransactionKey() + " " + tnx.getDescription() + " за " + tnx.getAmount() + tnx.getCurrency() );
			});
			
//			DataImporter importer = context.getBean(DataImporter.class);
//			InputStream is = new FileInputStream("c:\\Users\\Maksi\\OneDrive\\Расходы на машину.xlsx");
//			InputStream is = new FileInputStream("y:\\OneDrive\\Расходы на машину.xlsx");
//			importer.doImport(is);
		/**
		String regexp = "^([0-9]+[,.]?[0-9]+).+(��������)$";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher("50.55 ������ ��������");
		while (matcher.find()) {
			System.out.println(matcher.group());
		}
		/**/
	}

}
