package com.invillia.springoauth2rest.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;


/*
 * @Configuration -> significa que a classe possui Beans de configuração que serão utilizadas em toda a aplicação.
 * @EnableAuthorizationServer -> utilizada para marcar um mecanismo de gerenciamento de autenticação.
 * */

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${security.oauth2.client.client-id}")
    private String CLIENT_ID;

    @Value("${security.oauth2.client.client-secret}")
    private String SECRET;

    @Value("${security.oauth2.client.authorized-grant-types}")
    private String[] AUTHORIZED_GRANT_TYPES;

    @Value("${security.oauth2.client.scope}")
    private String[] SCOPES;

    @Value("${security.oauth2.client.access-token-validity-seconds}")
    private Integer ACCESS_TOKEN_VALIDITY_SECONDS;

    @Value("${security.oauth2.client.refresh-token-validity-seconds}")
    private Integer REFRESH_TOKEN_VALIDITY_SECONDS;

    final private AuthenticationManager authenticationManager;


    @Autowired
    public AuthorizationServerConfig(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /*  Define as configurações de segurança nos endpoints relativos aos tokens de acesso.

            Por padrão, o Spring Security prove um endpoint relacionado à tokens:

            /oauth/token

            Aqui estamos liberando o acesso à essas requisições.
         */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }


    /* Define os detalhes para o acesso da aplicação cliente ao servidor de autenticação.

       Com isso estamos dizendo que os tokens ficarão armazenados na memória e estarão disponíveis através
       do client client-id e do secret secret-id.
       Esses dois dados serão importantes na hora de gerarmos os tokens.
       Além disso, estamos dando acesso aos usuários através de password, authorization_code, refresh_token e implicit,
       com os escopos de leitura e/ou escrita.
       Também foram definidos os tempos que o token de acesso e o refresh token levarão para expirar, em segundos.
       O refresh token ainda não foi citado, mas ele é utilizado para atualizar o token de segurança de um usuário,
        gerando um novo token e um novo tempo de expiração.
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(CLIENT_ID)
                .secret(new BCryptPasswordEncoder().encode(SECRET))
                .authorizedGrantTypes(AUTHORIZED_GRANT_TYPES)
                .scopes(SCOPES)
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
                .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS);
    }


    /* Define configurações para os endpoints de autenticação e geração de tokens.

       Neste caso estamos definindo que nossos tokens poderão ser gerados através de requisições GET e POST
       utilizando o gerenciador authenticationManager
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }
}
