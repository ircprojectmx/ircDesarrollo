package mx.gob.salud.irc.server.services;

import java.util.ArrayList;
import java.util.HashMap;

import mx.gob.salud.irc.client.forms.FormDescription;
import mx.gob.salud.irc.client.forms.SectionDescription;
import mx.gob.salud.irc.client.services.remote.Form;
import mx.gob.salud.irc.client.utils.Results;
import mx.gob.salud.irc.server.forms.GenericFormService;
import mx.gob.salud.irc.server.forms.FormService;
import mx.gob.salud.irc.server.utils.Resources;
import mx.gob.salud.irc.server.utils.UtilsBD;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class FormImpl extends RemoteServiceServlet implements Form {

	private static final long serialVersionUID = 7191420510990873496L;
	
	private UtilsBD dbCon = new UtilsBD();

	public ArrayList<HashMap<String, String>> getSection(String idSection) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT SEC.DE_SECTION, SEC.CD_VISUAL_CONTROL, SEC.NU_COLUMNS, SEC.CD_VALIDATEONLINE, SEC.CD_SECTION_OWNER, \n");
		sql.append("SEC.CD_SAVE_CLASS_NAME, SEC.CD_SAVE_PARAMS ");
		sql.append("FROM TSYS_FORMS_SECTIONS SEC \n");
		sql.append("WHERE SEC.CD_SECTION = '"+idSection+"' ");
		
		return dbCon.queryLista(sql.toString());
	}

	public ArrayList<HashMap<String, String>> getSectionDefinition(String idSection) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT SECDEF.CD_RESET, SECDEF.CD_VALIDATE, SECDEF.CD_COLSPAN, SECDEF.CD_ROWSPAN, SECDEF.CD_ALIGN, SECDEF.CD_HIDE, SECDEF.CD_READONLY,\n");
		sql.append("FLD.ID_FIELD, FLD.CD_FIELD, FLD.NB_FIELD, FLD.CD_PROMPT, FLD.DE_FIELD, FLD.TP_FIELD, FLD.DE_STATIC_VALUES, FLD.DE_DYNAMIC_VALUES, FLD.NU_MAX_LEN, FLD.NU_MIN_LEN, FLD.CD_REQUIRED, FLD.CD_ONLY_NUMBERS, FLD.CD_DEPENDENT_ID_FIELD, FLD.NU_MIN_VALUE, FLD.NU_MAX_VALUE, FLD.DT_MIN_VALUE, FLD.DT_MAX_VALUE, FLD.NU_WIDTH, FLD.CD_CUSTOM_VALIDATION, FLD.CD_VISUAL_CONTROL \n"); 
		sql.append("FROM TSYS_FORMS_SECTIONS_DEF SECDEF, TSYS_FORMS_FIELDS FLD \n");
		sql.append("WHERE FLD.ID_FIELD = SECDEF.ID_FIELD \n");
		sql.append("AND SECDEF.CD_SECTION = '"+idSection+"' \n");
		sql.append("ORDER BY SECDEF.NU_ORDER ");
		
		return dbCon.queryLista(sql.toString());
	}

	public ArrayList<HashMap<String, String>> getForm(String app, String idForm) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT FRM.CD_VISUAL_CONTROL, FRM.CD_SAVE_CLASS_NAME, FRM.CD_SAVE_PARAMS, \n");
		sql.append("DEF.CD_SECTION \n");
		sql.append("FROM TSYS_FORMS FRM, TSYS_FORMS_DEF DEF \n");
		sql.append("WHERE FRM.CD_APPLICATION = '"+app+"' \n");
		sql.append("AND FRM.CD_FORM = '"+idForm+"' \n");
		sql.append("ORDER BY DEF.NU_ORDER ");
		
		return dbCon.queryLista(sql.toString());
	}

	public Results delete(HashMap<String,String> params, mx.gob.salud.irc.client.forms.Form form) {
		Results loadClass = new Results();
		FormDescription fd = Resources.formDescriptions.get(form.getApplication()+form.getCode());
		FormService service = GenericFormService.getFormService(loadClass, fd.getSaveClass());
		if (service == null)
			return (loadClass);
		else
			return service.delete(params, form);
	}

	public Results insert(HashMap<String,String> params, mx.gob.salud.irc.client.forms.Form form) {
		Results loadClass = new Results();
		FormDescription fd = Resources.formDescriptions.get(form.getApplication()+form.getCode());
		FormService service = GenericFormService.getFormService(loadClass, fd.getSaveClass());
		if (service == null)
			return (loadClass);
		else
			return service.insert(params, form);
	}

	public Results select(HashMap<String,String> params, mx.gob.salud.irc.client.forms.Form form) {
		Results loadClass = new Results();
		FormDescription fd = Resources.formDescriptions.get(form.getApplication()+form.getCode());
		FormService service = GenericFormService.getFormService(loadClass, fd.getSaveClass());
		if (service == null)
			return (loadClass);
		else
			return service.select(params, form);
	}

	public Results service(HashMap<String,String> params, mx.gob.salud.irc.client.forms.Form form) {
		Results loadClass = new Results();
		FormDescription fd = Resources.formDescriptions.get(form.getApplication()+form.getCode());
		FormService service = GenericFormService.getFormService(loadClass, fd.getSaveClass());
		if (service == null)
			return (loadClass);
		else
			return service.service(params, form);
	}

	public Results update(HashMap<String,String> params, mx.gob.salud.irc.client.forms.Form form) {
		Results loadClass = new Results();
		FormDescription fd = Resources.formDescriptions.get(form.getApplication()+form.getCode());
		FormService service = GenericFormService.getFormService(loadClass, fd.getSaveClass());
		if (service == null)
			return (loadClass);
		else
			return service.update(params, form);
	}

	public FormDescription getFormDescription(String app, String idForm, boolean readFromDB) {
		ArrayList<HashMap<String,String>> results = null;
		FormDescription fd = Resources.formDescriptions.get(app+idForm);
		SectionDescription sd = null;
		int index = 0, size = 0;
		
		if (readFromDB || fd == null){
			try{
				results = getForm(app, idForm);
			}catch(Exception e){
				System.out.println("Error al leer la tabla TSYS_FORMS, TSYS_FORMS_DEF: "+e.getMessage());
				results = null;
			}
			
			if (results != null){
				try{
					fd = new FormDescription();
					fd.setApplication(app);
					fd.setName(idForm);
					fd.loadDescription(results);
					size = fd.getNumSectionsNames();
					
					for (index = 0; index < size; index++){
						sd = getSectionDescription(fd.getSectionsNames(index));
						if (sd == null){
							fd = null;
							break;
						}
						//Si no es una subsección se agrega al Form
						if (sd.getOwner().trim().length() == 0){
							fd.addSection(sd);
						}
						else{
							//Si es una subseccion se agrega a la seccion.
							SectionDescription owner = fd.getSection(fd.getSectionIndex(sd.getOwner()));
							if (owner != null)
								owner.addSubSection(sd);
							else{
								System.out.println("Error al crear el Form:"+app+", "+idForm+". Error: No existe la seccion owner: "+sd.getOwner());
								fd = null;
								break;
							}
						}
					}
					if (fd != null){
						Resources.formDescriptions.put(app+idForm, fd);
						//Reset formTableDefinitions for this sections.
						resetFromTableDefinitions(fd);
					}
				}catch(Exception e){
					System.out.println("Error al crear el Form:"+app+", "+idForm+". Error: "+e.getMessage());
					fd = null;
				}
			}
		}
		return fd;
	}

	private SectionDescription getSectionDescription(String idSection) {
		ArrayList<HashMap<String,String>> results = null;
		SectionDescription sd = null;
		
		try{
			results = getSection(idSection);
		}catch(Exception e){
			System.out.println("Error al leer la tabla TSYS_FORMS_SECTIONS: "+e.getMessage());
			results = null;
		}
		
		if (results != null){
			sd = new SectionDescription();
			sd.setCode(idSection);
			sd.loadDescription(results);
			
			try{
				results = getSectionDefinition(idSection);
			}catch(Exception e){
				System.out.println("Error al leer la tabla TSYS_FORMS_SECTIONS_DEF: "+e.getMessage());
				results = null;
			}
			
			if (results != null){
				sd.loadFieldsDescription(results);
			}
			else
				sd = null;
		}
		
		return sd;
	}
	
	private void resetFromTableDefinitions(FormDescription fd){
		int index = 0, size = fd.getNumSections();
		
		if (Resources.formTableDefinitions.size() > 0){
			for (index = 0; index < size; index++){
				Resources.formTableDefinitions.remove(fd.getSection(index).getCode());
			}
		}
	}
}
