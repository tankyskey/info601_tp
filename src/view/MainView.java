package view;

import java.util.ArrayList;
import graphe.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainView extends JFrame{
	private int LARGEUR, HAUTEUR;
	private ArrayList<Node> nodes;
	private ArrayList<Link> links;
	private Color bckg_cl, font_cl;

	// constructeurs
	public MainView(int LARGEUR, int HAUTEUR){
		super();
		this.nodes = Node.getNodesInstances();
		this.links = Link.getLinkInstances();
		this.bckg_cl = Palette.black;
		this.font_cl = Palette.green;

		create_IHM();
		this.setVisible(true);
		this.setSize(LARGEUR, HAUTEUR);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public MainView(){
		this(500, 500);
	}

	// methodes
	public void create_IHM(){
		JPanel c1=new JPanel(new BorderLayout());
		JPanel c2=new JPanel();
		JPanel c3=new JPanel();
		JPanel c4=new JPanel();

		JLabel l1=new JLabel("Noeud: ");		l1.setForeground(font_cl);
	   	JLabel l2=new JLabel("sens (- | <-)");	l2.setForeground(font_cl);
	   	JLabel l3=new JLabel("Relation:");		l3.setForeground(font_cl);
	   	JLabel l4=new JLabel("sens (- | ->)");	l4.setForeground(font_cl);
	   	JLabel l5=new JLabel("Noeud:");			l5.setForeground(font_cl);
	   	JLabel l6=new JLabel("");				l6.setForeground(font_cl);

		JTextField f1=new JTextField("Nooder"); 
		JTextField f2=new JTextField("->");
		JTextField f3=new JTextField("Rel");
		JTextField f4=new JTextField("");
		JTextField f5=new JTextField("TheNode");
		JTextField f6=new JTextField("");

		JButton b1=new JButton("Create Node");	b1.setBackground(font_cl);
		JButton b2=new JButton("Link Nodes");	b2.setBackground(font_cl);
		JButton b3=new JButton("Search query");	b3.setBackground(font_cl);

		DrawAera drawMap = new DrawAera(links, nodes);

		b1.addActionListener(new ActionListener(){
			// Clear
			@Override
			public void actionPerformed(ActionEvent e){
				String str_n1 = f1.getText(),
					   str_r1 = f2.getText(),
					   str_r2 = f3.getText(),
					   str_n2 = f5.getText();

				Node n1 = null, n2 = null;

				if( !str_n1.isEmpty() )
					n1 = new Node(str_n1);

				if( !str_n2.isEmpty() ) 
					n2 = new Node(str_n2);

				if( !str_n1.isEmpty() && !str_n2.isEmpty() ) {
					// (n1)-[r]-(n2)
					if( str_r1.equals("--") && !str_r2.isEmpty() )
						n1.Dlink(n2, str_r2);

					// (n1)<-[r]-(n2)
					if( str_r1.equals("<-") && !str_r2.isEmpty() )
						n1.Blink(n2, str_r2);

					// (n1)-[r]->(n2)
					if( str_r1.equals("->") && !str_r2.isEmpty() )
						n1.link(n2, str_r2);
				}

				ArrayList<Node> nodes = new ArrayList<Node>();
				n1.parcourtEnProfondeur(nodes);
				Node pred = null;
				for(Node n: nodes) {
					if( pred != null ) {
						Link ln = pred.getLinkToNode(n);
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
					pred = n;
				}
				System.out.println("==============");
			}
		});
		b2.addActionListener(new ActionListener(){
			// New Map
			@Override
			public void actionPerformed(ActionEvent e){
				for( Element elt: Element.getInstances() ) {
					System.out.println(elt);
				}
			}
		});
		b3.addActionListener(new ActionListener(){
			// Launch
			@Override
			public void actionPerformed(ActionEvent e){
			}
		});

		c2.setBackground(bckg_cl);
		c4.setBackground(bckg_cl);
		drawMap.setBackground(bckg_cl);

		c3.setLayout(new BoxLayout(c3, BoxLayout.PAGE_AXIS));
		c3.add(l1); c3.add(f1);
		c3.add(l2); c3.add(f2);

		c4.setLayout(new BoxLayout(c4, BoxLayout.PAGE_AXIS));
		c4.add(l1); c4.add(f1);
		c4.add(l2); c4.add(f2);
		c4.add(l3); c4.add(f3);
		c4.add(l4); c4.add(f4);
		c4.add(l5); c4.add(f5);
		c4.add(l6); c4.add(f6);
		c4.add(b1);
		c4.add(b2);
		c4.add(b3);

		c1.add(drawMap, BorderLayout.CENTER);
		c1.add(c2, BorderLayout.EAST);
		c2.add(c4);
		this.add(c1);
		this.setSize(LARGEUR, HAUTEUR);
	}
}
