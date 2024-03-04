package probes.service;

import java.util.HashMap;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.probes.dto.Range;
import telran.probes.dto.SensorEmails;
import telran.probes.dto.SensorUpdateData;
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailsProviderClientServiceImpl implements EmailProviderClientService{
final RestTemplate restTemplate;
final ServiceConfiguration serviceConfiguration;
	HashMap<Long, String[]> cache = new HashMap<>();
	@Bean
	Consumer<SensorEmails> emailsConsumer() {
	    return this::updateProcessing;
	}
	void updateProcessing(SensorEmails sensorEmails) {
		if(sensorEmails.mails() != null && cache.containsKey(sensorEmails.id())) {
			cache.put(sensorEmails.id(), sensorEmails.mails());
		}
	}
		@Override
		public String[] getMails(long sensorId) {
			
			String[] res;
			if(!cache.containsKey(sensorId)) {
				 res = serviceRequest(sensorId);
//				 if(res == null) {
//					 res = new SensorEmails(sensorId, defaultEmail).mails();
//				 }
				cache.put(sensorId, res);
 			}else {
 				res = cache.get(sensorId);
 			}
			
		    return res;
		}
	
	private String[] serviceRequest(long sensorId) {
		String[] sensorEmails = null;
		ResponseEntity<?> responseEntity ;
		try {
			responseEntity = restTemplate.exchange(getUrl(sensorId), HttpMethod.GET, null, String[].class);
			if (responseEntity.getStatusCode().is4xxClientError()) {
				throw new Exception(responseEntity.getBody().toString());
			}
			sensorEmails = (String[]) responseEntity.getBody();
			log.debug("emails value {}", sensorEmails);
		} catch (Exception e) {
			log.error("error at service request: {}", e.getMessage());
			sensorEmails = new SensorEmails(sensorId, defaultEmail).mails();
			log.warn("default emails value: {}", sensorEmails);
		} 
		return sensorEmails;
		
	}
	private String getUrl(long sensorId) {
		String url = String.format("http://%s:%d%s%d",
				serviceConfiguration.getHost(),
				serviceConfiguration.getPort(),
				serviceConfiguration.getPath(),
				sensorId);
		log.debug("url created is {}", url);
		return url;
	}


}
