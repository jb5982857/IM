package com.nanshan.support.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jiangbo on 2018/9/13.
 */

public class MobileUtils {

    public static final Pattern PHONE_P = Pattern.compile("^((13[0-9])|(166)|(15[^4])|(18[0-9])|(17[0-9])|(147))\\d{8}$");

    //判断是否手机号
    public static boolean isMobileNO(String mobiles) {
        Matcher m = PHONE_P.matcher(mobiles);
        return m.matches();
    }

}
