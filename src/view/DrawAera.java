package view;

import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;
import java.awt.*;
import graphe.*;

public class DrawAera extends Canvas implements Observer {
	private ArrayList<Node> nodes;
	private ArrayList<Link> links;
	private int radius;
	private int updates;
	private Color city_cl, link_cl, font_cl, bckg_cl;

	public DrawAera(ArrayList<Link> links, ArrayList<Node> nodes){
		super();
		this.nodes = nodes;
		this.links = links;
		this.radius = 25;
		this.updates = 0;
		this.link_cl = Palette.red;
		this.city_cl = Palette.orange;
		this.font_cl = Palette.green;
		this.bckg_cl = this.getBackground();
	}

	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(3));

	}

	public void update(Observable obs, Object obj){
		updates++;
		this.repaint();
	}
}
