package mx.gob.salud.irc.client.forms.fields;

import java.util.ArrayList;
import mx.gob.salud.irc.client.Resources;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RadioButtonVField extends VerticalPanel {

	private ArrayList<RadioButton> options = null;
	private String values[] = null;
	
	/**
	 * Crea todos los radiobuttons indicados, todos como un solo grupo.
	 * @param group Nombre del Grupo para los RadioButtons.
	 * @param values String con los valores de Texto para cada RadioButton, i.e.: "Rojo,Blanco,Azul"
	 */
	public RadioButtonVField(String group, String desc, String val){
		String arrayDesc[] = null;
		int index = 0, size = 0;
		RadioButton rb = null;
	
		options = new ArrayList<RadioButton>();
		arrayDesc = desc.split(",");
		values = val.split(",");
		size = arrayDesc.length;
		
		for(index = 0; index < size; index++){
			rb = new RadioButton(group, arrayDesc[index]);
			rb.setStyleName(Resources.msg.styleFormLabel());
			if (index == 0)
				rb.setValue(true);
			add(rb);
			options.add(rb);
		}
	}
	
	public String getValue(){
		String ret = null;
		int index = 0, size = options.size();
		RadioButton rb = null;
		
		for(index = 0; index < size; index++){
			rb = options.get(index);
			if (rb.getValue())
				ret = values[index];
		}
		
		return (ret);
	}
	public void setEnabled(boolean enabled){
		int index = 0, size = options.size();
		RadioButton rb = null;
		
		for(index = 0; index < size; index++){
			rb = options.get(index);
			rb.setEnabled(enabled);
		}
	}
}
