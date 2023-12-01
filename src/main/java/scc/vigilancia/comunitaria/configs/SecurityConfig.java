package scc.vigilancia.comunitaria.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import scc.vigilancia.comunitaria.enums.UserType;
import scc.vigilancia.comunitaria.filter.AuthenticationFilter;
import scc.vigilancia.comunitaria.services.AuthenticationService;
import scc.vigilancia.comunitaria.services.UserService;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] WHITELIST_ENDPOINTS = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/base/**",
            "/auth/**",
            "/com/**"
    };
    private final UserService userService;
    private final AuthenticationService authService;

    public SecurityConfig(UserService userService,  @Lazy AuthenticationService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(new MD5PasswordEncoder()); //matching data.sql postgres requirement for md5 password encoding
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**", "/css/**", "/js/**", "/resource/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasAuthority(UserType.ADMINISTRADOR.name())
                .antMatchers("/community/create").hasAuthority(UserType.ADMINISTRADOR.name())
                .antMatchers("/community").hasAnyAuthority(UserType.ADMINISTRADOR.name(), UserType.MEMBRO.name(), UserType.MANTENEDOR.name())
                .antMatchers("/user/enter-community").hasAnyAuthority(UserType.ADMINISTRADOR.name(), UserType.MEMBRO.name(), UserType.MANTENEDOR.name())
                .antMatchers("/user/communities-to-enter").hasAnyAuthority(UserType.ADMINISTRADOR.name(), UserType.MEMBRO.name(), UserType.MANTENEDOR.name())
                .antMatchers("/user/communities").hasAnyAuthority(UserType.ADMINISTRADOR.name(), UserType.MEMBRO.name(), UserType.MANTENEDOR.name())
                .antMatchers("/user/invites-logged-user").hasAnyAuthority(UserType.ADMINISTRADOR.name(), UserType.MEMBRO.name(), UserType.MANTENEDOR.name())
                .antMatchers("/user/by-community/**").hasAnyAuthority(UserType.ADMINISTRADOR.name(), UserType.MEMBRO.name(), UserType.MANTENEDOR.name())
                .antMatchers("/post/**").hasAnyAuthority(UserType.ADMINISTRADOR.name(), UserType.MEMBRO.name(), UserType.MANTENEDOR.name())
                .antMatchers(WHITELIST_ENDPOINTS).permitAll()
                .and()
                .exceptionHandling()
                .and()
                .cors().configurationSource(corsConfigurationSource()).and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(
                        new AuthenticationFilter(userService, authService),
                        UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*"));

        config.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}