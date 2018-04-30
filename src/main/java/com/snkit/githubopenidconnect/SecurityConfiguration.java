package com.snkit.githubopenidconnect;

import static org.springframework.http.HttpMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	
	private final String LOGIN_URL = "/login";
	
    private final OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    public SecurityConfiguration(OAuth2RestTemplate oAuth2RestTemplate) {
        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }
	
    
    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/resources/**");
    }
    
    
    @Bean
    public OpenIdConnectAuthenticationFilter createOpenIdConnectFilter() {
        final OpenIdConnectAuthenticationFilter openIdConnectFilter = new OpenIdConnectAuthenticationFilter("/home");
        openIdConnectFilter.setRestTemplate(oAuth2RestTemplate);
        return openIdConnectFilter;
    }
	

	 
	 @Override
	    protected void configure(HttpSecurity httpSecurity) throws Exception {
	        httpSecurity
	                .addFilterAfter(new OAuth2ClientContextFilter(), AbstractPreAuthenticatedProcessingFilter.class)
	                .addFilterAfter(createOpenIdConnectFilter(), OAuth2ClientContextFilter.class)
	                .httpBasic().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/home"))
	                .and()
	                .authorizeRequests()
	                .antMatchers(GET, "/").authenticated()
	                .antMatchers("/app/rest/**").authenticated()  
	                .antMatchers(GET, "/home").authenticated();
	                // .antMatchers("/","/index*").permitAll()
	                // .anyRequest().authenticated();
	    }
	
}
