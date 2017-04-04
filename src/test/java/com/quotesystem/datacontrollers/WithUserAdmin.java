package com.quotesystem.datacontrollers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithMockUser;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(username = "junit", roles = { "USER", "ADMIN" })
public @interface WithUserAdmin {

}
