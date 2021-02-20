package org.lcdd.ses.back;

import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EnterpriseManager {

    private String name;
    private int max;
    private int min;
    private String path = "./saves/enterprises.json";
    private GraphUpdater graph;

    public EnterpriseManager(String name){
        this.name = name;
        File file = new File("./saves/enterprises.json");
        if(!file.exists()){
            try {
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public String getName() {
        return name;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void load(){
        JSONObject obj = new JSONObject(new File("./saves/enterprises.json"));
        this.min = obj.getInt("min");
        this.max = obj.getInt("max");
    }
    public void save(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", this.getName());
        jsonObject.put("max", this.getMax());
        jsonObject.put("min", this.getMin());

        File file = new File(path);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonObject.toString());
        }catch (IOException e){
            e.printStackTrace();
        }


    }
    public void start(){
        graph = new GraphUpdater(/*is*/);
    }

    public GraphUpdater getGraph() {
        return graph;
    }
}
