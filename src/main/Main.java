package main;

import java.lang.Thread;

import graphe.*;
import test.*;
import view.MainView;

public class Main extends Thread {
    public static void main(String[] args) {

		BusNet busNet = new BusNet();
		Main main = new Main();
		busNet.start();
		main.start();
    }

	public void run() {
		new MainView(500, 500);
	}

	public static void cypher() {
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
	}

}
