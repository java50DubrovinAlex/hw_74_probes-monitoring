package probes;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static probes.TestDb.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import probes.model.SensorRangeDoc;
import probes.repo.SensorRangeRepo;
import probes.service.SensorRangeService;
import telran.probes.dto.Range;
import telran.probes.dto.SensorRange;
import telran.probes.exceptions.SensorNotFoundException;
@SpringBootTest
class SensorRangeServiceTests {
@Autowired
SensorRangeService sensorRangeService;
@Autowired
SensorRangeRepo sensorRangeRepo;
@Autowired
TestDb testDb;

	@BeforeEach
	void setUp()  {
		testDb.creatDb();
		
	}

	@Test
	void getSensorRangeTest() {
		assertEquals(RANGE1, sensorRangeService.getSensorRange(ID1));
		assertThrows(SensorNotFoundException.class, () -> sensorRangeService.getSensorRange(ID_NOT_EXIST));
	}
}
