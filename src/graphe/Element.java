package graphe;

import java.util.ArrayList;
import java.util.HashMap;

public class Element {
    private static ArrayList<Element> instances = new ArrayList<Element>();
    private static int nbInstances = 0;

    private String label;
    private HashMap<String, String> properties = new HashMap<String, String>();
    private int id;

    public Element(String label) {
        this.label = label;
        this.id = nbInstances;

        instances.add(this);
        nbInstances++;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void addProperty(String key, String value) {
        this.properties.put(key, value);
    }

    public int getId() {
        return id;
    }

    public String getLabel(){
        return label;
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    public HashMap<String, String> getPorperties(){
        return properties;
    }

    public static ArrayList<Element> getIntances(){
        return instances;
    }
}