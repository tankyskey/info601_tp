package graphe;

import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Node extends Element {
    private static ArrayList<Node> instances = new ArrayList<Node>();
    private ArrayList<Link> links = new ArrayList<Link>();

	// ====== CSTR =====
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

	// ====== SETR =====

	// ====== GETR =====
    public static ArrayList<Node> getNodesInstances() {
        return instances;
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

    public Link getLinkToNode(Node b) {
        for( Link ln: links )
            if( ln.getNext(this).equals(b) )
                return ln;
        return null;
    }

    public ArrayList<Link> getLink() {
        return this.links;
    }

    public ArrayList<Node> getPreds() {
		ArrayList<Node> nodes = new ArrayList<Node>();

        for(Link ln: links){
			Node a = ln.getA();
			if( a != this) {
				nodes.add( a );
			}
        }

        return nodes;
    }

    public ArrayList<Node> getPreds(String lbl) {
		ArrayList<Node> nodes = new ArrayList<Node>();

        for(Link ln: links){
			Node a = ln.getA();
			if( a != this && ln.getLabel().equals(lbl) ) {
				nodes.add( a );
			}
        }

        return nodes;
    }

    public ArrayList<Node> getNexts() {
		ArrayList<Node> nodes = new ArrayList<Node>();

        for(Link ln: links){
			Node b = ln.getB();
			if( b != this) {
				nodes.add( b );
			}
        }

        return nodes;
    }

	public ArrayList<Node> getNexts(String lbl) {
		ArrayList<Node> nodes = new ArrayList<Node>();

        for(Link ln: links){
			Node b = ln.getB();
			if( b != this && ln.getLabel().equals(lbl) ) {
				nodes.add( b );
			}
        }

        return nodes;
	}

	// ====== METH =====
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

    public static ArrayList<Element> findNodesByLabel(String label) {
        ArrayList<Element> nodes = new ArrayList<Element>();

        for(Node n: instances)
            if(n.getLabel().equals(label))
                nodes.add(n);

        return nodes;
    }

    public static ArrayList<Element> findNodes(String label, String properties) {
        // TODO: finish properties matching
        ArrayList<Element> nodes = new ArrayList<Element>();
        String field = "([A-Za-z]+[0-9]*)",
               separator = "( *: *)";
        Pattern pattern = Pattern.compile(field+separator+field);
        Matcher matcher = pattern.matcher(properties);
        int nbProp = matcher.groupCount();

        for(Node n: instances) {
            if( n.getLabel().equals(label) ) {
                while( matcher.find() ) {
                    System.out.println(""+matcher.group(0)+" : "+matcher.group(3));
                }

                nodes.add(n);
            }
        }

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

    public void parcourtEnProfondeur(ArrayList<Node> parcourut) {
        if( !parcourut.contains(this) ) {
            parcourut.add(this);

            for( Link ln: links ) {
                if( ln.getB() != this ){
                    ln.getB().parcourtEnProfondeur(parcourut);
                }
            }
        }
    }

    public void printParcourtEnProfondeur(ArrayList<Node> parcourut) {
        if( !parcourut.contains(this) ) {
            parcourut.add(this);

            for( Link ln: links ) {
                if( ln.getB() != this ){
                    System.out.println(this+"-"+ln+"->"+ln.getB());
                    ln.getB().printParcourtEnProfondeur(parcourut);
                }
            }
        }
    }

    public boolean isLinkedTo(Node n) {
        if( n == this) {
            for(Link ln: links)
                if( ln.getA() == this && ln.getB() == this )
                    return true;
            return false;
        }

        for(Link ln: links)
            if( ln.getB().equals(n) )
                return true;
        return false;
    }

    public boolean isLinkedToBy(Node n, String rel) {
        if( n == this) {
            for(Link ln: links)
                if( ln.getA() == this && ln.getB() == this && ln.getLabel().equals(rel) )
                    return true;
            return false;
        }

        for(Link ln: links)
            if( ln.getLabel().equals(rel) )
                if( ln.getB().equals(n) )
                    return true;
        return false;
    }

}

