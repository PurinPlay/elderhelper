package ru.vk.bot;

import java.sql.Date;

public class Utility {
    public static Date parseDate(String date) {
        String[] temp = date.split("\\.");
        Date out = new Date(Integer.parseInt(temp[2]), Integer.parseInt(temp[1]), Integer.parseInt(temp[0]));
        return out;
    }
}

