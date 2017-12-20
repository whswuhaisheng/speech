package speech;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author 武海升
 * @version 2.0
 * @description
 * @date 2017-12-20 15:06
 */
public class SpeechText {


    private static final String serverURL = "http://vop.baidu.com/server_api";
    private static String token = "";
    private static final String testFileName = "F:\\xf\\20171220100059059_20171220100059518.wav";
    //put your own params here
    private static final String apiKey = "***";//这里的apiKey就是前面申请在应用卡片中的apiKey
    private static final String secretKey = "***";//这里的secretKey就是前面申请在应用卡片中的secretKey
    private static final String cuid = "***";//cuid是设备的唯一标示，因为我用的是PC，所以这里用的是网卡Mac地址

    public static void main(String[] args) throws Exception {
        getToken();
        method1();
    }

    private static void getToken() throws Exception {
        token = AccessTokenUtil.getAccessToken().getAccess_token();
    }

    private static void method1() throws Exception {
        File wavFile = new File(testFileName);
        HttpURLConnection conn = (HttpURLConnection) new URL(serverURL).openConnection();

        JSONObject params = new JSONObject();
        params.put("format", "wav");
        params.put("rate", 8000);
        params.put("channel", "1");
        params.put("token", token);
        params.put("cuid", RandomStringGenerator.getRandomStringByLength(60));
        params.put("len", wavFile.length());
        params.put("speech", DatatypeConverter.printBase64Binary(loadFile(wavFile)));

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        conn.setDoInput(true);
        conn.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(params.toString());
        wr.flush();
        wr.close();

        printResponse(conn);
    }


    private static String printResponse(HttpURLConnection conn) throws Exception {
        String speechText = "";
        if (conn.getResponseCode() != 200) {
            // request error
            return "";
        }
        InputStream is = conn.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        JSONObject jsonObject = JSONObject.parseObject(response.toString());
        String string = jsonObject.getString("err_msg");
        System.out.println(string);
        if(jsonObject!=null&&jsonObject.getInteger("err_no")==0){
            JSONArray resultArray = jsonObject.getJSONArray("result");
            StringBuffer sb = new StringBuffer();
            for (Object o : resultArray) {
                String text = (String) o;
                sb.append(text);
            }
            speechText = sb.toString();
        }
        System.out.println(jsonObject);
        System.out.println(speechText);
        return response.toString();
    }

    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            is.close();
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
        return bytes;
    }
}
