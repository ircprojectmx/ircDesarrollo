package mx.gob.salud.irc.client.utils.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import mx.gob.salud.irc.client.utils.UtilsStrings;

/**
 * Clase con para definir Tablas, esta definicion se envia a traves del servicio: CommonRequest. 
 * @author RMG
 * @see mx.gob.salud.irc.client.services.remote.CommonRequest
 */
public class TableDefinition implements Serializable{
	private static final long serialVersionUID = 8558210254325151608L;
	/**
	 * Nombre de la Tabla.
	 */
	private String name = null;
	/**
	 * Nombres de las columnas de la Tabla.
	 */
	private ArrayList<String> columns = null;
	/**
	 * Columnas que son llave primaria de la tabla. 
	 */
	private ArrayList<String> pk = null;
	/**
	 * Llaves Foraneas de la tabla.
	 */
	private HashMap<String, ArrayList<String>> fk = null;
	/**
	 * Condiciones a agregar en la consulta.
	 */
	private ArrayList<String> where = null;
	/**
	 * Columna para el caso de Catalogos que contiene la descripción del elemento en el catalogo.
	 */
	private ArrayList<String> columnDescription = null;
	/**
	 * Descripcion de la tabla.
	 */
	private String description = null;
	/**
	 * Tipo de cada Columna.
	 */
	private ArrayList<String> columnType = null;
	
	private int operation = SqlStatements.OPERATION_SELECT;
	
	public static String DEFAULT_FK_NAME = "NONAME_FK";
	
	public TableDefinition(){
		columns = new ArrayList<String>();
		pk = new ArrayList<String>();
		fk = new HashMap<String, ArrayList<String>>();
		where = new ArrayList<String>();
		columnDescription = new ArrayList<String>();
	}
	
	public void addColumn(String col){
		columns.add(col);
	}
	public void addPk(String colId){
		pk.add(colId);
	}
	
	public void addFk(String nameFk, String colMaster){
		ArrayList<String> list = null;
		try{
			list = fk.get(nameFk);
		}catch(Exception e){
			list = null;
		}
		if (list == null){
			list = new ArrayList<String>();
			fk.put(nameFk, list);
		}
		
		list.add(colMaster);
	}
	public void addCondition(String cond){
		where.add(cond);
	}
	
	
	public String getColumns(){
		return (UtilsStrings.toString(columns, ","));
	}
	public String getPkColumns(){
		return (UtilsStrings.toString(pk, ","));
	}
	
	public String getFkColumns(String nameFk){
		try{
			return (UtilsStrings.toString(fk.get(nameFk), ","));
		}catch(Exception e){
			return ("");
		}
	}
	public String getContitions(){
		return (UtilsStrings.toString(where, ","));
	}
	
	public String getColumn(int index){
		try{
			return (columns.get(index));
		}catch(Exception e){
			return (null);
		}
	}
	public String getPkColumn(int index){
		try{
			return (pk.get(index));
		}catch(Exception e){
			return (null);
		}
	}
	
	public String getFkColumn(String nameFk, int index){
		try{
			return (fk.get(nameFk).get(index));
		}catch(Exception e){
			return (null);
		}
	}
	
	public String getCondition(int index){
		try{
			return (where.get(index));
		}catch(Exception e){
			return (null);
		}
	}

	public int getOperation() {
		return operation;
	}

	public void setOperation(int operation) {
		this.operation = operation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColumnDescription(int index) {
		return (columnDescription.get(index));
	}
	
	public int getNumColumnDescription(){
		return (columnDescription.size());
	}
	
	public String getColumnDescription() {
		return (UtilsStrings.toString(columnDescription, ","));
	}

	public void addColumnDescription(String columnDescription) {
		this.columnDescription.add(columnDescription);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getColumnType(int index) {
		if (this.columnType == null)
			return (null);
		else
			return columnType.get(index);
	}

	public void addColumnType(String columnType) {
		if (this.columnType == null)
			this.columnType = new ArrayList<String>();
		this.columnType.add(columnType);
	}
	
	
}
