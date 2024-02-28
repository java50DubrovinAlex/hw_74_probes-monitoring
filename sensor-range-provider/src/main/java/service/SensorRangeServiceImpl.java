package service;


import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.SensorRangeDoc;
import repo.SensorRangeRepo;
import telran.probes.dto.Range;
import telran.probes.exceptions.SensorNotFoundException;
@Service
@RequiredArgsConstructor
@Slf4j
public class SensorRangeServiceImpl implements SensorRangeService {
final SensorRangeRepo sensorRangeRepo;
	@Override
	public Range getSensorRange(long sensorId) {
		SensorRangeDoc sensorRangeDoc = sensorRangeRepo
				.findById(sensorId).orElseThrow(() -> new  SensorNotFoundException());
		Range res = sensorRangeDoc.getRange();
		log.debug("found sensor range{}", res);
		return res;
	}

}
