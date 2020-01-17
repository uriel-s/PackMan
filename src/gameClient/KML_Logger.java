
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
		file  = new File("output.kml");
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
		return  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
		"\r\n" + 
		"-<kml xmlns=\"http://earth.google.com/kml/2.2\">\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"-<Document>\r\n" + 
		"\r\n" + 
		"<name>Points with TimeStamps</name>\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"-<Style id=\"paddle-a\">\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"-<IconStyle>\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"-<Icon>\r\n" + 
		"\r\n" + 
		"<href>http://maps.google.com/mapfiles/kml/paddle/A.png</href>\r\n" + 
		"\r\n" + 
		"</Icon>\r\n" + 
		"\r\n" + 
		"<hotSpot yunits=\"pixels\" xunits=\"pixels\" y=\"1\" x=\"32\"/>\r\n" + 
		"\r\n" + 
		"</IconStyle>\r\n" + 
		"\r\n" + 
		"</Style>\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"-<Style id=\"paddle-b\">\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"-<IconStyle>\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"+<Icon>\r\n" + 
		"\r\n" + 
		"<hotSpot yunits=\"pixels\" xunits=\"pixels\" y=\"1\" x=\"32\"/>\r\n" + 
		"\r\n" + 
		"</IconStyle>\r\n" + 
		"\r\n" + 
		"</Style>\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"-<Style id=\"hiker-icon\">\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"-<IconStyle>\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"-<Icon>\r\n" + 
		"\r\n" + 
		"<href>http://maps.google.com/mapfiles/ms/icons/hiker.png</href>\r\n" + 
		"\r\n" + 
		"</Icon>\r\n" + 
		"\r\n" + 
		"<hotSpot yunits=\"fraction\" xunits=\"fraction\" y=\".5\" x=\"0\"/>\r\n" + 
		"\r\n" + 
		"</IconStyle>\r\n" + 
		"\r\n" + 
		"</Style>\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"-<Style id=\"check-hide-children\">\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"-<ListStyle>\r\n" + 
		"\r\n" + 
		"<listItemType>checkHideChildren</listItemType>\r\n" + 
		"\r\n" + 
		"</ListStyle>\r\n" + 
		"\r\n" + 
		"</Style>\r\n" + 
		"\r\n" + 
		"<styleUrl>#check-hide-children</styleUrl>";
	}

	public String AddBlock(String date,String coorndinate )
	{
		String ans = "\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"-<Placemark>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"-<TimeStamp>\r\n" + 
				"\r\n" + 
				"<when>"+ date
				+"</when>\r\n" + 
				"\r\n" + 
				"</TimeStamp>\r\n" + 
				"\r\n" + 
				"<styleUrl>#hiker-icon</styleUrl>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"-<Point>\r\n" + 
				"\r\n" + 
				"<coordinates>"+coorndinate
				+"</coordinates>\r\n" + 
				"\r\n" + 
				"</Point>\r\n" + 
				"\r\n" + 
				"</Placemark>";

		return ans;
	}

	public void AddLoop() throws IOException
	{  
		int size;
		size = AutoGame.Robots.size();
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
		writer.write("\r\n" + 
				"</Document>\r\n" + 
				"\r\n" + 
				"</kml>");
	}

	//public File file()
	//{ File file = new File("output.kml");
	////Create the file
	//try {
	//	if (file.createNewFile())
	//	{
	//		System.out.println("File is created!");
	//	} else {
	//		System.out.println("File already exists.");
	//	}
	//} catch (IOException e1) {
	//	// TODO Auto-generated catch block
	//	e1.printStackTrace();
	//}
	// return file;//Write Content
	//}

	public String Date()
	{   String ans;  
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();  

		DateTimeFormatter dtf2=	DateTimeFormatter.ofPattern ("HH:mm:ss");  
		LocalDateTime now2 = LocalDateTime.now();  

		ans=now+"T"+now2+"Z";
		return (ans) ;




	}

}
