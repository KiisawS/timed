package tf56.timed.utils;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/***
 * @ClassName DateUtils
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/9 15:04
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
public class DateUtils {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYYMMDD = "yyyyMMdd";


    public static String formatDate(Date date) {
        return formatDate(date, YYYY_MM_DD_HH_MM_SS);
    }

    public static String formatDate(Date date, String pattern) {
        if(date == null || StringUtils.isEmpty(pattern)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date parseDate(String date) {
        return parseDate(date, YYYY_MM_DD_HH_MM_SS);
    }

    public static Date parseDate(String date, String pattern) {
        if(StringUtils.isBlank(date)) {
            return null;
        }
        try {
            SimpleDateFormat myFmt1=new SimpleDateFormat(pattern);
            return myFmt1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Date addDays(Date date,int days) {
        Calendar day = Calendar.getInstance();
        if(date != null) {
            day.setTime(date);
        }
        day.add(Calendar.DATE, days);
        return day.getTime();
    }
}
