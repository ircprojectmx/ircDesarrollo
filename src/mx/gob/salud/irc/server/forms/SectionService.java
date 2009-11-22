package mx.gob.salud.irc.server.forms;

import java.util.HashMap;

import mx.gob.salud.irc.client.forms.Form;
import mx.gob.salud.irc.client.forms.FormDescription;
import mx.gob.salud.irc.client.forms.Section;
import mx.gob.salud.irc.client.forms.SectionDescription;
import mx.gob.salud.irc.client.utils.Results;

public interface SectionService {

	public Results select(HashMap<String,String> params, FormDescription fd, SectionDescription sd, Form from, Section sec);
	public Results insert(HashMap<String,String> params, FormDescription fd, SectionDescription sd, Form from, Section sec);
	public Results update(HashMap<String,String> params, FormDescription fd, SectionDescription sd, Form from, Section sec);
	public Results delete(HashMap<String,String> params, FormDescription fd, SectionDescription sd, Form from, Section sec); 
	public Results service(HashMap<String,String> params, FormDescription fd, SectionDescription sd, Form from, Section sec);
}
