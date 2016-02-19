package gui;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import runners.CommandLineRunner;

public class ImageProxy extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private String folderPath;
	private String fileName;
	private JLabel image;
	private BufferedImage pic;
	private JLabel label;
	private JButton bRefresh;
	private JScrollPane scroll;
	private JPanel scrollPanel;
	private GUIFrame parent;

	ImageProxy(GUIFrame parent, String path, String name){
		
		this.parent = parent;
		
		this.folderPath = path;
		
		this.fileName = name;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.scrollPanel = new JPanel();
		this.scrollPanel.setLayout(new GridBagLayout());
		this.scroll = new JScrollPane(this.scrollPanel);
		this.add(this.scroll);
		
		this.label = new JLabel("Press Refresh to Load Image");
		this.scrollPanel.add(this.label);
		
		this.image = new JLabel();
		this.image.setVisible(false);
		this.scrollPanel.add(this.image);
		
		this.bRefresh = new JButton("Refresh");
		this.add(this.bRefresh);
		this.bRefresh.setLocation(0, 0);
		this.bRefresh.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ImageProxy.this.refresh();
			}
		});
	}
	
	public void refresh(){
		Thread t = new Thread(new Runnable(){
			@Override
			public void run() {
				ImageProxy.this.label.setVisible(true);
				ImageProxy.this.image.setVisible(false);
				
				ImageProxy.this.label.setText("Loading...");
				
				StringBuffer sb = new StringBuffer();
				ImageProxy.this.parent.dmm.output(sb);
				
				System.out.println(sb.toString());
		        
				try {
					BufferedWriter bwr = new BufferedWriter(new FileWriter(new File(ImageProxy.this.folderPath + ImageProxy.this.fileName + ".gv")));
			        
			        //write contents of StringBuffer to a file
			        bwr.write(sb.toString());
			       
			        //flush the stream
			        bwr.flush();
			       
			        //close the stream
			        bwr.close();
			        
			        File imageFile = new File(ImageProxy.this.folderPath + ImageProxy.this.fileName + ".png");
			        
			        if(imageFile.exists()) {
			        	imageFile.delete();
			        }
			        
			        // runs the dot
			        Thread t2 = new Thread(new Runnable() {

						@Override
						public void run() {
							CommandLineRunner.runApplication(ImageProxy.this.parent.dmm.config.getDotPath(), "-Tpng \"" + (ImageProxy.this.folderPath + ImageProxy.this.fileName) + ".gv\" -o \"" + (ImageProxy.this.folderPath + ImageProxy.this.fileName) + ".png\"");
					     
						}
			        		
			        });
			        t2.start();
			        //Thread.sleep(sb.length()/10);
			        long oldSize = -1;
			        while(t2.isAlive() || imageFile.length() <= 0 || oldSize != imageFile.length()) {
			        	oldSize = imageFile.length();
			        	Thread.sleep(250);
			        }
					ImageProxy.this.pic = ImageIO.read(imageFile);
					ImageProxy.this.image.setIcon(new ImageIcon(pic));
					imageFile.delete();
					ImageProxy.this.label.setVisible(false);
					ImageProxy.this.image.setVisible(true);
				} catch (Exception e){
					e.printStackTrace();
					ImageProxy.this.label.setText("Failed to load image");
				}
			}
		});
		t.start();
	}
}
