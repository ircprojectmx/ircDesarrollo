package mx.gob.salud.irc.server.forms;

import java.util.HashMap;

import mx.gob.salud.irc.client.forms.Form;
import mx.gob.salud.irc.client.forms.FormDescription;
import mx.gob.salud.irc.client.forms.SectionDescription;
import mx.gob.salud.irc.client.statics.Codes;
import mx.gob.salud.irc.client.utils.Results;
import mx.gob.salud.irc.server.utils.Resources;

public class GenericFormService implements FormService {

	private Results response = null;
	
	public GenericFormService(){
		response = new Results();
	}
	
	public Results delete(HashMap<String,String> params, Form form) {
		return response;
	}

	public Results insert(HashMap<String,String> params, Form form) {
		int index = 0, size = form.getNumSections();
		FormDescription fd = Resources.formDescriptions.get(form.getApplication()+form.getCode());
		SectionDescription currentSec = null;
		SectionService service = null;
		
		System.out.println(form.toString());
		for(index = 0; index <= size; index++){
			currentSec = fd.getSection(index);
			service = GenericSectionService.getSectionService(response, currentSec.getSaveClass());
			if (service == null)
				break;
			response = service.insert(params, fd, currentSec, form, form.getSection(index));
			if (response.getCode() != Codes.OK)
				break;
		}
			
		return response;
	}

	public Results select(HashMap<String,String> params, Form form) {
		// TODO Auto-generated method stub
		return response;
	}

	public Results service(HashMap<String,String> params, Form form) {
		// TODO Auto-generated method stub
		return response;
	}

	public Results update(HashMap<String,String> params, Form form) {
		// TODO Auto-generated method stub
		return response;
	}

	public static FormService getFormService(Results res, String className){
		FormService tmp = null;
		Class [] classParm = null;
		Object [] objectParm = null;
		         
		try {
		  Class cl = Class.forName(className);
		  java.lang.reflect.Constructor co = cl.getConstructor(classParm);
		  tmp = (FormService) co.newInstance(objectParm);
		  }
		catch (Exception e) {
			res.setCode(Codes.ERROR);
			res.setMethodName("getFormService");
			res.setDetail(e.getMessage());
		  return null;
		}
		return (tmp);
	}
}
