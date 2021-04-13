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

		DrawAera drawMap = new DrawAera(links, nodes);

		drawMap.setBackground(bckg_cl);

		c1.add(drawMap, BorderLayout.CENTER);
		this.add(c1);
		this.setSize(LARGEUR, HAUTEUR);
	}
}
