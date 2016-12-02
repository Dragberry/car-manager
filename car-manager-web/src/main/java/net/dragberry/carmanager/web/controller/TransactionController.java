package net.dragberry.carmanager.web.controller;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.dragberry.carmanager.common.Currency;
import net.dragberry.carmanager.common.TransactionStatus;
import net.dragberry.carmanager.service.TransactionService;
import net.dragberry.carmanager.service.TransactionTypeService;
import net.dragberry.carmanager.service.transfer.DataImporter;
import net.dragberry.carmanager.to.IssueTO;
import net.dragberry.carmanager.to.ResultTO;
import net.dragberry.carmanager.to.TransactionQueryListTO;
import net.dragberry.carmanager.to.TransactionSummaryTO;
import net.dragberry.carmanager.to.TransactionTO;
import net.dragberry.carmanager.to.TransactionTypeTO;
import net.dragberry.carmanager.to.UploadTransactionResult;
import net.dragberry.carmanager.web.security.CMSecurityContext;

@Controller
@RequestMapping("/service/transaction")
public class TransactionController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private TransactionTypeService transactionTypeService;
	
	@Autowired
	private DataImporter dataImporter;
	
	@Autowired
    private MessageSource messageSource;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public List<TransactionTO> fetchTransactionList(@RequestParam Long selectedCar) {
		TransactionQueryListTO query = new TransactionQueryListTO();
		query.setCarOwnerKey(CMSecurityContext.getLoggedCustomerKey());
		query.setCarKey(selectedCar);
		query.setStatuses(Arrays.asList(TransactionStatus.ACTIVE));
		return transactionService.fetchList(query).getResult();
	}

	@RequestMapping(value ="/summary", method = RequestMethod.GET)
	@ResponseBody
	public TransactionSummaryTO fetchTransactionSummary(@RequestParam Long selectedCar, @RequestParam String displayCurrency) {
		TransactionQueryListTO query = new TransactionQueryListTO();
		query.setCarOwnerKey(CMSecurityContext.getLoggedCustomerKey());
		query.setCarKey(selectedCar);
		query.setDisplayCurrency(Currency.valueOf(displayCurrency));
		query.setStatuses(Arrays.asList(TransactionStatus.ACTIVE));
		return transactionService.fetchSummary(query).getObject();
	}
	
	@RequestMapping(value ="/type/list", method = RequestMethod.GET)
	@ResponseBody
	public List<TransactionTypeTO> fetchTransactionTypeList() {
		List<TransactionTypeTO> list = transactionTypeService.fetchTypeListForCustomer(CMSecurityContext.getLoggedCustomerKey()).getResult();
		Collections.sort(list, (type1, type2) -> {
			return type1.getTransactionTypeKey() == 1L ? -1 : type1.getTransactionTypeKey().compareTo(type2.getTransactionTypeKey());
		});
		return list;
	}
	
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	@ResponseBody
	public ResultTO<TransactionTO> submitTransaction(@RequestBody TransactionTO transactionTO) {
		transactionTO.setCustomerKey(CMSecurityContext.getLoggedCustomerKey());
		ResultTO<TransactionTO> tnx = transactionService.createTransaction(transactionTO);
		if (tnx.hasIssues()) {
			tnx.getIssues().forEach(issue -> {
				issue.setMessage(messageSource.getMessage(issue.getMsgCode(), issue.getParams(), request.getLocale()));
			});
		}
		request.getLocale();
		return tnx;
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public ResultTO<UploadTransactionResult> uploadFile(@RequestParam("file") MultipartFile file) {
		try {
			InputStream is = file.getInputStream();
			dataImporter.doImport(is);
		} catch (Exception e) {
			ResultTO<UploadTransactionResult> result = new ResultTO<>(null);
			IssueTO issue = new IssueTO();
			issue.setMessage("An error has occured on the server!");
			result.getIssues().add(new IssueTO());
			return result;
		}
		return new ResultTO<UploadTransactionResult>(null);
	}
	
}
