package org.lcdd.ses.back;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.lcdd.ses.frame.SESPopup;
import org.lcdd.ses.frame.SESPopup.PopupType;

public class UserManager {

    private String path = "./saves/save-<user>.json";
    private double money = 250;
    private int actions = 0;
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
        File save = new File(path.replace("<user>", username));
        if (!save.exists())
            try {
                save.createNewFile();
                save.setReadable(true);
                save.setWritable(true);
                New = true;
            } catch (IOException e) {
            	new SESPopup(null, "SES - Alert", "Une erreur est survenue lors de la création du fichier de sauvegarde: "+e.getLocalizedMessage(), PopupType.ALERT).onComplete((es) -> System.exit(0));
            }
        else
        	New = false;
	}

	public void saveUser() {
        try {
            JSONObject userJson = new JSONObject();
            userJson.put("username", this.getUsername());
            userJson.put("money", this.getMoney());
            userJson.put("actions", this.getActions());
            
            Files.write(Paths.get(path.replace("<user>", username)), userJson.toString().getBytes());
        } catch (IOException e) {
        	new SESPopup(null, "SES - Alert", "Une erreur est survenue lors de la sauvegarde des données: "+e.getLocalizedMessage(), PopupType.ALERT).onComplete((es) -> System.exit(0));
        }
    }

    public void loadUser() {
        try {
        	JSONObject obj = new JSONObject(Files.readString(Paths.get(path.replace("<user>", username))));
        	this.money = obj.getDouble("money");
        	this.actions = obj.getInt("actions");
        } catch (IOException e) {
            new SESPopup(null, "SES - Alert", "Une erreur est survenue lors du chargement des données: "+e.getLocalizedMessage(), PopupType.ALERT).onComplete((es) -> System.exit(0));
        }
    }
    
    public void buy(){
        //if(money >= Math.round((100/GraphUpdater.getPrice())*8))    
    		money = money - Math.round((100/GraphUpdater.getPrice())*8);
            new SESPopup(null, "SES - Info", "Vous avez acheté une action pour " + Math.round((100/GraphUpdater.getPrice())*8), PopupType.ALERT);
            actions++;
        /*}else{
            SESPopup popup = new SESPopup(null,"SES - Info", "Vous n'avez pas assez d'argent", PopupType.BOOLEAN);
        }*/
    }
    public void sell(){
        if(this.actions >= 1){
            money = money + GraphUpdater.getPrice();
            new SESPopup(null, "SES - Info", "Vous avez vendu une action pour " + Math.round((100/GraphUpdater.getPrice())*8), PopupType.ALERT);
            actions--;
        }else{
            new SESPopup(null, "SES - Info", "Vous n'avez pas d'actions", PopupType.BOOLEAN);
        }
    }
    
    public int getActions() {return actions;}
    public void setActions(int actions) {this.actions = actions;}
    public double getMoney() {return money;}
    public void setMoney(double money) {this.money = money;}
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public void addAction(int number){this.actions = this.getActions() + number;}
    public void removeAction(int number){this.actions = this.getActions() - number;}
    public void addMoney(int number){this.money = this.getMoney() + number;}
    public void removeMoney(int number){this.money = this.getMoney() - number;}
    public boolean isNew() {return New;}
    
}
