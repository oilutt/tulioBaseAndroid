package oilutt.baseproject.utils;

import android.location.Location;
import android.text.TextUtils;

import java.math.BigDecimal;

import java.text.NumberFormat;
import java.util.Locale;

public final class NumberFormatUtil {

    /**
     * Formats the Double passed as parameter replacing "." with ","
     *
     * @param value
     * @return
     */
    public static String format(Double value) {
        return format(value.doubleValue(), ",");
    }

    /**
     * Formats the double passed as parameter replacing "." with ","
     *
     * @param value
     * @return
     */
    public static String format(double value) {
        return format(value, ",");
    }

    /**
     * Formats the Double passed as parameter replacing "." with decimalSeparator passed as parameter
     *
     * @param value
     * @param decimalSeparator
     * @return
     */
    public static String format(Double value, String decimalSeparator) {
        return format(value.doubleValue(), decimalSeparator);
    }

    /**
     * Formats the double passed as parameter replacing "." with decimalSeparator passed as parameter
     *
     * @param value
     * @param decimalSeparator
     * @return
     */
    public static String format(double value, String decimalSeparator) {
        String doubleStr = String.valueOf(value);
        return doubleStr.replaceAll("\\.", decimalSeparator);
    }

    /**
     * Receives string using "." or "," as its decimal separator and returns its Double
     *
     * @param valueStr
     * @return
     */
    public static Double toDouble(String valueStr) {
        valueStr = valueStr.replaceAll(",", "\\.");
        return toDouble(valueStr, "\\.");
    }

    /**
     * Receives string using a custom decimal separator and returns its Double
     *
     * @param valueStr
     * @param decimalSeparator
     * @return
     */
    public static Double toDouble(String valueStr, String decimalSeparator) {
        final String valueOf = valueStr.replaceAll(decimalSeparator, "\\.");
        return !TextUtils.isEmpty(valueOf) ? Double.parseDouble(valueOf) : null;
    }

    /**
     * Return the amount of decimalPlaces inside value passed as parameter
     *
     * @param valueStr
     * @return
     */
    public static int getDecimalPlaces(String valueStr) {
        valueStr = valueStr.replace(".", ",");
        return getDecimalPlaces(valueStr, ",");
    }

    /**
     * Return the amount of decimalPlaces inside value passed as parameter
     *
     * @param valueStr
     * @param decimalSeparator
     * @return
     */
    private static int getDecimalPlaces(String valueStr, String decimalSeparator) {
        String[] parts = valueStr.split(decimalSeparator);
        if (parts.length < 2) {
            return 0;
        }
        String decimalPart = parts[1];
        return decimalPart.length();
    }

    /**
     * Return string currency value
     *
     * @param value
     * @param locale
     * @return
     */
    public static String formatCurrency(double value, Locale locale) {
        return NumberFormat.getCurrencyInstance(locale).format(value);
    }

    /**
     * Return string currency value of locale default
     *
     * @param value
     * @return
     */
    public static String formatCurrency(double value) {
        return NumberFormat.getCurrencyInstance().format(value);
    }
    /**
     * Truncate double value
     * @param value
     * @param precision
     * @return
     */
    public static double truncate(double value, int precision){
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(precision, BigDecimal.ROUND_DOWN);
        return bd.doubleValue();
    }

    /**
     * Truncate coordinates values
     * @param location
     * @return
     */
    public static void truncate(Location location) {
        location.setLatitude(truncate(location.getLatitude(), 9));
        location.setLongitude(truncate(location.getLongitude(), 9));
    }

    /**
     * Format a string double with 2 numbers in the integer part
     * @param string
     * @return String formatString
     */
    public static String formatStringDouble(String string){
        String formatString = string;
        String[] aux = string.split("\\.");
        if(aux.length > 1 && Character.isDigit(aux[0].charAt(0)) && aux[0].length() == 1){
            formatString = "0" + string;
        }
        return formatString;
    }

    /**
     * Format a string integer with 3 numbers
     * @param string
     * @return String formatString
     */
    public static String formatStringInteger(String string){
        String formatString = string;
        while(formatString.length() != 3){
            formatString = "0" + string;
        }
        return formatString;
    }
}
