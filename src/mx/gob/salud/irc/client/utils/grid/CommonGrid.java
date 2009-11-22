package mx.gob.salud.irc.client.utils.grid;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.ui.Grid;

public class CommonGrid extends Grid {
	
	private String name = null;
	private ArrayList<ColumnDefinition> cd = null;
	
	private int column = 0;
	private int columns = 0;
	private int row = 0;
	private int rows = 0;
	
	private boolean paging = false;
	
	private ArrayList<HashMap<String,String>> originalData = null;

	/**
	 * Determinar tanto el numero como el formato de cada una de las columnas.
	 * @param colDef Definicion de la Columna
	 */
	public void addColumn(ColumnDefinition colDef){
		if (cd == null)
			cd = new ArrayList<ColumnDefinition>();
		
		cd.add(colDef);
		columns++;
	}
	
	public void setData(ArrayList<HashMap<String,String>> dataParam) throws GridException{
		if (cd == null)
			throw new GridException("Se requiere de una definicion de Columnas antes de aplicar datos.");
		else{
			rows = dataParam.size();
			resize(rows + 1, columns);
			originalData = dataParam;
			generateHeaders();
			setAllValues();
		}
	}
	
	private void setAllValues(){
		
	}
	
	private void generateHeaders(){
		generateColumnHeaders();
	}
	
	private void generateColumnHeaders(){
		int index = 0;
		ColumnHeader ch = null;
		
		for (index = 0; index < columns; index++){
			ch = new ColumnHeader(cd.get(index).getDisplayName());
			setWidget(0, index, ch);
		}
	}
}
