package view;

import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import graphe.*;

public class DrawAera extends Canvas implements Observer {
	private ArrayList<Node> nodes;
	private ArrayList<Link> links;
	private ArrayList<Shape> circles = new ArrayList<Shape>();
	private ArrayList<Shape> lines = new ArrayList<Shape>();
	private int radius;
	private int updates;
	private Color city_cl, link_cl, font_cl, bckg_cl;

	public DrawAera(ArrayList<Link> links, ArrayList<Node> nodes){
		super();
		this.nodes = nodes;
		this.links = links;
		this.radius = 50;
		this.updates = 0;
		this.link_cl = Palette.red;
		this.city_cl = Palette.orange;
		this.font_cl = Palette.green;
		this.bckg_cl = this.getBackground();

		init();
	}

	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(3));

		g2.setColor( Palette.green );
		for( Shape l: lines )
			g2.draw( l );

		g2.setColor( Palette.red );
		for( Shape c: circles )
			g2.fill( c );

		g2.setColor( Palette.yellow );
		for( Node n: nodes )
			g2.drawString( n.getLabel(), Integer.parseInt(n.getProperty("x")), Integer.parseInt(n.getProperty("y"))+radius/2 );
	}

	public void update(Observable obs, Object obj){
		updates++;
		this.repaint();
	}

	public void init() {
		for( Node n: nodes ) {
			// x = lenght * sin( a )
			// y = lenght * cos( a )
			int x = (int)(Math.random()*(double)(475 - radius ) + 25),
				y = (int)(Math.random()*(double)(500 - radius ) + 25);

			n.addProperty("x", String.valueOf(x) );
			n.addProperty("y", String.valueOf(y) );

			circles.add(new Ellipse2D.Double(x, y, radius, radius) );
			for( Link ln: n.getLink() ) {
				Node nxt = ln.getNext(n);

				if( nxt.hasProperty("x") ) {
					System.out.println( nxt );
					int halfRad = radius/2;
					lines.add(new Line2D.Double(x+halfRad, y+halfRad,
												Integer.parseInt( nxt.getProperty("x") ) + halfRad,
												Integer.parseInt( nxt.getProperty("y") ) + halfRad
												));
				}
			}
		}

	}
}
