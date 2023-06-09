package telran.monitoring;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import telran.monitoring.dto.DoctorDto;
import telran.monitoring.dto.PatientDto;
import telran.monitoring.dto.VisitDto;
import telran.monitoring.service.VisitsService;


@Component
public class RandomDbCreation {

	@Autowired
	VisitsService visitsService;
	static Logger LOG = LoggerFactory.getLogger(RandomDbCreation.class);
	@Value("${spring.jpa.hibernate.ddl-auto: create}")
	String ddlAutoProp;
	@Value("${app.visits.amount:20}")
	private int nVisits;
	@Value("${app.days.amount:35}")
	private int nDays;
	
	String patientNames[] = {"Abraham", "Sarah",
			"Itshak", "Rahel", "Asaf", "Yacob", "Rivka",
			"Yosef", "Benyanim", "Dan"};
	String doctorNames[] = {"Ruben", "Moshe",
			"Aron", "Yehashua", "David", "Salomon",
			"Nefertity", "Naftaly", "Natan", "Asher"};

	@PostConstruct
	void createDB() {
		if(ddlAutoProp.equals("create")) {
			addPatients();
			addDoctors();
			addVisits();
			LOG.info("*random db* new DB was created with visits: {}", nVisits);
		} else {
			LOG.warn("*random db* new db was not created");
		}
	}
	
	private void addVisits() {
		IntStream.range(0, nVisits).forEach(i -> addOneVisit());
		
	}

	private void addOneVisit() {
		int patientId = getRandomNumber(1, patientNames.length);
		String doctorId = "doctor" + getRandomNumber(1, doctorNames.length) + "@gmail.com";
		String visitDate = LocalDate.now().minusDays(getRandomNumber(0, nDays)).toString();	
		visitsService.addVisit(new VisitDto(patientId, doctorId, visitDate));
	}

	private void addDoctors() {
		IntStream.range(0, doctorNames.length)
		.forEach(i -> {
			visitsService.addDoctor(new DoctorDto("doctor" + (i + 1) + "@gmail.com", doctorNames[i]));
		});
		
	}

	private void addPatients() {
		IntStream.range(0, patientNames.length)
		.forEach(i -> {
			visitsService.addPatient(new PatientDto(i + 1, patientNames[i]));
		});
	}

	private int getRandomNumber(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
}
