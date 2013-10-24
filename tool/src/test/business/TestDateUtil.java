package test.business;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;

import business.DateUtil;

public class TestDateUtil {
	private DateUtil dateUtil = DateUtil.getInstance();
	@Test
	public void testDateFormat(){
		String fmt = "yyyy年M月dd日";
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1988);
		cal.set(Calendar.MONTH, 11);
		String str = sdf.format(cal.getTime());
		System.out.println(str);
	}
}
