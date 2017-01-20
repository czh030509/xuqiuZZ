package models;

import org.apache.commons.lang.StringUtils;
import services.taoguba.TGBUrlLib;
import services.xueqiu.XQUrlLib;
import utils.base.PropertyUtils;

public final class DefaultContant {
    private static int numLimit = 5;
    private static String fileName = "default.properties";

    private static String userid = PropertyUtils.getValue("userid", fileName);
    private static String cookie;
    private static String xl_ids = PropertyUtils.getValue("xl_ids", fileName);
    private static String tgb_userid = PropertyUtils.getValue("tgb_userid", fileName);
    private static String tgb_cookie;
    private static Boolean open_xueqiu = Boolean.parseBoolean(PropertyUtils.getValue("open_xueqiu", fileName));
    private static Boolean open_tgb = Boolean.parseBoolean(PropertyUtils.getValue("open_tgb", fileName));
    private static String token = PropertyUtils.getValue("token", fileName);
    private static Boolean open_xl = Boolean.parseBoolean(PropertyUtils.getValue("open_xl", fileName));
    private static int period = Integer.parseInt(PropertyUtils.getValue("period", fileName));
    private static String tgb_user = PropertyUtils.getValue("tgb_user", fileName);
    private static String tgb_psw = PropertyUtils.getValue("tgb_psw", fileName);

    private static String xq_user = PropertyUtils.getValue("xq_user", fileName);
    private static String xq_psw = PropertyUtils.getValue("xq_psw", fileName);


    public static int getNumLimit() {
        return numLimit;
    }

    public static Boolean isOpen_xl() {
        return open_xl;
    }

    public static String getXl_ids() {
        return xl_ids;
    }

    public static String getToken() {
        return token;
    }

    public static int getPeriod() {
        return period;
    }

    public static Boolean isOpen_tgb() {
        return open_tgb;
    }

    public static Boolean isOpen_xueqiu() {
        return open_xueqiu;
    }

    public static String getTgb_userid() {
        return tgb_userid;
    }

    public static String getCookie() {
        if (StringUtils.isBlank(cookie)) {
            cookie = XQUrlLib.login(xq_user, xq_psw);
        }

        return cookie;
    }

    public static String getUserid() {
        return userid;
    }

    public static String getTgb_cookie() {
        if (StringUtils.isBlank(tgb_cookie)) {
            tgb_cookie = TGBUrlLib.login(tgb_user, tgb_psw);
        }

        return tgb_cookie;
    }
}
