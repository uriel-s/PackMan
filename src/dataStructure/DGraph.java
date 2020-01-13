package dataStructure;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.sun.javafx.scene.paint.GradientUtils.Point;

import utils.Point3D;

import java.util.Collection;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;
public class DGraph implements graph ,Serializable{
	private int IDcounter;
	private int Ecounter;
	private int MC;
	private HashMap<Integer, node_data> Vertex;
	public static  int I=0;
	//constructors/

	public  DGraph()
	{
		HashMap h=new HashMap<Integer, node_data>();
		this.Vertex= h;
		IDcounter=0;
		Ecounter=0;
		MC=0;
	}
	public DGraph(DGraph g)
	{
		this.IDcounter=g.Ecounter;
		this.Ecounter=g.Ecounter;
		this.MC=g.MC;
		this.IDcounter=g.IDcounter;
		this.Vertex=DeepCopyVertex(g.getVErtex());

	}
	public DGraph DGraphCopy(DGraph g)
	{
		DGraph ans = new DGraph();
		ans.IDcounter=g.Ecounter;
		ans.Ecounter=g.Ecounter;
		ans.MC=g.MC;
		ans.IDcounter=g.IDcounter;
		ans.Vertex=DeepCopyVertex(g.getVErtex());
		return ans;

	}




	public void init(String json_file) {
		JSONObject line;
		try {
			line = new JSONObject(json_file);
			JSONArray Nodes = line.getJSONArray("Nodes");
			JSONArray Edges = line.getJSONArray("Edges");


			for(int i =0; i< Nodes.length();i++)
			{
				DNode n=	new DNode();

				int id = Nodes.getJSONObject(i).getInt("id");
				String loction = Nodes.getJSONObject(i).getString("pos");
				Point3D p= getloc(loction);
				n.SetKey(id);
				n.setLocation(p);
				this.addNode(n);
			}
			for(int i =0; i< Edges.length();i++)
			{
				Dedge d  =	new Dedge();

				int src = Edges.getJSONObject(i).getInt("src");
				int dest = Edges.getJSONObject(i).getInt("dest");
				double w = Edges.getJSONObject(i).getDouble("w");

				d.setSrc(src);
				d.setDest(dest);
				d.setWeight(w);
				DNode n = (DNode) this.getNode(src);
				n.AddEdge(d);
			}
		}
		catch (JSONException e) {e.printStackTrace();}

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


	//function that (deep) copy the vertexes in a graph/()
	public HashMap<Integer, node_data> DeepCopyVertex(HashMap<Integer, node_data> vertex)
	{
		HashMap<Integer, node_data> ans = new HashMap<>();
		Iterator<node_data> I=   this.getV().iterator();
		DNode n= new  DNode();
		DNode copiedNode = new DNode();
		while(I.hasNext())
		{
			n=  (DNode) I.next();
			copiedNode=  n.copyN(n);
			ans.put(copiedNode.getKey(), copiedNode);
		}
		return ans;
	}


	@Override
	public node_data getNode(int key) {
		if (this.Vertex.isEmpty())
		{
			System.out.println("this graph is empty");
			return null;
		}
		if(!this.Vertex.containsKey(key))
		{
			throw new  RuntimeException("Node is  not exist"+key);
		}
		return this.Vertex.get(key);
	}

	@Override
	public edge_data getEdge(int src, int dest)
	{
		if(!this.Vertex.containsKey(src))
			throw new  RuntimeException("src not exist");
		if(!Vertex.containsKey(dest))
			throw new  RuntimeException("destetion not exist");

		DNode n =  (DNode) this.Vertex.get(src);
		if(!n.getEdges().containsKey(dest))
		{
			System.out.println("edge is not exist");
			return null;
		}
		Dedge e=n.getEdge(dest);
		return e;
	}

	public HashMap getVErtex()
	{
		return this.Vertex ;
	}
	@Override
	public void addNode(node_data n) {
		this.Vertex.put(n.getKey(), (DNode) n);
		IDcounter ++;
		MC++;
	}
	//this function make Edge between two Vertexes.
	@Override
	public void connect(int src, int dest, double w) {

		if(!Vertex.containsKey(src)) {
			//System.out.println("sas");

			throw new  RuntimeException("src not exist");}
		if(!Vertex.containsKey(dest))
			throw new  RuntimeException("dest not exist");

		Dedge e = new Dedge(w);
		e.setSrc(src);
		e.setDest(dest);
		e.setWeight(w);
		DNode n = (DNode) this.Vertex.get(src);
		n.AddEdge(e);
		Ecounter++;
		MC++;
	}

	@Override
	public Collection<node_data> getV() {
		return this.Vertex.values();
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		DNode n = (DNode) this.getNode(node_id);
		return n.getEdges().values();
	}
	//delete node from the graph .
	@Override
	public node_data removeNode(int key)
	{
		if(!Vertex.containsKey(key))
			throw new  RuntimeException("node is not exist");

		Iterator<node_data> itrerator= this.getV().iterator();
		DNode n= new DNode();
		n=(DNode) this.getNode(key);
		node_data x= n.copyN(n);

		//disconect all the edges that connect with this Node
		while(itrerator.hasNext())
		{
			n =(DNode) itrerator.next();
			if (n.getEdges().containsKey(key))
			{
				n.getEdges().remove(key);
				Ecounter--;
				MC++;
			}
		}

		this.Vertex.remove(key);
		IDcounter--;
		MC++;
		return x;
	}
	@Override

	public edge_data removeEdge(int src, int dest) {
		if(!Vertex.containsKey(src)) {
			throw new  RuntimeException("src not exist");}
		if(!Vertex.containsKey(dest))
			throw new  RuntimeException("dest not exist");

		DNode n=(DNode) this.Vertex.get(src);
		Dedge x= n.getEdge(dest).copyE();
		n.RemoveEdge(dest);
		Ecounter--;
		MC++;
		return x;
	}

	@Override
	public int nodeSize() {
		return this.Vertex.size();
	}

	@Override
	public int edgeSize() {
		return this.Ecounter;
	}

	@Override
	public int getMC() {
		return this.MC;
	}



}
