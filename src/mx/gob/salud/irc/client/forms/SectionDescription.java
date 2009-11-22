package mx.gob.salud.irc.client.forms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import mx.gob.salud.irc.client.forms.fields.FieldDescription;
import mx.gob.salud.irc.client.utils.UtilsStrings;

public class SectionDescription implements Serializable {

	private static final long serialVersionUID = -4015427074661933977L;

	
	private String code = null;
	private String description = null;
	private String typeVisualControl = null;
	private String saveClass = null;
	private String saveParams = null;
	private int columns = 0;
	private boolean validateOnline = false;
	private String owner = null;
	
	//Datos
	private ArrayList<FieldDescription> fields = null;
	private ArrayList<SectionDescription> subSections = null;
	
	public SectionDescription(){
		fields = new ArrayList<FieldDescription>();
		subSections = new ArrayList<SectionDescription>();
	}
	
	public void loadDescription(ArrayList<HashMap<String,String>> results){
		HashMap<String,String> row = results.get(0);
		setDescription(UtilsStrings.emptyIfIsNull(row.get("de_section")));
		setTypeVisualControl(UtilsStrings.emptyIfIsNull(row.get("cd_visual_control")));
		setColumns(Integer.parseInt(UtilsStrings.zeroIfIsEmptyOrNull(row.get("nu_columns"))));
		setOwner(UtilsStrings.emptyIfIsNull(row.get("cd_section_owner")));
		setSaveClass(UtilsStrings.emptyIfIsNull(row.get("cd_save_class_name")));
		setSaveParams(UtilsStrings.emptyIfIsNull(row.get("cd_save_params")));
		String val = UtilsStrings.zeroIfIsEmptyOrNull(row.get("cd_validateonline"));
		if (val.trim().equalsIgnoreCase("1"))
			setValidateOnline(true);
		else
			setValidateOnline(false);
	}
	
	public void loadFieldsDescription(ArrayList<HashMap<String,String>> result){
		HashMap <String,String> row = null;
		FieldDescription newFd = null;
		int index = 0, rows = result.size();
		
		for (index = 0; index < rows; index++){
			row = result.get(index);
			newFd = new FieldDescription();
			newFd.load(row);
			addFieldDescription(newFd);
		}
	}
	
	public void addFieldDescription(FieldDescription field){
		fields.add(field);
	}
	
	public FieldDescription getFieldDescription(int index){
		return (fields.get(index));
	}
	
	public int getNumberFields(){
		return (fields.size());
	}
	
	public void addSubSection(SectionDescription sub){
		subSections.add(sub);
	}
	
	public SectionDescription getSubSection(int index){
		return (subSections.get(index));
	}
	
	public int getNumberSubSections(){
		return (subSections.size());
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTypeVisualControl() {
		return typeVisualControl;
	}
	public void setTypeVisualControl(String typeVisualControl) {
		this.typeVisualControl = typeVisualControl;
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
	public int getColumns() {
		return columns;
	}
	public void setColumns(int columns) {
		this.columns = columns;
	}
	public boolean isValidateOnline() {
		return validateOnline;
	}
	public void setValidateOnline(boolean validateOnline) {
		this.validateOnline = validateOnline;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public ArrayList<FieldDescription> getFields() {
		return fields;
	}
	public void setFields(ArrayList<FieldDescription> fields) {
		this.fields = fields;
	}
	public ArrayList<SectionDescription> getSubSections() {
		return subSections;
	}
	public void setSubSections(ArrayList<SectionDescription> subSections) {
		this.subSections = subSections;
	}
	

	public String toString(){
		int index = 0, size = 0;
		StringBuffer ret = new StringBuffer();
			ret.append("\n\tSECTION\n");
			ret.append("\tCode:"+code+"\n");
			ret.append("\tDescripcion:"+description+"\n");
			ret.append("\tTypeVisualControl:"+typeVisualControl+"\n");
			ret.append("\tSaveClass:"+saveClass+"\n");
			ret.append("\tSaveParams:"+saveParams+"\n");
			ret.append("\tColumns:"+columns+"\n");
			ret.append("\tValidateOnLine:"+validateOnline+"\n");
			ret.append("\tOwner:"+owner+"\n");
		
		size = fields.size();
		for (index = 0; index < size; index++){
			ret.append(fields.get(index).toString());
		}
		
		size = subSections.size();
		ret.append("\n\t SUB SECTIONS \n");
		if (size <= 0)
			ret.append("\t EMPTY \n");
		for (index = 0; index < size; index++){
			ret.append(subSections.get(index).toString());
		}
		return (ret.toString());
	}
}
