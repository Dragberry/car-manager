package net.dragberry.carmanager.service.transfer;

import java.io.InputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import net.dragberry.carmanager.repository.CarRepo;
import net.dragberry.carmanager.repository.CustomerRepo;
import net.dragberry.carmanager.repository.FuelRepo;
import net.dragberry.carmanager.repository.TransactionRepo;
import net.dragberry.carmanager.repository.TransactionTypeRepo;
import net.dragberry.carmanager.transferobject.Record;

/**
 * Excel data importer
 * 
 * @author Maksim Drahun
 *
 */
@Service("ExcelDataImporter")
public class ExcelDataImporter implements DataImporter {
	
	private static final Logger LOG = LogManager.getLogger(ExcelDataImporter.class);
	
	private static final int QUEUE_LIMIT = 20;
	
	@Autowired
	private ApplicationContext appContext;
	@Autowired
	private CarRepo carRepo;
	@Autowired
	private TransactionTypeRepo transactionTypeRepo;
	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private TransactionRepo transactionRepo;
	@Autowired
	private FuelRepo fuelRepo;
	
	private DataImportContext context;
	
	@Override
	public void doImport(InputStream is) throws Exception {
		context = new DataImportContext(carRepo, transactionTypeRepo, customerRepo, transactionRepo, fuelRepo, 1L, 3L);
		BlockingQueue<Record> recordQueue = new ArrayBlockingQueue<>(QUEUE_LIMIT);
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(availableProcessors);
		Reader reader = new Reader(context, is, recordQueue);
		executor.submit(reader);
		
		for (int i = 0; i < availableProcessors; i++) {
			Processor processor = appContext.getBean(Processor.class);
			processor.setQueue(recordQueue);
			executor.submit(processor);
		}
	}
}
