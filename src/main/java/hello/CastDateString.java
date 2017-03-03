package hello;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class CastDateString {

    /**
     * Date format
     */
    public static final SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * Cast Date to String
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        if (null == date) return "";
        return df.format(date);
    }

    /**
     * Cast String to date
     * @param date
     * @return
     */
    public static Date stringToDate(String date) {
        try {
            return df.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

//    /**
//     * 将date精确到天（将小时，分钟，秒都置为0）
//     * @return
//     */
//    public static Date dateWithOutHours(Date date){
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
//        String dateString = formatter.format(date);
//        String newDateString = dateString + "000000";
//        return stringToDate(newDateString);
//    }
//
//    /**
//     * 返回几天前的日期
//     * e.g. days = 1, 返回昨天
//     * @param days
//     * @return
//     */
//    public static Date dateOfDaysBefore(int days){
//        Date date = new Date();
//        Calendar calendar = new GregorianCalendar();
//        calendar.setTime(date);
//        calendar.add(calendar.DATE, - days);
//        date = calendar.getTime();  //几天前
//        return date;
//    }



}
