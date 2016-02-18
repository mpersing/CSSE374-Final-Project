package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MainDisplayPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private GUIFrame parent;

	public MainDisplayPanel(GUIFrame parent) {
		this.parent = parent;
		this.setFocusable(true);

		this.setLayout(new BorderLayout());
		
		this.add(new SelectorPanel(),BorderLayout.WEST);
	}
}
