package mx.gob.salud.irc.client.forms;

import java.io.Serializable;
import java.util.ArrayList;

public class Form implements Serializable{
	
	private static final long serialVersionUID = 400171451392766165L;

	private String application = null;
	private String code = null;
	
	private ArrayList<Section> sections = null;
	
	public Form(){
		sections = new ArrayList<Section>();
	}
	
	public void addSection(Section sec){
		sections.add(sec);
	}
	public Section getSection(int index){
		return (sections.get(index));
	}
	public int getNumSections(){
		return (sections.size());
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}
	
	public Form getCopy(boolean includeSubSections){
		Form ret = new Form();
		int index = 0, size = 0;

		ret.setApplication(application);
		ret.setCode(code);
		
		if (includeSubSections){
			size = sections.size();
			for (index = 0; index < size; index++){
				ret.addSection(sections.get(index).getCopy(includeSubSections));
			}
		}
		
		return (ret);
	}
	
	public String toString(){
		int index = 0, size = sections.size();
		StringBuffer ret = new StringBuffer();
		
		ret.append("F O R M :\n");
		ret.append("\tAplication = "+getApplication()+"\n");
		ret.append("\tForm Name = "+getCode()+"\n\n");
		ret.append("\tSECTIONS: \n");
		for (index = 0; index < size; index++){
			ret.append("\t"+sections.get(index).toString()+"\n");
		}
		
		return (ret.toString());
	}
}
