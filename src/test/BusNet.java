package test;

import java.util.ArrayList;
import java.util.Scanner;

import graphe.*;

public class BusNet {
    // Class
    private static Node transport = Node.create("Transport"),
            bus = Node.create("Bus"),
            ligne = Node.create("Ligne"),
            arret = Node.create("Arret");

    // Instances
    private static Node b1 = Node.create("B1"),
            b2 = Node.create("B2"),
            a = Node.create("A"),
            d = Node.create("D"),
            bourget = Node.create("Bourget"),
            halles = Node.create("Halles"),
            merrande = Node.create("Merrande");

    public static void main(String args[]) {
        /*
        // Class
        String[] queries = {
            "CREATE (:Bus)-[:Herite]->(:Transport)",
            "CREATE (:B1)-[:Herite]->(:Bus)",
            "CREATE (:A)-[:Herite]->(:Ligne)",
            "CREATE (:Halles)-[:Herite]->(:Arret)"
        };

        for( String q: queries){
            try {
                Cypher.query(q);
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        */

        //Node b1 = (Node) Node.findNodesByLabel("B1").get(0); 
        //ArrayList<Node> nodes = new ArrayList<Node>();
        //b1.parcourtEnProfondeur(nodes);
        //for(Node n: nodes) {
            //System.out.println(n);
        //}
        //System.out.println("==============");



        // ==========================================
        init();

        //for( Node e: Node.getNodesInstances() )
            //System.out.println(e.getLinked());

        //printAllNodes();
        //printNodesLinkedToBy("Halles", "Contient");

        menu();
    }

    public static void parcourtEnProfondeur(Node n0) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        Node pred = null;

        n0.parcourtEnProfondeur(nodes);

        for(Node n: nodes) {
            if( pred != null ) {
                Link ln = pred.getLinkToNode(n);
                if( ln != null ) {
                    int sens = ln.getSens();

                    System.out.print(pred);
                    if( sens == Link.ATOB )
                        System.out.print("-"+ln+"->");
                    else if( sens == Link.BTOA )
                        System.out.print("<-"+ln+"-");
                    else
                        System.out.print("-"+ln+"-");

                    System.out.println(n);
                }
            }
            pred = n;
        }
    }

    public static void printAllNodes() {
        // DONE
        for( Node n: Node.getNodesInstances())
            System.out.println(n);;
    }

    public static Node[] getNodesLinkedTo(String lbl) {
        // DONE
        ArrayList<Node> lst = new ArrayList<Node>();
        Node target = (Node) Node.findNodesByLabel( lbl ).get(0);

        for( Node n: Node.getNodesInstances() )
            if( n.isLinkedTo( target ) )
                lst.add(n);

        Node[] res = new Node[lst.size()];
        return lst.toArray(res);
    }

    public static Node[] getNodesLinkedFrom(String lbl) {
        ArrayList<Node> lst = new ArrayList<Node>();
        Node target = (Node) Node.findNodesByLabel( lbl ).get(0);

        for( Link ln: target.getLink() ) {
            if( ln.getSens() != Link.BTOA && ln.getB() != target)
                lst.add( ln.getB() );
        }

        Node[] res  = new Node[lst.size()];
        return lst.toArray(res);
    }

    public static Node[] getNodesLinkedToBy(Node target, String rel) {
        // DONE
        ArrayList<Node> lst = new ArrayList<Node>();

        for( Node n: Node.getNodesInstances() )
            if( n.isLinkedToBy( target, rel ) )
                lst.add(n);

        Node[] res = new Node[lst.size()];
        return lst.toArray(res);
    }

    public static Node[] getNodesLinkedToBy(String nodeLbl, String rel) {
        // DONE
        ArrayList<Element> nodes = Node.findNodesByLabel( nodeLbl );
        if( nodes.isEmpty() )
            return null;

        Node target = (Node) nodes.get(0);
        return getNodesLinkedToBy(target, rel);
    }

    public static ArrayList<Node> goAfromB(String dst, String dep) {
        return goAfromB(dst, dep, new ArrayList<Node>());
    }

    public static ArrayList<Node> goAfromB(String dst, String dep, ArrayList<Node> visited) {
        ArrayList<Node> correspondances = new ArrayList<Node>();

        // ligne de bus contenant l'arret de départ
        Node[] lignesDep = getNodesLinkedToBy(dep, "Contient");
        // ligne de bus contenant l'arret d'arrivé
        Node[] lignesDst = getNodesLinkedToBy(dst, "Contient");

        // on cherche les lignes en commun au deux arrets
        for(Node n: lignesDst )
            for(Node t: lignesDep)
                if( n.equals(t) )
                    correspondances.add(n);

        return correspondances;
    }

    public static void init() {
        // Inheritance
        bus.link(transport, "Herite", Link.ATOB);

        // Isntaces
        b1.link(bus, "Instance", Link.ATOB);
        b2.link(bus, "Instance", Link.ATOB);
        a.link(ligne, "Instance", Link.ATOB);
        d.link(ligne, "Instance", Link.ATOB);
        bourget.link(arret, "Instance", Link.ATOB);
        halles.link(arret, "Instance", Link.ATOB);
        merrande.link(arret, "Instance", Link.ATOB);

        // Others
        b1.link(a, "Parcourt", Link.ATOB);
        b2.link(d, "Parcourt", Link.ATOB);
        a.link(bourget, "Contient", Link.ATOB);
        a.link(halles, "Contient", Link.ATOB);
        d.link(halles, "Contient", Link.ATOB);
        d.link(merrande, "Contient", Link.ATOB);
    }


    public static void menu() {
        Scanner input = new Scanner(System.in);
        int choix;

        do {
            System.out.println("1. get all nodes");
            System.out.println("2. get all nodes going to node 'n'");
            System.out.println("3. get all nodes comming from 'a'");
            System.out.println("4. deepth parcourt from 'a'");
            System.out.println("5. go to bus stop 'a' to bus stop 'b'");
            System.out.println("9. exit");

            choix = input.nextInt();
            switch( choix ) {
                case 1: printAllNodes(); break; // DONE
                case 2: subMenu1(input); break; // DONE
                case 3: subMenu2(input); break; // DONE
                case 4: subMenu3(input); break; // DONE
                case 5: subMenu4(input); break;
            }
        } while ( choix != 9 );

        input.close();
    }

    public static void subMenu1(Scanner input) {
        System.out.println("enter starting node: ");
        String lbl = input.next();
        Node target = (Node) Node.findNodesByLabel(lbl).get(0);

        for( Node n: getNodesLinkedTo(lbl) )
            System.out.println(lbl+"<-"+n.getLinkToNode(target)+"-"+n);
    }

    public static void subMenu2(Scanner input) {
        System.out.println("enter starting node: ");
        String lbl = input.next();
        Node target = (Node) Node.findNodesByLabel(lbl).get(0);

        for( Node n: getNodesLinkedFrom(lbl) ){
            System.out.println(lbl+"-"+target.getLinkToNode(n)+"->"+n);
        }
    }

    public static void subMenu3(Scanner input) {
        System.out.println("enter starting node: ");
        String lbl = input.next();
        Node target = (Node) Node.findNodesByLabel(lbl).get(0);

        ArrayList<Node> parcourut = new ArrayList<Node>();
        target.printParcourtEnProfondeur(parcourut);
    }

    public static void subMenu4(Scanner input) {

    }

    public static void subMenu5(Scanner input) {}
}