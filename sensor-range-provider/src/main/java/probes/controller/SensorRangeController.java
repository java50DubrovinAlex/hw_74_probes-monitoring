package probes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import probes.service.SensorRangeService;
import telran.probes.UrlConstans;
import telran.probes.dto.*;
import static telran.probes.messages.ErrorMessages.MISSING_SENSOR_ID;//can I write like that
@RestController
@RequiredArgsConstructor
@Slf4j
public class SensorRangeController {
	final SensorRangeService sensorRangeService;
	@GetMapping(UrlConstans.SENSOR_RANGE + "{sensorId}")
	Range getSensorRange(@PathVariable @NotNull(message = MISSING_SENSOR_ID) long sensorId) {
		Range res = sensorRangeService.getSensorRange(sensorId);
		if(res == null) {
			log.warn("getSensorRange: no range for sensor with sensorID {}", sensorId);
		}else {
			log.trace("getSensorRange: range of sensor whit sensorId {} is {}", sensorId, res);
		}
				return res;
	}
}
