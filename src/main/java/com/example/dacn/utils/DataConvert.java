package com.example.dacn.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DataConvert {
    public Date parseBirthday(Date birthday) {
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(timeZone);

        String formattedDate = dateFormat.format(birthday);
        Date parsedBirthday;
        try {
            parsedBirthday = dateFormat.parse(formattedDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return parsedBirthday;
    }
}
