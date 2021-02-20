package org.lcdd.ses.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.lcdd.ses.back.business.Business;
import org.lcdd.ses.back.business.BusinessManager;

public class SESBusinessesGeneratorIA {

	@SuppressWarnings("unused")
	private BusinessManager businessManager;
	
	HashMap<String, List<Double>> values = new HashMap<>();
	
	public SESBusinessesGeneratorIA(BusinessManager businessManager) {
		this.businessManager = businessManager;
		for(Business b : businessManager.getBusinesses())
			try {
				check(b, 0);
			}catch(StackOverflowError e) {
				//new SESBusinessesGeneratorIA(businessManager);
			}
		businessManager.saveAll();
		System.out.println(values);
		System.exit(0);
	}

	private void check(Business b, int s) {
		List<Double> change = new ArrayList<>();
		for(int i = 0; i < 100; i++)
			change.add(loop(b));
		
		if(b.getGraphUpdater().getPrice() >= 105) {
			b.setMax(b.getMax()-0.1);
			b.getGraphUpdater().setPrice(0.0);
			check(b, s);
		}else if(b.getGraphUpdater().getPrice() <= 95) {
			b.setMin(b.getMin()+0.1);
			b.getGraphUpdater().setPrice(0.0);
			check(b, s);
		}else {
			System.out.println("==================================================================="+s);
			b.getGraphUpdater().setPrice(0.0);
			System.out.println("good."+b.getName()+": "+b.getGraphUpdater().getPrice()+", "+b.getMin()+" < "+b.getMax());
			if(s >= 10)
				values.put(b.getName(), Arrays.asList(b.getMin(), b.getMax()));
			else
				check(b, s+1);
		}
	}
	private Double loop(Business b) {
		if(!b.isStarted())
			b.start(null);
		
		if(b.getMin() > 0)
			b.setMin(b.getMin()-0.1);
		if(b.getMax() < 0)
			b.setMax(b.getMax()+0.1);
		
		System.out.println(b.getName()+": "+b.getMin() +" < "+b.getMax());
		try {
			b.getGraphUpdater().setPrice(b.getGraphUpdater().rand());
		}catch(StackOverflowError e) {}
		return b.getGraphUpdater().getPrice();
	}
	
}
