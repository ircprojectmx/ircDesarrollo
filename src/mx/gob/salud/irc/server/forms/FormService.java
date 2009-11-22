package mx.gob.salud.irc.server.forms;

import java.util.HashMap;

import mx.gob.salud.irc.client.forms.Form;
import mx.gob.salud.irc.client.utils.Results;

public interface FormService {

	public Results select(HashMap<String,String> params, Form form);
	public Results insert(HashMap<String,String> params, Form form);
	public Results update(HashMap<String,String> params, Form form);
	public Results delete(HashMap<String,String> params, Form form); 
	public Results service(HashMap<String,String> params, Form form);
}
