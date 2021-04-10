package graphe;

import java.util.ArrayList;

public class Node extends Element {
    private static ArrayList<Node> instances = new ArrayList<Node>();
    private ArrayList<Link> links = new ArrayList<Link>();

    public Node(String label) {
        super(label);

        instances.add(this);
    }

    public static Node create(String label) {
        return new Node(label);
    }

    public void link(Node b, String label, int sens) {
        Link ln = new Link(this, b, label, sens);

        this.links.add(ln);
        b.links.add(ln);
    }

    public void link(Node b, String label) {
        Link ln = new Link(this, b, label, Link.ATOB);

        this.links.add(ln);
        b.links.add(ln);
    }

    public void Dlink(Node b, String label) {
        Link ln = new Link(this, b, label, Link.DUAL);

        this.links.add(ln);
        b.links.add(ln);
    }

    public void Blink(Node b, String label) {
        Link ln = new Link(this, b, label, Link.BTOA);

        this.links.add(ln);
        b.links.add(ln);
    }

    public static ArrayList<Node> getInstances() {
        return instances;
    }

    public static ArrayList<Node> findNodesByLabel(String label) {
        ArrayList<Node> nodes = new ArrayList<Node>();

        for(Node n: instances)
            if(n.getLabel().equals(label))
                nodes.add(n);

        return nodes;
    }

    public boolean equals(Node b) {
        if( this.getLabel() != b.getLabel() ) return false;

        return true;
    }
}
