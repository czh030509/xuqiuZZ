package services.weibo;

import models.DefaultContant;
import weiboclient4j.StatusService;
import weiboclient4j.WeiboClient;
import weiboclient4j.model.Timeline;
import weiboclient4j.oauth2.SinaWeibo2AccessToken;
import weiboclient4j.params.Paging;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/12/9 11:39
 */
public class Xlweibo {
    private static String appKey = "1143867001";
    private static String appSecret = "0a3a7acf538b1c2509c8ee4c38708193";
    private static String token = DefaultContant.getToken();

    private WeiboClient myClient;
    private StatusService service;

    public Xlweibo() {
        myClient = new WeiboClient(token);
        service = myClient.getStatusService();
    }

    public Timeline getTimeline(long sinceId) throws Exception {
        Paging paging = Paging.create()
                .sinceId(sinceId)
                .count(25);

        Timeline timeline = service.getFriendsTimeline(paging);
        return timeline;
    }

    // https://api.weibo.com/oauth2/authorize?client_id=1143867001&redirect_uri=https%3A%2F%2Fxueqiu.com%2F8327235130&response_type=code&state=&display=default&forcelogin=false
    private static void getUrl() {
        String authorizationCallback = "https://xueqiu.com/8327235130"; // 你的Callback地址
        String state = "";

        WeiboClient client = new WeiboClient(appKey, appSecret);

        //此url用于跳转登录账号使用
        String url = client.getAuthorizationUrl(state, authorizationCallback);
    }

    private static String getToken() throws Exception {
        // 浏览器重定向到url; 用户授权; 然后返回callback地址，此callback地址就有code了
        String code = "b265a120d5a89a6920dc8054ec72c3f6";
        String accessTokenCallback = "https://xueqiu.com/8327235130";

        WeiboClient client = new WeiboClient(appKey, appSecret);
        SinaWeibo2AccessToken accessToken = client.getAccessTokenByCode(code, accessTokenCallback);
        String token = accessToken.getToken();
        return token;
    }
}
