package mx.gob.salud.irc.client.forms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import mx.gob.salud.irc.client.forms.fields.FieldDescription;
import mx.gob.salud.irc.client.utils.UtilsStrings;

public class FormDescription implements Serializable{

	private static final long serialVersionUID = -6295831371561039368L;

	
	private String application = null;
	private String name = null;
	
	private String saveClass = null;
	private String saveParams = null;
	private String vcType = null;
	
	private ArrayList<String> sectionsNames = null;
	private ArrayList<SectionDescription> sections = null;
	
	public FormDescription(){
		sectionsNames = new ArrayList<String>();
		sections = new ArrayList<SectionDescription>();
	}

	public void loadDescription(ArrayList<HashMap<String,String>> results){
		FieldDescription newVc = new FieldDescription();
		HashMap<String,String> row = null;
		int index = 0, size = results.size();
		
		// FRM.CD_VISUAL_CONTROL, FRM.CD_SAVE_CLASS_NAME, FRM.CD_SAVE_PARAMS,DEF.CD_SECTION
		row = results.get(0);
		newVc.setCode(getName());
		setVcType(UtilsStrings.emptyIfIsNull(row.get("cd_visual_control"))); 
		newVc.setVcType(getVcType());
		setSaveClass(UtilsStrings.emptyIfIsNull(row.get("cd_save_class_name")));
		setSaveParams(UtilsStrings.emptyIfIsNull(row.get("cd_save_params")));
		
		for (index = 0; index < size; index++){
			row = results.get(index);
			addSectionName(UtilsStrings.emptyIfIsNull(row.get("cd_section")));
		}
	}	
	
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSaveClass() {
		return saveClass;
	}
	public void setSaveClass(String saveClass) {
		this.saveClass = saveClass;
	}
	public String getSaveParams() {
		return saveParams;
	}
	public void setSaveParams(String saveParams) {
		this.saveParams = saveParams;
	}
	public String getVcType() {
		return vcType;
	}
	public void setVcType(String vcType) {
		this.vcType = vcType;
	}
	public String getSectionsNames(int index) {
		return sectionsNames.get(index);
	}
	public int getNumSectionsNames() {
		return sectionsNames.size();
	}
	public int getNumSections() {
		return sections.size();
	}
	public SectionDescription getSection(int index) {
		return sections.get(index);
	}
	public void addSection(SectionDescription sec) {
		this.sections.add(sec);
	}
	
	public void addSectionName(String name) {
		this.sectionsNames.add(name);
	}
	
	public int getSectionIndex(String secName){
		int index = 0, size = sections.size();
		
		for(index = 0; index < size; index++){
			if (sections.get(index).getCode().equals(secName))
				break;
		}
		
		return (index);
	}
	
	public SectionDescription getSection(String secName){
		SectionDescription tmp = null;
		int index = getSectionIndex(secName);
		
		if (index > 0 && index < sections.size())
			tmp = getSection(index);
		return (tmp);
	}
	
	public String toString(){
		int index = 0, size = 0;
		StringBuffer ret = new StringBuffer();
			ret.append("FORM\n");
			ret.append("Application:"+application+"\n");
			ret.append("Name:"+name+"\n");
			ret.append("\tTypeVisualControl:"+vcType+"\n");
			ret.append("\tSaveClass:"+saveClass+"\n");
			ret.append("\tSaveParams:"+saveParams+"\n");
		
		size = sections.size();
		ret.append("\nSECTIONS \n");
		if (size <= 0)
			ret.append("EMPTY \n");
		for (index = 0; index < size; index++){
			ret.append(sections.get(index).toString());
		}
		return (ret.toString());
	}
}
