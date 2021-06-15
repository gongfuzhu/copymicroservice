package com.github.microservice.auth.server.core.service;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service
@Primary
public class ClientDetailsServiceImpl implements ClientDetailsService, Serializable {

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
//
//        clients.inMemory()
//                .withClient("client")
//                .secret("secret")
//                .authorizedGrantTypes("client_credentials", "password", "refresh_token")
//                .scopes("all")
//                .resourceIds("oauth2-resource")
//                .accessTokenValiditySeconds(1200)
//                .refreshTokenValiditySeconds(50000);


        return new ClientDetails() {
            @Override
            public String getClientId() {
                return clientId;
            }

            @Override
            public Set<String> getResourceIds() {
                return Set.of("oauth2-resource");
            }

            @Override
            public boolean isSecretRequired() {
                return true;
            }

            @Override
            public String getClientSecret() {
                return "secret";
            }

            @Override
            public boolean isScoped() {
                return true;
            }

            @Override
            public Set<String> getScope() {
                return Set.of("all");
            }

            @Override
            public Set<String> getAuthorizedGrantTypes() {
                return Set.of("client_credentials", "password", "refresh_token");
            }

            @Override
            public Set<String> getRegisteredRedirectUri() {
                return null;
            }

            @Override
            public Collection<GrantedAuthority> getAuthorities() {
                return List.of();
            }

            @Override
            public Integer getAccessTokenValiditySeconds() {
                return 1200;
            }

            @Override
            public Integer getRefreshTokenValiditySeconds() {
                return 50000;
            }

            @Override
            public boolean isAutoApprove(String scope) {
                return false;
            }

            @Override
            public Map<String, Object> getAdditionalInformation() {
                return null;
            }
        };
    }
}
