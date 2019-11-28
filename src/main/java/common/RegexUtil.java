package common;/**
 * Created by V on 2019/11/28.
 */

import java.util.regex.Pattern;

/**
 * V
 * 2019/11/28 1:02
 */
public class RegexUtil {
    public static boolean isNumberString(String str) {
        String numberRegex = "^[\\d]*$";
        if(Pattern.matches(numberRegex,str)){
            return true;
        }
        return false;
    }
}
