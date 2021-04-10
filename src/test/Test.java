package test;

import graphe.*;

public class Test {
    public static final Error valid_err = new Error("valid querry rejected");
    public static final Error invalid_err = new Error("invalid querry accepted");
    public static Error error;

    public static void runTest() {
        System.out.println("\n============\nvalid Query: ");
        if( !validQuery() ) {
            error = valid_err;
            throw valid_err;
        }

        System.out.println("\n[============\ninvalid Query: ");
        if( invalidQuery() ) {
            error = invalid_err;
            throw invalid_err;
        }
    }

    public static boolean validQuery() {
        return (
            Cypher.query("CREATE (n:Type)") &&
            Cypher.query("CREATE (n: Type)") &&
            Cypher.query("CREATE (n : Type)") &&
            Cypher.query("CREATE (n :Type)") &&
            Cypher.query("CREATE (:Type)") &&
            Cypher.query("CREATE (n:Type {key: value})") &&
            Cypher.query("CREATE (n: Type {key : value})") &&
            Cypher.query("CREATE (n : Type {key:value})") &&
            Cypher.query("CREATE (n :Type {key :value})") &&
            Cypher.query("CREATE (:Type {key:value})")
        );
    }

    public static boolean invalidQuery() {
        return (
            Cypher.query("Test") ||
            Cypher.query("CREATE") ||
            Cypher.query("CREATE (n)") ||
            Cypher.query("CREATE (:)") ||
            Cypher.query("CREATE (n:")
        );
    }
}