package org.lcdd.ses.back.business;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.lcdd.ses.SESMain;
import org.lcdd.ses.frame.SESPopup;
import org.lcdd.ses.frame.SESPopup.PopupType;

public class BusinessManager {

    private String path = "./saves/businesses.json";
    private boolean New = false;
    
    private List<Business> businesses = new ArrayList<>();
    
    private Business baseBusiness;

    public BusinessManager(){
        File file = new File("./saves/businesses.json");
        if(!file.exists()){
            try {
                file.createNewFile();
                New = true;
                saveAll();
            }catch (IOException e){
            	new SESPopup(SESMain.getFrame(), "SES - Alert", "Une erreur est survenue lors de la création du fichier d'entreprises: "+e.getLocalizedMessage(), PopupType.ALERT).setStopOnClose(true);
            }
        }else {
        	New = false;
        	loadAll();
        }
        base();
    }
    
    public void base() {
    	baseBusiness = new Business("Le coin des developpeurs", -5, 5, 1250);
    	if(isNew()) {
    		businesses.add(new Business("Le jardin des lapins", -4, 4, 850));
    		businesses.add(new Business("Axelapin", -2, 7, 650));
    		businesses.add(new Business("Fondactul", -3, 3, 1120));
    	}
    }

    public void loadAll(){
       try {
    	   JSONArray obj = new JSONArray(new String(Files.readAllBytes(Paths.get(path))));
           for(int i = 0; i < obj.length(); i++) {
           		JSONObject o = obj.getJSONObject(i);
           		businesses.add(new Business(o.getString("name"), o.getInt("min"), o.getInt("max"), o.getInt("max-update-time")));
           }
       }catch(IOException e) {
    	   new SESPopup(SESMain.getFrame(), "SES - Alert", "Une erreur est survenue lors du chargement du fichier d'entreprises: "+e.getLocalizedMessage(), PopupType.ALERT).setStopOnClose(true);
       }
    }
    public void saveAll(){
        try {
        	JSONArray obj = new JSONArray();
        	for(Business b : businesses)
    	        obj.put(For(b));
            Files.write(Paths.get(path), obj.toString().getBytes());
        }catch (IOException e){
        	new SESPopup(SESMain.getFrame(), "SES - Alert", "Une erreur est survenue lors de la sauvegarde du fichier d'entreprises: "+e.getLocalizedMessage(), PopupType.ALERT).setStopOnClose(true);
        }
    }
    
    private JSONObject For(Business b) {
    	JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", b.getName());
        jsonObject.put("max-update-time", b.getMaxUpdateTime());
        jsonObject.put("max", b.getMax());
        jsonObject.put("min", b.getMin());
		return jsonObject;
	}

	public boolean isNew() {return New;}
    public List<Business> getBusinesses() {return businesses;}
    public Business getBaseBusiness() {return baseBusiness;}
    
}
