package com.fipsoftware.workbook;

import com.fipsoftware.workbook.converter.WorkBookConverter;
import com.fipsoftware.workbook.model.WorkBook;
import com.fipsoftware.workbook.model.WorkHistory;
import com.fipsoftware.workbook.validator.WorkBookValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@SpringBootApplication
@Slf4j
public class DemoApplication {
	@Value("#{file.path}")
	private static String path;

	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);
		WorkHistory workHistory = WorkHistory.builder()
				.companyName("fipsoft")
				.position("Java Junior")
				.salary(new BigDecimal(100000))
				.workFrom(new Date())
				.workUntil(new Date()).build();
		WorkHistory workHistory1 = WorkHistory.builder()
				.companyName("web-fontaine")
				.position("Senior Java")
				.salary(new BigDecimal(200000))
				.workFrom(new Date())
				.workUntil(new Date())
				.build();
		Collection<WorkHistory> workHistories = new ArrayList<>();
		workHistories.add(workHistory);
		workHistories.add(workHistory1);
		byte[] photo = {};
		WorkBook workBook = WorkBook.builder().SocialSecurity("social Security")
				.firstName("Arshak")
				.lastName("Tovmasyan")
				.dateOfBirth(new Date())
				.photo(photo)
				.workHistories(workHistories)
				.build();
		/*System.out.println(WorkBookConverter.convertWorkbookToXML(workBook));
		String xml = WorkBookConverter.convertWorkbookToXML(workBook);
		WorkBook workBook1 = WorkBookConverter.convertXMLToWorkbook(xml);
		System.out.println(workBook1.toString());

		String xmlPath = "/Users/arshaktovmasyan/IdeaProjects/Workbook/workbook.xml";
		String xsdPath = "/Users/arshaktovmasyan/IdeaProjects/Workbook/workbook.xsd";
		WorkBookValidator workBookValidator = new WorkBookValidator();
		workBookValidator.validate(xmlPath, xsdPath);*/
		WorkBookConverter workBookConverter = new WorkBookConverter();
		workBookConverter.getBiriqXml("/Users/arshaktovmasyan/IdeaProjects/Workbook/XMLFiles");
	}
}
