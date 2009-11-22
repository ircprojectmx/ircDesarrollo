package mx.gob.salud.irc.client.forms.fields;

import java.util.ArrayList;
import java.util.HashMap;

import mx.gob.salud.irc.client.Resources;
import mx.gob.salud.irc.client.utils.UserMessage;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;

public class ListBoxField extends ListBox {

	private String pk = null;
	private String desc = null;
	private String fk = null;
	
	private String childPk = null;
	private String childDesc = null;
	private HashMap<String,ArrayList <HashMap<String,String>>> childData = null;
	
	private ListBoxField child = null;
	

	public ListBoxField(){
		
	}
	
	/**
	 * Crea la lista con Datos estaticos para seleccion.
	 * @param staticDataId Cadena con los valores separados por comas i.e.: "R,B,V"
	 * @param staticDataDesc Cadena con los valores separados por comas i.e.: "Rojo,Blanco,Verde"
	 */
	public ListBoxField(String staticDataId, String staticDataDesc){
		setData(staticDataId,staticDataDesc);
	}
	
	/**
	 * Datos estaticos para seleccion.
	 * @param staticData Cadena con los valores separados por comas i.e.: "Rojo,Blanco,Verde"
	 */
	public void setData(String id, String value){
		String arrayId[] = null, arrayVal[] = null;
		int index = 0, size = 0;
		
		arrayId = id.split(",");
		arrayVal = value.split(",");
		size = arrayId.length;
		
		for(index = 0; index < size; index++){
			insertItem(arrayVal[index], arrayId[index], index);
		}
	}
	public void setData(ArrayList<HashMap<String, String>> data, String id, String description){
		HashMap<String, String> tmp = null;
		int index = 0, size = data.size(), descIndex = 0;
		String pkVal = null, colDesc[] = null;
		StringBuffer descVal = null;
		
		clear();
		
		pk = id;
		desc = description;
		id = pk.toLowerCase();
		colDesc = desc.toLowerCase().split(",");
		
		for(index = 0; index < size; index++){
			tmp = data.get(index);
			pkVal = tmp.get(id);
			
			descVal = new StringBuffer();
			for (descIndex = 0; descIndex < colDesc.length; descIndex++){
				if (descIndex > 0)
					descVal.append(" ");
				descVal.append(tmp.get(colDesc[descIndex]));
			}
			insertItem(descVal.toString(), pkVal, index);
		}
	}
	
	public void setDataChild(HashMap<String,ArrayList<HashMap<String,String>>> chData, String id, String description, String idMaster){
		fk = idMaster;
		childPk = id;
		childDesc = description;
		childData = chData;
		
		addChangeHandler(new ChangeHandler() {
	          public void onChange(ChangeEvent event) {
	        	  ArrayList<HashMap<String, String>> values = null;
	        	//UserMessage.ok("Num:"+getSelectedIndex(), getItemText(getSelectedIndex())+",Value:"+getValue(getSelectedIndex()), null);
	        	if (child != null){
	        		values = childData.get(getValue(getSelectedIndex()));
	        		if (values != null)
	        			child.setData(values, pk, childDesc);
	        		else
	        			UserMessage.warning(Resources.msg.formTitle(), Resources.msg.errorNotExistData(getItemText(getSelectedIndex())), null);
	        	}
			}
          });
	}
	
	public void setChild(ListBoxField ch){
		child = ch;
	}
}
