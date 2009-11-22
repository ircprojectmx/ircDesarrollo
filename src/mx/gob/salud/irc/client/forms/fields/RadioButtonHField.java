package mx.gob.salud.irc.client.forms.fields;

import java.util.ArrayList;

import mx.gob.salud.irc.client.Resources;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RadioButton;

public class RadioButtonHField extends HorizontalPanel{

	private ArrayList<RadioButton> options = null;
	private String values[] = null;
	
	/**
	 * Crea todos los radiobuttons indicados, todos como un solo grupo.
	 * @param group Nombre del Grupo para los RadioButtons.
	 * @param values String con los valores de Texto para cada RadioButton, i.e.: "Rojo,Blanco,Azul"
	 */
	public RadioButtonHField(String group, String desc, String val){
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
	
	/**
	 * Crea a partir de un bloque de datos del utilsBD indicando la columna que debe tomar de los datos.
	 * @param group Nombre del Grupo para los radiobutons.
	 * @param values Bloque de datos del utilsBD.
	 * @param fieldName Nombre de la columna que debe tomar como dato.
	 *
	public RadioButtonHField(String group, ArrayList<HashMap<String, String>> values, String fieldName){
		int index = 0, size = 0;
		HashMap<String, String> tmp = null;
		RadioButton rb = null;
		
		size = values.size();
		try{
			for(index = 0; index < size; index++){
				tmp = values.get(index);
				rb = new RadioButton(group,tmp.get(fieldName));
				rb.setStyleName(Resources.msg.styleFormLabel());
				if (index == 0)
					rb.setValue(true);
				add(rb);
				options.add(rb);
			}
		}catch(Exception e){
			UserMessage.error(Resources.msg.formTitle(), Resources.msg.loadingData("RadioButton."), Resources.msg.errorReadingHashTable("Constructor."), null);
		}
	}
	
	/**
	 * Indica si los RadioButtons van en un panel Vertical u Horizontal. 
	 * @param ori 0, Horizontal y 1 Vertical.
	 *
	private void setOrientation(int orient){
		switch(orient){
		case 0:
			panel = new HorizontalPanel();
			break;
		case 1:
			panel = new VerticalPanel();
			break;
		}
	}*/
}
