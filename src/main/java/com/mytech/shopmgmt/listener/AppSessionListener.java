package com.mytech.shopmgmt.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class AppSessionListener
 *
 */
@WebListener
public class AppSessionListener implements HttpSessionListener {

    public static int SESSION_COUNT = 0;

    @Override
    public void sessionCreated(HttpSessionEvent se)  {
        SESSION_COUNT++;
        System.out.println("Created session: " + SESSION_COUNT);
        HttpSessionListener.super.sessionCreated(se);
    }


    @Override
    public void sessionDestroyed(HttpSessionEvent se)  {
        System.out.println("Destroyed session: " + --SESSION_COUNT);
        HttpSessionListener.super.sessionDestroyed(se);
    }
	
}
