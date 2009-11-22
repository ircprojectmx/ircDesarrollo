package mx.gob.salud.irc.client.forms.fields;

import java.io.Serializable;
import java.util.HashMap;

import mx.gob.salud.irc.client.Resources;
import mx.gob.salud.irc.client.utils.UserMessage;
import mx.gob.salud.irc.client.utils.UtilsStrings;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;

public class FieldDescription implements Serializable{

	private static final long serialVersionUID = -5745657499178357420L;
	private String code = "";
	private String type = "";
	private int id = 0;
	private String name = "";
	private String prompt = "";
	private String description = "";
	private String staticValues = "";
	private String dynamicValues = "";
	private int maxLen = 0;
	private int minLen = 0;
	private boolean required = false;
	private boolean onlyNumbers = false;
	private int dependentIdField = 0;
	private int minValue = 0;
	private int maxValue = 0;
	private String dtMinValue = "";
	private String dtMaxValue = "";
	private int width = 0;
	private boolean customValidation = false;
	private String vcType = "";
	
	private boolean reset = false;
	private boolean validate = false;
	private int colspan = 0;
	private int rowspan = 0;
	private int align = 0;
	private boolean hide = false;
	private boolean readOnly = false;
	
	
	public void load(HashMap <String,String> row){
		try{
			setId(Integer.parseInt(UtilsStrings.zeroIfIsEmptyOrNull(row.get("id_field"))));
			setCode(UtilsStrings.emptyIfIsNull(row.get("cd_field")));
			setName(UtilsStrings.emptyIfIsNull(row.get("nb_field")));
			setPrompt(UtilsStrings.emptyIfIsNull(row.get("cd_prompt")));
			setDescription(UtilsStrings.emptyIfIsNull(row.get("de_field")));
			setType(UtilsStrings.emptyIfIsNull(row.get("tp_field")));
			setStaticValues(UtilsStrings.emptyIfIsNull(row.get("de_static_values")));
			setDynamicValues(UtilsStrings.emptyIfIsNull(row.get("de_dynamic_values")));
			setMaxLen(Integer.parseInt(UtilsStrings.zeroIfIsEmptyOrNull(row.get("nu_max_len"))));
			setMinLen(Integer.parseInt(UtilsStrings.zeroIfIsEmptyOrNull(row.get("nu_min_len"))));
			setRequired(UtilsStrings.intToBoolean(UtilsStrings.zeroIfIsEmptyOrNull(row.get("cd_required"))));
			setOnlyNumbers(UtilsStrings.intToBoolean(UtilsStrings.zeroIfIsEmptyOrNull(row.get("cd_only_numbers"))));
			setDependentIdField(Integer.parseInt(UtilsStrings.zeroIfIsEmptyOrNull(row.get("cd_dependent_id_field"))));
			setMinValue(Integer.parseInt(UtilsStrings.zeroIfIsEmptyOrNull(row.get("nu_min_value"))));
			setMaxValue(Integer.parseInt(UtilsStrings.zeroIfIsEmptyOrNull(row.get("nu_max_value"))));
			setDtMinValue(UtilsStrings.emptyIfIsNull(row.get("dt_min_value")));
			setDtMaxValue(UtilsStrings.emptyIfIsNull(row.get("dt_max_value")));
			setWidth(Integer.parseInt(UtilsStrings.zeroIfIsEmptyOrNull(row.get("nu_width"))));
			setCustomValidation(UtilsStrings.intToBoolean(UtilsStrings.zeroIfIsEmptyOrNull(row.get("cd_custom_validation"))));
			setVcType(UtilsStrings.emptyIfIsNull(row.get("cd_visual_control")));

			setReset(UtilsStrings.intToBoolean(UtilsStrings.zeroIfIsEmptyOrNull(row.get("cd_reset"))));
			setValidate(UtilsStrings.intToBoolean(UtilsStrings.zeroIfIsEmptyOrNull(row.get("cd_validate"))));
			setColspan(Integer.parseInt(UtilsStrings.zeroIfIsEmptyOrNull(row.get("cd_colspan"))));
			setRowspan(Integer.parseInt(UtilsStrings.zeroIfIsEmptyOrNull(row.get("cd_rowspan"))));
			setAlign(Integer.parseInt(UtilsStrings.zeroIfIsEmptyOrNull(row.get("cd_align"))));
			setHide(UtilsStrings.intToBoolean(UtilsStrings.zeroIfIsEmptyOrNull(row.get("cd_hide"))));
			setReadOnly(UtilsStrings.intToBoolean(UtilsStrings.zeroIfIsEmptyOrNull(row.get("cd_readonly"))));
		}catch(Exception e){
			UserMessage.error(Resources.msg.formTitle(), Resources.msg.errorReadingHashTable("Loading Field Description"), e.getMessage(), null);
		}
	}
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String nombre) {
		this.name = nombre;
	}
	public String getPrompt() {
		return prompt;
	}
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String descripcion) {
		this.description = descripcion;
	}
	public String getStaticValues() {
		return staticValues;
	}
	public void setStaticValues(String staticValues) {
		this.staticValues = staticValues;
	}
	public String getDynamicValues() {
		return dynamicValues;
	}
	public void setDynamicValues(String dynamicValues) {
		this.dynamicValues = dynamicValues;
	}
	public int getMaxLen() {
		return maxLen;
	}
	public void setMaxLen(int maxLen) {
		this.maxLen = maxLen;
	}
	public int getMinLen() {
		return minLen;
	}
	public void setMinLen(int minLen) {
		this.minLen = minLen;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public boolean isOnlyNumbers() {
		return onlyNumbers;
	}
	public void setOnlyNumbers(boolean onlyNumbers) {
		this.onlyNumbers = onlyNumbers;
	}
	public int getDependentIdField() {
		return dependentIdField;
	}
	public void setDependentIdField(int dependentIdField) {
		this.dependentIdField = dependentIdField;
	}
	public int getMinValue() {
		return minValue;
	}
	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}
	public int getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}
	public String getDtMinValue() {
		return dtMinValue;
	}
	public void setDtMinValue(String dtMinValue) {
		this.dtMinValue = dtMinValue;
	}
	public String getDtMaxValue() {
		return dtMaxValue;
	}
	public void setDtMaxValue(String dtMaxValue) {
		this.dtMaxValue = dtMaxValue;
	}
	public boolean isCustomValidation() {
		return customValidation;
	}
	public void setCustomValidation(boolean customValidation) {
		this.customValidation = customValidation;
	}
	public String getVcType() {
		return vcType;
	}
	public void setVcType(String vct) {
		this.vcType = vct;
	}
	
	public boolean isReset() {
		return reset;
	}
	public void setReset(boolean reset) {
		this.reset = reset;
	}
	public boolean isValidate() {
		return validate;
	}
	public void setValidate(boolean validate) {
		this.validate = validate;
	}
	public int getColspan() {
		return colspan;
	}
	public void setColspan(int colspan) {
		this.colspan = colspan;
	}
	public int getRowspan() {
		return rowspan;
	}
	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}
	public void setAlign(int i) {
		align = i;
	}
	public HorizontalAlignmentConstant getAlign() {
		HorizontalAlignmentConstant ret = null;
		switch(align){
		case 0:
			ret = HasHorizontalAlignment.ALIGN_LEFT;
			break;
		case 1:
			ret = HasHorizontalAlignment.ALIGN_CENTER;
			break;
		case 2:
			ret = HasHorizontalAlignment.ALIGN_RIGHT;
			break;
		}
		return (ret);
	}
	
	public boolean isHide() {
		return hide;
	}
	public void setHide(boolean hide) {
		this.hide = hide;
	}
	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readonly) {
		this.readOnly = readonly;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Copia la definicion del campo y su valor actual, lo que omite es el control visual.
	 * @return Variable nueva donde se copiara la definicion.
	 */
	public FieldDescription copyFieldDescription(){
		FieldDescription item = new FieldDescription();
		item.setId(getId());
		item.setCode(getCode());
		item.setName(getName());
		item.setPrompt(getPrompt());
		item.setDescription(getDescription());
		item.setType(getType());
		item.setStaticValues(getStaticValues());
		item.setDynamicValues(getDynamicValues());
		item.setMaxLen(getMaxLen());
		item.setMinLen(getMinLen());
		item.setRequired(isRequired());
		item.setOnlyNumbers(isOnlyNumbers());
		item.setDependentIdField(getDependentIdField());
		item.setMinValue(getMinValue());
		item.setMaxValue(getMaxValue());
		item.setDtMinValue(getDtMinValue());
		item.setDtMaxValue(getDtMaxValue());
		item.setWidth(getWidth());
		item.setCustomValidation(isCustomValidation());
		item.setVcType(getVcType());

		item.setReset(isReset());
		item.setValidate(isValidate());
		item.setColspan(getColspan());
		item.setRowspan(getRowspan());
		item.setAlign(getAlign());
		item.setHide(isHide());
		item.setReadOnly(isReadOnly());
		
		return (item);
	}

	private void setAlign(HorizontalAlignmentConstant align2) {
		if (align2 == HasHorizontalAlignment.ALIGN_LEFT ||
			align2 == HasHorizontalAlignment.ALIGN_DEFAULT)
			align = 0;
		else if(align2 == HasHorizontalAlignment.ALIGN_CENTER)
			align = 1;
		else if(align2 == HasHorizontalAlignment.ALIGN_RIGHT)
			align = 3;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String toString(){
		StringBuffer ret = new StringBuffer();
		ret.append("\t\tFIELD ");
		ret.append("\t\tCode:"+code+"\n");
		ret.append("\t\tType:"+type+"\n");
		ret.append("\t\tID:"+id+"\n");
		ret.append("\t\tName:"+name+"\n");
		ret.append("\t\tPrompt:"+prompt+"\n");
		ret.append("\t\tDescription:"+description+"\n");
		ret.append("\t\tStaticValues:"+staticValues+"\n");
		ret.append("\t\tDynamicValues:"+dynamicValues+"\n");
		ret.append("\t\tMaxLen:"+maxLen+"\n");
		ret.append("\t\tMinLen"+minLen+"\n");
		ret.append("\t\tRequired:"+required+"\n");
		ret.append("\t\tOnlyNumbers:"+onlyNumbers+"\n");
		ret.append("\t\tDependentIdField:"+dependentIdField+"\n");
		ret.append("\t\tMinValue:"+minValue+"\n");
		ret.append("\t\tMaxValue:"+maxValue+"\n");
		ret.append("\t\tDtMinValue:"+dtMinValue+"\n");
		ret.append("\t\tDtMaxValue:"+dtMaxValue+"\n");
		ret.append("\t\tWidt:"+width+"\n");
		ret.append("\t\tCustomValidation:"+customValidation+"\n");
		ret.append("\t\tVcType:"+vcType+"\n");
		ret.append("\t\tReset:"+reset+"\n");
		ret.append("\t\tValidate"+validate+"\n");
		ret.append("\t\tColspan:"+colspan+"\n");
		ret.append("\t\tRowSpan:"+rowspan+"\n");
		ret.append("\t\tAlign:"+align+"\n");
		ret.append("\t\tHide:"+hide+"\n");
		ret.append("\t\tReadOnly:"+readOnly+"\n");
		return (ret.toString());
	}
}
