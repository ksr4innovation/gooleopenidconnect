package com.snkit.githubopenidconnect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class HomeController {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	
    @RequestMapping("/api/rest/home")
    @ResponseBody
    public User home() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        
        OpenIdConnectUserDetails priniciple = (OpenIdConnectUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        log.info("HomeController: username: " + username);
        
        User u = new User();
        u.setEmail(priniciple.getEmail());
        u.setToken(priniciple.getAccessToken().getAdditionalInformation().get("id_token").toString());
        
        return u ;
    }
    
    @RequestMapping("/api/rest/user")
    @ResponseBody
    public User user() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        
        OpenIdConnectUserDetails priniciple = (OpenIdConnectUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        log.info("user: username: " + username);
        
        User u = new User();
        u.setEmail(priniciple.getEmail());
        u.setToken(priniciple.getAccessToken().getAdditionalInformation().get("id_token").toString());
        
        return u ;
    }
}