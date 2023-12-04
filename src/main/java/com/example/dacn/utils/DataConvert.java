package com.example.dacn.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
@Service
public class DataConvert {
    public static Date parseBirthday(Date birthday) {
        try {
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(timeZone);

        String formattedDate = dateFormat.format(birthday);
        Date parsedBirthday = dateFormat.parse(formattedDate);
        return parsedBirthday;


        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}
