package mx.gob.salud.irc.client.services.remote;

import java.util.ArrayList;
import java.util.HashMap;

import mx.gob.salud.irc.client.forms.FormDescription;
import mx.gob.salud.irc.client.utils.Results;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("Form")
public interface Form extends RemoteService {

	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static FormAsync instance;
		public static FormAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(Form.class);
			}
			return instance;
		}
	}
	
	public ArrayList<HashMap<String,String>> getSection(String idSection);
	public ArrayList<HashMap<String,String>> getSectionDefinition(String idSection);
	public ArrayList<HashMap<String,String>> getForm(String app, String idForm);
	public FormDescription getFormDescription(String app, String idForm, boolean readFromDB);
	public Results select(HashMap<String,String> params, mx.gob.salud.irc.client.forms.Form form);
	public Results insert(HashMap<String,String> params, mx.gob.salud.irc.client.forms.Form form);
	public Results update(HashMap<String,String> params, mx.gob.salud.irc.client.forms.Form form);
	public Results delete(HashMap<String,String> params, mx.gob.salud.irc.client.forms.Form form); 
	public Results service(HashMap<String,String> params, mx.gob.salud.irc.client.forms.Form form);
}
