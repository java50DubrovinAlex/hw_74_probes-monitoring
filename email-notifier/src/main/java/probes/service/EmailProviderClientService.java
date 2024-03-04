package probes.service;

import telran.probes.dto.SensorEmails;

public interface EmailProviderClientService {
	String [] defaultEmail = {"email_default@gmail.com"};
	String[] getMails(long sensorId);
}
