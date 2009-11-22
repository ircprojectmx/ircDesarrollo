package mx.gob.salud.irc.client.services.remote;

import java.util.ArrayList;
import java.util.HashMap;

import mx.gob.salud.irc.client.utils.db.TableDefinition;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("CommonRequest")
public interface CommonRequest extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static CommonRequestAsync instance;
		public static CommonRequestAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(CommonRequest.class);
			}
			return instance;
		}
	}
	
	ArrayList<HashMap<String,String>> exec(String params);
	ArrayList<TableDefinition> getCatalogDefinitions(boolean reloadFromDB);
	ArrayList<HashMap<String,String>> getCatalog(boolean reloadFromDB, String name);
}
