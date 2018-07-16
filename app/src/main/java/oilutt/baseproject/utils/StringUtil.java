package oilutt.baseproject.utils;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

/**
 * Created by rafael.tonholo on 25/11/2016.
 */
public final class StringUtil {
    private static String sLineSeparator = null;

    private StringUtil() {
        super();
    }

    public static boolean isOneMoreWord(@NonNull String str){
        String[] aux = str.split(" ");
        return aux.length >= 2;
    }

    /**
     * Gets the initials from a {@link String}. Max 2 initials (first and last).
     *
     * @param str {@link String} that will take the initials
     * @return The Initials.
     */
    public static String getInitials(@NonNull String str) {
        String initials = "";
        if (str == null) {
            return initials;
        }

        final String[] splitTitulo = str.split(" ");

        // Finds the first valid string to be the initial
        int initialPosition = 0;
        for (int i = 0; i < splitTitulo.length; ++i) {
            if (!splitTitulo[i].isEmpty()) {
                initials += splitTitulo[i].charAt(0);
                initialPosition = i;
                break;
            }
        }

        // Finds the last valid string to be the second initial
        for (int i = splitTitulo.length - 1; !initials.isEmpty() && i > initialPosition; --i) {
            if (!splitTitulo[i].isEmpty()) {
                initials += splitTitulo[i].charAt(0);
                break;
            }
        }

        return initials;
    }

    /**
     * Trims a String. If its null, returns null, avoiding {@link NullPointerException}
     *
     * @param string String to trim
     * @return Trimmed String or null
     */
    public static String trim(String string) {
        return string == null ? null : string.trim();
    }

    /**
     * Calculate the parameters quantity to put in a Sql Prepared Statement.
     *
     * @param i Number of columns
     * @return Parameter {@link String} to creation of Sql Prepared Statement.
     */
    public static String params(int i) {
        String params = "?";
        for (int p = 1; p < i; p++) {
            params += ",?";
        }
        return params;
    }

    /**
     * Returns true if the string is null or 0-length.
     *
     * @param string the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(@Nullable String string) {
        return string == null || string.isEmpty();
    }

    /**
     * Returns true if the Editable is null or 0-length.
     *
     * @param text the Editable to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(@Nullable Editable text) {
        return text == null || isEmpty(text.toString());
    }

    /**
     * Returns an empty string if the passed as parameter is null
     *
     * @param string The desired string
     * @return an empty string if the passed as parameter is null
     */
    public static String safe(@Nullable String string) {
        return string == null ? "" : string;
    }

    /**
     * Check the index of any character passed as {@code search} parameter.
     *
     * @param text   The text
     * @param search the characters to search
     * @return the index of the very first character encountered
     */
    public static int indexOfAny(String text, char... search) {
        for (char c : search) {
            final int indexOf = text.indexOf(c);
            if (indexOf > -1) {
                return indexOf;
            }
        }

        return -1;
    }

    /**
     * Replace special chars for normal chars.
     *
     * @param text The desired text to replace special characters
     * @return the new text
     */
    public static String replaceSpecialChars(String text) {
        final char[] special = new char[]{
                'ç', 'Ç', 'á', 'é', 'í', 'ó', 'ú', 'ý', 'Á', 'É', 'Í', 'Ó', 'Ú', 'Ý', 'à', 'è',
                'ì', 'ò', 'ù', 'À', 'È', 'Ì', 'Ò', 'Ù', 'ã', 'õ', 'ñ', 'ä', 'ë', 'ï', 'ö', 'ü',
                'ÿ', 'Ä', 'Ë', 'Ï', 'Ö', 'Ü', 'Ã', 'Õ', 'Ñ', 'â', 'ê', 'î', 'ô', 'û', 'Â', 'Ê',
                'Î', 'Ô', 'Û'
        };

        int indexOfAny = indexOfAny(text, special);
        if (indexOfAny < 0) {
            return text;
        }

        return text.replace("ç", "c").replace("Ç", "C").replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o")
                .replace("ú", "u").replace("ý", "y").replace("Á", "A").replace("É", "E").replace("Í", "I").replace("Ó", "O")
                .replace("Ú", "U").replace("Ý", "Y").replace("à", "a").replace("è", "e").replace("ì", "i").replace("ò", "o")
                .replace("ù", "u").replace("À", "A").replace("È", "E").replace("Ì", "I").replace("Ò", "O").replace("Ù", "U")
                .replace("ã", "a").replace("õ", "o").replace("ñ", "n").replace("ä", "a").replace("ë", "e").replace("ï", "i")
                .replace("ö", "o").replace("ü", "u").replace("ÿ", "y").replace("Ä", "A").replace("Ë", "E").replace("Ï", "I")
                .replace("Ö", "O").replace("Ü", "U").replace("Ã", "A").replace("Õ", "O").replace("Ñ", "N").replace("â", "a")
                .replace("ê", "e").replace("î", "i").replace("ô", "o").replace("û", "u").replace("Â", "A").replace("Ê", "E")
                .replace("Î", "I").replace("Ô", "O").replace("Û", "U");
    }

    /**
     * Returns displayable styled text from the provided HTML string. Any &lt;img&gt; tags in the
     * HTML will display as a generic replacement image which your program can then go through and
     * replace with real images.
     * <p>
     * <p>This uses TagSoup to handle real HTML, including all of the brokenness found in the wild.
     */
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }

    /**
     * <a href="https://android.googlesource.com/platform/libcore.git/+/51b1b6997fd3f980076b8081f7f1165ccc2a4008/ojluni/src/main/java/java/beans/Introspector.java">
     * Documentation
     * </a>
     *
     * @param name
     * @return
     */
    public static String capitalize(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        char chars[] = name.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    /**
     * Utility method to take a string and convert it to normal Java variable
     * name capitalization.  This normally means converting the first
     * character from upper case to lower case, but in the (unusual) special
     * case when there is more than one character and both the first and
     * second characters are upper case, we leave it alone.
     * <p>
     * Thus "FooBah" becomes "fooBah" and "X" becomes "x", but "URL" stays
     * as "URL".
     *
     * @param value The string to be decapitalized.
     * @return The decapitalized version of the string.
     */
    public static String decapitalize(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        if (value.length() > 1 && Character.isUpperCase(value.charAt(1)) &&
                Character.isUpperCase(value.charAt(0))) {
            return value;
        }
        char chars[] = value.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    public static String format(String string, Object... params) {
        for (int i = 0; i < params.length; i++) {
            string = string.replace("{" + i + "}", "%" + (i + 1) + "$s");
        }
        string = string.replaceAll("<br>", "\n");
        return String.format(string, params);
    }

    /**
     * Returns true if and only if this string contains the specified
     * sequence of char values ignoring the case of both.
     *
     * @param source  The string source
     * @param looking the looking string
     * @return {@code true} if contains
     */
    public static boolean containsIgnoreCase(String source, String looking) {
        return source.toLowerCase().contains(looking.toLowerCase());
    }

    /**
     * Returns the index within this string of the first occurrence of the
     * specified substring ignoring case.
     * <p>
     * <p>The returned index is the smallest value <i>k</i> for which:
     * <blockquote><pre>
     * this.startsWith(str, <i>k</i>)
     * </pre></blockquote>
     * If no such value of <i>k</i> exists, then {@code -1} is returned.
     *
     * @param source  the string used to search.
     * @param looking the substring to search for.
     * @return the index of the first occurrence of the specified substring,
     * or {@code -1} if there is no such occurrence.
     */
    public static int indexOfIgnoreCase(String source, String looking) {
        return source.toLowerCase().indexOf(looking.toLowerCase());
    }

    /**
     * Returns a string containing the tokens joined by delimiters.
     *
     * @param tokens an array objects to be joined. Strings will be formed from
     *               the objects by calling object.toString().
     */
    public static String join(String delimiter, Object[] tokens) {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (Object token : tokens) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(delimiter);
            }
            sb.append(token);
        }
        return sb.toString();
    }

    /**
     * Returns a string containing the tokens joined by delimiters.
     *
     * @param tokens an array objects to be joined. Strings will be formed from
     *               the objects by calling object.toString().
     */
    public static String join(String delimiter, Iterable tokens) {
        StringBuilder sb = new StringBuilder();
        Iterator<?> it = tokens.iterator();
        if (it.hasNext()) {
            sb.append(it.next());
            while (it.hasNext()) {
                sb.append(delimiter);
                sb.append(it.next());
            }
        }
        return sb.toString();
    }

    /**
     * Converts {@link InputStream} to {@link String}.
     *
     * @param stream The desired InputStream
     * @return The InputStream as String
     * @throws IOException
     */
    public static String inputStreamToString(InputStream stream) throws IOException {
        final StringBuilder builder = new StringBuilder();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = in.readLine()) != null) {
                builder.append(line); // + "\r\n"(no need, json has no line breaks!)
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }

        return builder.toString();
    }

    /**
     * Gets the line separator (break line).
     * If the SDK version is 19 or higher then it will use System.lineSeparator()
     * otherwise will use System.getProperty("line.separator") is available or \n as default.
     * @return
     */
    public static String getLineSeparator() {
        if (sLineSeparator == null) {
            if (Build.VERSION.SDK_INT >= 19) {
                sLineSeparator = System.lineSeparator();
            } else {
                sLineSeparator = System.getProperty("line.separator");
                if (StringUtil.isEmpty(sLineSeparator)) {
                    sLineSeparator = "\n";
                }
            }
        }

        return sLineSeparator;
    }

    /**
     * Adds a new sentence to the {@link StringBuilder} passed as parameter.
     * A blank always precedes a new sentence.
     * @param sb
     * @param str
     */
    public static void appendSentence(StringBuilder sb, String str) {
        sb.append(" ");
        sb.append(str.trim());
    }


    /**
     * A string replace like for StringBuilder.
     * StringBuilder sb = new StringBuilder();
     * sb.append("axxiom").append("-").append("cemig");
     * new StringUtil.Replacer(sb)
     *  .replace("axxiom", "axx")
     *  .replace("cemig", "light")
     *
     *  String result = sb.toString();
     *  //result: axx-light
     */
    public static class Replacer {
        private final StringBuilder sb;
        public Replacer(StringBuilder sb) {
            this.sb = sb;
        }

        public final Replacer replace(String oldStr, String newStr) {
            if (sb == null || sb.length() == 0) {
                return  this;
            }

            int index = sb.indexOf(oldStr);
            while (index != -1) {
                sb.replace(index, index + oldStr.length(), newStr);
                index += newStr.length();
                index = sb.indexOf(oldStr, index);
            }

            return this;
        }
    }
}
