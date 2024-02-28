package sensorRange;
import static sensorRange.TestDb.*;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import model.SensorRangeDoc;
import repo.SensorRangeRepo;
import service.SensorRangeService;
import telran.probes.dto.Range;
import telran.probes.dto.SensorRange;
@SpringBootTest
@ContextConfiguration(classes = { SensorRangeService.class, SensorRangeRepo.class })
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
		assertEquals(sensorRanges[0], sensorRangeService.getSensorRange(ID1));
	}
}
