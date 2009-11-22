package mx.gob.salud.irc.client.forms;

import java.util.ArrayList;
import com.allen_sauer.gwt.log.client.Log;

import mx.gob.salud.irc.client.Resources;
import mx.gob.salud.irc.client.forms.fields.FieldControl;
import mx.gob.salud.irc.client.forms.fields.FieldDescription;
import mx.gob.salud.irc.client.utils.UserMessage;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

public class SectionControl {

	private SectionDescription sectionDesc = null;
	private Section section = null;
	
	private VisualControl vc = null;
	private ArrayList<FieldControl> fields = null;
	private FlexTable body = null;
	private ArrayList<SectionControl> subSections = null;
	
	private boolean enabled = true;
	
	public void generateFromDescription(SectionDescription sd){
		//Create Section
		section = new Section();
		section.setCode(sd.getCode());
		//UserMessage.ok("Generando Seccion:"+section.getCode(),"SetCode OK",null);
		//Create Descriptions
		sectionDesc = sd;
		//Create Sections Fields
		Log.debug("Creando Controles.");
		createFieldControls();
		//Create Controls
		Log.debug("Creando Controles Visuales.");
		createVisualControls();
		//UserMessage.ok("Generando Seccion:"+section.getCode(),"Seting Section",null);
		vc.setSection(this);
		
		//Create Sub Sections
		Log.debug("Creando SubSecciones.");
		createSubSectionControls();
	}
	
	private void createFieldControls(){
		int index = 0, size = sectionDesc.getNumberFields();
		FieldControl fc = null;
		
		fields = new ArrayList<FieldControl>();
		for (index = 0; index < size; index++){
			fc = new FieldControl();
			Log.debug("SectionControl, Creando Campos:"+sectionDesc.getFieldDescription(index).getCode());
			fc.generateFromDescription(sectionDesc.getFieldDescription(index), sectionDesc.getFields(), fields);
			fields.add(fc);
			section.addField(fc.getField());
		}
	}
	
	private void createSubSectionControls(){
		int index = 0, size = sectionDesc.getNumberSubSections();
		SectionControl sc = null;
		
		subSections = new ArrayList<SectionControl>();
		for (index = 0; index < size; index++){
			sc = new SectionControl();
			sc.generateFromDescription(sectionDesc.getSubSection(index));
			addSubSection(sc);
		}
	}
	
	private void createVisualControls(){
		int index = 0, size = fields.size(), row = 0, column = 0, columnSpan = 0;
		Label lab = null;
		HorizontalPanel hp = null;
		FlexCellFormatter format = null;
		
		//Create Visual Control Section
		FieldDescription newVcSec = new FieldDescription();
		newVcSec.setCode(sectionDesc.getCode());
		newVcSec.setVcType(sectionDesc.getTypeVisualControl());
		newVcSec.setName(sectionDesc.getDescription());
		vc = new VisualControl(newVcSec,null,null);
		
		//Creando todos los controles visuales.
		createVisualFields();
		UserMessage.wait(Resources.msg.formTitle(), Resources.msg.loadingData("SectionDesc:"+sectionDesc.getCode()), Resources.msg.runningProc("CreateVisualControl"));
		
		body = new FlexTable();
		//body.setBorderWidth(1);
		body.setStyleName(Resources.msg.styleFormTable());
		body.setCellPadding(Integer.parseInt(Resources.msg.styleFormTableCellPad()));
		body.setCellSpacing(Integer.parseInt(Resources.msg.styleFormTableCellSpa()));
		format = body.getFlexCellFormatter();
		for (index = 0, row = 0, column = 0, columnSpan = 0; index < size; index++,column++, columnSpan++){
			//Para descartar todos los campos que no requieran control visual
			while (index < size && !VisualControl.isVisualControl(fields.get(index).getVcType())){
				index ++;
			}
			
			lab = new Label(fields.get(index).getFieldDescription().getName()+":");
			lab.setStyleName(Resources.msg.styleFormLabel());
			hp = new HorizontalPanel();
			hp.setStyleName(Resources.msg.styleFormLabel());
			//hp.setBorderWidth(1);
			if (!fields.get(index).getVcType().equalsIgnoreCase(VisualControl.VC_LABEL) && !fields.get(index).getVcType().equalsIgnoreCase(VisualControl.VC_EMPTY)){
				hp.add(lab);
				hp.setCellVerticalAlignment(lab, HasAlignment.ALIGN_MIDDLE);
			}
			if (!fields.get(index).getVcType().equalsIgnoreCase(VisualControl.VC_EMPTY)){
				hp.add(fields.get(index).getVisualControl().getControl());
			}
			body.setWidget(row, column, hp);
			if (fields.get(index).getFieldDescription().getColspan() > 0){
				format.setColSpan(row, column, fields.get(index).getFieldDescription().getColspan());
				format.setHorizontalAlignment(row, column, fields.get(index).getFieldDescription().getAlign());
				//column += fields.get(index).getColspan();
				column = 0;
				columnSpan += fields.get(index).getFieldDescription().getColspan();
			}
			else{
				hp.setWidth("100%");
				if (!fields.get(index).getVcType().equalsIgnoreCase(VisualControl.VC_EMPTY))
					hp.setCellHorizontalAlignment(fields.get(index).getVisualControl().getControl(), HasAlignment.ALIGN_RIGHT);
			}
			if (column >= (sectionDesc.getColumns() - 1) || columnSpan >= (sectionDesc.getColumns() - 1)){
				column = -1;
				row++;
				columnSpan = -1;
			}
		}
	}
	
	private void createVisualFields(){
		int index = 0, size = fields.size();
		
		UserMessage.wait(Resources.msg.formTitle(), Resources.msg.loadingData("SectionDesc:"+sectionDesc.getCode()), Resources.msg.runningProc("CreateVisualFields"));
		
		for (index = 0; index < size; index++){
			fields.get(index).createVisualControl(sectionDesc.getFields(), fields);
		}
	}

	public VisualControl getVisualControl() {
		return vc;
	}

	public FlexTable getBody(){
		return (body);
	}
	
	public void setBody(FlexTable ft){
		body = ft;
	}
	
	public void setFields(ArrayList<FieldControl> flds) {
		this.fields = flds;
		for (int index = 0; index < fields.size(); index++){
			sectionDesc.addFieldDescription(fields.get(index).getFieldDescription());
		}
	}

	public SectionDescription getSectionDescription() {
		return sectionDesc;
	}

	public void setSectionDescription(SectionDescription section) {
		this.sectionDesc = section;
	}
	
	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public void addSubSection(SectionControl sec){
		//jugo de tomate frio(8)
		vc.addSection(sec);
		subSections.add(sec);
		section.addSubSection(sec.getSection());
	}
	
	public int getNumSubSections(){
		if (subSections == null)
			return (0);
		else
			return (subSections.size());
	}
	public SectionControl getSubSectionControl(int index){
		if (subSections == null)
			return (null);
		else
			return (subSections.get(index));
	}
	
	public SectionDescription getSubSection(int index){
		return (sectionDesc.getSubSection(index));
	}
	
	/**
	 * Recupera una Sub Seccion por su nombre (CD_SECTION).
	 * @param fieldCode Nombre de la Seccion, Campo (CD_SECTION).
	 * @return 
	 */
	public SectionControl getSubSectionControl(String fieldCode){
		SectionControl tmp = null;
		int index = 0, size = 0;
		
		if (subSections == null)
			return (null);
		else{
			size = getNumSubSections();
			for (index = 0; index < size; index++ ){
				if (getSubSection(index).getCode().equals(fieldCode)){
					tmp = getSubSectionControl(index);
					break;
				}
			}
		}
		return (tmp);
	}
	
	public int getNumFields(){
		return (fields.size());
	}
	
	/**
	 * Recupera el campo de esta seccion en el orden en el que fue agregado (NU_ORDER).
	 * @param index Numero de campo a recuperar.
	 * @return FieldDescription con el campo solicitado.
	 */
	public FieldDescription getField(int index){
		return (fields.get(index).getFieldDescription());
	}

	/**
	 * Recupera un campo por el codigo (CD_FIELD).
	 * @param fieldCode Nombre del Campo (CD_FIELD).
	 * @return 
	 */
	public FieldDescription getField(String fieldCode){
		FieldDescription tmp = null;
		int index = 0, size = fields.size();
		
		for (index = 0; index < size; index++ ){
			if (fields.get(index).getFieldDescription().getCode().equals(fieldCode)){
				tmp = fields.get(index).getFieldDescription();
				break;
			}
		}
		return (tmp);
	}
	
	public void generateCalculatedValues(){
		int index = 0, size = fields.size();
		AutoSection as = null;
		
		if (isEnabled()){
			if (sectionDesc.getTypeVisualControl().equals(VisualControl.VC_SECAUT)){
				as = (AutoSection) vc.getControl();
				as.generateCounter();
			}
			
			if (!section.isEmpty())
				locaCalculatedValues();
				
			size = subSections.size();
			for (index = 0; index < size; index++){
				subSections.get(index).generateCalculatedValues();
			}
		}
	}
	
	private void locaCalculatedValues(){
		
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean en) {
		enabled = en;
		section.setEmpty(enabled);
		
		int index = 0, size = fields.size();
		for (index = 0; index < size; index++){
			fields.get(index).setEnabled(en);
		}
	}
	
	public void clearSubSections(){
		subSections.clear();
	}
	
	public boolean validate(){
		int index = 0, size = fields.size();
		AutoSection as = null;
		boolean ret = false;
		
		if (isEnabled()){
			if (sectionDesc.getTypeVisualControl().equals(VisualControl.VC_SECAUT)){
				as = (AutoSection) vc.getControl();
				as.generateSections();
			}
			
			if (!section.isEmpty())
				ret = localValidations();
			else
				ret = true;
			
			if (ret){		
				size = subSections.size();
				for (index = 0; index < size && ret; index++){
					ret = subSections.get(index).validate();
				}
			}
		}
		else
			ret = true;
		
		return (ret);
	}
	
	private boolean localValidations(){
		return true;
	}
	
	/**
	 * Recupera solo los Datos de una seccion, pero que esten habilitados.
	 * Para ser enviados al servidor.
	 * @return
	 */
	public Section getSections(){
		Section ret = null, tmp = null;
		int index = 0, size = 0;
		
		if (isEnabled()){
			size = getNumSubSections();
			ret = section.getCopy(false);
			
			for (index = 0; index < size; index++){
				tmp = getSubSectionControl(index).getSections();
				if (tmp != null)
					ret.addSubSection(tmp);
			}
		}
		
		return (ret);
	}
}
