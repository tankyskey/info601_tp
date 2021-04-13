package test;

import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Thread;

import graphe.*;

		// quel ligne croise la ligne courante
		// horaire des bus

public class BusNet extends Thread {

    public static void main(String args[]) {
        init();
        menu();
    }

	public static void cypher() {
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

	}

    public static boolean goAtoB(Node dep, Node dst, ArrayList<Node> ligneTaken) {
		// lignes de l'arret de depart
		ArrayList<Node> lignes = dep.getPreds("Contient");

		for(Node l: lignes) {
			// si la ligne est deja visite, pass a la suivante
			if( ligneTaken.contains(l) ) continue;

			// marque la ligne comme visite
			ligneTaken.add( dep );
			ligneTaken.add( l );

			// si la ligne contient l'arret, arrete tout
			if( l.isLinkedToBy(dst, "Contient") ) {
				ligneTaken.add( dst );
				return true;
			}

			// sinon pour chaque ligne,
			for( Node stops: l.getNexts("Contient") ) {
				// si la destination est contenu, l'ajoute et retourne true
				if( goAtoB( stops, dst, ligneTaken ) ) {
					return true;
				}
			}

			// la ligne ne mene pas a la destination, on la supprime du parcourt
			ligneTaken.remove( l );
			ligneTaken.remove( dep );
		}

		// l'arret dep ne mene pas la destination
		return false;
    }

    public static void init() {
		// Class
		Node transport = (Node)Node.create("Transport").addProperty("x", "20").addProperty("y", "20"),
			 bus = (Node)Node.create("Bus").addProperty("x", "20").addProperty("y", "120"),
			 ligne = (Node)Node.create("Ligne").addProperty("x", "120").addProperty("y", "120"),
			 marque  = (Node)Node.create("Brand").addProperty("x", "100").addProperty("y", "400"),
			 arret = (Node)Node.create("Arret").addProperty("x", "450").addProperty("y", "200");

		// Instances
		Node b1 = (Node)Node.create("B1").addProperty("x", "50").addProperty("y", "220").addProperty("capacite", "50"),
			 b2 = (Node)Node.create("B2").addProperty("x", "50").addProperty("y", "300").addProperty("capacite", "100"),
			 a = (Node)Node.create("A").addProperty("x", "230").addProperty("y", "60"),
			 d = (Node)Node.create("D").addProperty("x", "230").addProperty("y", "120"),
			 b = (Node)Node.create("B").addProperty("x", "230").addProperty("y", "180"),
			 c = (Node)Node.create("C").addProperty("x", "230").addProperty("y", "240"),
			 synchro = (Node)Node.create("Synchro").addProperty("x", "170").addProperty("y", "265"),
			 mercedez = (Node)Node.create("Mercedez").addProperty("x", "170").addProperty("y", "330"),
			 bourget = (Node)Node.create("Bourget").addProperty("x", "340").addProperty("y", "60"),
			 halles = (Node)Node.create("Halles").addProperty("x", "340").addProperty("y", "120"),
			 merrande = (Node)Node.create("Merrande").addProperty("x", "340").addProperty("y", "180"),
			 plaine = (Node)Node.create("Plaine").addProperty("x", "340").addProperty("y", "240"),
			 therme = (Node)Node.create("Therme").addProperty("x", "340").addProperty("y", "300");

		// Inheritance
		bus.link(transport, "Herite", Link.ATOB);

		// Isntaces
		b1.link(bus, "Instance", Link.ATOB);
		b2.link(bus, "Instance", Link.ATOB);
		a.link(ligne, "Instance", Link.ATOB);
		d.link(ligne, "Instance", Link.ATOB);
		b.link(ligne, "Instance", Link.ATOB);
		c.link(ligne, "Instance", Link.ATOB);
		mercedez.link(marque, "Instance", Link.ATOB);
		synchro.link(marque, "Instance", Link.ATOB);
		bourget.link(arret, "Instance", Link.ATOB);
		halles.link(arret, "Instance", Link.ATOB);
		merrande.link(arret, "Instance", Link.ATOB);
		plaine.link(arret, "Instance", Link.ATOB);
		therme.link(arret, "Instance", Link.ATOB);

		// Others
		b1.link(a, "Parcourt", Link.ATOB);
		b2.link(d, "Parcourt", Link.ATOB);
		b1.link(synchro, "ConcuPar", Link.ATOB);
		b2.link(mercedez, "ConcuPar", Link.ATOB);
		a.link(bourget, "Contient", Link.ATOB);
		a.link(halles, "Contient", Link.ATOB);
		d.link(halles, "Contient", Link.ATOB);
		d.link(merrande, "Contient", Link.ATOB);
		b.link(merrande, "Contient", Link.ATOB);
		b.link(plaine, "Contient", Link.ATOB);
		c.link(plaine, "Contient", Link.ATOB);
		c.link(therme, "Contient", Link.ATOB);
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
                case 1: subMenu1(input); break; // DONE
                case 2: subMenu2(input); break; // DONE
                case 3: subMenu3(input); break; // DONE
                case 4: subMenu4(input); break; // DONE
                case 5: subMenu5(input); break;
            }

			System.out.println("===============");
        } while ( choix != 9 );

        input.close();
    }

	public static void subMenu1(Scanner input) {
        for( Node n: Node.getNodesInstances())
            System.out.println(n);;
	}

    public static void subMenu2(Scanner input) {
        System.out.println("enter starting node: ");
        String lbl = input.next();
        Node target = (Node) Node.findNodesByLabel(lbl).get(0);

        for( Node n: target.getPreds() )
            System.out.println(lbl+"<-"+n.getLinkToNode(target)+"-"+n);
    }

    public static void subMenu3(Scanner input) {
        System.out.println("enter starting node: ");
        String lbl = input.next();
        Node target = (Node) Node.findNodesByLabel(lbl).get(0);

        for( Node n: target.getNexts() ){
            System.out.println(lbl+"-"+target.getLinkToNode(n)+"->"+n);
        }
    }

    public static void subMenu4(Scanner input) {
        System.out.println("enter starting node: ");
        String lbl = input.next();
        Node target = (Node) Node.findNodesByLabel(lbl).get(0);

        ArrayList<Node> parcourut = new ArrayList<Node>();
        target.printParcourtEnProfondeur(parcourut);
    }

    public static void subMenu5(Scanner input) {
		// Correspondance
        System.out.println("enter starting stop: ");
        String lbl = input.next();
        System.out.println("enter destination: ");
        String lbl_dst = input.next();

		try {
			Node arret = (Node) Node.findNodesByLabel(lbl).get(0);
			Node arret_dst = (Node) Node.findNodesByLabel(lbl_dst).get(0);
			Node ArretClasse = (Node) Node.findNodesByLabel("Arret").get(0);

			// si l'une des valeur entree n'est pas un arret de bus, throw une erreur
			if( !arret.isLinkedToBy(ArretClasse, "Instance") || !arret_dst.isLinkedToBy(ArretClasse, "Instance") ) throw new Exception("");

			ArrayList<Node> correspondance = new ArrayList<Node>();
			if( goAtoB(arret, arret_dst, correspondance) ) {
				for( Node c: correspondance ) {
					c.setProperty("color", "black");
					System.out.println( c );
				}
			} else {
				System.out.println("pas de correspondance trouve");
			}

		} catch (Exception e) {
			System.out.println( "Les arrets entré n'ont pas été trouvé" );
		}
	}

	public BusNet() {
	}

	public void run() {
		init();
		menu();
	}
}




