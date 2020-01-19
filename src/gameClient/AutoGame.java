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
import java.util.Arrays;
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
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import utils.*;


/**
 * this class represent a gui for automatic game 
 * using algorithms to choose the movment of the robots
 * @author Yair
 *
 */
public class AutoGame implements Runnable

{
	static DGraph gr;
	public static game_service game;
	double scaleParams [];
	public static ArrayList<Fruit> fruitA = new ArrayList<>();
	public static ArrayList<robot> Robots = new ArrayList<>();
	public KML_Logger kml;
	boolean delete;

	//constructor
	public AutoGame() throws JSONException, IOException
	{

		fruitA.clear();
		AutoGame.gr=new DGraph();
		kml= new KML_Logger();
		String g = choose_level();
		//System.out.println(game.toString());
		gr.init(g);
		set_scale(game);
		paint();
		PaintFruits();
		PaintRobots();
		run();
	System.out.println("the end 3");
	
	}

	/**
	 * 
	 * @return the number of points collected so far in this level
	 * @throws JSONException
	 */
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
	 * set the scale of range (x0,x1,y0,y1) to match the coordinate in the level
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


		Collection<node_data> d =gr.getV();
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

	/**
	 * open the stddraw window and draw the graph
	 */
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
				 * draw green point on 80% way of the edge
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
	 * let the user choose witch level to active 
	 * @return
	 */
	public String choose_level() {
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
	 * this function read the fruits data from json,add it to the list of fruits object and paint them in
	 * the correct location
	 */
	private void PaintFruits() 
	{
		JSONObject line;
		try {
			Iterator<String> f_iter = game.getFruits().iterator();
			fruitA.clear();
			//read the fruits data , build a fruit object and add it to the list
			while(f_iter.hasNext())
			{
				line = new JSONObject(f_iter.next());
				JSONObject ttt = line.getJSONObject("Fruit");

				double value = ttt.getDouble("value");
				int type = ttt.getInt("type");
				String pos = ttt.getString("pos");
				Point3D p= getloc(pos);
				Fruit f = new Fruit(type,value,p);

				fruitA.add(f);
				// -1 indicates a banana
				if(f.getType()==-1) {
					StdDraw.setPenColor(Color.YELLOW);
					StdDraw.setPenRadius(0.04);
					StdDraw.point(f.getPos().x(), f.getPos().y());
					StdDraw.setPenColor(Color.black);
					StdDraw.text(f.getPos().x(), f.getPos().y(),""+f.getValue());

				}
				// 1 indicates an apple
				if(f.getType()==1) {
					StdDraw.setPenColor(Color.RED);
					StdDraw.setPenRadius(0.04);
					StdDraw.point(f.getPos().x(), f.getPos().y());
					StdDraw.setPenColor(Color.black);
					StdDraw.text(f.getPos().x(), f.getPos().y(),""+f.getValue());

				}

			}
			SortFruitsA();
		}


		catch (Exception e) {
			e.printStackTrace();	
		}
	} 
	/**
	 * sort the list of fruits by value
	 */
	private void SortFruitsA() {
        
		int n = fruitA.size();
		if(n<=1) return;
		
		for (int i = 0; i < n-1; i++) {
			for (int j = 0; j < n-i-1; j++) {
				if (fruitA.get(j).getValue() < fruitA.get(j+1).getValue()) 
				{ 
					Fruit temp = fruitA.get(j); 
					fruitA.set(j, fruitA.get(j+1) ); 
					fruitA.set(j+1, temp ); 

				}
			}
		}


	}




	/**
	 * find what edges the fruit locate on
	 * @return
	 */
	private static Fruit findFruit() {
		Collection<node_data> search = gr.getV();
		for(Fruit fruit : fruitA) {

			for (node_data d : search) {
				int k = d.getKey();

				for(edge_data e : gr.getE(k)) {
					DNode src = (DNode) gr.getNode(e.getSrc());
					DNode dst = (DNode) gr.getNode(e.getDest());

					double SrcToDSt = src.getLocation().distance2D(dst.getLocation());

					//System.out.println("fruit :"+ fruit.getValue()+ " underTarget = "+fruit.isUnderTarget());                       
					if(fruit.isUnderTarget() == false) {

						double src2fruit = src.getLocation().distance2D(fruit.getPos());
						double fruit2dest = fruit.getPos().distance2D(dst.getLocation());
						double ans= src2fruit + fruit2dest;


						if(  Math.abs(SrcToDSt - ans) < 0.00001  ) 
						{ 
							fruit.setEdge(e);
							return fruit ;

						}
					}
				}

			}
		}

		return null;
	}







	private void locateRobots() throws JSONException {
		JSONObject line;
		line = new JSONObject(game.toString());
		JSONObject t = line.getJSONObject("GameServer");
		int rs = t.getInt("robots");
		System.out.println(rs);
		for(int i = 0 ; i< rs ; i++) {
			try {
				int Bestp ;
				Fruit fruit= findFruit();
				if (fruit==null) System.out.println("this fruit is NULL!!!");
				Dedge edge =(Dedge) fruit.getEdge();
				if (edge==null) System.out.println("this edge is NULL!!!");

				int min = Math.min( edge.getDest()  ,edge.getSrc() );
				int max = Math.max( edge.getDest()  ,edge.getSrc() );
				fruit.setUnderTarget(true);

				if(fruit.getType() == 1)	Bestp= min;
				else	Bestp= max;

				game.addRobot(Bestp);
			}
			catch (Exception e) {
				e.printStackTrace();	
			}
		}
		for(Fruit fruit : fruitA) {
			fruit.setUnderTarget(false);
		}

		PaintRobots();
	}
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
				robot r = new robot( id,  speed,  src,  dest, p, value);
				Robots.add(r);
				StdDraw.setPenColor(Color.black);
				StdDraw.setPenRadius(0.03);
				StdDraw.picture(r.getPos().x(), r.getPos().y(),"ice.png",0.0005,0.0005);
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

	public void startGameGUI() throws JSONException, IOException{
		locateRobots();
		game.startGame();
		int i=0;
		while(game.isRunning()) {

			StdDraw.enableDoubleBuffering();
			StdDraw.clear();
			moveRobots(game, gr);
			i++;
			if( i==3500) 
			{    kml.AddLoop();
			i=0;
			}

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
		System.out.println("the end");
		kml.End();

	}

	private static void FindClosestFruit(robot r)
	{  
		findFruit();
		Graph_Algo ag= new Graph_Algo(gr);
		double MinDis=Double.MAX_VALUE;
		int x;
		List<node_data > ShortWay =r.ShortWay;

		for(Fruit fruit : fruitA) {
			if (fruit.getEdge()!=null && !fruit.isUnderTarget()) {
				edge_data edge = fruit.getEdge();
				int min = Math.min( edge.getDest()  ,edge.getSrc() );
				int max = Math.max( edge.getDest()  ,edge.getSrc() );
				if(fruit.getType() == 1)	x= min;
				else	x= max;


				DNode n = (DNode) gr.getNode(x);    
				List<node_data > tmp = ag.shortestPath(r.getSrc(), x);
				if( tmp.size() <MinDis ) {
					MinDis =  tmp.size();
					ShortWay=tmp;
					fruit.setUnderTarget(true);
				}

			}
		}
		r.ShortWay=ShortWay;

	}


	/**
	 * move the robots. choosing the next node for each robot
	 * @param game
	 * @param gg
	 */
	private static void moveRobots(game_service game, graph gg) {

		for(Fruit fruit : fruitA) {
			fruit.setUnderTarget(false);
		}

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

						robot r =Robots.get(i);
						r.setSrc(src);

						if(r.ShortWay.size()==1  || r.ShortWay.size()==0) {
							// send the robot i to build a new short way list to next node for it
							nextNode(i,gg, src);
						}
						//System.out.println(r.ShortWay);
						if(r.ShortWay.size()>1)
							dest=r.ShortWay.get(1).getKey();
						game.chooseNextEdge(rid, dest);
						r.ShortWay.remove(0);
						//System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));
						//System.out.println(ttt);
					}
				}
				catch (JSONException e) {e.printStackTrace();}
			}
		}
	}
	/**
	 * this algo choose the next node the robot will torn to
	 * using the shortest path algorithm
	 * @param g
	 * @param src
	 * @return
	 */
	private static void nextNode(int i,graph g, int src) {
		robot r = Robots.get(i);
		{
			DNode n = new DNode();
			int dest;
			n=(DNode) gr.getNode(src);
			Fruit fruit = findFruit();
			Dedge edge =(Dedge) fruit.getEdge();
			int min = Math.min( edge.getDest()  ,edge.getSrc() );
			int max = Math.max( edge.getDest()  ,edge.getSrc() );
			fruit.setUnderTarget(false);

			int temp;
			if(fruit.getType() == -1) {
				dest= min;
				temp = max;
			} else {
				dest = max ;
				temp = min;
			}
			Graph_Algo graph_algo= new Graph_Algo(g);
			if(src != dest) {
				List<node_data > ShortWay = graph_algo.shortestPath(src, dest);
				r.ShortWay=ShortWay;

			} else {
				List<node_data > ShortWay = graph_algo.shortestPath(src, temp);
				r.ShortWay=ShortWay;
			}

		}

	}


	@Override
	public void run() {
		try {
			startGameGUI();
		} 
		catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}