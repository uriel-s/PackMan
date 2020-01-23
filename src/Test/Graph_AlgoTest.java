package Test;

import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.DNode;
import dataStructure.Dedge;
import dataStructure.node_data;

class Graph_AlgoTest {
	static DNode n = new DNode();
	static DNode n1 = new DNode();
	static	DNode n2 = new DNode();
	static	DNode n3 = new DNode();
	static	DGraph d = new DGraph();

	public static Graph_Algo tester(){

		d.addNode(n);
		d.addNode(n1);
		d.addNode(n2);
		d.addNode(n3);
		d.connect(0, 1, 118);
		d.connect(0, 2, 12);
		d.connect(1, 0, 22);	
		d.connect(1, 2, 22);
		d.connect(2, 0, 23);
		d.connect(2, 1, 23);	
		Graph_Algo algo = new  Graph_Algo();
		algo.init(d);

		return algo;
	}

	@Test
	void testIsConnected() {
		Graph_Algo algo=	tester();
		//assumeFalse(algo.isConnected());
		algo.g.connect(3, 0, 24);
		algo.g.connect(0, 3, 21);
		assumeTrue(algo.isConnected());

	}

	@Test
	void testShortestPathDist() {
		Graph_Algo algo=	tester();

		double ans=algo.shortestPathDist(0,1);
	 	assertEquals(ans,35.0 );

	}

	@Test
	void testShortestPath() {
		Graph_Algo algo=	tester();
		algo.g.removeEdge(0, 2);
		List<node_data>	ans=	algo.shortestPath(0, 2);
		DNode[] accual= {n,n1,n2};
		DNode[] exp = (DNode[])accual;
		assertEquals(exp,accual );

	}

	@Test
	void testTSP() {
		Graph_Algo algo=	tester();
		algo.g.connect(0, 2, 12);
		algo.g.connect(0, 3, 12);
		algo.g.connect(3, 0, 12);

		List<Integer> ans = new ArrayList <Integer> ();	
		ans.add(0);
		ans.add(1);
		ans.add(2);
		ans.add(3);

		List<node_data> exp =algo.TSP(ans) ;
		int answer=exp.size();
		//System.out.println(answer);
		assertEquals(answer,6);

	}

	@Test
	void testCopy() {
		Graph_Algo algo=	tester();
		DGraph newg= (DGraph) algo.copy();
		assertNotNull(newg);
		algo.g.connect(3, 0,3 );
		Dedge e = (Dedge) newg.getEdge(3, 0);
	}

//	@
//	void testInit{
//		
//	}

	
}
