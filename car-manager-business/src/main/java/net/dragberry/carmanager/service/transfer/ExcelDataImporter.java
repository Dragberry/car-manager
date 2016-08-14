package net.dragberry.carmanager.service.transfer;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Excel data importer
 * 
 * @author Maksim Drahun
 *
 */
@Service("ExcelDataImporter")
public class ExcelDataImporter implements DataImporter {
	
	private static final Logger LOG = LogManager.getLogger(ExcelDataImporter.class);
	
	@Autowired
	private ApplicationContext appContext;
	
	@Override
	public void doImport(InputStream is) throws Exception {
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(availableProcessors);
		Producer producer = appContext.getBean(Producer.class);
		producer.setInputStream(is);
		executor.submit(producer);
		
		for (int i = 0; i < availableProcessors - 1; i++) {
			Consumer consumer = appContext.getBean(Consumer.class);
			executor.submit(consumer);
		}
		executor.shutdown();
		executor.awaitTermination(10, TimeUnit.MINUTES);
		System.out.println("Main thread has been finished!");
	}
}
