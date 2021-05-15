package com.bekiruzun.todoapp.common;


import com.bekiruzun.todoapp.dao.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getUserId() {
        return getUser().getId();
    }

    public static String getUserName() {
        return getUser().getUsername();
    }
}
