package com.quotesystem.users;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithMockUser;

import com.quotesystem.auth.AuthData;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(username = "junit", authorities = { AuthData.USER, AuthData.ADMIN })
public @interface WithUserAdmin {

}
