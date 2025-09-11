package org.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            String registrationId = userRequest.getClientRegistration().getRegistrationId();
            Map<String, Object> attributes = oAuth2User.getAttributes();

            logger.info("OAuth2 login attempt from provider: {}", registrationId);

            String role = determineUserRole(attributes);
            logger.info("User role determined: {}", role);

            return createOAuth2User(attributes, role);

        } catch (Exception e) {
            logger.error("Error during OAuth2 user processing", e);
            throw new OAuth2AuthenticationException("User processing failed");
        }
    }

    private String determineUserRole(Map<String, Object> attributes) {
        String email = (String) attributes.get("email");

        if (email != null && email.toLowerCase().contains("admin")) {
            return "ROLE_ADMIN";
        }
        return "ROLE_USER";
    }

    private OAuth2User createOAuth2User(Map<String, Object> attributes, String role) {
        return new DefaultOAuth2User(
                Collections.singletonList(() -> role),
                attributes,
                "sub"
        );
    }
}
