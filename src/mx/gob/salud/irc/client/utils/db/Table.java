package mx.gob.salud.irc.client.utils.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Contiene los datos de una tabla y su definicion(TableDefinition).
 * @author RMG
 * @see TableDefinition
 */
public class Table implements Serializable{

	private static final long serialVersionUID = -3852923739724647772L;
	/**
	 * Definición de esta tabla.
	 */
	protected TableDefinition tdef = null;
	/**
	 * Datos de esta tabla.
	 */
	protected ArrayList<HashMap<String,String>> data = null;
	
	
	public TableDefinition getTdef() {
		return tdef;
	}
	public void setTdef(TableDefinition tdef) {
		this.tdef = tdef;
	}
	public ArrayList<HashMap<String, String>> getData() {
		return data;
	}
	public void setData(ArrayList<HashMap<String, String>> data) {
		this.data = data;
	}
	
	
	
}
