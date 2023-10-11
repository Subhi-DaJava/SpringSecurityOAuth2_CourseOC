package com.openclassrooms.controllers;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class LoginController {
    private final OAuth2AuthorizedClientService auth2AuthorizedClientService;

    public LoginController(OAuth2AuthorizedClientService auth2AuthorizedClientService) {
        this.auth2AuthorizedClientService = auth2AuthorizedClientService;
    }

    // l’annotation @GetMapping pour associer l’URL.
    @GetMapping("/user")
    public String getUser() {
        return "Welcome, User";
    }

    @GetMapping("/admin")
    public String getAdmin() {
        return "Welcome, Admin";
    }
//
//    @GetMapping("/")
//    public String getGitHub(Principal user) {
//        return "Welcome, GitHub User: " + user.getName() + ", userToString: { " + user +" }";
//    }


    @GetMapping("/*")
    public String getUserInfo(Principal user, @AuthenticationPrincipal OidcUser oidcUser) {

        StringBuilder userInfo = new StringBuilder();

        if(user instanceof UsernamePasswordAuthenticationToken) {
            userInfo.append(getUsernamePasswordLoginInfo(user));
        } else if(user instanceof OAuth2AuthenticationToken) {
            userInfo.append(getOAuth2LoginInfo(user, oidcUser));
        }

        return userInfo.toString();
    }

    private StringBuffer getUsernamePasswordLoginInfo(Principal user) {
        StringBuffer usernameInfo = new StringBuffer();

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) user;

        if(token.isAuthenticated()) {
            User u = (User) token.getPrincipal();
            usernameInfo.append("Welcome, ").append(u.getUsername());
        } else {
            usernameInfo.append("NotAuthenticated");
        }

        return usernameInfo;
    }

    private StringBuffer getOAuth2LoginInfo(Principal user, OidcUser oidcUser)  {

        StringBuffer protectedInfo = new StringBuffer();
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) user;
        OAuth2AuthorizedClient authClient = auth2AuthorizedClientService.loadAuthorizedClient(
                token.getAuthorizedClientRegistrationId(), token.getName());

        if(token.isAuthenticated()){
            Map<String, Object> userAttributes = token.getPrincipal().getAttributes();
            String userToken = authClient.getAccessToken().getTokenValue();
            protectedInfo.append("Welcome, ").append(userAttributes.get("name")).append("<br><br>");
            protectedInfo.append("e-mail, ").append(userAttributes.get("email")).append("<br><br>");
            protectedInfo.append("Access token : ").append(userToken).append("<br><br>");
            OidcIdToken idToken = oidcUser.getIdToken();
            if(idToken != null)  {
                protectedInfo.append("idToken value : ").append(idToken.getTokenValue()).append("<br>");
                protectedInfo.append("Token mapped values <br>");
                Map<String, Object> claims = idToken.getClaims();
                for (String key: claims.keySet()) {
                    protectedInfo.append(" ").append(key).append(" : ").append(claims.get(key)).append("<br>");
                }
            }

        } else {
            protectedInfo.append("NotAuthenticated");
        }
        return protectedInfo;
    }
}
