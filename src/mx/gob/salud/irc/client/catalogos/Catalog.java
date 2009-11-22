package mx.gob.salud.irc.client.catalogos;

import java.util.ArrayList;
import java.util.HashMap;
import mx.gob.salud.irc.client.utils.db.Table;
import mx.gob.salud.irc.client.utils.db.TableDefinition;

public class Catalog extends Table{

	private static final long serialVersionUID = 6141428246991574092L;
	private HashMap<String,String> pkDescData = null;
	private HashMap<String,ArrayList <HashMap<String,String>>> fkPkDescData = null;
	
	public Catalog(){
	}
	
	/**
	 * Cambia los datos obtenidos originalmente en un ArrayList a un HashMap del ID del catalogo contra la Descripcion.
	 * @param deleteOriginal Indica si debe borrar el ArrayList con los datos originales.
	 */
	public void generateMapPkDescription(boolean deleteOriginal){
		HashMap<String,String> temp = null;
		int index = 0, rows = 0;
		
		if (data != null){
			pkDescData = new HashMap<String,String>();
			rows = data.size();
			for (index = 0; index < rows; index++){
				temp = (HashMap<String,String>)data.get(index);
				pkDescData.put(temp.get(tdef.getPkColumns()), temp.get(tdef.getColumnDescription()));
			}
			
			if (pkDescData.size() <= 0)
				pkDescData = null;
			else
				if (deleteOriginal)
					data = null;
		}
	}
	
	/**
	 * Crea un mapa donde la llave es la llave foranea y los datos son los registros que tienen la misma llave foranea.
	 * Para esto los datos originales deben estar ordenados por Fk,Pk,Desc.
	 * @param deleteOriginal
	 */
	public void generateMapFkPkDescription(boolean deleteOriginal){
		HashMap<String,String> temp = null;
		String key = "", fkColName = tdef.getFkColumns(TableDefinition.DEFAULT_FK_NAME).toLowerCase();
		ArrayList<HashMap<String,String>> value = null;
		int index = 0, rows = 0;
		
		if (data != null){
			fkPkDescData = new HashMap<String,ArrayList<HashMap<String,String>>>();
			rows = data.size();
			value = new ArrayList <HashMap<String,String>>();
			key = data.get(0).get(fkColName);
			for (index = 0; index < rows; index++){
				temp = (HashMap<String,String>)data.get(index);
				if (!key.equals(temp.get(fkColName))){
					fkPkDescData.put(key, value);
					key = temp.get(fkColName);
					value = new ArrayList<HashMap<String,String>>();
				}
				value.add(temp);
			}
			fkPkDescData.put(key, value);
			
			if (fkPkDescData.size() <= 0)
				fkPkDescData = null;
			else
				if (deleteOriginal)
					data = null;
		}
	}

	public HashMap<String, String> getPkDescData(boolean deleteOriginal) {
		if (pkDescData == null)
			generateMapPkDescription(deleteOriginal);
		return pkDescData;
	}

	public HashMap<String, ArrayList<HashMap<String, String>>> getFkPkDescData(boolean deleteOriginal) {
		if (fkPkDescData == null)
			generateMapFkPkDescription(deleteOriginal);
		return fkPkDescData;
	}
	
}
