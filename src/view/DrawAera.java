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
		int x = 10, y = 500;
		double a = 0, length = 100;

		for( Node n: nodes ) {
			if( !n.hasProperty("x") ) {
				n.addProperty("x", String.valueOf(x) );
				n.addProperty("y", String.valueOf(y) );
			} else {
				x = Integer.parseInt( n.getProperty("x") );
				y = Integer.parseInt( n.getProperty("y") );
			}

			circles.add(new Ellipse2D.Double(x, y, radius, radius) );
			for( Link ln: n.getLink() ) {
				Node nxt = ln.getNext(n);

				if( nxt.hasProperty("x") ) {
					int halfRad = radius/2;
					lines.add(new Line2D.Double(x+halfRad, y+halfRad,
												Integer.parseInt( nxt.getProperty("x") ) + halfRad,
												Integer.parseInt( nxt.getProperty("y") ) + halfRad
												));
				}
			}

			a = 360/( (double)n.getLink().size() );
			x += (int)(length * Math.sin( a ));
			y += (int)(length * Math.cos( a ));
		}

	}
}

