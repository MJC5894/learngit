package cn.sunline.tiny.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeABS {
	 public static double timeABS(Date date,String str2) throws ParseException {
		   	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        Calendar bef = Calendar.getInstance();
	        Calendar aft = Calendar.getInstance();
	        String string = sdf.format(date).toString();
	        bef.setTime(sdf.parse(string));
	        aft.setTime(sdf.parse(str2));
	        int surplus = aft.get(Calendar.DATE) - bef.get(Calendar.DATE);
	        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
	        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
	        return (Math.abs(month + result) + surplus);
	   }
}
