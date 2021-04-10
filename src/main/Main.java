package main;

import graphe.*;
//import test.Test;

public class Main {
    public static void main(String[] args) {
        // Test.runTest();

        // Node a = new Node("test");
        // Node b = new Node("test");
        // if( a.equals(b) ) System.out.println("YES");

        try {
            Cypher.query("CREATE (n:Type)-[:Test]-(:T2), (:T3), (:Bus)-[:Rel]->(a:Arret)<-[:Rel]-(n:Bus2)");
            //Cypher.query("CREATE (:a)<-[:Rel]-(:b)");
        } catch (Exception e) {
            System.err.println(e);
        }

        for( Node n: Node.getNodesInstances() )
            System.out.println( n.getLinked() );
        
    }

}