package speech;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author 武海升
 * @version 2.0
 * @description
 * @date 2017-12-12 13:35
 */
public class AccessTokenUtil {

    public static final String API_KEY = "efgbInTwwuPxqgG5tLePuu7G";
    public static final String SECRET_KEY = "qRY8OIRfrRhRW457jxc76M8jcoKinTxb";

    public static AccessToken getAccessToken() {
        AccessToken token = new AccessToken();
        String url = "https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials&client_id="+API_KEY+"&client_secret="+SECRET_KEY+"" ;
        try {
            URL getUrl=new URL(url);
            HttpURLConnection http=(HttpURLConnection)getUrl.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);


            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] b = new byte[size];
            is.read(b);

            String message = new String(b, "UTF-8");
            com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(message);



            token.setAccess_token(jsonObject.getString("access_token"));
            token.setExpires_in(new Integer(jsonObject.getString("expires_in")));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }

    public static void main(String[] args) {
        AccessToken accessToken = AccessTokenUtil.getAccessToken();
    }

}
