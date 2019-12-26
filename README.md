# sky
# 一.获取自定义机器人webhook
参考官网文档一步步申请webhook

[钉钉开发文档](https://ding-doc.dingtalk.com/doc#/serverapi2/qf2nxq/26eaddd5)

配置一个WebHook地址
https://oapi.dingtalk.com/robot/send?access_token=**********
![image.png](https://www.chengbinbin.cn/upload/2019/12/image-1f7ba1fbbca545838038b45f764de4e6.png)

# 二.引入钉钉jar
[服务端API SDK下载](https://ding-doc.dingtalk.com/doc#/faquestions/vzbp02)
taobao-sdk-java-auto_1479188381469-20191225.jar

在resources下创建lib
导入taobao-sdk-java-auto_1479188381469-20191225.jar包

pom.xml
修改
```language
<dependency>
    <groupId>com.dingtalk</groupId>
    <artifactId>com.dingtalk</artifactId>
    <scope>system</scope>
    <systemPath>${project.basedir}/src/main/resources/lib/taobao-sdk-java-auto_1479188381469-20191225.jar</systemPath>
</dependency>

<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <includeSystemScope>true</includeSystemScope>
            </configuration>
        </plugin>
    </plugins>
</build>

```

# 三.接口调用

```language
@RequestMapping("/alarm")
@RestController
@Slf4j
public class AlarmController {


    private String secret = "你的secret";


    private String webhook = "https://oapi.dingtalk.com/robot/send?access_token=*******";


    @RequestMapping(value = "/pushData", method = RequestMethod.POST)
    public void alarm(@RequestBody List<AlarmMessageDto> alarmMessageList) {
        log.info("alarmMessage:{}", alarmMessageList.toString());


        alarmMessageList.forEach(info -> {
            try {
                Long timestamp = System.currentTimeMillis();


                String stringToSign = timestamp + "\n" + secret;
                Mac mac = Mac.getInstance("HmacSHA256");
                mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
                byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
                String sign = "&timestamp=" + timestamp + "&sign=" + URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");


                DingTalkClient client = new DefaultDingTalkClient(webhook + sign);
                OapiRobotSendRequest request = new OapiRobotSendRequest();
                request.setMsgtype("text");
                OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
                text.setContent("业务告警:\n" + info.getAlarmMessage());
                request.setText(text);


                OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
                at.setAtMobiles(Arrays.asList("所有人"));
                request.setAt(at);


                OapiRobotSendResponse response = client.execute(request);
                log.info("execute:{}" + response.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

```

# 四.效果
![image.png](https://www.chengbinbin.cn/upload/2019/12/image-6146fb54b5594143b827fd4c8e464090.png)

# 五.代码
[GitHub](https://github.com/learnr27/sky)
