package net.dragberry.carmanager.application;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.dragberry.carmanager.application.config.BusinessConfig;
import net.dragberry.carmanager.service.transfer.DataImporter;

public class Launcher {

	public static void main(String[] args) throws Exception {
		/**/
			ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(BusinessConfig.class);
			DataImporter importer = context.getBean(DataImporter.class);
			InputStream is = new FileInputStream("c:\\Users\\Maksi\\OneDrive\\–асходы на машину.xlsx");
			importer.doImport(is);
		/**
		String regexp = "^([0-9]+[,.]?[0-9]+).+(получено)$";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher("50.55 литров получено");
		while (matcher.find()) {
			System.out.println(matcher.group());
		}
		/**/
	}

}
