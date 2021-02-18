package org.lcdd.ses.back;

import org.json.JSONObject;

import java.io.*;

public class UserManager {

    private String path = "./saves/save.json";
    private int money;
    private int actions = 0;
    private String username;

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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        saveUser();
    }

    public int getActions() {
        return actions;
    }

    public void setActions(int actions) {
        this.actions = actions;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        } catch (IOException e) {
            e.printStackTrace();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
