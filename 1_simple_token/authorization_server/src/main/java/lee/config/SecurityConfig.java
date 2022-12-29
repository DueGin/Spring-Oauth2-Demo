package lee.config;

import lee.model.MyUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //密码模式才需要配置,认证管理器
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    // 配置哪些url需要拦截，或者放行
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // 取消跨域请求拦截
                .authorizeRequests()
                .anyRequest().permitAll() // 其他所有url放行

                .and()
                .formLogin() // 允许表单登录提交

                .and()
                .logout();
    }

    // 配置用户账号密码（正常来说得从数据库查）
    @Bean
    public UserDetailsService userDetailsService() {
        return s -> {
            if ("admin".equals(s) || "user".equals(s)) {
                return new MyUserDetails(s, passwordEncoder().encode(s), s);
            }
            return null;
        };
    }
}