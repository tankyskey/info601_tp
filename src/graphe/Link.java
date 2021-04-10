package graphe;

import java.util.ArrayList;

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

    public static ArrayList<Link> getInstances() {
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
}
