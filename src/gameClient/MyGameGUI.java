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
import java.io.IOException;
import java.util.ArrayList;
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

import com.sun.java.swing.plaf.windows.resources.windows;
import com.sun.media.jfxmedia.events.NewFrameEvent;

///import dataStructure.DGraph;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;




public abstract class MyGameGUI extends JFrame implements ActionListener, MouseListener
{
	private JButton Button;
	private JLabel Lebel;
	private JTextField Tfield;
	private JTextArea JArea;

	public MyGameGUI() 
	{
		setLayout(new FlowLayout());
		Lebel= new JLabel("choosing server");
		Button= new JButton("button 1");
		Tfield= new JTextField(10);
		JArea= new JTextArea(5,5);

		add(Lebel);
		add(Button);
		add(Tfield);
		add(JArea);

	}
	public static void main(String[] args) 
	{
		MyGameGUI menu = new MyGameGUI();
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}

}





