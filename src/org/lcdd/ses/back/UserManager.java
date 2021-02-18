package org.lcdd.ses.back;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;
import org.lcdd.ses.frame.SESPopup;
import org.lcdd.ses.frame.SESPopup.PopupType;

public class UserManager {

    private String path = "./saves/save.json";
    private int money;
    private int actions = 0;
    private String username;
    private boolean New;

    public UserManager(String username, int money) {
        this.money = money;
        this.username = username;
        File file = new File("./saves/");
        if (!file.exists()) {
            file.mkdir();
        }
        File save = new File("./saves/save.json");
        if (!save.exists()) {
            try {
                save.createNewFile();
                save.setReadable(true);
                save.setWritable(true);
                New = true;
            } catch (IOException e) {
            	new SESPopup(null, "SES - Alert", "Une erreur est survenue lors de la création du fichier de sauvegarde: "+e.getLocalizedMessage(), PopupType.ALERT).onComplete((es) -> System.exit(0));
            }
        }else {
        	New = false;
        }
        saveUser();
    }

    public void saveUser() {
        JSONObject userJson = new JSONObject();
        userJson.put("username", this.getUsername());
        userJson.put("money", this.getMoney());
        userJson.put("actions", this.getActions());
        try {
            FileWriter file = new FileWriter(path);
            file.write(userJson.toString());
            System.out.println(userJson.toString());
            file.flush();
            file.close();
        } catch (IOException e) {
        	new SESPopup(null, "SES - Alert", "Une erreur est survenue lors de la sauvegarde des données: "+e.getLocalizedMessage(), PopupType.ALERT).onComplete((es) -> System.exit(0));
        }
    }

    public void loadUser() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String json = br.readLine();
            JSONObject JO = new JSONObject(json);
            this.setMoney(JO.getInt("money"));
            this.setUsername(JO.getString("username"));
            this.setActions(JO.getInt("actions"));
            br.close();
        } catch (IOException e) {
            new SESPopup(null, "SES - Alert", "Une erreur est survenue lors du chargement des données: "+e.getLocalizedMessage(), PopupType.ALERT).onComplete((es) -> System.exit(0));
        }
    }
    
    public int getActions() {return actions;}
    public void setActions(int actions) {this.actions = actions;}
    public int getMoney() {return money;}
    public void setMoney(int money) {this.money = money;}
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public void addAction(int number){this.actions = this.getActions() + number;}
    public void removeAction(int number){this.actions = this.getActions() - number;}
    public void addMoney(int number){this.money = this.getMoney() + number;}
    public void removeMoney(int number){this.money = this.getMoney() - number;}
    public boolean isNew() {return New;}
    /*public void buy(){
        if(this.money >= GraphUpdater.getBuyPrice()){
            money = money - GraphUpdater.getBuyPrice();
            SESPopup popup = new SESPopup(null,"SES - Info", "Vous avez acheté une action pour " + GraphUpdater.getBuyPrice(), PopupType.BOOLEAN);
            actions++;
        }else{
            SESPopup popup = new SESPopup(null,"SES - Info", "Vous n'avez pas assez d'argent", PopupType.BOOLEAN);
        }
    }
    public void sell(){
        if(this.actions >= 1){
            money = money + GraphUpdater.getSellPrice();
            SESPopup popup = new SESPopup(null,"SES - Info", "Vous avez vendu une action pour " + GraphUpdater.getSellPrice(), PopupType.BOOLEAN);
            actions--;
        }else{
            SESPopup popup = new SESPopup(null,"SES - Info", "Vous n'avez pas d'actions", PopupType.BOOLEAN);
        }
    }*/
    
}
