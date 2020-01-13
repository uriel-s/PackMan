package gameClient;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
 import javax.swing.JPanel;
import javax.swing.JTextField;


import algorithms.*;
import dataStructure.*;
import utils.*;



public class MyGameGUI

{
	DGraph gr;
    
	public MyGameGUI()
	{
		this.gr=new DGraph();
		choose_level();
	}

	private void set_scale(game_service game) {

		StdDraw.setCanvasSize(1000,600);

		double max_x = Double.MIN_VALUE;
		double max_y = Double.MIN_VALUE;
		double min_x = Double.MAX_VALUE;
		double min_y = Double.MAX_VALUE;

		gr.init(game.getGraph());


		Collection<node_data> d =gr.getV();
		for(node_data node : d) {
			max_x = Math.max(max_x, node.getLocation().x());
			max_y = Math.max(max_y, node.getLocation().y());
			min_x = Math.min(min_x, node.getLocation().x());
			min_y = Math.min(min_y, node.getLocation().y());

		}


		StdDraw.setXscale(min_x-0.002,max_x+0.002);
		StdDraw.setYscale(min_y-0.002,max_y+0.002);
		paint();
		PaintFruits(game);
		PaintRobots(game);
	}
	
	//open the stddraw window and draw the graph
	public void paint() {

		Collection<node_data> search = gr.getV();
		StdDraw.setPenRadius(0.005);

		for (node_data d : search)   //outside loop is drawing each node
		{
			StdDraw.setPenColor(Color.BLUE);
			StdDraw.setPenRadius(0.015);

			int k = d.getKey();
			double x = d.getLocation().x();
			double y = d.getLocation().y();
			StdDraw.point(x, y);
			StdDraw.text(x,y+0.0002,""+k);

			for(edge_data e :  gr.getE(k)) //inner loop is drawing each node's edges
			{
				StdDraw.setPenColor(Color.darkGray);
				StdDraw.setPenRadius(0.004);
				int dest = e.getDest();
				node_data n = gr.getNode(dest);
				double x1 = n.getLocation().x();
				double y1 = n.getLocation().y();
				StdDraw.line(x, y, x1, y1);

				/*
				 * draw yellow point on 80% way of the edge
				 */
				StdDraw.setPenColor(Color.GREEN);
				double a=0,b=0;
				if(x<x1 && y<y1) {
					a=x+(Math.abs(x-x1)*0.8);
					b=y+(Math.abs(y-y1)*0.8);
				}
				if(x>x1 && y>y1 ) {
					a=x-(Math.abs(x-x1)*0.8);
					b=y-(Math.abs(y-y1)*0.8);
				}
				if(x>x1 && y<y1 ) {
					a=x-(Math.abs(x-x1)*0.8);
					b=y+(Math.abs(y-y1)*0.8);
				}
				if(x<x1 && y>y1) {
					a=x+(Math.abs(x-x1)*0.8);
					b=y-(Math.abs(y-y1)*0.8);
				}
				StdDraw.setPenRadius(0.015);
				StdDraw.point(a,b);
			}
		}
	}
	
	public void choose_level() {
		JSONObject line;
		try {
			JFrame in = new JFrame();
			String level = JOptionPane.showInputDialog(in,"choose a level [0-23]:");
			int scenario_num =Integer.parseInt(level); 
			game_service game = Game_Server.getServer(scenario_num);
			String g = game.getGraph();
			gr.init(g);
			line = new JSONObject(game.toString());
			JSONObject ttt = line.getJSONObject("GameServer");
			int rs = ttt.getInt("robots");
			for(int i=0;i<rs;i++) {
				game.addRobot(i);
			}
			set_scale(game);

		}
		catch (Exception e) {
			e.printStackTrace();	
		}
	}


	private void PaintFruits(game_service game) 
	{

		JSONObject line;
		try {
			Iterator<String> f_iter = game.getFruits().iterator();
			while(f_iter.hasNext())
			{
				line = new JSONObject(f_iter.next());
				JSONObject ttt = line.getJSONObject("Fruit");

				double value = ttt.getDouble("value");
				int type = ttt.getInt("type");
				String pos = ttt.getString("pos");
				Point3D p= getloc(pos);
				Fruit f = new Fruit(type,value,p);
				if(f.getType()==-1) {
					StdDraw.setPenColor(Color.YELLOW);
					StdDraw.setPenRadius(0.03);
					StdDraw.point(f.getPos().x(), f.getPos().y());

				}
				if(f.getType()==1) {
					StdDraw.setPenColor(Color.RED);
					StdDraw.setPenRadius(0.03);
					StdDraw.point(f.getPos().x(), f.getPos().y());
				}

			}
		}
		catch (Exception e) {
			e.printStackTrace();	
		}
	}
	private void PaintRobots(game_service game) {
		JSONObject line;
		try {
			Iterator<String> r_iter = game.getRobots().iterator();
			while(r_iter.hasNext())
			{
				line = new JSONObject(r_iter.next());
				JSONObject ttt = line.getJSONObject("Robot");

				double value = ttt.getDouble("value");
				int src = ttt.getInt("src");
				int dest = ttt.getInt("dest");
				int speed = ttt.getInt("speed");
				int id = ttt.getInt("id");
				String pos = ttt.getString("pos");
				Point3D p= getloc(pos);
				robot f = new robot( id,  speed,  src,  dest, p, value);
				StdDraw.setPenColor(Color.black);
				StdDraw.setPenRadius(0.03);
				StdDraw.point(f.getPos().x(), f.getPos().y());
			}
		}

		catch (Exception e) {
			e.printStackTrace();	
		}

	}

	public Point3D getloc (String s)
	{
		String[] locations = s.split(",");
		double x = Double.parseDouble(locations[0]);
		double y = Double.parseDouble(locations[1]);
		double z = Double.parseDouble(locations[2]);
		Point3D p = new Point3D(x,y,z);
		return p;
	}

	public void nextNode() {
		if(StdDraw.isMousePressed()) {
			

			
			
		}
	}
	
}
