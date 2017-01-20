package services.xueqiu; /**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/11/28 11:12
 */
import java.util.Map;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import utils.base.HttpClientUtils;
import utils.base.Md5Utils;

public class XQUrlLib {
    public static String getInfos(String userId, String cookie, String count) {
        String url = "https://xueqiu.com/v4/statuses/user_timeline.json";
        String user_agent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.113 Safari/537.36";
        String header = "User-Agent:" + user_agent + ";"
                + "Host:xueqiu.com;"
                + "Cookie:" + cookie;

        Map<String, String> maps = Maps.newHashMap();
        maps.put("user_id", userId);
        if (StringUtils.isBlank(count)) {
            count = "10";
        }
        maps.put("count", count);

        String res = HttpClientUtils.postWithCookieHeader(url, maps, header, null);
        return res;
    }

    public static String login(String phone, String psw) {
        String cookie = "";
        String url = "https://xueqiu.com/user/login";

        Map<String, String> headers = Maps.newHashMap();
        headers.put("Accept", "*/*");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "zh-CN,zh;q=0.8");
        headers.put("Connection", "keep-alive");
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headers.put("Host", "xueqiu.com");
        headers.put("Origin", "https://xueqiu.com");
        headers.put("Referer", "https://xueqiu.com/");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.75 Safari/537.36");
        headers.put("X-Requested-With", "XMLHttpRequest");

        Map<String, String> maps = Maps.newHashMap();
        maps.put("areacode", "86");
        maps.put("password", Md5Utils.md5Encode(psw));
        maps.put("remember_me", "on");
        maps.put("telephone", phone);

        HttpResponse response = HttpClientUtils.postMapAndHeaderForResponse(url, headers, maps);
        Header[] hs = response.getHeaders("set-cookie");

        for (Header h : hs) {
            cookie = cookie + h.getValue().split(";")[0] + ";";
        }

        return cookie;
    }

    public static void main(String[] args) throws InterruptedException {
        String cookie = login("18611127212", "123456czh");
        System.out.println(cookie);
    }
}
