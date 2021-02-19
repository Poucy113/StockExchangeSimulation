package org.lcdd.ses.back.business;

public class Action {

	private double amount = 0.0;
	private Business business;
	
	public Action(double _a, Business _b) {
		this.amount = _a;
		this.business = _b;
	}
	
	public double getAmount() {return amount;}
	public void setAmount(double amount) {this.amount = amount;}
	public Business getBusiness() {return business;}
	public void setBusiness(Business business) {this.business = business;}
	
}
