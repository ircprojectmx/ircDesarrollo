package mx.gob.salud.irc.server.services;

import java.util.ArrayList;
import java.util.HashMap;
import mx.gob.salud.irc.client.services.remote.CommonRequest;
import mx.gob.salud.irc.client.utils.UtilsStrings;
import mx.gob.salud.irc.client.utils.db.SqlStatements;
import mx.gob.salud.irc.client.utils.db.Table;
import mx.gob.salud.irc.client.utils.db.TableDefinition;
import mx.gob.salud.irc.server.utils.Resources;
import mx.gob.salud.irc.server.utils.UtilsBD;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class CommonRequestImpl extends RemoteServiceServlet implements CommonRequest {

	private static final long serialVersionUID = 2630454428226114917L;
	private UtilsBD dbCon = new UtilsBD();
	
	public ArrayList<HashMap<String, String>> exec(String params) {
		return dbCon.queryLista(params);
	}
	
	public ArrayList<TableDefinition> getCatalogDefinitions(boolean reloadFromDB) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT CAT.ID_CATALOG, CAT.CD_NAME, CAT.DE_CATALOG, CAT.DE_WEHRE_MEMORY, COL.NB_COLUMN, COL.CD_TYPE, \n");
		sql.append("COL.CD_PRIMARY_KEY, COL.CD_FORING_KEY, COL.CD_DESCRIPTION  \n"); 
		sql.append("FROM dbo.TSYS_CATALOGS CAT, dbo.TSYS_CATALOGS_COLUMNS COL \n");
		sql.append("WHERE COL.ID_CATALOG = CAT.ID_CATALOG \n");
		sql.append("ORDER BY COL.ID_CATALOG, COL.NU_ORDER \n");
		
		if (Resources.catalogDefinition.size() <= 0 || reloadFromDB)
			loadCatalogDefinitionLocal(dbCon.queryLista(sql.toString()), Resources.catalogDefinition);
		
		return (Resources.catalogDefinition);
	}
	
	private void loadCatalogDefinitionLocal(ArrayList<HashMap<String, String>> result, ArrayList<TableDefinition> list){
		TableDefinition tDef = null;
		Table catalog = null;
		int size = 0, index = 0, id = 0, newId = 0;
		HashMap<String,String> temp = null;
		String val = null, columnName = null;
				
		try{
			if (list.size() > 0)
				list.clear();
			size = result.size();
			temp = result.get(0);
			newId = id = Integer.parseInt(UtilsStrings.zeroIfIsEmptyOrNull(temp.get("id_catalog")));
			for (index = 0; index < size; index++){
				tDef = new TableDefinition();
				
				
				temp = result.get(index);
				val = UtilsStrings.emptyIfIsNull(temp.get("cd_name"));
				tDef.setName(val);
				val = UtilsStrings.emptyIfIsNull(temp.get("de_catalog"));
				tDef.setDescription(val);
				val = UtilsStrings.emptyIfIsNull(temp.get("de_where_memory"));
				tDef.addCondition(val);
	
				val = UtilsStrings.zeroIfIsEmptyOrNull(temp.get("id_catalog"));
				newId = Integer.parseInt(val);
				do{
					columnName = UtilsStrings.emptyIfIsNull(temp.get("nb_column"));
					tDef.addColumn(columnName);
					val = UtilsStrings.emptyIfIsNull(temp.get("cd_type"));
					tDef.addColumnType(val);
					if (UtilsStrings.toBoolean(temp.get("cd_primary_key")))
						tDef.addPk(columnName);
					if (UtilsStrings.toBoolean(temp.get("cd_foring_key")))
						tDef.addFk(TableDefinition.DEFAULT_FK_NAME,columnName);
					if (UtilsStrings.toBoolean(temp.get("cd_description")))
						tDef.addColumnDescription(columnName);
					
					index++;
					if (index < size){
						temp = result.get(index);
						
						val = UtilsStrings.zeroIfIsEmptyOrNull(temp.get("id_catalog"));
						newId = Integer.parseInt(val);
					}
					else
						break;
				}while(id == newId);
				index--;
				id = newId;
				
				list.add(tDef);
				
				catalog = new Table();
				catalog.setTdef(tDef);
			}
		}catch(Exception e){
			System.out.println("CommonRequest.loadCatalogDefinitionLocal(), error: " + e.getMessage());
			list = null;
		}
	}

	public ArrayList<HashMap<String,String>> getCatalog(boolean reloadFromDB, String name) {
		int index = 0, size = 0;
		String sql = null;
		TableDefinition td = null;
		ArrayList<HashMap<String, String>> results = Resources.catalogs.get(name);
		
		if (results == null || reloadFromDB){
			size = Resources.catalogDefinition.size();
			for (index = 0; index < size; index++){
			    if (Resources.catalogDefinition.get(index).getName().equals(name))
			    	break;
			}
			
			if (index < size){
				td = Resources.catalogDefinition.get(index);
				td.setOperation(SqlStatements.OPERATION_SELECT_CATALOG);
	            sql = SqlStatements.generate(td);
	            
	            results = exec(sql);
	            if (results == null){
	            	System.out.println("CommonRequest.getCatalogs(), error: Al cargar el catalogo de la BD: "+name);
	            	return (null);
	            }
	            else{
	            	Resources.catalogs.put(name, results);
	            }
			}
			else
				System.out.println("CommonRequest.getCatalogs(), error: Al cargar el catalogo de la BD: "+name+", no existe.");
		}
		
		return results;
	}
}
