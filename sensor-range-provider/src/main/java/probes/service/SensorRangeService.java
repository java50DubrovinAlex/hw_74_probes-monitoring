package probes.service;

import telran.probes.dto.Range;

public interface SensorRangeService {
	Range getSensorRange(long sensorId);
}
