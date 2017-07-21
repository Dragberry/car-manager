package net.dragberry.carmanager.web.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.web.servlet.MockMvc;

import net.dragberry.carmanager.service.TransactionService;
import net.dragberry.carmanager.web.security.CMSecurityContext;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CMSecurityContext.class)
public class TransactionControllerTest {
	
	@Mock
	private TransactionService transactionService;
	
	@Test
	public void fetchTransactionListTest() throws Exception {
		PowerMockito.mockStatic(CMSecurityContext.class);
        BDDMockito.given(CMSecurityContext.getLoggedCustomerKey()).willReturn(1000L);
        verify(transactionService, times(1)).fetchList(BDDMockito.anyObject());
        
		TransactionController controller = new TransactionController();
		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(get(getFullURL(TransactionController.LIST_URL))
				.param("customerKey", "1000")
				.param("selectedCar", "1000"))
			.andExpect(status().isOk());
	}
	
	public static String getFullURL(String part) {
		return TransactionController.SERVICE_URL + part;
	}

}

