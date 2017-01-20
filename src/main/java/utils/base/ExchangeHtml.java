package utils.base;

public class ExchangeHtml {
    public static String exchange(String origin) {
       return origin.replaceAll("(<[^>]*>)", "").replaceAll("&nbsp;", "");
    }
}
