package speech;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 武海升
 * @version 2.0
 * @description
 * @date 2017-12-12 13:10
 */
public class Test {

    public static final String APP_ID = "9939900";
    public static final String API_KEY = "efgbInTwwuPxqgG5tLePuu7G";
    public static final String SECRET_KEY = "qRY8OIRfrRhRW457jxc76M8jcoKinTxb";

    public static void main(String[] args) throws Exception {

        String params = "";

        String filePath = "D:\\dd\\xf\\"+"VOICE"+new Date().getTime()/1000+".mp3";
        String requestUrl = "http://tsn.baidu.com/text2audio";
        String generalUrl = requestUrl;
        URL url = new URL(generalUrl);
        System.out.println(generalUrl);
        // 打开和URL之间的连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        System.out.println("打开链接，开始发送请求"+new Date().getTime()/1000);
        connection.setRequestMethod("POST");
        // 设置通用的请求属性
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);

        // 得到请求的输出流对象
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes(params);
        out.flush();
        out.close();

        // 建立实际的连接
        connection.connect();
        // 获取所有响应头字段
        Map<String, List<String>> headers = connection.getHeaderFields();
        // 遍历所有的响应头字段
        for (String key : headers.keySet()) {
            System.out.println(key + "--->" + headers.get(key));
        }
        // 定义 BufferedReader输入流来读取URL的响应
        InputStream inputStream = connection.getInputStream();
        FileOutputStream outputStream = new FileOutputStream(filePath);
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len=inputStream.read(buffer))!=-1) {
            outputStream.write(buffer,0,len);
        }
        outputStream.close();
        System.out.println("请求结束"+new Date().getTime()/1000);







        AipSpeech client = null;
        try {
            client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 设置可选参数
        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("spd", "5");
        options.put("pit", "5");
        TtsResponse voiceData =client.synthesis("你好百度", "zh", 1, options);
        System.out.println(voiceData.getErrorCode());

    }

}
