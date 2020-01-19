package Test;

import gameClient.*;
import utils.Point3D;
import dataStructure.DGraph;
import dataStructure.DNode;
import dataStructure.robot;
import static org.junit.jupiter.api.Assertions.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.sun.xml.internal.ws.policy.spi.AssertionCreationException;

import Server.Game_Server;
import Server.game_service;

class robotTest  {

	@Test
	void testRobot() throws JSONException {
		game_service game = Game_Server.getServer(23);
		JSONObject line ;
		line = new JSONObject(game.toString());
		JSONObject t = line.getJSONObject("GameServer");
		int rs = t.getInt("robots");
		assertEquals(rs, 3);


	}


	@Test
	public void robot() 
	{	//			robot r = new robot( id,  speed,  src,  dest, p, value);
Point3D pos= new Point3D(12.5, 15);
int id=0;
int speed =3;
int src=0;
int dest=15;
double value=200; 

robot  r = new robot( id,  speed,  src,  dest, pos, value);
	assertEquals(0,r.getSrc());
	assertEquals(0,r.getId());
	assertEquals(200,r.getValue(),0.001);
	assertEquals(15,r.getDest());
	assertEquals(pos,r.getPos());

	}


	public void setId() {
		robot r1 = new robot();
		robot r2 = new robot();
		r1.setId(1);
		r2.setId(2);
		assertEquals(2,r2.getId());
		assertEquals(1,r1.getId());
	}

	@Test
	public void getId() {
		robot r1 = new robot();
		robot r2 = new robot();
		r1.setId(1);
		r2.setId(2);
		assertEquals(2,r2.getId());
		assertEquals(1,r1.getId());
	}

	




}