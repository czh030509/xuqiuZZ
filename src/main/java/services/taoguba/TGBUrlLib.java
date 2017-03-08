package services.taoguba; /**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/11/28 11:12
 */

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import utils.base.HttpClientUtils;

import java.util.Map;

public class TGBUrlLib {
    public static String login(String userName, String psw) {
        String cookie = "";
        String url = "https://www.taoguba.com.cn/newLogin";

        Map<String, String> headers = Maps.newHashMap();
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        headers.put("Accept-Encoding", "gzip, deflate");
        headers.put("Accept-Language", "zh-CN,zh;q=0.8");
        headers.put("Cache-Control", "max-age=0");
        headers.put("Connection", "keep-alive");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Host", "www.taoguba.com.cn");
        headers.put("Origin", "https://www.taoguba.com.cn");
        headers.put("Referer", "https://www.taoguba.com.cn/gotoLogin");
        headers.put("Upgrade-Insecure-Requests", "1");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.75 Safari/537.36");

        Map<String, String> maps = Maps.newHashMap();
        maps.put("pwdlevel", "Y");
        maps.put("loginType", "1");
        maps.put("userName", userName);
        maps.put("pwd", psw);
        maps.put("save", "Y");

        HttpResponse response = HttpClientUtils.postMapAndHeaderForResponse(url, headers, maps);
        Header[] hs = response.getHeaders("set-cookie");

        for (Header h : hs) {
            cookie = cookie + h.getValue().split(";")[0] + ";";
        }

        return cookie;
    }

    public static void main(String[] args) throws InterruptedException {
        String cookie = login("czh030509", "123456czh");
        System.out.println(cookie);
    }
}
