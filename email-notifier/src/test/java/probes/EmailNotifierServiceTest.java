package probes;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.net.URI;

import telran.probes.dto.*;
import org.junit.jupiter.api.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.*;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.client.RestTemplate;

import jakarta.validation.constraints.NotNull;
import probes.service.EmailProviderClientService;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmailNotifierServiceTest {
	private static final long SENSOR_ID = 123;
	private static final String [] EMAILS = {"email1@gmail.com", "email2@gmail.com"};
	private static final SensorEmails SENSOR_EMAILS = new SensorEmails(SENSOR_ID, EMAILS);
	private static final String URL = "http://localhost:8282/emails/sensor/";
	private static final Short SENSOR_EMAILS_DEFAULT = null;
	private static final long SENSOR_ID_NOT_FOUND = 124;
	private static final String SENSOR_NOT_FOUND_MESSAGE = null;
	private static final long SENSOR_ID_UNAVAILABLE = 0;
	private static final Range SENSOR_EMAILS_UPDATED = null;
	@Autowired
	InputDestination producer;
	@Autowired
	EmailProviderClientService emailProviderClientService;
	@MockBean
	RestTemplate restTemplate;
	private String updateBindingName = "emasilsConsumer-in-0";


	@Test
	@Order(1)
	 void normalFlowNoCache() {
		ResponseEntity<SensorEmails> responseEntity = new ResponseEntity<>( SENSOR_EMAILS, HttpStatus.OK);
		when(restTemplate.exchange(getUrl(SENSOR_ID), HttpMethod.GET, null, SensorEmails.class))
		.thenReturn(responseEntity );
		assertEquals(EMAILS, emailProviderClientService.getMails(SENSOR_ID));
	}
	
	@SuppressWarnings("unchecked")
	@Order(2)
	void normalFlowWithCache() {
		when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), any(Class.class)))
		.thenAnswer(new Answer<ResponseEntity<?>>() {
			@Override
			public ResponseEntity<?> answer(InvocationOnMock invocation) throws Throwable {
				fail("method exchange should not be called");
				return null;
			}
		});
		assertEquals(SENSOR_EMAILS, emailProviderClientService.getMails(SENSOR_ID));
	}
	
	@Test
	@Order(3)
	void sensorNotFoundTest() {
		ResponseEntity<String> responseEntity = new ResponseEntity<>(SENSOR_NOT_FOUND_MESSAGE,HttpStatus.NOT_FOUND);
		when(restTemplate.exchange(getUrl(SENSOR_ID_NOT_FOUND), HttpMethod.GET, null, String.class))
		.thenReturn(responseEntity );
		assertEquals(SENSOR_EMAILS_DEFAULT, emailProviderClientService.getMails(SENSOR_ID_NOT_FOUND));
		
	}
	
	@Test
	@Order(4)
	void defaultRangeNotInCache() {
		ResponseEntity<SensorEmails> responseEntity = new ResponseEntity<>(SENSOR_EMAILS,HttpStatus.OK);
		when(restTemplate.exchange(getUrl(SENSOR_ID_NOT_FOUND), HttpMethod.GET, null, SensorEmails.class))
		.thenReturn(responseEntity );
		assertEquals(SENSOR_EMAILS, emailProviderClientService.getMails(SENSOR_ID_NOT_FOUND));
	}
	@SuppressWarnings("unchecked")
	@Test
	void remoteWebServiceAnavailable() {
		when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), any(Class.class)))
		.thenThrow(new Exception("Service is unavailable"));
		assertEquals(SENSOR_EMAILS_DEFAULT, emailProviderClientService.getMails(SENSOR_ID_UNAVAILABLE));
	}
	@Test
	void updateRangeSensorInMap() throws InterruptedException {
		
		producer.send
		(new GenericMessage<SensorUpdateData>(new SensorUpdateData(SENSOR_ID,
				SENSOR_EMAILS_UPDATED, null)), updateBindingName);
		Thread.sleep(100);
		assertEquals(SENSOR_EMAILS_UPDATED, emailProviderClientService.getMails(SENSOR_ID));
	}
	
private String getUrl(long sensorId) {
		
		return URL + sensorId;
	}

}
