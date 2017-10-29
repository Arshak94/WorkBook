package com.fipsoftware.workbook;

import com.fipsoftware.workbook.DBConnection.DBConnectImpl;
import com.fipsoftware.workbook.Threads.ProcessFile;
import com.fipsoftware.workbook.config.AppConfig;
import com.fipsoftware.workbook.model.WorkBook;
import com.fipsoftware.workbook.model.WorkHistory;
import com.fipsoftware.workbook.service.converter.WorkBookConverter;
import com.fipsoftware.workbook.service.converter.WorkBookConverterImpl;
import com.fipsoftware.workbook.service.validator.WorkBookValidator;
import com.fipsoftware.workbook.service.validator.WorkBookValidatorImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@Slf4j
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

        WorkBookValidator workBookValidator = new WorkBookValidatorImpl();

        WorkBookConverter workBookConverter = new WorkBookConverterImpl(workBookValidator);

        AppConfig appConfig = new AppConfig();

        WorkHistory workHistory1 = WorkHistory.builder()
                .id(1)
                .companyName("FipSoftware")
                .position("Java Junior")
                .salary(new BigDecimal(100000))
                .workFrom(new Date())
                .workUntil(new Date())
                .build();
        WorkHistory workHistory2 = WorkHistory.builder()
                .id(2)
                .companyName("FipSoftware")
                .position("Java Developer")
                .salary(new BigDecimal(100000))
                .workFrom(new Date())
                .workUntil(new Date())
                .build();
        ArrayList<WorkHistory> workHistories = new ArrayList<>();
        workHistories.add(workHistory1);
        workHistories.add(workHistory2);
        WorkHistory workHistory3 = WorkHistory.builder()
                .id(3)
                .companyName("xtech")
                .position("Java Developer")
                .salary(new BigDecimal(100000))
                .workFrom(new Date())
                .workUntil(new Date())
                .build();
        WorkHistory workHistory4 = WorkHistory.builder()
                .id(4)
                .companyName("sfl")
                .position("Java Developer")
                .salary(new BigDecimal(100000))
                .workFrom(new Date())
                .workUntil(new Date())
                .build();
        ArrayList<WorkHistory> workHistories1 = new ArrayList<>();
        workHistories1.add(workHistory3);
        workHistories1.add(workHistory4);


//        byte[] arr = workBookConverter
//                .convertPhotoToByteArray("/Users/arshaktovmasyan/IdeaProjects/Workbook/XMLFiles/photo.png");
        byte[] array = {1,2,3,4,5};
        WorkBook workBook = WorkBook.builder()
                .id(1)
                .SocialSecurity("Socialsecurity")
                .firstName("Arshak")
                .lastName("Tovmasyan")
                .dateOfBirth(new Date())
                .photo(array)
                .workHistory(workHistories)
                .build();
        WorkBook workBook1 = WorkBook.builder()
                .id(2)
                .SocialSecurity("SocialSecurity1")
                .firstName("Arshak1")
                .lastName("Tovmasyan1")
                .dateOfBirth(new Date())
                .photo(array)
                .workHistory(workHistories1)
                .build();
        DBConnectImpl dbConnect = new DBConnectImpl();


        Map<String, String> config = appConfig.getXMLFolderPath();

        String folderPath = config.get("folder");

        File folder = new File(folderPath);

        ArrayList<Thread> threads = new ArrayList<Thread>();

        for (File file: folder.listFiles()) {
            Thread thread = new Thread(new ProcessFile(file.getAbsolutePath(), workBookConverter, dbConnect));
            thread.start();

            threads.add(thread);
        }


        for(Thread thread: threads)
        {
            try {
                thread.join();
            } catch(Exception e) {}
        }
        System.out.println(dbConnect.findAll().toString());

//        System.out.println(workBookConverter.convertWorkbookToXML(workHistory1, "test"));
//       // System.out.println(workBookConverter.convertWorkbookToXML(workBook, "test"));
//        workBookConverter.convertWorkbookToJSON(workHistory1, "workhistoryjson");
//        List<WorkBook> workBooks = new ArrayList<>();
//        workBooks = workBookConverter.convertJSONToWorkBook(FOLDER_PATH);
//        for (WorkBook work:
//             workBooks) {
//            System.out.println(work.toString());
//        }


    }
}
