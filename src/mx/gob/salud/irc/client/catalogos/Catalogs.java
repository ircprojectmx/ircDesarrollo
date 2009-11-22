package mx.gob.salud.irc.client.catalogos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import mx.gob.salud.irc.client.Resources;
import mx.gob.salud.irc.client.services.CommonCallBack;
import mx.gob.salud.irc.client.services.remote.CommonRequest;
import mx.gob.salud.irc.client.services.remote.CommonRequestAsync;
import mx.gob.salud.irc.client.statics.Codes;
import mx.gob.salud.irc.client.utils.UserMessage;
import mx.gob.salud.irc.client.utils.UtilsStrings;
import mx.gob.salud.irc.client.utils.db.TableDefinition;
import mx.gob.salud.irc.client.utils.db.SqlStatements;
public class Catalogs {

	private HashMap<String,Catalog> catList = null;
	
	private CommonCallBack complete = null;
	private Iterator iterator = null;
	private boolean forceLoadFromDB = false;

	public Catalogs(){
		catList = new HashMap<String,Catalog>(); 
	}

	public void load(boolean forceLoadFromDB, CommonCallBack ccb){
		complete = ccb;
		this.forceLoadFromDB = forceLoadFromDB; 
		UserMessage.wait(Resources.msg.appName(), Resources.msg.loadingData(Resources.msg.appCode()), Resources.msg.runningProc("Loading Catalogs Definition"));
		loadDefinition();
		UserMessage.wait(Resources.msg.appName(), Resources.msg.loadingData(Resources.msg.appCode()), Resources.msg.runningProc("Loading Catalogs from Server"));
	}
	
	private void loadData(){
		iterator = catList.keySet().iterator();
		
		loadFromServer();
	}
	
	private void loadFromServer(){
		String key = null;
		CommonRequestAsync cReq = GWT.create(CommonRequest.class);
		
		if (iterator.hasNext()) {
			key = (String) iterator.next();
			final Catalog cat = catList.get(key);
            
            cReq.getCatalog(forceLoadFromDB, cat.getTdef().getName(), new AsyncCallback<ArrayList<HashMap<String,String>>>() {
				public void onFailure(Throwable caught) {
					complete.setCode(Codes.ERROR_NETWORK);
					complete.setMessage(Resources.msg.errorNetWork());
					complete.setDetail(caught.getMessage());
					complete.setMethodName("loadFromServer");
					complete.onFailure();
				}
				public void onSuccess(ArrayList<HashMap<String, String>> result) {
					if (result == null){
						complete.setCode(Codes.ERROR_DB);
						complete.setMessage(Resources.msg.errorDB()+":"+cat.getTdef().getName());
						complete.setMethodName("loadFromServer");
						complete.onFailure();
					}
					else{
						cat.setData(result);
						loadFromServer();
					}
				}
			});
        }
		else{
			complete.onSuccess();
		}
	}
	
	private void loadDefinition(){
		CommonRequestAsync cReq = GWT.create(CommonRequest.class);
		
		cReq.getCatalogDefinitions(forceLoadFromDB, new AsyncCallback<ArrayList<TableDefinition>>() {
			public void onFailure(Throwable caught) {
				complete.setCode(Codes.ERROR_NETWORK);
				complete.setMessage(Resources.msg.errorNetWork());
				complete.setDetail(caught.getMessage());
				complete.setMethodName("loadDefinition");
				complete.onFailure();
			}
			public void onSuccess(ArrayList<TableDefinition> result) {
				if (result == null){
					complete.setCode(Codes.ERROR_DB);
					complete.setMessage(Resources.msg.errorDB());
					complete.setMethodName("loadDefinition");
					complete.onFailure();
				}
				else{
					loadDefinitions(result);
				}
			}
		});
	}
	
	private void loadDefinitions(ArrayList<TableDefinition> result){
		Catalog cat = null;
		int size = 0, index = 0;
				
		try{
			size = result.size();
			for (index = 0; index < size; index++){
				cat = new Catalog();
				cat.setTdef(result.get(index));
				catList.put(result.get(index).getName(), cat);
			}
			
			loadData();
		}catch(Exception e){
			complete.setCode(Codes.ERROR_DB);
			complete.setMessage(Resources.msg.errorReadingHashTable("Catalog Definition"));
			complete.setMethodName("loadDefinitions");
			complete.setDetail(e.getMessage());
			complete.onFailure();
		}
		/*
		String aTmp[] = null, aName[] = null, aColumns[] = null, 
		aPk[] = null, aFk[] = null, aDesc[] = null, aCond[] = null;
		String list = null;
		Catalog value = null;
		TableDefinition tmp = null;
		
		
		list = Resources.properties.catalogList();
		aName = list.split(",");
		list = Resources.properties.catalogColumns();
		aColumns = list.split(";");
		list = Resources.properties.catalogPk();
		aPk = list.split(";");
		list = Resources.properties.catalogFk();
		aFk = list.split(";");
		list = Resources.properties.catalogDescription();
		aDesc = list.split(";");
		list = Resources.properties.catalogConditions();
		aCond = list.split(";");
		
		size = aName.length;
		for (index = 0; index < size; index++){
			value = new Catalog();
			tmp = new TableDefinition();
			aTmp = aColumns[index].split(",");
			for (count = 0; count < aTmp.length; count++){
				tmp.addColumn(aTmp[count]);
			}
			
			aTmp = aPk[index].split(",");
			for (count = 0; count < aTmp.length; count++){
				tmp.addPk(aTmp[count]);
			}
			
			aTmp = aFk[index].split(",");
			for (count = 0; count < aTmp.length; count++){
				tmp.addFk(TableDefinition.DEFAULT_FK_NAME,aTmp[count]);
			}
			
			aTmp = aCond[index].split(",");
			for (count = 0; count < aTmp.length; count++){
				tmp.addCondition(aTmp[count]);
			}
			
			tmp.setColumnDescription(aDesc[index]);
			tmp.setName(aName[index]);
			
			value.setTdef(tmp);
			catList.put(aName[index], value);
		}*/
	}
	
	public Catalog get(String val){
		return (catList.get(val));
	}
}
