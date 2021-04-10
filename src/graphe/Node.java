package graphe;

import java.util.ArrayList;

public class Node extends Element {
    private static ArrayList<Node> instances = new ArrayList<Node>();
    private ArrayList<Link> links;

    public Node(String label) {
        super(label);

        instances.add(this);
    }

    public void link(Node b, String label, int sens) {
        Link ln = new Link(this, b, label, sens);

        this.links.add(ln);
        b.links.add(ln);
    }

    public static ArrayList<Node> getInstances() {
        return instances;
    }

    public boolean equals(Node b) {
        if( this.getLabel() != b.getLabel() ) return false;

        return true;
    }
}
