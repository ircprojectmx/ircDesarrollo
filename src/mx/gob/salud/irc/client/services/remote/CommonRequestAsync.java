package mx.gob.salud.irc.client.services.remote;

import java.util.ArrayList;
import java.util.HashMap;

import mx.gob.salud.irc.client.utils.db.TableDefinition;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CommonRequestAsync {

	void exec(String params, AsyncCallback<ArrayList<HashMap<String,String>>> callback);
	void getCatalogDefinitions(boolean reloadFromDB, AsyncCallback<ArrayList<TableDefinition>> callback);
	void getCatalog(boolean reloadFromDB, String name, AsyncCallback<ArrayList<HashMap<String,String>>> callback);
}
