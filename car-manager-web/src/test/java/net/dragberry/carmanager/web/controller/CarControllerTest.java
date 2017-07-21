package net.dragberry.carmanager.web.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import net.dragberry.carmanager.service.CarService;
import net.dragberry.carmanager.to.CarTO;
import net.dragberry.carmanager.to.ResultList;
import net.dragberry.carmanager.web.security.CMSecurityContext;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(CMSecurityContext.class)
public class CarControllerTest {
	
	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),                        
            Charset.forName("utf8")                     
    );
	
	private static final String CAR_BRAND = "Mersedes";
	private static final String CAR_MODEL = "C180";
	private static final int CAR_KEY = 1000;
	private static final LocalDate CAR_PURCHASE_DATE = LocalDate.of(2014, 11, 7);
	private static final LocalDate CAR_SALE_DATE = LocalDate.of(2016, 11, 7);
	
	@Autowired
	@InjectMocks
    private CarController controller;
	@Mock
	private CarService carService;
	
	@Test
	public void fetchCarListTestOk() throws Exception {
		when(carService.fetchCarList(anyLong())).thenReturn(getCarList());
		
		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(get(CarController.LIST_URL)
				.param("customerKey", "1000"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].carKey").value(CAR_KEY))
			.andExpect(jsonPath("$[0].brand").value(CAR_BRAND))
			.andExpect(jsonPath("$[0].model").value(CAR_MODEL))
			.andExpect(jsonPath("$[0].purchaseDate").value(CAR_PURCHASE_DATE.format(DateTimeFormatter.ISO_DATE)))
			.andExpect(jsonPath("$[0].saleDate").value(CAR_SALE_DATE));
	}
	
	public static ResultList<CarTO> getCarList() {
		CarTO car = new CarTO();
		car.setCarKey((long) CAR_KEY);
		car.setBrand(CAR_BRAND);
		car.setModel(CAR_MODEL);
		car.setPurchaseDate(CAR_PURCHASE_DATE);
		car.setSaleDate(CAR_SALE_DATE);
		ResultList<CarTO> result = new ResultList<CarTO>();
		result.addItem(car);
		return result;
	}

}
