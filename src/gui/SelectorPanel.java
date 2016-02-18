package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import data.api.IUMLModifier;

public class SelectorPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	// connecting the two
	private Map<JCheckBox, IUMLModifier> checkBoxToMod;
	
	public SelectorPanel(List<IUMLModifier> list){
		this.checkBoxToMod = new HashMap<>();
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.repopCheckBoxes(list);
		
	}
	
	
	
	public void setList(List<IUMLModifier> list){
		this.repopCheckBoxes(list);
	}
	
	private void repopCheckBoxes(List<IUMLModifier> list){
		// remove checkboxes
		for (JCheckBox c : this.checkBoxToMod.keySet()){
			this.remove(c);
		}
		this.checkBoxToMod.clear();
		
		// add new ones
		this.repopCheckBoxesHelper(list, 0);
	}
	
	/**
	 * 
	 * @param l, The list to add
	 * @param i, The indentation level
	 */
	private void repopCheckBoxesHelper(List<IUMLModifier> list, int i){
		for (IUMLModifier m : list){
			
			String name = m.getDisplayName();
			JCheckBox c = new JCheckBox(name);
			
			this.checkBoxToMod.put(c, m);
			
			c.setBorder(new EmptyBorder(0, 10*i, 0, 0));
			c.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					m.setEnabled(c.isSelected());
					SelectorPanel.this.updateCheckBoxStates();
				}
			});
			
			this.add(c);
			
			List<IUMLModifier> list2 = m.getList();
			if (list2 != null && list2.size() > 0){
				this.repopCheckBoxesHelper(list2,i+1);
			}
		}
	}
	
	public void updateCheckBoxStates(){
		for(JCheckBox c : this.checkBoxToMod.keySet()){
			c.setSelected(this.checkBoxToMod.get(c).getEnabled());
		}
	}
	
}
