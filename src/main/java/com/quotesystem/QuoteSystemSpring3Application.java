package com.quotesystem;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@SpringBootApplication
@ComponentScan(basePackages = { "com.quotesystem" })
@EnableAutoConfiguration
public class QuoteSystemSpring3Application {

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
					.antMatchers(HttpMethod.POST, "/template/**").hasAuthority("ROLE_ADMIN")
					.antMatchers(HttpMethod.GET, "/template/**").hasAuthority("ROLE_USER").antMatchers("/quote/**").hasAuthority("ROLE_USER")
					.antMatchers("/logout").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
					.antMatchers("/authenticate").authenticated()
					.and().csrf().disable();
		}

	}

}