package gui;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import data.impl.DataManagerManager;

public class GUIFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public DataManagerManager dmm;
	
	public GUIFrame(){
		super("Design Parser");
		
		this.dmm = new DataManagerManager();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new LandingPanel(this));
        
        this.setPreferredSize(new Dimension(600, 400));
        
        this.pack();
        this.setVisible(true);
        
        this.setLocationRelativeTo(null);
	}
	
	public void changePanelTo(JPanel panel){
		this.setContentPane(panel);
		this.invalidate();
		this.validate();
	}
	
	public List<Object> getList(){
		return null;
	}
	
}
