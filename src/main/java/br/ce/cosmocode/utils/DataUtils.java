package br.ce.cosmocode.utils;
/**
 * @author orlando-dev
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtils {
	
	public static String obterDataFormatada(Date data) {
		DateFormat format = new SimpleDateFormat("dd/MM/YYYY");
		return format.format(data);
	}
	
}
