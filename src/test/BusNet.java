package test;

import graphe.*;

public class BusNet {
    public static void main(String args[]) {
        // Class
        Node transport = Node.create("Transport"),
             bus = Node.create("Bus"),
             ligne = Node.create("Ligne"),
             arret = Node.create("Arret");

        // Instances
        Node b1 = Node.create("B1"),
             b2 = Node.create("B2"),
             a = Node.create("A"),
             d = Node.create("D"),
             bourget = Node.create("Bourget"),
             halles = Node.create("Halles"),
             merrande = Node.create("Merrande");

        // ==========================================
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
}