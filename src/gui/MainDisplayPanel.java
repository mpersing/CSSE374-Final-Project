package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MainDisplayPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private GUIFrame parent;

	public MainDisplayPanel(GUIFrame parent) {
		this.parent = parent;
		this.setFocusable(true);

		this.setLayout(new BorderLayout());
		
		SelectorPanel sel = new SelectorPanel(this.parent.dmm.getList());
		JScrollPane selScroll = new JScrollPane(sel);
		
		this.add(selScroll,BorderLayout.WEST);
		Dimension dim = this.parent.getPreferredSize();
		dim.width = dim.width / 3;
		selScroll.setPreferredSize(dim);
		
		
		JPanel p = new JPanel();
		p.setBackground(Color.red);
		JScrollPane pScroll = new JScrollPane(p);
		this.add(pScroll, BorderLayout.CENTER);
	}
}
