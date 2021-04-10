package graphe;

import java.util.ArrayList;
import java.util.Set;

public class Link extends Element {
    private static ArrayList<Link> instances = new ArrayList<Link>();

    public static final int DUAL = 0;
    public static final int ATOB = 1;
    public static final int BTOA = 2;

    private Node a, b;
    private int sens;

    public Link(Node a, Node b, String label, int sens) {
        super(label);
        this.a = a;
        this.b = b;
        this.sens = sens;

        instances.add(this);
    }

    public static ArrayList<Link> getLinkInstances() {
        return instances;
    }

    public Node getA() {
        return a;
    }

    public Node getB() {
        return b;
    }

    public Node getNext() {
        if( sens == ATOB ) return b;
        if( sens == BTOA ) return a;
        else return null;
    }

    public Node getNext(Node n) {
        if( a.equals(n) ) {
            return b;
        } else {
            return a;
        }
    }

    public String toString() {
        String res = "[:"+getLabel();
        
        Set<String> keys = getPorperties().keySet();
        if( keys.size() > 0)
            res += " {";

        for( String key: keys )
            res += key+": "+getProperty(key)+", ";

        res = res.replaceAll(", $", "");

        if( keys.size() > 0)
            res += "}";

        return res+"]";
    }

    public int getSens() {
        return sens;
    }
}
