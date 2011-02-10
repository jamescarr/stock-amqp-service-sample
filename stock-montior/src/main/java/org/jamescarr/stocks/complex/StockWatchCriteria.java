package org.jamescarr.stocks.complex;

import org.jamescarr.stocks.StockPrice;

public class StockWatchCriteria {
	private String banding;
	private String direction;
	private double percentage;
	private String ticker;
	private String routingKey;
	public String getRoutingKey() {
		return routingKey;
	}
	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
	}
	public String getBanding() {
		return banding;
	}
	public void setBanding(String banding) {
		this.banding = banding;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public double getPercentage() {
		return percentage / 100;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public boolean matches(StockPrice price) {
		if(!price.getTicker().equals(ticker)) return false;
		
		double actualPercentage = price.getChange() / price.getPrice();
		if(price.getPrice() > price.getPrevious()){
			if(banding.equals(">=") && direction.equals("up")){
				return actualPercentage >= getPercentage();
			}else if(banding.equals("<=") && direction.equals("up")){
				return actualPercentage <= getPercentage();
			}
			
		}else{
			if(banding.equals(">=") && direction.equals("down")){
				return -actualPercentage >= getPercentage();
			}else if(banding.equals("<=") && direction.equals("down")){
				return -actualPercentage <= getPercentage();
			}
		}
		return false;
	}
	@Override
	public String toString() {
		return "percentage: " + percentage;
	}
	
	
}
