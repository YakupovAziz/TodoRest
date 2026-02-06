package todo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //http.csrf(csrf -> csrf.disable())
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                    .authorizeHttpRequests(auth -> auth
//                            .requestMatchers(
//                                    "/admin/**",
//                                    "/admin/auth/**",
//                                    "/actuator/health").permitAll()
                            //.requestMatchers("/api/todos/**").hasRole("USER")
                            .requestMatchers(HttpMethod.POST,"/api/todos/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT,"/api/todos/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE,"/api/todos/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET,"/api/todos/**").hasAnyRole("USER", "ADMIN")
                            .requestMatchers(HttpMethod.PATCH,"/api/todos/**").hasAnyRole("USER", "ADMIN")
                            .anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder){

        UserDetails ramesh = User.builder()
                .username("ramesh")
                .password(encoder.encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("admin1984"))
                .roles("ADMIN")
                .build();

        UserDetails usermar = User.withUsername("marina")
                .password(encoder.encode("mar123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(ramesh,admin, usermar);
    }
}
