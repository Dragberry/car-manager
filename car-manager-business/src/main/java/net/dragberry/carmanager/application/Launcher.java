package net.dragberry.carmanager.application;

import java.io.FileInputStream;
import java.io.InputStream;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.dragberry.carmanager.application.config.BusinessConfig;
import net.dragberry.carmanager.service.transfer.DataImporter;

public class Launcher {

	public static void main(String[] args) throws Exception {
		try(ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(BusinessConfig.class)) {
			DataImporter importer = context.getBean(DataImporter.class);
			InputStream is = new FileInputStream("c:\\Users\\Maksi\\OneDrive\\Расходы на машину.xlsx");
			importer.doImport(is);
		}
	}

}
