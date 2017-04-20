package com.quotesystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.quotesystem.auth.AuthData;

@SpringBootApplication
@ComponentScan(basePackages = { "com.quotesystem" })
@EnableAutoConfiguration
public class QuoteSystemSpring3Application extends SpringBootServletInitializer {


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(QuoteSystemSpring3Application.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(QuoteSystemSpring3Application.class, args);
	}

	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Autowired
		public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication().withUser("admin").password("demo123").authorities("ROLE_ADMIN", "ROLE_USER")
					.and().withUser("sales").password("demo123").authorities("ROLE_USER");
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.httpBasic().and().sessionManagement().sessionFixation().migrateSession() // invalidate old session if user logs in again
					.and().authorizeRequests()
					.antMatchers(HttpMethod.OPTIONS).permitAll()
					.antMatchers("/template/**").hasAnyAuthority(AuthData.ADMIN, AuthData.USER)
					.antMatchers("/quote/**").hasAnyAuthority(AuthData.ADMIN, AuthData.USER)
					.antMatchers("/logout").hasAnyAuthority(AuthData.ADMIN, AuthData.USER)
					.antMatchers("/authenticate").authenticated()
					.and().csrf().disable();
		}

	}

}