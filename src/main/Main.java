package main;

import graphe.*;
//import test.Test;
import view.MainView;

public class Main {
    public static void main(String[] args) {
        // Test.runTest();

        // Node a = new Node("test");
        // Node b = new Node("test");
        // if( a.equals(b) ) System.out.println("YES");

        try {
            //Cypher.query("CREATE (n:Type)-[:Test]-(:T2), (:T3), (:Bus)-[:Rel]->(a:Arret)<-[:Rel]-(n:Bus2)");
            //Cypher.query("CREATE (n:a)<-[r:Rel]-(o:b)-[r2:Rel]->(p:c)");
            //Cypher.query("MATCH (n:a)<-[r:Rel]-(o:b)-[r2:Rel]->(p:c)");
            Cypher.query("CREATE (a:Test) RETURN a");
        } catch (Exception e) {
            System.err.println(e);
        }

        for( Node n: Node.getNodesInstances() )
            System.out.println( n.getLinked() );
        
        Node a = new Node("A"),
             b = new Node("B"),
             c = new Node("C"),
             d = new Node("D"),
             e = new Node("E"),
             f = new Node("F");

        a.Dlink(b, "herite");

        System.out.println("=========");
        new MainView(500, 500);
    }

}