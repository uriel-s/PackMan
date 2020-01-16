
package gameClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONException;

public class KML_Logger {
	public String KMLstring;
	//public AutoGame AG;
	//public MyGameGUI myGG;
	
	public KML_Logger() throws JSONException
	{
		//this.AG = new AutoGame();
		//this.myGG = new MyGameGUI();
		this.KMLstring= "" ;

		KMLstring+= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
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
	String ans ="\r\n" + 
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

	
	
	public String Date()
{
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	   LocalDateTime now = LocalDateTime.now();  
	   System.out.println(dtf.format(now));  
  	return dtf.format(now) ;
	
}
	
}
