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

import com.google.gson.JsonObject;

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
/**
 * this class represent an gui for the manual robots game
 * 
 * @author Yair
 *
 */


public class ClientGame implements Runnable

{
	static DGraph gr;
	game_service game;
	double scaleParams [];

	public ClientGame() throws JSONException
	{
		ClientGame.gr=new DGraph();
		String g = choose_level();
		gr.init(g);
		set_scale(game);
		paint();
		PaintFruits();
		PaintRobots();
		run();
	}


	public int FindGrade() throws JSONException {
		String info = game.toString();
		JSONObject line;
		try {
			line = new JSONObject(info);
			JSONObject ttt = line.getJSONObject("GameServer");
			int grade = ttt.getInt("grade");
			return grade ;

		}
		catch (JSONException e) {e.printStackTrace();}
		return 0;



	}

	/**
	 * set the scale of the canvas to match to coordinate of the graph in the specified level
	 * @param game
	 * @throws JSONException
	 */

	private void set_scale(game_service game) throws JSONException {
		StdDraw.setCanvasSize(1000,600);

		double max_x = Double.MIN_VALUE;
		double max_y = Double.MIN_VALUE;
		double min_x = Double.MAX_VALUE;
		double min_y = Double.MAX_VALUE;

		gr.init(game.getGraph());


		Collection<node_data> d = gr.getV();
		for(node_data node : d) {
			max_x = Math.max(max_x, node.getLocation().x());
			max_y = Math.max(max_y, node.getLocation().y());
			min_x = Math.min(min_x, node.getLocation().x());
			min_y = Math.min(min_y, node.getLocation().y());

		}
		double arr [] = {max_x,max_y,min_x,min_y};
		this.scaleParams = arr;
		StdDraw.setXscale(min_x-0.002,max_x+0.002);
		StdDraw.setYscale(min_y-0.002,max_y+0.002);

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
	/**
	 * let the user choose witch level to play with an input window
	 * @return
	 */
	public String choose_level() {
		JSONObject line;
		try {
			JFrame in = new JFrame();
			String level = JOptionPane.showInputDialog(in,"choose a level [0-23]:");
			int scenario_num =Integer.parseInt(level); 
			this.game = Game_Server.getServer(scenario_num);
			String g = this.game.getGraph();
			return g;
		}
		catch (Exception e) {
			e.printStackTrace();	
		}
		return null;
	}

	/**
	 * get the fruits from the game server
	 * build Fruit object for each fruit 
	 * draw the fruit on the graph - red point for apple and yellow for banana
	 */
	private void PaintFruits() 
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
	/**
	 * for each robot let the user choose where to locate them in the beggining of the game
	 * use input dialog window
	 * @throws JSONException
	 */
	private void locateRobots() throws JSONException {
		JSONObject line;
		line = new JSONObject(game.toString());
		JSONObject t = line.getJSONObject("GameServer");
		int rs = t.getInt("robots");
		System.out.println(rs);
		for(int i = 0 ; i< rs ; i++) {
			try {
				String locateRobot = JOptionPane.showInputDialog(null,"choose where to put your robot,you have "+(rs-i)+" robots left" );
				int lr =Integer.parseInt(locateRobot); 
				game.addRobot(lr);
			}
			catch (Exception e) {
				e.printStackTrace();	
			}

		}
		PaintRobots();
	}
	/**
	 * paint the robots locations on the graph
	 * @throws JSONException
	 */
	private void PaintRobots() throws JSONException {
		JSONObject line;
		line = new JSONObject(game.toString());
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
				StdDraw.picture(f.getPos().x(), f.getPos().y(),"ice.png",0.0005,0.0005);
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

	public void startGameGUI() throws JSONException{
		locateRobots();
		game.startGame();
		System.out.println("robot located");
		while(game.isRunning()) {
			StdDraw.enableDoubleBuffering();
			StdDraw.clear();
			moveRobots(game, gr);
			paint();
			PaintFruits();
			PaintRobots();
			int grade=FindGrade();
			long t = game.timeToEnd();
			String TimeLeft = "Time to end : " + t;
			String Score = "your score is : " + grade;

			StdDraw.text((scaleParams[0]+scaleParams[2])/2,scaleParams[1]+0.001 , TimeLeft);
			StdDraw.text((scaleParams[0]+scaleParams[2])/2,scaleParams[1]+0.0005 , Score);
			StdDraw.show();

		}

	}





	private static void moveRobots(game_service game, graph gg) {
		List<String> log = game.move();
		if(log!=null) {
			long t = game.timeToEnd();
			for(int i=0;i<log.size();i++) {
				String robot_json = log.get(i);
				try {
					JSONObject line = new JSONObject(robot_json);
					JSONObject ttt = line.getJSONObject("Robot");
					int rid = ttt.getInt("id");
					int src = ttt.getInt("src");
					int dest = ttt.getInt("dest");

					if(dest==-1) {
						dest = nextNode(gg, src);
						game.chooseNextEdge(rid, dest);
						System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));
						System.out.println(ttt);
					}
				}
				catch (JSONException e) {e.printStackTrace();}
			}
		}
	}
	/**
	 * let the user choose next node by clicking the mouse
	 * @param g
	 * @param src
	 * @return
	 */
	private static int nextNode(graph g, int src) {

		if(StdDraw.isMousePressed()) {
			double x = StdDraw.mouseX();
			double y = StdDraw.mouseY();
			Collection<node_data> search = gr.getV();
			for(node_data d : search ) {
				Point3D p = d.getLocation();
				double _x =p.x();
				double _y =p.y();
				double distanceX = Math.abs(x-_x);
				double distanceY = Math.abs(y-_y);
				if(distanceX<0.0008 && distanceY<0.0008) {
					return d.getKey();
				}
			}

		}
		return -1;
	}

	@Override
	public void run() {
		try {
			startGameGUI();
		} catch (JSONException e) {
			e.printStackTrace();
		}		
	}

}
