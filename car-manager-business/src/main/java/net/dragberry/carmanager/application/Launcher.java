package net.dragberry.carmanager.application;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
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
import net.dragberry.carmanager.transferobject.QueryListTO;
import net.dragberry.carmanager.transferobject.ResultList;
import net.dragberry.carmanager.transferobject.TransactionTO;
import net.dragberry.carmanager.ws.json.CurrencyExRate;

public class Launcher {

	public static void main(String[] args) throws Exception {
		
		/**/
			ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(BusinessConfig.class);
			TransactionService transactionService = context.getBean(TransactionService.class);
			ResultList<TransactionTO> list = transactionService.fetchList(new QueryListTO());
			list.getResult().forEach(tnx -> System.out.println(tnx.getTransactionKey() + " " + tnx.getDescription() + " " + (tnx.getFuel() != null ? tnx.getFuel().getQuantity() : null)));
			
			
//			DataImporter importer = context.getBean(DataImporter.class);
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
