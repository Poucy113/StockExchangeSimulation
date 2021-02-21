package org.lcdd.ses.back;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.lcdd.ses.SESMain;
import org.lcdd.ses.back.business.Action;
import org.lcdd.ses.back.business.Business;
import org.lcdd.ses.frame.SESPopup;
import org.lcdd.ses.frame.SESPopup.PopupType;

public class UserManager {

    private String path = "./saves/save-<user>.json";
    private double money = 250;
    private List<Action> actions = new ArrayList<>();
    private String username;
    private boolean New;

    public UserManager(String username, int money) {
        this.money = money;
        this.username = username;
        createFiles();
        saveUser();
    }
	public UserManager(String username) {
    	this.username = username;
    	createFiles();
    	if(isNew())
    		saveUser();
    	else
    		loadUser();
	}
	
	private void createFiles() {
    	File file = new File("./saves/");
        if (!file.exists())
            file.mkdir();
        File save = new File(path.replace("<user>", username.replaceAll(" ", "_")));
        if (!save.exists())
            try {
                save.createNewFile();
                save.setReadable(true);
                save.setWritable(true);
                New = true;
            } catch (IOException e) {
            	new SESPopup(SESMain.getFrame(), "SES - Alert", "Une erreur est survenue lors de la création du fichier de sauvegarde: "+e.getLocalizedMessage(), PopupType.ALERT).onComplete((es) -> {System.exit(0);return true;});
            }
        else
        	New = false;
	}

	public void saveUser() {
        try {
            JSONObject userJson = new JSONObject();
            userJson.put("username", this.getUsername());
            userJson.put("money", this.getMoney());
            userJson.put("actions", toJSONArrayActions());
            
            Files.write(Paths.get(path.replace("<user>", username.replaceAll(" ", "_"))), userJson.toString().getBytes());
        } catch (IOException e) {
        	new SESPopup(SESMain.getFrame(), "SES - Alert", "Une erreur est survenue lors de la sauvegarde des données: "+e.getLocalizedMessage(), PopupType.ALERT).onComplete((es) -> {System.exit(0);return true;});
        }
    }

    private JSONArray toJSONArrayActions() {
    	JSONArray array = new JSONArray();
    	for(Action act : actions)
    		array.put(new JSONObject("{\"business\":\""+act.getBusiness().getName()+"\",\"amount\":"+act.getAmount()+"}"));
		return array;
	}
    
	public void loadUser() {
        try {
        	JSONObject obj = new JSONObject(new String(Files.readAllBytes(Paths.get(path.replace("<user>", username.replaceAll(" ", "_"))))));
        	JSONArray array = obj.getJSONArray("actions");
        	this.money = obj.getDouble("money");
        	for(int i = 0; i < array.length(); i++) {
        		JSONObject s = array.getJSONObject(i);
        		Business bui = null;
        		for(Business bb : SESMain.getFrame().getbManager().getBusinesses())
        			if(bb.getName().equals(s.getString("business")))
        				bui = bb;
        		if(bui != null)
        			this.actions.add(new Action(s.getDouble("amount"), bui));
        	}
        } catch (IOException e) {
            new SESPopup(SESMain.getFrame(), "SES - Alert", "Une erreur est survenue lors du chargement des données: "+e.getLocalizedMessage(), PopupType.ALERT).onComplete((es) -> {System.exit(0);return true;});
        }
    }
    
    public void buy(){
    	double price = SESMain.getFrame().getGraph().getBusiness().getGraphUpdater().getPrice();
    	//double percent = (100/price)*8;
		double amount = round(price, 2);
		double total = UserManager.round(((price+(100/price)*8)) /100, 2);
		money = money - total;
        new SESPopup(SESMain.getFrame(), "SES - Info", "Vous avez acheté une action pour "+total+"€", PopupType.ALERT);
        actions.add(new Action(amount, SESMain.getFrame().getGraph().getBusiness()));
        SESMain.getFrame().resizeFrame();
    }
    public void sell(){
        if(this.actions.size() >= 1){
            money = money+(SESMain.getFrame().getGraph().getBusiness().getGraphUpdater().getPrice() /100);
            double c = (actions.get(0).getAmount()-(SESMain.getFrame().getGraph().getBusiness().getGraphUpdater().getPrice() /100));
            new SESPopup(SESMain.getFrame(), "SES - Info", "<html>Vous avez vendu une action pour "+(SESMain.getFrame().getGraph().getBusiness().getGraphUpdater().getPrice() /100)+"€<br>"+(c <= 0 ? "+"+c : c)+"€</html>", PopupType.ALERT);
            actions.remove(0);
            SESMain.getFrame().resizeFrame();
        }else{
            new SESPopup(SESMain.getFrame(), "SES - Info", "Vous n'avez pas d'actions", PopupType.ALERT);
        }
    }
    
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    
    public double getMoney() {return money;}
    public void setMoney(double money) {this.money = money;}
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public void addMoney(int number){this.money = this.getMoney() + number;}
    public void removeMoney(int number){this.money = this.getMoney() - number;}
    public boolean isNew() {return New;}
    public List<Action> getActions() {return actions;}
    
}
