package business;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {// 判断2个日期之间的差距是否大于多少年
	// 已第一个日期为标准，判断第二个日期的年龄是否大于多少岁
	private DateUtil() {
	}

	private static DateUtil dateUtil = new DateUtil();

	public static DateUtil getInstance() {
		return dateUtil;
	}

	/**
	 * 判断compareDate是否比basicDate大于age岁
	 * 
	 * @param basicDate
	 *            基准日期
	 * @param compareDate
	 *            要判断的日期
	 * @param age
	 * @return 如果小于age岁不返回任何结果，如果大于age会，返回提示信息字符串
	 */
	public String judgeAgeMsg(Date basicDate, Date compareDate, int age) {
		Calendar basicCal = Calendar.getInstance();
		basicCal.setTime(basicDate);
		basicCal.add(Calendar.YEAR, -age);
		Calendar compareCal = Calendar.getInstance();
		compareCal.setTime(compareDate);
		if (basicCal.compareTo(compareCal) > 0) {
			return getjudgeAgeMsg(basicDate, compareDate, age);
		}
		return "";
	}

	private String getjudgeAgeMsg(Date basicDate, Date compareDate, int age) {
		String fmt = "yyyy年M月dd日";
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		StringBuffer sb = new StringBuffer();
		sb.append("您的出身日期为");
		sb.append(sdf.format(compareDate.getTime()));
		sb.append("；以申报开始时间");
		sb.append(sdf.format(basicDate.getTime()));
		sb.append(" 为准，您的年龄大于");
		sb.append(age);
		sb.append("岁。");
		return sb.toString();
	}

}
