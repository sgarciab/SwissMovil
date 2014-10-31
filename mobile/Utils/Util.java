package mobile.Utils;

public class Util {

    public static boolean isNumeric(String value) {
        try {
            Long.parseLong(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
