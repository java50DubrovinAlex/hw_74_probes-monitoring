package probes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import probes.service.SensorEmailsService;
import telran.probes.UrlConstans;
import static telran.probes.messages.ErrorMessages.MISSING_SENSOR_ID;
@RestController
@RequiredArgsConstructor
@Slf4j
public class SensorEmailsController {
final SensorEmailsService sensorEmeilsService;
@GetMapping(UrlConstans.SENSOR_EMAILS + "{sensorId}")
String[]getSensorEmails(@PathVariable @NotNull(message = MISSING_SENSOR_ID) long sensorId){
	String[]res = sensorEmeilsService.getSensorEmails(sensorId);
	if(res==null) {
		log.warn("getSensorEmails: no emails for sensor with sensorID {}", sensorId);
	}else {
		log.trace("getSensorEmails: emails fo sensor with id {} are {}", sensorId, res);
	}
	return res;
	
}

}
