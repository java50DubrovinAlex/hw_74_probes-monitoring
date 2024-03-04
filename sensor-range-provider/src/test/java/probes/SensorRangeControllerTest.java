package probes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static probes.TestDb.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import probes.controller.SensorRangeController;
import probes.service.SensorRangeService;


@WebMvcTest
class SensorRangeControllerTest {
	@Autowired
	MockMvc mockMvc;
	@MockBean
	SensorRangeService sensorRangeService;
	@Autowired
	ObjectMapper mapper;

	@Test
	void getSensorRangeTest() throws Exception {
		when(sensorRangeService.getSensorRange(ID1)).thenReturn(RANGE1);
		String jsonExpected = mapper.writeValueAsString(RANGE1);
		String actualJSON = mockMvc.perform(get("/range/sensor/1")).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString();
		assertEquals(jsonExpected, actualJSON);

	}

}
