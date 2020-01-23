package Test;

import static org.junit.Assume.assumeFalse;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import dataStructure.DGraph;
import dataStructure.DNode;
import dataStructure.Dedge;
import dataStructure.edge_data;

class DGraphTest {
	DGraph DGrahp1 = new DGraph();
	public DGraphTest() {
		DNode n = new DNode();
		DNode n1 = new DNode();
		DNode n2 = new DNode();
		DNode n3 = new DNode();
		DGrahp1.addNode(n);
		DGrahp1.addNode(n1);
		DGrahp1.addNode(n2);
		DGrahp1.addNode(n3);

	}

	@Test
	void testGetNode() {
		DNode actual= new DNode();
		DGraph a= new  DGraph();
		a.addNode(actual);
		DNode expected= new DNode();
		expected=(DNode) a.getNode(actual.getKey());

		assertEquals(expected, actual);

	}

	//		@Test
	//		void testGetEdge() {
	//Dedge expected = new Dedge(0);
	//
	//		}

	@Test
	void testAddNode() {
		DGraphTest d=new DGraphTest();
		DNode n4 =new DNode();
		int accual =d.DGrahp1.nodeSize();

		d.DGrahp1.addNode(n4);
		int expected= d.DGrahp1.nodeSize();

		assertEquals(expected,accual+1 );
	}
	@Test
	void testConnect() {
		DGraph d = new DGraph();
		DNode n = new DNode();
		DNode n1 = new DNode();
		n.SetKey(0);
		n1.SetKey(1);
		d.addNode(n);
		d.addNode(n1);
		d.connect(0, 1, 2);
		// expected = new Dedge();		

		 Dedge expected= (Dedge) d.getEdge(0, 1);

		assumeFalse( (expected==null));

	}




	@Test
	void testRemoveNode() {
		DGraphTest d=new DGraphTest();
		DNode sas= new DNode();
		sas.SetKey(0);
		d.DGrahp1.addNode(sas);

		d.DGrahp1.removeNode(0);

		assertEquals(d.DGrahp1.nodeSize(),4);
	}

	@Test
	void testRemoveEdge() {
		DGraph a= new DGraph();

		DNode n0= new DNode();
		DNode n1= new DNode();
		a.addNode(n1);
		a.addNode(n0);

		a.connect(n0.getKey(), n1.getKey(), 0);
		int	sz=a.edgeSize();
		edge_data x= a.removeEdge(n0.getKey(), n1.getKey());
		sz=a.edgeSize();
		assertEquals(sz, 0);


	}
	@Test

	void testNodesize() {
		DGraphTest d=new DGraphTest();
		int x= d.DGrahp1.nodeSize();
		assertEquals(d.DGrahp1.nodeSize(),4);

	}	
	@Test

	void testEdgeSize() {
		DGraphTest d=new DGraphTest();
		assertEquals(d.DGrahp1.nodeSize(),4);

	}
	@Test
	void testGetMC() {

		DGraph a= new DGraph();

		DNode n0= new DNode();
		DNode n1= new DNode();
		a.addNode(n1);
		a.addNode(n0);
		assertEquals(a.getMC(),2);

	}


}
