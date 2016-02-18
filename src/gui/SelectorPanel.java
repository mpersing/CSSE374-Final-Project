package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class SelectorPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	private List<Object> list;
	
	private List<JCheckBox> checkBoxes;
	
	public SelectorPanel(){
		this.list = new ArrayList<>();
		this.checkBoxes = new ArrayList<>();
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	public void setList(List<Object> newList){
		this.list = newList;
		
		this.redoCheckBoxes();
	}
	
	private void redoCheckBoxes(){
		// remove from this
		for (JCheckBox c : this.checkBoxes){
			this.remove(c);
		}
		this.checkBoxes.clear();
		
		// add new ones
		for (Object o : list){
			String name = ""; // o.getDisplayName();
			JCheckBox c = new JCheckBox(name);
			checkBoxes.add(c);
			this.add(c);
		}
	}
	
}
