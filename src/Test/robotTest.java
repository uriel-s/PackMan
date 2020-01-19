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
static String JSONSTRING = "{\"Robot\":{\"id\":0,\"value\":1,\"src\":1,\"dest\":10,\"speed\":1,\"pos\":\"35.4,32.3,0.0\"}}";

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
	 {
		Point3D p = new Point3D(23.4, 12) ;
		robot  r = new robot(0, 1, 1, 10, p, 1);
		assertEquals(1,r.getSrc());
        assertEquals(0,r.getId());
        assertEquals(1,r.getValue(),0.001);
        assertEquals(10,r.getDest());
        assertEquals(p,r.getPos());
		
	 }



}