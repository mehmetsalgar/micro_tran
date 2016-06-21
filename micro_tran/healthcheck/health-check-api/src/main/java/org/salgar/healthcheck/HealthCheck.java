package org.salgar.healthcheck;

public interface HealthCheck {
	final static String alive_signal = "We are alive!";
	
	public String giveAlive();
}