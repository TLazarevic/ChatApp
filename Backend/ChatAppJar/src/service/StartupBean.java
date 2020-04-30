package service;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import beans.Host;

@Startup
@Singleton
public class StartupBean {
 
	@EJB Host host ;
	
	
 @PostConstruct
 public void init() {
 
 }
  
}