package com.techelevator;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    private String fileName;

    public Log(String fileName) {
        this.fileName = fileName;
    }

    public void write(String logMessage) throws IOException {
        File logFile = new File(fileName);

            try(PrintWriter p = new PrintWriter((new FileWriter(logFile, true)))) {
                p.println(logMessage);
                p.flush();
            }

    }

    public static String insertDate() {

        DateFormat dateFormatting = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
        return dateFormatting.format(new Date());
    }
}
