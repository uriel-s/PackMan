package algorithms;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.synth.SynthStyle;

import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.DNode;
import dataStructure.graph;
import dataStructure.node_data;
import gameClient.*;
import utils.Point3D;

public class sdf {

	public static void main(String [] args) {

//		Point3D p= new Point3D(10, 10);
//		Point3D p1= new Point3D(50, 50);
//		Point3D p2= new Point3D(60, 40);
//		Point3D p3= new Point3D(5,90);
//		Point3D p4= new Point3D(15, 0);
//		Point3D p5= new Point3D(20, 50);
//		Point3D p6= new Point3D(10, 40);
//		Point3D p7= new Point3D(70,-10);
//
//
//		DNode n = new DNode();
//		DNode n1 = new DNode();
//		DNode n2 = new DNode();
//		DNode n3 = new DNode();
//		DNode n4 = new DNode();
//		DNode n5 = new DNode();
//		DNode n6 = new DNode();
//		DNode n7 = new DNode();
//
//		n.setLocation(p);
//		n1.setLocation(p1);
//		n2.setLocation(p2);
//		n3.setLocation(p3);
//		n4.setLocation(p4);
//		n5.setLocation(p5);
//		n6.setLocation(p6);
//		n7.setLocation(p7);
//		
//		DGraph DGrahp1 = new DGraph();
//
//		DGrahp1.addNode(n);
//		DGrahp1.addNode(n1);
//		DGrahp1.addNode(n2);
//		DGrahp1.addNode(n3);
//		DGrahp1.addNode(n4);
//		DGrahp1.addNode(n5);
//		DGrahp1.addNode(n6);
//		DGrahp1.addNode(n7);
//
//		DGrahp1.connect(0, 1, 11);
//		DGrahp1.connect(0, 2, 22);
//		DGrahp1.connect(1, 0, 22);	
//		DGrahp1.connect(1, 2, 22);
//		DGrahp1.connect(2, 0, 23);
//		DGrahp1.connect(2, 1, 23);
//		DGrahp1.connect(3, 0, 24);
//		DGrahp1.connect(0, 3, 21);
//		DGrahp1.connect(6, 5, 2);
//		DGrahp1.connect(5, 4, 2);
//		DGrahp1.connect(4, 7, 2);
//		DGrahp1.connect(7, 2, 2);
//		DGrahp1.connect(2, 7, 5);
//		DGrahp1.connect(7, 4, 9);
	    DGraph DGrahp1 = new DGraph();
//		game_service game = Game_Server.getServer(scenario_num); // you have [0,23] games
        
		graph_gui g = new graph_gui(DGrahp1);
		g.setVisible(true);

	}
	

}
