package com.its.test.velocity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTools {

	public String format(String format, Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(date);
	}
}
