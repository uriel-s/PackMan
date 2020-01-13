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
import oop_dataStructure.OOP_DGraph;
import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_graph;
import oop_dataStructure.oop_node_data;
import utils.*;



public class MyGameGUI extends JFrame implements ActionListener, MouseListener

{
	DGraph gr;

	public MyGameGUI(DGraph g)
	{
		this.gr=g;
		initGUI(gr);
	}
	public MyGameGUI(String g)
	{
		this.gr.init(g);
		initGUI(gr);
	}
	/*
	 * initiate a gui window using JFrame which shows a menu of choices do display and draw them
	 * using the algorithms of the project   
	 */
	private void initGUI(DGraph gr) 
	{
		this.setSize(500, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MenuBar menuBar = new MenuBar();
		Menu menu1 = new Menu("Menu");
		Menu menu2 = new Menu("file");

		menuBar.add(menu1);
		menuBar.add(menu2);

		this.setMenuBar(menuBar);
		MenuItem item0 = new MenuItem("draw graph");
		item0.addActionListener(this);
        
		MenuItem item1 = new MenuItem("shortest Path way");
		item1.addActionListener(this);

		MenuItem item3 = new MenuItem("tsp");
		item3.addActionListener(this);

		MenuItem item4 = new MenuItem("isConnected");
		item4.addActionListener(this);

		MenuItem item5 = new MenuItem("save to file");
		item5.addActionListener(this);

		MenuItem item6 = new MenuItem("draw from file");
		item6.addActionListener(this);

		MenuItem item7 = new MenuItem("choose level");
		item7.addActionListener(this);

		menu1.add(item0);
		menu1.add(item1);
		menu1.add(item3);
		menu1.add(item4);
		menu1.add(item7);

		menu2.add(item5);
		menu2.add(item6);



		this.addMouseListener(this);

	}
    //open the stddraw window and draw the graph
	public void paint() {

		StdDraw.setCanvasSize(1000, 500);
		StdDraw.setXscale(35,36);
		StdDraw.setYscale(32,33);
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
			StdDraw.text(x,y+4,""+k);

			for(edge_data e :  gr.getE(k)) //inner loop is drawing each node's edges
			{
				StdDraw.setPenColor(Color.RED);
				StdDraw.setPenRadius(0.004);
				int dest = e.getDest();
				node_data n = gr.getNode(dest);
				double x1 = n.getLocation().x();
				double y1 = n.getLocation().y();
				StdDraw.line(x, y, x1, y1);
				StdDraw.setPenColor(Color.BLACK);
				/*
				 * draw the weight on 40% way of the edge
				 */
				double c=0,s=0;
				if(x<x1 && y<y1) {
					c=x+(Math.abs(x-x1)*0.4);
					s=y+(Math.abs(y-y1)*0.4);
				}
				if(x>x1 && y>y1 ) {
					c=x-(Math.abs(x-x1)*0.4);
					s=y-(Math.abs(y-y1)*0.4);
				}
				if(x>x1 && y<y1 ) {
					c=x-(Math.abs(x-x1)*0.4);
					s=y+(Math.abs(y-y1)*0.4);
				}
				if(x<x1 && y>y1) {
					c=x+(Math.abs(x-x1)*0.4);
					s=y-(Math.abs(y-y1)*0.4);
				}
				StdDraw.text(c,s,""+ e.getWeight());
				/*
				 * draw yellow point on 80% way of the edge
				 */
				StdDraw.setPenColor(Color.YELLOW);
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
	@Override
	//choose witch function to apply according to the action performed from the menu 
	public void actionPerformed(ActionEvent e) 
	{
		String str = e.getActionCommand();
		if(str.equals("draw graph"))
		{
			paint();
		}
		if(str.equals("choose level"))
		{
			choose_level();
		}
		if(str.equals("shortest Path way"))
		{
			shortest_Path();
		}
		if(str.equals("tsp")) {
			tsp();
		}
		if(str.equals("isConnected")) {
			isConnected();
		}
		if(str.equals("save to file")) {
			saveToFile();
		}
		if(str.equals("draw from file")) {
			drawfromfile();
		}

	}
  private void choose_level() {
	  try {
			JFrame in = new JFrame();
			String level = JOptionPane.showInputDialog(in,"choose a level [0-23]:");
			int scenario_num =Integer.parseInt(level); 
			game_service game = Game_Server.getServer(scenario_num); 
			String g = game.getGraph();
			
	  }
	  
	  catch (Exception e) {
			e.printStackTrace();
		}
  }


	private void tsp() {
		try {
			List<Integer> targets = new ArrayList<Integer>();
			JFrame in = new JFrame();
			String str = "-1";
			String travel ="";
			while(!(travel.equals(str))){
				travel = JOptionPane.showInputDialog(in,"Enter targets Node,enter -1 when you finish:");
				int s = Integer.parseInt(travel);
				targets.add(s);
			}
			targets.remove(targets.size()-1);
			Graph_Algo newTsp = new Graph_Algo();
			newTsp.init(gr);
			paint();
			List<node_data> dis = newTsp.TSP(targets);
//			for(node_data x : dis) {
//				System.out.print(x.getKey()+"\t");
//			}
			paint();
			for (int i=0; i<dis.size()-1; i++) {
				double x1 = dis.get(i).getLocation().x();
				double y1 = dis.get(i).getLocation().y();
				double x2 = dis.get(i+1).getLocation().x();
				double y2 = dis.get(i+1).getLocation().y();

				StdDraw.setPenColor(Color.GREEN);
				StdDraw.setPenRadius(0.004);	
				StdDraw.line(x1, y1, x2, y2);
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
   /*
    * draw the graph and show on the bottom of the screen if is connected or not
    */

	private void isConnected() {
		paint();
		StdDraw.setPenColor();
		StdDraw.setFont();


		Graph_Algo graphIsC = new Graph_Algo();
		graphIsC.init(gr);
		if(graphIsC.isConnected()) {
			StdDraw.text(0,-95,"the graph is connected" );

		} else {

			StdDraw.text(0,-95,"the graph is NOT connected");


		}
	}

	private void saveToFile() {
		Graph_Algo t=new Graph_Algo();
		t.setG(this.gr);
		JFileChooser j;
		FileNameExtensionFilter filter;

		j = new JFileChooser(FileSystemView.getFileSystemView());
		j.setDialogTitle("Save graph to file"); 
		filter = new FileNameExtensionFilter(" .txt","txt");
		j.setFileFilter(filter);

		int userSelection = j.showSaveDialog(null);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			System.out.println("Save as file: " + j.getSelectedFile().getAbsolutePath());
			t.save(j.getSelectedFile().getName());

		}
	}

	private void drawfromfile() {
		this.gr = null;
		JFileChooser jf = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int returnV = jf.showOpenDialog(null);
		Graph_Algo gra = new Graph_Algo();
		if (returnV == JFileChooser.APPROVE_OPTION) {
			File selected = jf.getSelectedFile();
			gra.init(selected.getName());
		}
		this.gr = gra.getG();
		paint();
	}


	public void shortest_Path() {
		try {
			JFrame in = new JFrame();
			String Source = JOptionPane.showInputDialog(in,"Enter Source-Node:");
			String Dest = JOptionPane.showInputDialog(in,"Enter Destination-Node:");

			int src = Integer.parseInt(Source);
			int dest = Integer.parseInt(Dest);

			Graph_Algo G = new Graph_Algo();
			G.init(gr);

			List<node_data> dis = G.shortestPath(src, dest);
			double distance = G.shortestPathDist(src, dest);
			paint();
			StdDraw.setPenColor();
			StdDraw.text(0, -95, "the shortest distance between "+src+" --> "+dest+" is :"+distance);
			for (int i=0; i<dis.size()-1; i++) {
				double x1 = dis.get(i).getLocation().x();
				double y1 = dis.get(i).getLocation().y();
				double x2 = dis.get(i+1).getLocation().x();
				double y2 = dis.get(i+1).getLocation().y();

				StdDraw.setPenColor(Color.GREEN);
				StdDraw.setPenRadius(0.004);	
				StdDraw.line(x1, y1, x2, y2);
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("mouseClicked");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//System.out.println("mousePressed");

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//System.out.println("mouseReleased");

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//System.out.println("mouseEntered");

	}

	@Override
	public void mouseExited(MouseEvent e) {
		//System.out.println("mouseExited");
	}
	



}
