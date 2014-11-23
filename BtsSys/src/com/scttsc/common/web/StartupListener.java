package com.scttsc.common.web;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;

public class StartupListener extends ContextLoaderListener
  implements ServletContextListener
{
  private final Log log = LogFactory.getLog(getClass());

  public void contextInitialized(ServletContextEvent paramServletContextEvent)
  {
    super.contextInitialized(paramServletContextEvent);
    this.log.debug("contextInitialized");
    ServletContext localServletContext = paramServletContextEvent.getServletContext();
  }

 

 
}