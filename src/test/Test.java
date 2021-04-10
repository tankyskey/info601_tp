package test;

import graphe.*;

public class Test {
    public static final Error valid_err = new Error("valid querry rejected");
    public static final Error invalid_err = new Error("invalid querry accepted");
    public static Error error;

    /*
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
            (Boolean)Cypher.query("CREATE (n:Type)") &&
            (Boolean)Cypher.query("CREATE (n: Type)") &&
            (Boolean)Cypher.query("CREATE (n : Type)") &&
            (Boolean)Cypher.query("CREATE (n :Type)") &&
            (Boolean)Cypher.query("CREATE (:Type)") &&
            (Boolean)Cypher.query("CREATE (n:Type {key: value})") &&
            (Boolean)Cypher.query("CREATE (n: Type {key : value})") &&
            (Boolean)Cypher.query("CREATE (n : Type {key:value})") &&
            (Boolean)Cypher.query("CREATE (n :Type {key :value})") &&
            (Boolean)Cypher.query("CREATE (:Type {key:value})")
        );
    }

    public static boolean invalidQuery() {
        return (
            (Boolean)Cypher.query("Test") ||
            (Boolean)Cypher.query("CREATE") ||
            (Boolean)Cypher.query("CREATE (n)") ||
            (Boolean)Cypher.query("CREATE (:)") ||
            (Boolean)Cypher.query("CREATE (n:")
        );
    }
    */
}