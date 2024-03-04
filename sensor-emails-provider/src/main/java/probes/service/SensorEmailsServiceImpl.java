package probes.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import probes.model.SensorEmailsDoc;
import probes.repo.SensorEmailsRepo;
import telran.probes.exceptions.SensorNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class SensorEmailsServiceImpl implements SensorEmailsService{
	final SensorEmailsRepo sensorEmailsRepo;
	@Override
	public String[] getSensorEmails(long sensorID) {
		SensorEmailsDoc sensorEmailsDoc = sensorEmailsRepo.findById(null)
				.orElseThrow(() -> new SensorNotFoundException());
		String[]res = sensorEmailsDoc.build().mails();
		log.debug("found sensor emails {}", res.toString());
		return res;
	}

}
