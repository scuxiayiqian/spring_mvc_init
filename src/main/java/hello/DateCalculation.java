package hello;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by zhonghaojian on 15/8/24.
 */
public class DateCalculation {

    /**
     * 将date精确到天（将小时，分钟，秒都置为0）
     * @return
     */
    public static Date dateWithOutHours(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(date);
        String newDateString = dateString + "000000";
        return CastDateString.stringToDate(newDateString);
    }

    /**
     * 返回几天前的日期
     * e.g. days = 1, 返回昨天
     * @param days
     * @return
     */
    public static Date dateOfDaysBefore(int days){
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, - days);
        date = calendar.getTime();  //几天前
        return date;
    }
}
