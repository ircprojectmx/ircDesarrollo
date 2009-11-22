package mx.gob.salud.irc.server.forms;

import java.util.HashMap;

import mx.gob.salud.irc.client.forms.Form;
import mx.gob.salud.irc.client.forms.FormDescription;
import mx.gob.salud.irc.client.forms.Section;
import mx.gob.salud.irc.client.forms.SectionDescription;
import mx.gob.salud.irc.client.forms.fields.FieldDescription;
import mx.gob.salud.irc.client.statics.Codes;
import mx.gob.salud.irc.client.utils.Results;
import mx.gob.salud.irc.client.utils.db.TableDefinition;
import mx.gob.salud.irc.server.utils.Resources;

public class GenericSectionService implements SectionService {

	public Results delete(HashMap<String, String> params, FormDescription fd, SectionDescription sd, Form from, Section sec) {
		// TODO Auto-generated method stub
		return null;
	}

	public Results insert(HashMap<String, String> params, FormDescription fd, SectionDescription sd, Form from, Section sec) {
		int index = 0, size = 0;
		TableDefinition td = getTableDefinition(sd);
		Results ret = new Results();
		
		for (index = 0; index < size; index++){
		}
		
		return ret;
	}

	public Results select(HashMap<String, String> params, FormDescription fd, SectionDescription sd, Form from, Section sec) {
		// TODO Auto-generated method stub
		return null;
	}

	public Results service(HashMap<String, String> params, FormDescription fd, SectionDescription sd, Form from, Section sec) {
		// TODO Auto-generated method stub
		return null;
	}

	public Results update(HashMap<String, String> params, FormDescription fd, SectionDescription sd, Form from, Section sec) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static SectionService getSectionService(Results res, String className){
		SectionService tmp = null;
		Class [] classParm = null;
		Object [] objectParm = null;
		         
		try {
		  Class cl = Class.forName(className);
		  java.lang.reflect.Constructor co = cl.getConstructor(classParm);
		  tmp = (SectionService) co.newInstance(objectParm);
		  }
		catch (Exception e) {
			res.setCode(Codes.ERROR);
			res.setMethodName("getSectionService");
			res.setDetail(e.getMessage());
		  return null;
		}
		return (tmp);
	}

	
	public TableDefinition getTableDefinition(SectionDescription sd){
		TableDefinition td = null;
		FieldDescription fd = null;
		int index = 0, size = 0;
		String type = null, use = null;
		
		td = Resources.formTableDefinitions.get(sd.getCode());
		if (td == null){
			td = new TableDefinition();
			td.setName(sd.getSaveParams());
			size = sd.getNumberFields();
			
			for(index = 0; index < size; index++){
				fd = sd.getFieldDescription(index);
				type = fd.getVcType();
				use = fd.getStaticValues();
				if (type.equals(FormConstants.TP_COLUMN_PK))
					td.addPk(fd.getCode());
				else if (use.equals(FormConstants.USE_AS_FK))
					td.addFk(TableDefinition.DEFAULT_FK_NAME, fd.getCode());
				else
					td.addColumn(fd.getCode());
			}
		}
		
		return (td);
	}
}
