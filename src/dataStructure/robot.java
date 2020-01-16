package dataStructure;

import java.util.ArrayList;
import java.util.List;

import utils.Point3D;

public class robot {
	private int id;
	private int speed;
	private int src;
	private int dest;
	private Point3D pos;
	private double value;
	public List<node_data > ShortWay ;
	public Fruit fruit  ;
	
	
	public robot(int id, int speed, int src, int dest, Point3D pos, double value) {
		this.id = id;
		this.speed = speed;
		this.src = src;
		this.dest = dest;
		this.pos = pos;
		this.value = value;
        this.ShortWay  = new ArrayList <node_data> ();

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getSrc() {
		return src;
	}

	public void setSrc(int src) {
		this.src = src;
	}

	public int getDest() {
		return dest;
	}

	public void setDest(int dest) {
		this.dest = dest;
	}

	public Point3D getPos() {
		return pos;
	}

	public void setPos(Point3D pos) {
		this.pos = pos;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}


}
