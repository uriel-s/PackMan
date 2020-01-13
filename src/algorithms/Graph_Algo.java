package algorithms;

import java.util.*;


import dataStructure.DGraph;
import dataStructure.DNode;
import dataStructure.Dedge;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author
 *
 */
public class Graph_Algo implements graph_algorithms{
	public DGraph g;
	public List<node_data> list = new ArrayList <node_data> ();

	//constructors

	public Graph_Algo()
	{
		this.g = new DGraph();
	}
	public Graph_Algo(graph g) {
		this.g=(DGraph) g;

	}
	/**
	 * Init this set of algorithms on the parameter - graph.
	 */
	public void init(graph g) {
		this.g=(DGraph) g;
	}


	public DGraph getG() {
		return g;
	}
	public void setG(DGraph g) {
		this.g = g;
	}
	public void setG(graph graph) {
		this.g = (DGraph) graph;
	}


	/**
	 * Init a graph from file
	 * @param file_name
	 */
//function
	@Override
	public void init(String file_name) {
		graph f = null;
		try
		{
			FileInputStream file = new FileInputStream(file_name);
			ObjectInputStream in = new ObjectInputStream(file);

			f = (graph)in.readObject();
			this.init(f);
			in.close();
			file.close();

			System.out.println("Object has been deserialized");
		}
		catch(IOException ex)
		{
			System.out.println("IOException is caught");
		}

		catch(ClassNotFoundException ex)
		{
			System.out.println("ClassNotFoundException is caught");
		}
	}
	/** Saves the graph to a file.
	 * 
	 * @param file_name
	 */
	@Override
	public void save(String file_name) {
		graph f = this.copy();
		try
		{
			FileOutputStream file = new FileOutputStream(file_name);
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(f);
			out.close();
			file.close();
			System.out.println("Object has been serialized");
		}
		catch(IOException ex)
		{
			//	ex.printStackTrace();
			System.out.println("IOException is caught");
		}
	}

	@Override
	public boolean isConnected() {
		Iterator<node_data> iter= g.getV().iterator();
		DNode root= (DNode) iter.next();

		//first set all node to "unvisited."
		all0(g);
		//than set all the Node that connect with Root to visited Nodes with the func Rootconect.
		Rootconect(root);
		while(iter.hasNext())
			//if one of the Node isnt rootconect return false;
		{
			DNode n= (DNode) iter.next();
			if (!n.isVisited() ) {
				return false;
			}
		}
		//than check the other way
		//checking if every Nodes connect to root
		all0(g);
		Iterator<node_data> iter2= g.getV().iterator();
		iter2.next();
		while(iter2.hasNext())
		{
			DNode n= (DNode) iter2.next();
			if(ConnectWith (n.getKey(),root.getKey())==false) {
				return false;
			}
		}
		return true;
	}


	//this fuctin set all the vertexes in a graph to "unvisited" nodes(boolean)
	public void all0(DGraph g) {
		Iterator<node_data> itrerator= g.getV().iterator();
		DNode n= new DNode();
		while( itrerator.hasNext())
		{
			n= (DNode) itrerator.next();
			n.setVisited(false);
			//make the shortpath list empty;
			List<node_data> l = new ArrayList <node_data> ();
			l.add(n);
			n.setShortestPathN(l);
		}

	}
	//this func checking(and set "isvisited") if there is a way beetwen node root to other nodes
	//only nodes that have way from root to the, will be "visited"
	public void Rootconect(DNode root)
	{
		if (root.isVisited()) return ;
		root.setVisited(true);

		Iterator<edge_data> iter= g.getE(root.getKey()).iterator();
		Dedge e=  new Dedge(0);

		while(iter.hasNext())
		{
			e=  (Dedge) iter.next();
			int key= e.getDest();
			DNode n = (DNode) g.getVErtex().get(key);
			Rootconect(n) ;
		}
	}
	//checking if there eneyway from src vertex to dest vertex .
	public boolean ConnectWith(int src,int dest)
	{
		//if they connect return true ,other keep looking with niebieors if there away to dst
		// 	  algorithm recursive/ every node going to is niebieors , and stop when  he go to node he visited allreay

		DNode n = (DNode) g.getNode(src);
		if(n.isVisited())return false;
		n.setVisited(true);
		if(n.getEdges().containsKey(dest))return true;

		Iterator<edge_data> iter= g.getE(n.getKey()).iterator();
		Dedge e= new Dedge(0);

		while(iter.hasNext())
		{
			e=(Dedge) iter.next();
			int key= e.getKey();
			n = (DNode) g.getVErtex().get(key);
			if(ConnectWith(key, dest))return true;
		}

		return false;
	}

	@Override// using dijkstra algorithm
	public double shortestPathDist(int src, int dest) {
		// set all the  Nodes wight= infinit .src Node 0
		this.invinityAll();
		this.all0(g);
		DNode Src = (DNode) g.getNode(src);
		DNode Dst = (DNode) g.getNode(dest);

		Src.setWeight(0);
		//set the distance of all the Nodes
		Sourcdijkstra(Src);
		//if there isnt way from src-->dest
		if(Dst.getWeight()==Double.MAX_VALUE)
			throw new  RuntimeException("Nodes arent connected");
		return Dst.getWeight();

	}


	public void Sourcdijkstra (DNode src)
	{ //Recursion stop conditions
		if (src.isVisited())return;
		Iterator<edge_data> I= g.getE(src.getKey()).iterator();
		Dedge e= new Dedge(0);

		//set all the nibs the min dis from src to them.
		while (I.hasNext() )
		{
			e=(Dedge) I.next();
			minWeight(e);
		}
		src.setVisited(true);
		// do the same to other niebs and than continue with Recursion.
		NeighborsDijkstra(src);
	}

	// help func to  Dijkstra/ sending all the nibs for Dijkstra func
	public void NeighborsDijkstra (DNode src)
	{
		Iterator<edge_data> I= g.getE(src.getKey()).iterator();
		Dedge e=  new Dedge(0);
		while (I.hasNext() )
		{
			e=(Dedge) I.next();
			DNode n =(DNode) g.getNode(e.getDest());
			Sourcdijkstra(n);
		}
	}


	//set the small weight(distance from root) in node between his correct weight and new weight.
	//new wight = the weight in the node src and the  edge that conect to the node;
	public void  minWeight(Dedge e)
	{
		DNode dest = (DNode) g.getNode( e.getDest() );
		DNode src = (DNode) g.getNode( e.getSrc() );
		double NewWeight= e.getWeight()+src.getWeight();
		if(NewWeight<dest.getWeight()) {
			dest.setWeight(NewWeight);
			//sendind to func that wirting the SHortetsPAsth nodes list
			List<node_data> l=SetShortList(src,dest);
			dest.setShortestPathN(l);

		}
	}

	//set the path to the dst  node / add the new node to the list of his dst node
	public List<node_data> SetShortList(DNode src,DNode dest) {
		List<node_data> ans = new ArrayList <node_data> ();
		ans.addAll(src.GetShortestPath());
		ans.add(dest);
		return ans;
	}



	public Dedge minEdge(DNode n)
	{
		Iterator<edge_data> I= g.getE(n.getKey()).iterator();
		Dedge e=  (Dedge) I.next();
		Dedge ans=e;
		double min ;
		while (I.hasNext())
		{
			e=(Dedge) I.next();
			if( e.getWeight()>ans.getWeight() ) ans=e;

		}
		return e;
	}
	//make the distance of all the nodes in the graph infinity VALUE
	public void invinityAll()
	{
		Iterator<node_data> I= g.getV().iterator();
		DNode n= new  DNode();
		while(I.hasNext())
		{
			n= (DNode) I.next();
			n.setWeight(Double.MAX_VALUE);
		}
	}


	//send the nodes list of the way from one node to other/
	@Override
	public List<node_data> shortestPath(int src, int dest) {
		//using this function to get the list

		double x =shortestPathDist(src, dest);
		DNode n=(DNode) g.getNode(dest);
		return n.GetShortestPath();

	}

	@Override
	public List<node_data> TSP(List<Integer> targets) {
		
		if(targets.size()==2)
		{
			return shortestPath(targets.get(0), targets.get(1));
		}
		List<node_data> ans = new ArrayList <node_data> ();
		Iterator<Integer> I= targets.iterator();
		int	src= I.next();
		int dest;
		
		while(I.hasNext())
		{
			dest=I.next();
			
			//System.out.println(src+" --> " +dest );
			List<node_data> tmp=shortestPath(src, dest);
			ans.addAll(tmp);
			ans.remove(ans.size()-1);
			src=dest;
			
		}
		DNode n = (DNode) g.getNode(src);
ans.add(n);
		return ans;
	}

	@Override
	public graph copy() {

		return g.DGraphCopy(g);

	}


	public Iterator<node_data> iterator() {
		return this.list.iterator();
	}
}
 