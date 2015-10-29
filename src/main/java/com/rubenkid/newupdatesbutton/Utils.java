package com.rubenkid.newupdatesbutton;

/**
 * Created by ruben on 10/26/15.
 */
public class Utils {

    /**
     * Determines whether a provided color is dark accordingly to W3C with a minor adjustment - the
     * brightness border is a bit higher now
     * @see <a hreaf="http://www.w3.org/WAI/ER/WD-AERT/#color-contrast">W3C recommendation</a>
     */
    public static boolean isDarkColor(int testColor) {
        final int red = testColor >> 16 & 0xFF;
        final int green = testColor >> 8 & 0xFF;
        final int blue = testColor & 0xFF;
        return (red * 299 + green * 587 + blue * 114 ) / 1000 < 146;
    }
}
