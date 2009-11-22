package mx.gob.salud.irc.client.services.remote;

import java.util.ArrayList;
import java.util.HashMap;

import mx.gob.salud.irc.client.forms.FormDescription;
import mx.gob.salud.irc.client.utils.Results;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FormAsync {

	public void getSection(String idSection, AsyncCallback<ArrayList<HashMap<String,String>>> callback);
	public void getSectionDefinition(String idSection, AsyncCallback<ArrayList<HashMap<String,String>>> callback);
	public void getForm(String app, String idForm, AsyncCallback<ArrayList<HashMap<String,String>>> callback);
	public void getFormDescription(String app, String idForm, boolean readFromDB, AsyncCallback<FormDescription> callback);
	public void select(HashMap<String,String> params, mx.gob.salud.irc.client.forms.Form form, AsyncCallback<Results> callback);
	public void insert(HashMap<String,String> params, mx.gob.salud.irc.client.forms.Form form, AsyncCallback<Results> callback);
	public void update(HashMap<String,String> params, mx.gob.salud.irc.client.forms.Form form, AsyncCallback<Results> callback);
	public void delete(HashMap<String,String> params, mx.gob.salud.irc.client.forms.Form form, AsyncCallback<Results> callback); 
	public void service(HashMap<String,String> params, mx.gob.salud.irc.client.forms.Form form, AsyncCallback<Results> callback);
}
