package dataStructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import dataStructure.DGraph;
import utils.Point3D;

public class DNode implements node_data, Serializable{
	private	int key;
	private int Tag;
	private double Weight;
	private Point3D Location ;
	private String Info;
	private	HashMap  <Integer, edge_data>  Edges;
	private boolean Visited;
	private List<node_data> ShortestPath;
	private Object list;

//constructors

	public  DNode() {
		this.key=DGraph.I;
		DGraph.I++;
		this.Edges = new HashMap<>();
		this.Visited=false;
		this.Weight=0;
		ShortestPath = new ArrayList <node_data> ();	
		ShortestPath.add(this);
		List<edge_data> list = new ArrayList <edge_data> ();	

	}

	public  DNode(Point3D p) {
		this.key=DGraph.I;
		DGraph.I++;
		this.Edges = new HashMap<>();
		this.Visited=false;
		this.Weight=0;
		this.Location=p;
		ShortestPath = new ArrayList <node_data> ();	
		ShortestPath.add(this);
		List<edge_data> list = new ArrayList <edge_data> ();
	}
	
	
	
	public boolean isVisited() {
		return this.Visited;
	}

	public void setVisited(boolean b) {
		this.Visited=b;
	}

	@Override
	public int getKey() {
		return this.key;
	}

	public void SetKey(int x) {
		this.key=x;
	}
	@Override
	public Point3D getLocation() {

		return this.Location;
	}
	public Collection<edge_data> getED() {
		return this.getEdges().values();
	}
	@Override
	public void setLocation(Point3D p) {
		this.Location=p;
	}

	@Override
	public double getWeight() {
		return this.Weight;
	}

	@Override
	public void setWeight(double w) {
		this.Weight=w;
	}

	@Override
	public String getInfo() {
		return this.Info;
	}

	@Override
	public void setInfo(String s) {
		this.Info=s;

	}

	@Override
	public int getTag() {
		return this.Tag;	
	}


	@Override
	public void setTag(int t) {
		this.Tag=t;

	}

	public  List<node_data> GetShortestPath()
	{
		return this.ShortestPath;
	}

	public  void setShortestPathN(List l) {
		this.ShortestPath=l;
	}



	public Dedge getEdge(int dest)
	{
		return (Dedge) this.Edges.get(dest);
	}


	public void AddEdge(Dedge e)
	{  
		this.Edges.put(e.getDest(),(edge_data) e);
	}


	public void RemoveEdge(int e)
	{  
		this.Edges.remove(e);
	}

	public String EdgesString() {
		return this.Edges.toString();
	}

	public String toString()
	{
		String s = Integer.toString(this.key);
	  return s;
	}
//HashMap repesent the other  Vertex this one conneect with/nibires
	public HashMap<Integer, edge_data> getEdges() {
		return this.Edges;
	}
	public DNode copyN(DNode n) {
		DNode ans = new DNode();
		ans.Info=n.getInfo();
		ans.key=n.getKey();
		ans.Location=n.getLocation();
		ans.Tag=n.getTag();
		ans.Weight=n.getWeight();

		ans.Edges=(HashMap<Integer, edge_data>) n.getEdges().clone(); 
				//n.EdgesDeepCopy();		
		return ans;	
	}

	
	
	
	// function for copy the nibrires vertexes
	
	private HashMap<Integer, edge_data> EdgesDeepCopy() {
		Iterator<edge_data> I=    this.getED().iterator();

		HashMap ans = new HashMap<>();
		Dedge e=  new Dedge(0);
		Dedge copiedE=  new Dedge(0);

		while(I.hasNext()) 
		{
			int i=I.next().getDest();
			e=this.getEdge(i);
			copiedE=  e.copyE();
		}
		return ans;
	}
	
}


