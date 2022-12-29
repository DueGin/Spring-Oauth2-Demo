package lee;

import com.gargoylesoftware.htmlunit.HttpMethod;

import java.io.IOException;
import java.lang.*;
import java.util.Map;

import org.junit.jupiter.api.Test;


public class TT1 {

    //测试对资源服务1的访问

    public static String zuul = "http://127.0.0.1:5001/";

    //授权码模式
    @Test
    public void getTokenByCode() throws IOException {
        //标准模式
        //浏览器访问
        //http://127.0.0.1:5001/authorization_server/oauth/authorize?client_id=client1&response_type=code&scope=scope1&redirect_uri=http://www.baidu.com

        //重定向结果
        //https://www.baidu.com/?code=QaBozt

        String[] params = new String[]{
                "client_id", "client1",
                "client_secret", "1234",
                "grant_type", "authorization_code",
                "code", "ZLhfdp",//这个code是从getCode()方法获取的
                "redirect_uri", "http://www.baidu.com"
        };
        HttpUtil.send(HttpMethod.POST, zuul + "authorization_server/oauth/token", null, null, params);
        //{"access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2UxIl0sInVzZXJfbmFtZSI6ImFkbWluIiwic2NvcGUiOlsic2NvcGUxIl0sImV4cCI6MTYxNDA2MDI2MSwiYXV0aG9yaXRpZXMiOlsiYWRtaW4iXSwianRpIjoiNTRlN2FiOTktNWNhMy00YWEwLThkNzYtMjAwNmJkZGY3NjE1IiwiY2xpZW50X2lkIjoiY2xpZW50MSJ9.tKQwT6ZnFXxJJS00TOYZM9lQtypMDA0HLK1Cn56xTgY","token_type":"bearer","refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2UxIl0sInVzZXJfbmFtZSI6ImFkbWluIiwic2NvcGUiOlsic2NvcGUxIl0sImF0aSI6IjU0ZTdhYjk5LTVjYTMtNGFhMC04ZDc2LTIwMDZiZGRmNzYxNSIsImV4cCI6MTYxNDA2MTQ2MSwiYXV0aG9yaXRpZXMiOlsiYWRtaW4iXSwianRpIjoiN2RjYmFlMGItYzFjNS00NGNhLThiZGQtOWYxOGQyNzExNTAzIiwiY2xpZW50X2lkIjoiY2xpZW50MSJ9.gtHUeOItJeVDzmC4wCyGyiKQrDqkoIl1XW6xx06Wzxc","expires_in":299,"scope":"scope1","jti":"54e7ab99-5ca3-4aa0-8d76-2006bddf7615"}
    }

    //验证jwt token
    @Test
    public void checkToken() throws IOException {
        String[] params = new String[]{
                "token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2UxIl0sInVzZXJfbmFtZSI6InVzZXIiLCJzY29wZSI6WyJzY29wZTEiXSwiZXhwIjoxNjcyMjE5NTQ2LCJhdXRob3JpdGllcyI6WyJ1c2VyIl0sImp0aSI6ImY3YTU2OWFkLWJlZWUtNGJlYi04MTcxLTA0ZTkwMGNlZTI4NiIsImNsaWVudF9pZCI6ImNsaWVudDEifQ.lO3t60tQ0DI6mITnVcZpeu4EyYcjkn2LJpfknLO7mSw",
        };
        HttpUtil.send(HttpMethod.POST, zuul + "authorization_server/oauth/check_token", null, null, params);
        //{"aud":["resource1"],"user_name":"admin","scope":["scope1"],"active":true,"exp":1614060261,"authorities":["admin"],"jti":"54e7ab99-5ca3-4aa0-8d76-2006bddf7615","client_id":"client1"}
    }

    //使用jwt token访问resource
    @Test
    public void getResourceByToken() throws IOException {
        Map<String, String> head = RootUtil.buildMap("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2UxIl0sInVzZXJfbmFtZSI6InVzZXIiLCJzY29wZSI6WyJzY29wZTEiXSwiZXhwIjoxNjcyMjIwNzYyLCJhdXRob3JpdGllcyI6WyJ1c2VyIl0sImp0aSI6ImFjNmZiNTQ3LTFlZTgtNGI2Ny04NzM1LTU1MGM1ZGEyOTNkMCIsImNsaWVudF9pZCI6ImNsaWVudDEifQ.QEZ2qdNGXxh172uR4Gw-XJPq1EICvpkaM1RZZWRHKqc");
        HttpUtil.send(HttpMethod.POST, zuul + "resource_server1/me", head, null);
        //{"authorities":[{"authority":"admin"}],"details":{"remoteAddress":"127.0.0.1","sessionId":null,"tokenValue":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2UxIl0sInVzZXJfbmFtZSI6ImFkbWluIiwic2NvcGUiOlsic2NvcGUxIl0sImV4cCI6MTYxNDA2MDI2MSwiYXV0aG9yaXRpZXMiOlsiYWRtaW4iXSwianRpIjoiNTRlN2FiOTktNWNhMy00YWEwLThkNzYtMjAwNmJkZGY3NjE1IiwiY2xpZW50X2lkIjoiY2xpZW50MSJ9.tKQwT6ZnFXxJJS00TOYZM9lQtypMDA0HLK1Cn56xTgY","tokenType":"Bearer","decodedDetails":null},"authenticated":true,"userAuthentication":{"authorities":[{"authority":"admin"}],"details":null,"authenticated":true,"principal":"admin","credentials":"N/A","name":"admin"},"clientOnly":false,"oauth2Request":{"clientId":"client1","scope":["scope1"],"requestParameters":{"client_id":"client1"},"resourceIds":["resource1"],"authorities":[],"approved":true,"refresh":false,"redirectUri":null,"responseTypes":[],"extensions":{},"grantType":null,"refreshTokenRequest":null},"principal":"admin","credentials":"","name":"admin"}
    }
}