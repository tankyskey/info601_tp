package main;

import graphe.*;
import test.Test;

public class Main {
    public static void main(String[] args) {
        // Test.runTest();

        // Node a = new Node("test");
        // Node b = new Node("test");
        // if( a.equals(b) ) System.out.println("YES");

        Cypher.query("CREATE (n:Type {key: value, key2: value2, a: b, c: d})");

        for( Element e: Element.getIntances() )
            System.out.println(e);
    }

}