package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class DateUtil {

	public String getDataInicio() {
		Date data = new Date();
		GregorianCalendar dataCal = new GregorianCalendar();
		dataCal.setTime(data);
		int mes = dataCal.get(Calendar.MONTH);
		int ano = dataCal.get(Calendar.YEAR);

		return ano + "-" + getMes(mes) + "-" + "01";
	}

	public String getDataFim() {
		Date data = new Date();
		GregorianCalendar dataCal = new GregorianCalendar();
		dataCal.setTime(data);
		int mes = dataCal.get(Calendar.MONTH);
		int ano = dataCal.get(Calendar.YEAR);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, mes);
		int ultimoDia = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		return ano + "-" + getMes(mes) + "-" + ultimoDia;
	}

	public String getMes(int mes) {
		if (mes == 0) {
			return "01";
		} else if (mes == 1) {
			return "02";
		} else if (mes == 2) {
			return "03";
		} else if (mes == 3) {
			return "04";
		} else if (mes == 4) {
			return "05";
		} else if (mes == 5) {
			return "06";
		} else if (mes == 6) {
			return "07";
		} else if (mes == 7) {
			return "08";
		} else if (mes == 8) {
			return "09";
		} else if (mes == 9) {
			return "10";
		} else if (mes == 10) {
			return "11";
		} else {
			return "12";
		}
	}

	public static boolean getMaiorData(String data1, String data2) throws Exception {

		Date date1 = getDateString(formatDate(data1));
		Date date2 = getDateString(data2);

		if (date1.after(date2)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean verificaMaiorData(String data1, String data2) throws Exception {

		Date date1 = getDateString(formatDate(data1));
		Date date2 = getDateString(formatDate(data2));

		if (date2.after(date1)) {
			return true;
		} else {
			return false;
		}
	}

	public static Date getDateString(String data) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = format.parse(data);
		return date;
	}

	public static String formatDate(String data) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date date = format.parse(data);
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
		String retorno = dt1.format(date);

		return retorno;
	}

	public static boolean isValidDate(String data) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		df.setLenient(false);
		try {
			df.parse(data);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static void mascaraData(KeyEvent event, TextField textfield) {
		if ("0123456789".contains(event.getCharacter()) == false) {
			event.consume();
		}

		if (event.getCharacter().trim().length() == 0) { // apagando

			if (textfield.getText().length() == 3) {
				textfield.setText(textfield.getText().substring(0, 2));
				textfield.positionCaret(textfield.getText().length());
			}
			if (textfield.getText().length() == 6) {
				textfield.setText(textfield.getText().substring(0, 5));
				textfield.positionCaret(textfield.getText().length());
			}

		} else { // escrevendo

			if (textfield.getText().length() == 10)
				event.consume();

			if (textfield.getText().length() == 2) {
				textfield.setText(textfield.getText() + "/");
				textfield.positionCaret(textfield.getText().length());
			}
			if (textfield.getText().length() == 5) {
				textfield.setText(textfield.getText() + "/");
				textfield.positionCaret(textfield.getText().length());
			}

		}
	}
}