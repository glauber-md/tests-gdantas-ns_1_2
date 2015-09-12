/**
 * 
 */
package com.gdantas.util;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Glauber M. Dantas (glauber.dantas@tvftecnologia.com.br) TVF Tecnologia
 *
 */
public class MessageUtil {

	private static final SimpleDateFormat defaultSdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public static String messageFromArray(Object ... items) {
		return StringUtils.join(items, ",");
	}
	
	public static String getFormattedTelephoneNumber(String ddd, String tel) {
		return MessageFormat.format("({0}) {1}", ddd, tel); 
	}
	
	public static String getFormattedDate(Date data) {
		return defaultSdf.format(data); 
	}
	
	public static Date getDateFromString(String sData) throws ParseException {
		return defaultSdf.parse(sData); 
	}
	
	public static Date getDateFromString(String sData, String sdfPattern) throws ParseException {
		SimpleDateFormat _sdf = new SimpleDateFormat();
		try {
			_sdf.applyPattern(sdfPattern);
		} catch (Exception e) {
			// Tries to use the default formatter
			_sdf = defaultSdf;
		}
		return _sdf.parse(sData); 
	}
	
}
