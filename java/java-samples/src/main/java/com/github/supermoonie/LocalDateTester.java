package com.github.supermoonie;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.util.Calendar;
import java.util.Date;

/**
 * @author supermoonie
 * @since 2020/7/1
 */
public class LocalDateTester {

    public static void main(String[] args) {
        System.out.println(ZoneId.systemDefault());
        LocalDate localDate = LocalDate.now(ZoneId.of("Asia/Shanghai"));
        LocalDateTime localDateTime = localDate.atStartOfDay();
        long timestamp = localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println("-------------------");
        System.out.println(timestamp);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp)));
        System.out.println("-------------------");
        Date date = truncate(new Date());
        System.out.println(date.getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("-------------------");
        System.out.println(formatter.format(localDate.atStartOfDay()));
        System.out.println("-------------------");
        LocalTime localTime = LocalTime.now();
        System.out.println(localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        System.out.println(localTime.toSecondOfDay());
        System.out.println("---------------------");
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.plusDays(-1);
        System.out.println(today.format(DateTimeFormatter.ISO_DATE));
        System.out.println(yesterday.format(DateTimeFormatter.ISO_DATE));
        System.out.println("---------------------");
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(1590940800000L)));
        System.out.println("---------------------");
        LocalDate thisMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate preMonth = thisMonth.plusMonths(-1);
        System.out.println(thisMonth.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.println(preMonth.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        Date start = new Date(thisMonth.atStartOfDay().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        Date end = new Date(preMonth.atStartOfDay().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(start));
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(end));
    }

    private static Date truncate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
