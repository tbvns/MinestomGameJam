package xyz.tbvns;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;

import java.text.DecimalFormat;

/**
 * Static utility class.
 */
public class Utils {

    /**
     * Converts a String into a Component.
     *
     * @param text The string to convert
     * @return The resulting component
     */
    public static Component format(String text) { return MiniMessage.miniMessage().deserialize(text); }

    /**
     * Converts a String into a Component without italics.
     *
     * @param text The string to convert
     * @return The resulting component
     */
    public static Component formatWithoutItalics(String text) { return MiniMessage.miniMessage().deserialize(text).decoration(TextDecoration.ITALIC, false); }

    /**
     * Formats a number with commas. <br>
     * Example: 1234567 returns "1,234,567".
     *
     * @param number The number to format
     * @return The formatted number String
     * @author Wyndev
     */
    public static String formatWithCommas(double number) {
        String formattedNumber = new DecimalFormat("#,###.##").format(number);
        if (formattedNumber.endsWith("\\.0")) formattedNumber.replaceAll("\\.0", "");
        return formattedNumber;
    }

}
