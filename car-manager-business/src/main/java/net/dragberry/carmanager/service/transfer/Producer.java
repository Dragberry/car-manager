package net.dragberry.carmanager.service.transfer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.dragberry.carmanager.dao.CustomerDao;
import net.dragberry.carmanager.transferobject.Record;

@Component
@Scope(scopeName = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Producer extends Thread {
	
	private String name;

	@Autowired
	private BlockingQueue<Record> queue;
	@Autowired
	private CustomerDao customerDao;
	
	public Producer() {
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void run() {
		for (int i = 0; i < 8000; i++) {
//			try {
//				queue.put(new Record());
				System.out.println(Thread.currentThread().getName()+ ": " + i + " " + name);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			customerDao.findOne(1L);
		}
	}

}
