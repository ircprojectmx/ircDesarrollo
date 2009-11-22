package mx.gob.salud.irc.server.utils;

import java.util.ArrayList;
import java.util.HashMap;

import mx.gob.salud.irc.client.forms.FormDescription;
import mx.gob.salud.irc.client.utils.db.TableDefinition;

public class Resources {

	public static ArrayList<TableDefinition> catalogDefinition = new ArrayList<TableDefinition>();
	public static HashMap<String, ArrayList<HashMap<String,String>>> catalogs = new HashMap<String, ArrayList<HashMap<String,String>>>();
	public static Properties properties = new Properties();
	public static HashMap<String, FormDescription> formDescriptions = new HashMap<String, FormDescription>();
	public static HashMap<String, TableDefinition> formTableDefinitions = new HashMap<String, TableDefinition>();
}
