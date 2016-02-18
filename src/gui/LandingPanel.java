package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class LandingPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel label;
	private JProgressBar progressBar;

	private GUIFrame parent;

	public LandingPanel(GUIFrame parent) {
		this.parent = parent;
		this.setFocusable(true);

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// the button panel
		JPanel p = new JPanel();
		JButton bConfig = new JButton("Load Config");
		bConfig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				int returnValue = fc.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					LandingPanel.this.parent.dmm.loadConfig(f);
				}
			}
		});
		p.add(bConfig);

		JButton bAnalyze = new JButton("Analyze");
		bAnalyze.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				LandingPanel.this.parent.dmm.analyze();
				LandingPanel.this.parent.changePanelTo(new MainDisplayPanel(LandingPanel.this.parent));
			}
		});
		p.add(bAnalyze);
		this.add(p);

		// analyzing display
		label = new JLabel("Analyzing some class");
		label.setVisible(false);
		this.add(label);

		progressBar = new JProgressBar();
		this.setMax(100);
		progressBar.setVisible(false);
		this.add(progressBar);
	}

	public void setText(String text) {
		this.label.setText(text);
	}

	public void setMax(int n) {
		this.progressBar.setMaximum(n);
	}

	public void setValue(int n) {
		this.progressBar.setValue(n);
	}

}
