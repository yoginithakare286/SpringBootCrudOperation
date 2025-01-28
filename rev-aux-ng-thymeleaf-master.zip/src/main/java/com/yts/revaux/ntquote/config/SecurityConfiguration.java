package com.yts.revaux.ntquote.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.yts.revaux.ntquote.security.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import tech.jhipster.config.JHipsterProperties;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    private final JHipsterProperties jHipsterProperties;

    @Value("${server.servlet.context-path:/}")
    private String contextPath;

    public SecurityConfiguration(JHipsterProperties jHipsterProperties) {
        this.jHipsterProperties = jHipsterProperties;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        String path = contextPath.endsWith("/") ? contextPath : contextPath + "/";
        http
            .cors(withDefaults())
            //.csrf(csrf -> csrf.ignoringRequestMatchers(path + "/api/**"))
            .csrf(csrf ->
                csrf
                    // Enable CSRF for Thymeleaf and disable for APIs
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .ignoringRequestMatchers(path + "api/**")
            )
            .headers(headers ->
                headers
                    //.contentSecurityPolicy(csp -> csp.policyDirectives(jHipsterProperties.getSecurity().getContentSecurityPolicy()))
                    .contentSecurityPolicy(csp ->
                        csp.policyDirectives(
                            "default-src 'self'; " +
                            "style-src 'self' 'unsafe-inline' https://cdn.jsdelivr.net https://cdn.datatables.net https://cdnjs.cloudflare.com; " +
                            "script-src 'self' 'unsafe-inline' https://code.jquery.com https://cdn.datatables.net https://cdn.jsdelivr.net;"
                        )
                    )
                    .frameOptions(FrameOptionsConfig::sameOrigin)
                    .referrerPolicy(referrer -> referrer.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
                    .permissionsPolicy(permissions ->
                        permissions.policy(
                            "camera=(), fullscreen=(self), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), midi=(), payment=(), sync-xhr=()"
                        )
                    )
            )
            .authorizeHttpRequests(authz ->
                // prettier-ignore
                authz
                    .requestMatchers(mvc.pattern(path + "login2"), mvc.pattern(path + "index2"), mvc.pattern(path + "index2.html"),mvc.pattern("/*.js"),mvc.pattern("/*.txt"), mvc.pattern("/*.json"), mvc.pattern("/*.map"), mvc.pattern("/*.css"), mvc.pattern("/assets/**")).permitAll()
                    // temp fix to let team continue to work
                    .requestMatchers(mvc.pattern(path + "ntquoteui/**")).permitAll()
                    //.requestMatchers(mvc.pattern(path + "index2")).permitAll()
                    .requestMatchers(mvc.pattern(path + "hello"),mvc.pattern("/*.js"),mvc.pattern("/*.txt"), mvc.pattern("/*.json"), mvc.pattern("/*.map"), mvc.pattern("/*.css"), mvc.pattern("/assets/**")).permitAll()
                    .requestMatchers(mvc.pattern(path + "*.js"), mvc.pattern(path + "*.txt"), mvc.pattern(path + "*.json"), mvc.pattern(path + "*.map"), mvc.pattern(path + "*.css")).permitAll()
                    .requestMatchers(mvc.pattern(path + "*.ico"), mvc.pattern(path + "*.png"), mvc.pattern(path + "*.svg"), mvc.pattern(path + "*.webapp")).permitAll()
                    .requestMatchers(mvc.pattern(path + "app/**")).permitAll()
                    .requestMatchers(mvc.pattern(path + "i18n/**")).permitAll()
                    .requestMatchers(mvc.pattern(path + "content/**")).permitAll()
                    .requestMatchers(mvc.pattern(path + "swagger-ui/**")).permitAll()
                    .requestMatchers(mvc.pattern(HttpMethod.POST, path + "api/authenticate")).permitAll()
                    .requestMatchers(mvc.pattern(HttpMethod.GET, path + "api/authenticate")).permitAll()
                    .requestMatchers(mvc.pattern(path + "api/register")).permitAll()
                    .requestMatchers(mvc.pattern(path + "api/activate")).permitAll()
                    .requestMatchers(mvc.pattern(path + "api/account/reset-password/init")).permitAll()
                    .requestMatchers(mvc.pattern(path + "api/account/reset-password/finish")).permitAll()
                    .requestMatchers(mvc.pattern(path + "api/admin/**")).hasAuthority(AuthoritiesConstants.ADMIN)
                    .requestMatchers(mvc.pattern(path + "api/**")).authenticated()
                    //.requestMatchers(mvc.pattern(path + "v3/api-docs/**")).hasAuthority(AuthoritiesConstants.ADMIN)
                    .requestMatchers(mvc.pattern(path + "v3/api-docs/**")).permitAll()
                    .requestMatchers(mvc.pattern(path + "management/health")).permitAll()
                    .requestMatchers(mvc.pattern(path + "management/health/**")).permitAll()
                    .requestMatchers(mvc.pattern(path + "management/info")).permitAll()
                    .requestMatchers(mvc.pattern(path + "management/prometheus")).permitAll()
                    .requestMatchers(mvc.pattern(path + "management/**")).hasAuthority(AuthoritiesConstants.ADMIN)
            )
            .formLogin(form -> form.loginPage(path + "login2").defaultSuccessUrl(path + "nt-quote/ntquotes", true).permitAll())
            .logout(logout -> logout.logoutUrl(path + "logout").logoutSuccessUrl(path + "login?logout").permitAll())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exceptions ->
                exceptions
                    .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                    .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()));
        return http.build();
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }
}
