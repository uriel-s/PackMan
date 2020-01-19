
package gameClient;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.sun.glass.ui.Robot;

import Server.Game_Server;
import Server.game_service;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


import algorithms.*;
import dataStructure.*;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import utils.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONException;
import org.json.JSONObject;

import Server.game_service;

public class KML_Logger {
	public String KMLstring;
	public File file;
	public FileWriter writer;

	//public AutoGame AG;
	//public MyGameGUI myGG;
	//public static StringBuffer kmlStr;


	public KML_Logger() throws JSONException, IOException
	{
		//kmlStr = new StringBuffer();
	//	file  = new File("ben.kml");
		//writer = new FileWriter(file);
		//Create the file
/*		try {
			if (file.createNewFile())
			{
				System.out.println("File is created!");
			} else {

				System.out.println("File already exists.");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		//this.AG = ag;
		//this.myGG = new MyGameGUI();
		// AG.run();
		System.out.println("new  kml");
		KMLstring= startStr();
		//		writer.write(KMLstring);



	}

	private String startStr() {
		// TODO Auto-generated method stub
		return "\r\n" + 
		"<kml xmlns=\"http://earth.google.com/kml/2.2\">\r\n" + 
		"  <Document>\r\n" + 
		"    <name>Points with TimeStamps</name>\r\n" + 
		"    <Style id=\"paddle-a\">\r\n" + 
		"      <IconStyle>\r\n" + 
		"        <Icon>\r\n" + 
		"          <href>http://maps.google.com/mapfiles/kml/paddle/A.png</href>\r\n" + 
		"        </Icon>\r\n" + 
		"        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" + 
		"      </IconStyle>\r\n" + 
		"    </Style>\r\n" + 
		"    <Style id=\"paddle-b\">\r\n" + 
		"      <IconStyle>\r\n" + 
		"        <Icon>\r\n" + 
		"          <href>http://maps.google.com/mapfiles/kml/paddle/B.png</href>\r\n" + 
		"        </Icon>\r\n" + 
		"        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" + 
		"      </IconStyle>\r\n" + 
		"    </Style>\r\n" + 
		"    <Style id=\"hiker-icon\">\r\n" + 
		"      <IconStyle>\r\n" + 
		"        <Icon>\r\n" + 
		"          <href>http://maps.google.com/mapfiles/ms/icons/hiker.png</href>\r\n" + 
		"        </Icon>\r\n" + 
		"        <hotSpot x=\"0\" y=\".5\" xunits=\"fraction\" yunits=\"fraction\"/>\r\n" + 
		"      </IconStyle>\r\n" + 
		"    </Style>\r\n" + 
		"    <Style id=\"check-hide-children\">\r\n" + 
		"      <ListStyle>\r\n" + 
		"        <listItemType>checkHideChildren</listItemType>\r\n" + 
		"      </ListStyle>\r\n" + 
		"    </Style>\r\n" + 
		"    <styleUrl>#check-hide-children</styleUrl>\r\n" ;

	}
public  void addveretex() {
Iterator<DNode> I = (Iterator<DNode>) AutoGame.gr.getVErtex();
	while(I.hasNext()) 
	{ DNode n = I.next();
		String date =Date();
		Point3D p = n.getLocation();
		double x=p.x();
		double y=p.y();
		String pos="";
		pos+=x+","+y;
	KMLstring+=AddBlock(0, date, pos);
	}
} 
	public String AddBlock( int type ,String date,String coorndinate )
	{ String ans="";
		//node
		if (type == 0) {
		 ans = "<Placemark>\r\n" + 
				"      <TimeStamp>\r\n" + 
				"        <when>"+date+"</when>\r\n" + 
				"      </TimeStamp>\r\n" + 
				"      <styleUrl>#paddle-a</styleUrl>\r\n" + 
				"      <Point>\r\n" + 
				"        <coordinates>"+coorndinate+"</coordinates>\r\n" + 
				"      </Point>\r\n" + 
				"    </Placemark>\r\n" ; }

	//robot
	if(type == 1) {
		 ans = "<Placemark>\r\n" + 
				"      <TimeStamp>\r\n" + 
				"        <when>"+date+"</when>\r\n" + 
				"      </TimeStamp>\r\n" + 
				"      <styleUrl>#hiker-icon</styleUrl>\r\n" + 
				"      <Point>\r\n" + 
				"        <coordinates>"+coorndinate+"</coordinates>\r\n" + 
				"      </Point>\r\n" + 
				"    </Placemark>\r\n" ; }
	
	
	//fruit
	if(type == 2) {
		 ans = "<Placemark>\r\n" + 
				"      <TimeStamp>\r\n" + 
				"        <when>"+date+"</when>\r\n" + 
				"      </TimeStamp>\r\n" + 
				"      <styleUrl>#paddle-b</styleUrl>\r\n" + 
				"      <Point>\r\n" + 
				"        <coordinates>"+coorndinate+"</coordinates>\r\n" + 
				"      </Point>\r\n" + 
				"    </Placemark>\r\n" ; }
	
	
	
	return ans;
	}

	public void AddRobot(robot r) throws IOException
	{  

			String coorndinate= RobotLocToString(r);
			String date = Date();
			String block =AddBlock(1,date, coorndinate);
			//kmlStr.append(block);
			//System.out.print(block);
			//		writer.write( block);
			KMLstring+= block;
		}
	
	public void AddFruit(Fruit fruit) throws IOException
		{
			String coorndinate= FruitLocToString(fruit);
			String date = Date();
			String block =AddBlock(2,date, coorndinate);
			//kmlStr.append(block);
			//System.out.print(block);
			//writer.write( block);
			KMLstring+= block;

		}







	public String FruitLocToString(Fruit fruit)
	{
		Point3D p=	fruit.getPos();
		double x=p.x();
		double y=p.y();
		String ans="";
		ans+=x+","+y;
		return ans;
	}




	public String RobotLocToString(robot r)
	{    
		Point3D p= r.getPos();
		double x=p.x();
		double y=p.y();
		String ans="";
		ans+=x+","+y;
		return ans;
	}


	public void  End() throws IOException {

		//kmlStr.append("</Document>\r\n" + "</kml>") ;

		//writer.write(kmlStr+"");
		KMLstring +="</Document>\r\n" + "</kml>";
		System.out.println("the end2");
		//	if(delete==true) file.delete();
	
		    BufferedWriter writer = new BufferedWriter(new FileWriter("05.kml"));
		    writer.write(KMLstring);
		     
		    writer.close();

	}


	public String Date()
	{ 
		String ans;  
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	LocalDateTime now = LocalDateTime.now();  

	
	ans=""+now ;
	return (ans) ;




	}

}
