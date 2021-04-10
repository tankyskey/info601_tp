package main;

import graphe.*;
import test.Test;

public class Main {
    public static void main(String[] args) {
        Test.runTest();

        // Node a = new Node("test");
        // Node b = new Node("test");
        // if( a.equals(b) ) System.out.println("YES");

        Element.query("CREATE (n:Type {key: value})");
    }

}