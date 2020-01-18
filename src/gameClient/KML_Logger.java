
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
	public static String KMLstring;
	public FileWriter writer;
	public File file;
	//public AutoGame AG;
	//public MyGameGUI myGG;


	public KML_Logger() throws JSONException, IOException
	{
		file  = new File("darmon.kml");
		writer = new FileWriter(file);

		//Create the file
		try {
			if (file.createNewFile())
			{
				System.out.println("File is created!");
			} else {

				System.out.println("File already exists.");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//this.AG = ag;
		//this.myGG = new MyGameGUI();
		// AG.run();
		System.out.println("new  kml");
		KMLstring= startStr();
		writer.write(KMLstring);



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

	public String AddBlock(String date,String coorndinate )
	{
		String ans = "<Placemark>\r\n" + 
						"      <TimeStamp>\r\n" + 
						"        <when>"+date+"</when>\r\n" + 
						"      </TimeStamp>\r\n" + 
						"      <styleUrl>#hiker-icon</styleUrl>\r\n" + 
						"      <Point>\r\n" + 
						"        <coordinates>"+coorndinate+"</coordinates>\r\n" + 
						"      </Point>\r\n" + 
						"    </Placemark>\r\n" ; 

		return ans;
	}

	public void AddLoop() throws IOException
	{  
		int size;
		size =AutoGame.Robots.size();
		for(int i=0;i<size;i++)
		{
			String coorndinate= RobotLocToString(i);
			String date = Date();
			String block =AddBlock(date, coorndinate);
			writer.write( block);
			//System.out.print(block);
		}
		size=AutoGame.fruitA.size()		;
		for(int i=0;i<size;i++) 
		{
			String coorndinate= FruitLocToString(i);
			String date = Date();
			String block =AddBlock(date, coorndinate);
			writer.write( block);
			//System.out.print(block);

		}

	}





	public String FruitLocToString(int i)
	{
		Point3D p=	AutoGame.fruitA.get(i).getPos();
		double x=p.x();
		double y=p.y();
		String ans="";
		ans+=x+","+y;
		return ans;
	}




	public String RobotLocToString(int i)
	{ 
		//Packmen_game.Robots.get(i).getPos();
		Point3D p= AutoGame.Robots.get(i).getPos();
		double x=p.x();
		double y=p.y();
		String ans="";
		ans+=x+","+y;
		return ans;
	}


	public void  End() throws IOException {
		
		
		String end = "</Document>\r\n" + "</kml>" ;
		writer.write(end);
		System.out.println("the end2"	+ "");
	//	if(delete==true) file.delete();

		 
	}

	
	public String Date()
	{   String ans;  
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	LocalDateTime now = LocalDateTime.now();  

	//		DateTimeFormatter dtf2=	DateTimeFormatter.ofPattern ("HH:mm:ss");  
	//		LocalDateTime now2 = LocalDateTime.now();  

	ans=""+now ;
	System.out.println(ans);
	return (ans) ;




	}

}
