package graphe;

import java.util.ArrayList;
import java.util.Set;

public class Node extends Element {
    private static ArrayList<Node> instances = new ArrayList<Node>();
    private ArrayList<Link> links = new ArrayList<Link>();

    public Node(String label) {
        super(label);

        instances.add(this);
    }

    public Node(String label, String properties) {
        this(label);
        if( properties != null && properties.length() > 0) {
            String[] paires = properties.subSequence(1, properties.length()-1)
                            .toString()
                            .replace(" ", "")
                            .split(",");

            for( String paire: paires) {
                String key = paire.split(":")[0],
                    val = paire.split(":")[1];

                this.addProperty(key, val);
            }
        }
    }

    public static Node create(String label, String properties) {
        return new Node(label, properties);
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
        link(b, label, Link.ATOB);
    }

    public void Dlink(Node b, String label) {
        link(b, label, Link.DUAL);
    }

    public void Blink(Node b, String label) {
        link(b, label, Link.BTOA);
    }

    public static ArrayList<Node> getNodesInstances() {
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

    public String toString() {
        String res = "(:"+getLabel();

        Set<String> keys = getPorperties().keySet();
        if( keys.size() > 0)
            res += " {";
        for( String key: keys ){
            res += key +": "+getProperty(key)+", ";
        }
        res = res.replaceAll(", $", "");
        if( keys.size() > 0)
            res += "}";

        return res+")";
    }

    public String getLinked() {
        String res = this.toString();

        for(Link ln: links){
            int sens = ln.getSens();
            Node b = ln.getB();

            if(this != b) {
                if( sens == Link.BTOA ) res += "<-";
                else res += "-";

                res += ln.toString();

                if( sens == Link.ATOB ) res += "->";
                else res += "-";
            } else {
                if( sens == Link.ATOB ) res += "<-";
                else res += "-";

                res += ln.toString();

                if( sens == Link.BTOA ) res += "->";
                else res += "-";
            }

            res += ln.getNext(this).toString()+"\n\t";
        }

        return res;
    }
}
