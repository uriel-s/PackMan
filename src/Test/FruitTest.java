package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import Server.Game_Server;
import Server.game_service;
import dataStructure.Fruit;
import gameClient.AutoGame;
import utils.Point3D;
import gameClient.AutoGame.*;

class FruitTest {
   
	Fruit f;
	@Test
	void test1() throws JSONException {
		game_service game = Game_Server.getServer(0);
		JSONObject line ;
		line = new JSONObject(game.toString());
		JSONObject t = line.getJSONObject("GameServer");
		//System.out.println(t);
		int fs = t.getInt("fruits");
		assertEquals(fs, 1);
	}
	@Test
	void testBuildFruit() throws JSONException {
		game_service game = Game_Server.getServer(0);
		JSONObject line ; 
		line = new JSONObject(game.getFruits().get(0));
		JSONObject ttt = line.getJSONObject("Fruit");
		//System.out.println(ttt);
		double value = ttt.getDouble("value");
		int type = ttt.getInt("type");
		String pos = ttt.getString("pos");
	    
		Point3D p= AutoGame.getloc(pos);
		 f = new Fruit(type,value,p);
		assertEquals(5, f.getValue());
		assertEquals(-1, f.getType());
		assertEquals(pos, p.toString());
		if(p.equals(f.getPos())==false) {
			fail("");
		}

	}
	@Test
	void test3() {
		
		
	}
	
}
