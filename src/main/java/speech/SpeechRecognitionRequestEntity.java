package speech;

/**
 * @author 武海升
 * @version 2.0
 * @description
 * @date 2017-12-20 15:46
 */
public class SpeechRecognitionRequestEntity {
    // 语音压缩的格式
    private String format;

    /**
     * 注意，采样率的数据类型一定是 int，不能是 String
     */
    // 采样率，支持 8000 或者 16000，在我们的项目中，写 16000
    private int rate;
    // 声道数，仅支持单声道，请填写 1
    private String channel;
    // 开发者身份验证密钥
    private String token;
    // 用户 ID，推荐使用设备 mac 地址 手机 IMEI 等设备唯一性参数
    private String cuid;

    /**
     * 注意：这里填写的是原始语音的长度，不是使用 base64 编码的语音长度
     */
    // 原始语音长度，单位字节
    private long len;
    // 真实的语音数据，需要进行 base64 编码
    private String speech;
    // 语种选择，中文=zh、粤语=ct、英文=en，不区分大小写，默认中文
    private String lan;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public long getLen() {
        return len;
    }

    public void setLen(long len) {
        this.len = len;
    }

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }
}
