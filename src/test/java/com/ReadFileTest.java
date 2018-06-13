package com;

import lombok.AllArgsConstructor;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alejandrosantamaria on 13/06/18.
 */
public class ReadFileTest {

    @Test
    public void readFile(){
        String filepath = "ethusd.csv";
        List<Rate> inputList = new ArrayList<>();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy");

        try {
            File file = new ClassPathResource("ethusd.csv").getFile();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            inputList = br.lines()
                    .map(line -> {
                        String[] tokens = line.split(",");
                        return new Rate(LocalDate.parse(tokens[0], dtf), Double.valueOf(tokens[1]));
                    })
                    .collect(Collectors.toList());

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        inputList.stream().forEach(rate -> {System.out.println(rate.localDate + "," + rate.rate);});
    }


    @AllArgsConstructor
    private class Rate{
        private LocalDate localDate;
        private Double rate;
    }
}
