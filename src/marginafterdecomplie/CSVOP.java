/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marginafterdecomplie;

/**
 *
 * @author Huabing Li
 */
import java.io.BufferedWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import org.apache.commons.csv.CSVPrinter;

public class CSVOP {
    private static final String SAMPLE_CSV_FILE_PATH = "./users.csv";

    public static void main(String[] args) throws IOException {
        try (
            Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withHeader("name", "email", "phone", "country")
                    .withIgnoreHeaderCase()
                    .withTrim()
                   // .equals(1==1)
            
            );
        ) {
            for (CSVRecord csvRecord : csvParser.getRecords()) {
                // Accessing values by the names assigned to each column

                String name = csvRecord.get("Name");
                String email = csvRecord.get("Email");
                String phone = csvRecord.get("Phone");
                String country = csvRecord.get("Country");
                System.out.println("Record No - " + csvRecord.getRecordNumber());
                System.out.println("---------------");
                System.out.println("Name : " + name);
                System.out.println("Email : " + email);
                System.out.println("Phone : " + phone);
                System.out.println("Country : " + country);
                System.out.println("---------------\n\n");
            }
        }
    }
    
 
    private static final String Report_CSV_FILE = "./sample.csv";

    public static void CSVWriter(String[] ReportRecord) throws IOException {
        try (
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(Report_CSV_FILE));

            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("ID", "Name", "Designation", "Company"));
        ) {
            csvPrinter.printRecord("1", "Sundar Pichai ♥", "CEO", "Google");
            csvPrinter.printRecord("2", "Satya Nadella", "CEO", "Microsoft");
            csvPrinter.printRecord("3", "Tim cook", "CEO", "Apple");

            csvPrinter.printRecord(Arrays.asList("4", "Mark Zuckerberg", "CEO", "Facebook"));
           

            csvPrinter.flush();            
        }
    }
}
    
    
    
    
    
    

