package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;
import telran.probes.dto.Range;
import telran.probes.dto.SensorRange;

@Document(collection = "sensor-range")
@Getter
@ToString
@NoArgsConstructor
public class SensorRangeDoc {
	@Id
    long sensorId;
    Range range;
 public SensorRangeDoc(SensorRange sensorRange)   {
	 sensorId = sensorRange.id();
	 range = sensorRange.range();
	 
 }
 public SensorRange build() {
	 return new SensorRange(sensorId, range);
 }
}