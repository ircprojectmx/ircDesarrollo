package mx.gob.salud.irc.client.forms;

import java.io.Serializable;
import java.util.ArrayList;

import mx.gob.salud.irc.client.forms.fields.Field;

public class Section implements Serializable{

	private static final long serialVersionUID = 4145532177281524960L;
	private String code = null;
	
	private ArrayList<Field> fields = null;
	private ArrayList<Section> subSections = null;
	
	private boolean empty = true;
	
	public Section(){
		fields = new ArrayList<Field>();
		subSections = new ArrayList<Section>();
	}
	
	public void addField(Field fld){
		fields.add(fld);
	}
	public Field getField(int index){
		return (fields.get(index));
	}
	public int getNumFields(){
		return (fields.size());
	}
	
	public void addSubSection(Section sub){
		subSections.add(sub);
	}
	public Section getSubSection(int index){
		return (subSections.get(index));
	}
	public int getNumSubSections(){
		return (subSections.size());
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}
	
	public Section getCopy(boolean includeSubSections){
		Section ret = new Section();
		int index = 0, size = fields.size();
		
		ret.setCode(code);
		for (index = 0; index < size; index++){
			ret.addField(fields.get(index).getCopy());
		}
		
		if (includeSubSections){
			size = subSections.size();
			for (index = 0; index < size; index++){
				ret.addSubSection(subSections.get(index).getCopy(includeSubSections));
			}
		}
		
		return (ret);
	}
	
	public String toString(){
		int index = 0, size = fields.size();
		StringBuffer ret = new StringBuffer();
		
		ret.append("SECTION NAME = "+getCode()+"\n");
		ret.append("\tEmpty = "+isEmpty()+"\n");
		ret.append("\tFIELDS: \n");
		for (index = 0; index < size; index++){
			ret.append("\t\t"+fields.get(index).toString()+"\n");
		}
		
		ret.append("SUB SECTIONS:\n");
		size = subSections.size();
		for (index = 0; index < size; index++){
			ret.append(subSections.toString());
		}
		
		return (ret.toString());
	}
}
