package net.dragberry.carmanager.service.transfer;

import java.io.InputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

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
	private TaskExecutor taskExecutor;
	
	@Override
	public void doImport(InputStream is) throws Exception {
		BlockingQueue<Record> recordQueue = new ArrayBlockingQueue<>(500);
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		
		Reader reader = appContext.getBean(Reader.class);
		reader.setTransactionQueue(recordQueue);
		reader.setIs(is);
		taskExecutor.execute(reader);
		
		Thread.sleep(5000);
		
		for (int i = 0; i < availableProcessors / 4; i++) {
			Processor processor = appContext.getBean(Processor.class);
			processor.setQueue(recordQueue);
			taskExecutor.execute(processor);
		}
	}
}
