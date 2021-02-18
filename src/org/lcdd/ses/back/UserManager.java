package org.lcdd.ses.back;

public class UserManager {

    private int money;
    private int actions;
    private String username;

    public UserManager(String username, int money) {

        this.money = money;
        this.username = username;

    }

    public int getActions() {
        return actions;
    }

    public int getMoney() {
        return money;
    }

    public String getUsername() {
        return username;
    }

    public void setActions(int actions) {
        this.actions = actions;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
