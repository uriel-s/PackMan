package gameClient;


import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.css.Counter;

import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptorFactory;

import Server.Game_Server;
import Server.fruits;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.DNode;
import dataStructure.Dedge;
import dataStructure.Fruit;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import dataStructure.robot;
import utils.Point3D;
import utils.StdDraw;
import java.util.Date;

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
	static int counter =0;
	private Thread t;
	private static long time;


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
		t = new Thread(this);
		t.start();

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
				kml.AddFruit(f);

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
	private  void SetEdgeFruit() {
		Collection<node_data> search = gr.getV();
		for(Fruit fruit : fruitA) {

			for (node_data d : search) {
				int k = d.getKey();

				for(edge_data e : gr.getE(k)) {
					DNode src = (DNode) gr.getNode(e.getSrc());
					DNode dst = (DNode) gr.getNode(e.getDest());

					double SrcToDSt = src.getLocation().distance2D(dst.getLocation());

					//System.out.println("fruit :"+ fruit.getValue()+ " underTarget = "+fruit.isUnderTarget());                       

					double src2fruit = src.getLocation().distance2D(fruit.getPos());
					double fruit2dest = fruit.getPos().distance2D(dst.getLocation());
					double ans= src2fruit + fruit2dest;


					if(  Math.abs(SrcToDSt - ans) < 0.0000001  ) 
					{ 
						fruit.setEdge(e);
						//	return fruit ;

					}
				}
			}


		}
		//return fruitA.get(0);
	}

	private  Fruit findFruit() {
		for(Fruit fruit : fruitA)
			if(fruit.isUnderTarget()==false) {
				fruit.setUnderTarget(true);;
				return fruit;
			}
		return fruitA.get(0);
	}




	private void locateRobots() throws JSONException {
		JSONObject line;
		line = new JSONObject(game.toString());
		JSONObject t = line.getJSONObject("GameServer");
		int rs = t.getInt("robots");   // get the number of robots
		for(int i = 0 ; i < rs ; i++) {
			try {
				int Bestp ;
				SetEdgeFruit();
				Fruit fruit= findFruit();
				//if (fruit==null) System.out.println("this fruit is NULL!!!");
				Dedge edge =(Dedge) fruit.getEdge();
				//if (edge==null) System.out.println("this edge is NULL!!!");

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
				kml.AddRobot(r);
				StdDraw.setPenColor(Color.black);
				StdDraw.setPenRadius(0.03);
				StdDraw.picture(r.getPos().x(), r.getPos().y(),"ice.png",0.0005,0.0005);
			}
		}

		catch (Exception e) {
			e.printStackTrace();	
		}

	}


	public static Point3D getloc (String s)
	{
		String[] locations = s.split(",");
		double x = Double.parseDouble(locations[0]);
		double y = Double.parseDouble(locations[1]);
		double z = Double.parseDouble(locations[2]);
		Point3D p = new Point3D(x,y,z);
		return p;
	}

	public void startGameGUI() throws JSONException, IOException, InterruptedException{
		locateRobots();
		game.startGame();
		time =game.timeToEnd();
		int i=0;
		while(game.isRunning()) {

			StdDraw.enableDoubleBuffering();
			StdDraw.clear();
			//			if( i==3500) 
			//			{    kml.AddLoop();
			//			i=0;
			//			}

			paint();
			PaintFruits();
			PaintRobots();
			moveRobots(game, gr);

			int grade = FindGrade();
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


	private  Fruit FindFarFruit(int i)
	{robot r = Robots.get(i);
	findFruit();
	Graph_Algo ag= new Graph_Algo(gr);
	double MaxDis=Double.MIN_VALUE;
	double tmp;
	int x;
	Fruit ans =null;
	for(Fruit fruit : fruitA) {
		if (fruit.getEdge()!=null) {
			edge_data edge = fruit.getEdge();
			int min = Math.min( edge.getDest()  ,edge.getSrc() );
			int max = Math.max( edge.getDest()  ,edge.getSrc() );
			if(fruit.getType() == 1)	x= min;
			else	x= max;


			tmp = ag.shortestPathDist (r.getSrc(), x);
			if( tmp >MaxDis ) {
				MaxDis =  tmp;
				ans =fruit; 
			}

		}
	}
	if(ans!=null)  {
		ans.setUnderTarget(true);
	}
	return ans; 
	}


	private  Fruit FindClosestFruit(int i)
	{  
		robot r = Robots.get(i);
		Graph_Algo ag= new Graph_Algo(gr);
		double MinDis=Double.MAX_VALUE;
		double tmp;
		int x;
		Fruit ans =null;
		for(Fruit fruit : fruitA) {
			if (fruit.getEdge()!=null&&fruit.isUnderTarget()==false) {
				edge_data edge = fruit.getEdge();
				int min = Math.min( edge.getDest()  ,edge.getSrc() );
				int max = Math.max( edge.getDest()  ,edge.getSrc() );
				if(fruit.getType() == 1)	x= min;
				else	x= max;


				tmp = ag.shortestPathDist (r.getSrc(), x);
				if( tmp <MinDis ) {
					MinDis =  tmp;
					ans =fruit; 
				}

			}
		}
		if(ans!=null)  {
			ans.setUnderTarget(true);
			return ans; }

		else {
			System.out.println("didnt find fruit");
			ans = findFruit();
			if (ans==null) { System.out.println("didnt find fruit 2"); ans=fruitA.get(0);}
		}
		return ans;
	}



	/**
	 * move the robots. choosing the next node for each robot
	 * @param game
	 * @param gg
	 * @throws IOException 
	 * @throws InterruptedException 
	 */





	private  void moveRobots(game_service game, graph gg) throws IOException, InterruptedException {

				for(Fruit fruit : fruitA) {
					fruit.setUnderTarget(false);
				}

		long tmp = game.timeToEnd();
		//if(Math.abs(time-tmp)>0) {
		time = tmp;
		List<String> log = game.move();
		if(log!=null) {
			//long t = game.timeToEnd();
			long t = game.timeToEnd();

			for(int i=0;i<log.size();i++) {
				String robot_json = log.get(i);
				try {

					JSONObject line = new JSONObject(robot_json);
					JSONObject ttt = line.getJSONObject("Robot");
					int rid = ttt.getInt("id");
					int src = ttt.getInt("src");
					int dest = ttt.getInt("dest");
					int speed = ttt.getInt("speed");

					if(dest==-1) {
						robot r = Robots.get(i);
						r.setSrc(src);


						if(r.ShortWay.size()==1  || r.ShortWay.size()==0) {
							// send the robot i to build a new short way list to next node for it

							if(speed>=4)nextNode2(i,gg, src);
							else nextNode(i, gg, src);
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
		//}
	}
	/**
	 * this algorithm choose the next node the robot will torn to
	 * using the shortest path algorithm
	 * @param g
	 * @param src
	 * @return
	 * @throws InterruptedException 
	 */
	private  void nextNode(int i,graph g, int src) throws InterruptedException {
		robot r = Robots.get(i);
SetEdgeFruit();
DNode n = new DNode();
		int dest;
		n=(DNode) gr.getNode(src);
		Fruit fruit;
		if(i==2)	 fruit = findFruit();
		else  fruit =FindClosestFruit(i) ;
		
		//else fruit = FindFarFruit(i);	
		System.out.println(i);
		Dedge edge =(Dedge) fruit.getEdge();
		int min = Math.min( edge.getDest()  ,edge.getSrc() );
		int max = Math.max( edge.getDest()  ,edge.getSrc() );
		//fruit.setUnderTarget(false);

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



	private  void nextNode2(int i,graph g, int src) throws InterruptedException {
		robot r = Robots.get(i);
		SetEdgeFruit();
		{
			DNode n = new DNode();
			int dest;
			Fruit fruit;
			n=(DNode) gr.getNode(src);

			if(i==0)  fruit =findFruit();
			
			else fruit =FindClosestFruit(i);
			
			Dedge edge =(Dedge) fruit.getEdge();
			int min = Math.min( edge.getDest()  ,edge.getSrc() );
			int max = Math.max( edge.getDest()  ,edge.getSrc() );
			//fruit.setUnderTarget(false);

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
			System.out.println(game.toString());
		}

		catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}