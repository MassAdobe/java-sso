package com.guangl.sso;

import com.google.common.base.Strings;
import com.guangl.sso.constants.ConstantsConfig;
import com.guangl.sso.constants.HttpConstant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @ClassName: LoginContollerTests
 * @Author: MassAdobe
 * @Email: massadobe8@gmail.com
 * @Description: TODO
 * @Date: Created in 2020-01-10 11:15
 * @Version: 1.0.0
 * @param: * @param null
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SsoSysApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginContollerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private Long guid = 1L;
    private Long sysid = 1L;

    @Before
    public void setUp() throws Exception {
        String url = String.format("http://localhost:%d/", port);
        System.out.println(String.format("port is : [%d]", port));
    }

    /**
     * @ClassName: LoginContollerTests
     * @Author: MassAdobe
     * @Email: massadobe8@gmail.com
     * @Description: /sso-sys/login/confirmMobile
     * @Date: Created in 2020-01-10 11:25
     * @Version: 1.0.0
     * @param: * @param null
     */
    @Test
    public void confirmMobile() {
        // 已激活
        String body = Strings.lenientFormat("{\"phoneNum\": \"%s\",\"mark\":%s}", "18201750093", "1");
        System.out.println(Strings.lenientFormat("【CONFIRM-MOBILE-REQUEST】;【已激活】:【BODY】: %s", body));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf("application/json"));
        HttpEntity<String> str = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<String> result = this.testRestTemplate.exchange("http://localhost:" + port + ConstantsConfig.APPLICATION_NAME + "/login/confirmMobile", HttpMethod.POST, str, String.class);
        System.out.println(Strings.lenientFormat("【CONFIRM-MOBILE-RESPONSE】;【已激活】:【ANS】: %s", result.getBody()));
        Assert.assertEquals("", "{\"code\":0,\"msg\":\"成功\",\"data\":{\"ans\":\"已激活\"}}", result.getBody());
        // 未激活
        System.out.println();
        body = Strings.lenientFormat("{\"phoneNum\": \"%s\",\"mark\":%s}", "18017111310", "2");
        System.out.println(Strings.lenientFormat("【CONFIRM-MOBILE-REQUEST】;【未激活】:【BODY】: %s", body));
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf("application/json"));
        str = new HttpEntity<>(body, httpHeaders);
        result = this.testRestTemplate.exchange("http://localhost:" + port + ConstantsConfig.APPLICATION_NAME + "/login/confirmMobile", HttpMethod.POST, str, String.class);
        System.out.println(Strings.lenientFormat("【CONFIRM-MOBILE-RESPONSE】;【未激活】:【ANS】: %s", result.getBody()));
        Assert.assertEquals("", "{\"code\":0,\"msg\":\"成功\",\"data\":{\"ans\":\"未激活\"}}", result.getBody());
    }

    /**
     * @ClassName: LoginContollerTests
     * @Author: MassAdobe
     * @Email: massadobe8@gmail.com
     * @Description: /sso-sys/login/signIn
     * @Date: Created in 2020-01-10 15:04
     * @Version: 1.0.0
     * @param: * @param null
     */
    @Test
    public void signIn() {
        // 登录成功
        String body = Strings.lenientFormat("{\"phoneNum\": \"%s\",\"password\": \"%s\",\"mark\": %s,\"sign\": \"2e62ca9bf4bdba06212a2431d0de4b60\"}", "18201750093", "Uel3G0jvAM98", "1");
        System.out.println(Strings.lenientFormat("【SIGN-IN-REQUEST】;【登录成功】:【BODY】: %s", body));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpConstant.HEADER_TIMESTAMP_KEY, String.valueOf(System.currentTimeMillis()));
        System.out.println(Strings.lenientFormat("【SIGN-IN-REQUEST】;【登录成功】:【HEADER】: %s", httpHeaders.getFirst(HttpConstant.HEADER_TIMESTAMP_KEY)));
        httpHeaders.setContentType(MediaType.valueOf("application/json"));
        HttpEntity<String> str = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<String> result = this.testRestTemplate.exchange("http://localhost:" + port + ConstantsConfig.APPLICATION_NAME + "/login/signIn", HttpMethod.POST, str, String.class);
        System.out.println(Strings.lenientFormat("【SIGN-IN-RESPONSE】;【登录成功】:【ANS】: %s,【%s】: %s, 【%s】: %s, 【%s】: %s", result.getBody(), HttpConstant.ACCESS_TOKEN_KEY, result.getHeaders().getFirst(HttpConstant.ACCESS_TOKEN_KEY), HttpConstant.DELTA_TM_KEY, result.getHeaders().getFirst(HttpConstant.DELTA_TM_KEY), HttpConstant.SALT_KEY, result.getHeaders().getFirst(HttpConstant.SALT_KEY)));
        Assert.assertNotNull("", result.getHeaders().getFirst(HttpConstant.ACCESS_TOKEN_KEY));
        Assert.assertNotNull("", result.getHeaders().getFirst(HttpConstant.DELTA_TM_KEY));
        Assert.assertNotNull("", result.getHeaders().getFirst(HttpConstant.SALT_KEY));
        Assert.assertEquals("", "{\"code\":0,\"msg\":\"成功\",\"data\":\"\"}", result.getBody());
        // 登录失败（密码不同)
        System.out.println();
        body = Strings.lenientFormat("{\"phoneNum\": \"%s\",\"password\": \"%s\",\"mark\": %s,\"sign\": \"2e62ca9bf4bdba02a2431d0de4b60\"}", "18201750093", "Uel3G0xvAM98", "1");
        System.out.println(Strings.lenientFormat("【SIGN-IN-REQUEST】;【登录失败】:【BODY】: %s", body));
        System.out.println(Strings.lenientFormat("【SIGN-IN-REQUEST】;【登录失败】:【HEADER】: %s", httpHeaders.getFirst(HttpConstant.HEADER_TIMESTAMP_KEY)));
        str = new HttpEntity<>(body, httpHeaders);
        result = this.testRestTemplate.exchange("http://localhost:" + port + ConstantsConfig.APPLICATION_NAME + "/login/signIn", HttpMethod.POST, str, String.class);
        System.out.println(Strings.lenientFormat("【SIGN-IN-RESPONSE】;【登录失败】:【ANS】: %s,【%s】: %s, 【%s】: %s, 【%s】: %s", result.getBody(), HttpConstant.ACCESS_TOKEN_KEY, result.getHeaders().getFirst(HttpConstant.ACCESS_TOKEN_KEY), HttpConstant.DELTA_TM_KEY, result.getHeaders().getFirst(HttpConstant.DELTA_TM_KEY), HttpConstant.SALT_KEY, result.getHeaders().getFirst(HttpConstant.SALT_KEY)));
        Assert.assertNull("", result.getHeaders().getFirst(HttpConstant.ACCESS_TOKEN_KEY));
        Assert.assertNull("", result.getHeaders().getFirst(HttpConstant.DELTA_TM_KEY));
        Assert.assertNull("", result.getHeaders().getFirst(HttpConstant.SALT_KEY));
        Assert.assertEquals("", "{\"code\":2998,\"msg\":\"登录用户名或者密码错误\",\"data\":\"\"}", result.getBody());
        // 用户非法（用户无根）
        System.out.println();
        body = Strings.lenientFormat("{\"phoneNum\": \"%s\",\"password\": \"%s\",\"mark\": %s,\"sign\": \"2e62ca9bf4bdba02a2431d0de4b60\"}", "18017111310", "Uel3G0xvAM98", "2");
        System.out.println(Strings.lenientFormat("【SIGN-IN-REQUEST】;【用户非法】:【BODY】: %s", body));
        System.out.println(Strings.lenientFormat("【SIGN-IN-REQUEST】;【用户非法】:【HEADER】: %s", httpHeaders.getFirst(HttpConstant.HEADER_TIMESTAMP_KEY)));
        str = new HttpEntity<>(body, httpHeaders);
        result = this.testRestTemplate.exchange("http://localhost:" + port + ConstantsConfig.APPLICATION_NAME + "/login/signIn", HttpMethod.POST, str, String.class);
        System.out.println(Strings.lenientFormat("【SIGN-IN-RESPONSE】;【用户非法】:【ANS】: %s,【%s】: %s, 【%s】: %s, 【%s】: %s", result.getBody(), HttpConstant.ACCESS_TOKEN_KEY, result.getHeaders().getFirst(HttpConstant.ACCESS_TOKEN_KEY), HttpConstant.DELTA_TM_KEY, result.getHeaders().getFirst(HttpConstant.DELTA_TM_KEY), HttpConstant.SALT_KEY, result.getHeaders().getFirst(HttpConstant.SALT_KEY)));
        Assert.assertNull("", result.getHeaders().getFirst(HttpConstant.ACCESS_TOKEN_KEY));
        Assert.assertNull("", result.getHeaders().getFirst(HttpConstant.DELTA_TM_KEY));
        Assert.assertNull("", result.getHeaders().getFirst(HttpConstant.SALT_KEY));
        Assert.assertEquals("", "{\"code\":2898,\"msg\":\"非法用户\",\"data\":\"\"}", result.getBody());
    }

    /**
     * @ClassName: LoginContollerTests
     * @Author: MassAdobe
     * @Email: massadobe8@gmail.com
     * @Description: /sso-sys/login/signUp
     * @Date: Created in 2020-01-20 14:55
     * @Version: 1.0.0
     * @param: * @param null
     */
    @Test
    public void signUp() {
        // 成功激活密码
        String body = Strings.lenientFormat("{\"phoneNum\": \"18201750093\",\"password\": \"abcdefg\",\"confirmPwd\": \"abcdefg\",\"mark\": 1}");
        System.out.println(Strings.lenientFormat("【SIGN-UP-REQUEST】;【激活成功】:【BODY】: %s", body));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpConstant.HEADER_TIMESTAMP_KEY, String.valueOf(System.currentTimeMillis()));
        httpHeaders.setContentType(MediaType.valueOf("application/json"));
        HttpEntity<String> str = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<String> result = this.testRestTemplate.exchange("http://localhost:" + port + ConstantsConfig.APPLICATION_NAME + "/login/signUp", HttpMethod.POST, str, String.class);
        System.out.println(Strings.lenientFormat("【SIGN-UP-RESPONSE】;【激活成功】:【ANS】: %s,【%s】: %s, 【%s】: %s, 【%s】: %s", result.getBody(), HttpConstant.ACCESS_TOKEN_KEY, result.getHeaders().getFirst(HttpConstant.ACCESS_TOKEN_KEY), HttpConstant.DELTA_TM_KEY, result.getHeaders().getFirst(HttpConstant.DELTA_TM_KEY), HttpConstant.SALT_KEY, result.getHeaders().getFirst(HttpConstant.SALT_KEY)));
        Assert.assertNotNull("", result.getHeaders().getFirst(HttpConstant.ACCESS_TOKEN_KEY));
        Assert.assertNotNull("", result.getHeaders().getFirst(HttpConstant.DELTA_TM_KEY));
        Assert.assertNotNull("", result.getHeaders().getFirst(HttpConstant.SALT_KEY));
        Assert.assertEquals("", "{\"code\":0,\"msg\":\"成功\",\"data\":\"\"}", result.getBody());
        // 激活密码时密码不一致
        System.out.println();
        body = Strings.lenientFormat("{\"phoneNum\": \"18201750093\",\"password\": \"abcdefg\",\"confirmPwd\": \"gfedcba\",\"mark\": 1}");
        System.out.println(Strings.lenientFormat("【SIGN-UP-REQUEST】;【激活失败】:【BODY】: %s", body));
        httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpConstant.HEADER_TIMESTAMP_KEY, String.valueOf(System.currentTimeMillis()));
        httpHeaders.setContentType(MediaType.valueOf("application/json"));
        str = new HttpEntity<>(body, httpHeaders);
        result = this.testRestTemplate.exchange("http://localhost:" + port + ConstantsConfig.APPLICATION_NAME + "/login/signUp", HttpMethod.POST, str, String.class);
        System.out.println(Strings.lenientFormat("【SIGN-UP-RESPONSE】;【激活失败】:【ANS】: %s,【%s】: %s, 【%s】: %s, 【%s】: %s", result.getBody(), HttpConstant.ACCESS_TOKEN_KEY, result.getHeaders().getFirst(HttpConstant.ACCESS_TOKEN_KEY), HttpConstant.DELTA_TM_KEY, result.getHeaders().getFirst(HttpConstant.DELTA_TM_KEY), HttpConstant.SALT_KEY, result.getHeaders().getFirst(HttpConstant.SALT_KEY)));
        Assert.assertNull("", result.getHeaders().getFirst(HttpConstant.ACCESS_TOKEN_KEY));
        Assert.assertNull("", result.getHeaders().getFirst(HttpConstant.DELTA_TM_KEY));
        Assert.assertNull("", result.getHeaders().getFirst(HttpConstant.SALT_KEY));
        Assert.assertEquals("", "{\"code\":2998,\"msg\":\"登录用户名或者密码错误\",\"data\":\"\"}", result.getBody());
        // 手机号码不对
        System.out.println();
        body = Strings.lenientFormat("{\"phoneNum\": \"11111111111\",\"password\": \"abcdefg\",\"confirmPwd\": \"abcdefg\",\"mark\": 1}");
        System.out.println(Strings.lenientFormat("【SIGN-UP-REQUEST】;【激活失败】:【BODY】: %s", body));
        httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpConstant.HEADER_TIMESTAMP_KEY, String.valueOf(System.currentTimeMillis()));
        httpHeaders.setContentType(MediaType.valueOf("application/json"));
        str = new HttpEntity<>(body, httpHeaders);
        result = this.testRestTemplate.exchange("http://localhost:" + port + ConstantsConfig.APPLICATION_NAME + "/login/signUp", HttpMethod.POST, str, String.class);
        System.out.println(Strings.lenientFormat("【SIGN-UP-RESPONSE】;【激活失败】:【ANS】: %s,【%s】: %s, 【%s】: %s, 【%s】: %s", result.getBody(), HttpConstant.ACCESS_TOKEN_KEY, result.getHeaders().getFirst(HttpConstant.ACCESS_TOKEN_KEY), HttpConstant.DELTA_TM_KEY, result.getHeaders().getFirst(HttpConstant.DELTA_TM_KEY), HttpConstant.SALT_KEY, result.getHeaders().getFirst(HttpConstant.SALT_KEY)));
        Assert.assertNull("", result.getHeaders().getFirst(HttpConstant.ACCESS_TOKEN_KEY));
        Assert.assertNull("", result.getHeaders().getFirst(HttpConstant.DELTA_TM_KEY));
        Assert.assertNull("", result.getHeaders().getFirst(HttpConstant.SALT_KEY));
        Assert.assertEquals("", "{\"code\":2998,\"msg\":\"登录用户名或者密码错误\",\"data\":\"\"}", result.getBody());
    }

}
