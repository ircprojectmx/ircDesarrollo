package mx.gob.salud.irc.client.forms.fields;

import java.util.ArrayList;
import mx.gob.salud.irc.client.forms.VisualControl;

public class FieldControl {

	private Field field = null;
	private FieldDescription fieldDescription = null;
	private VisualControl vc = null;
	
	public void generateFromDescription(FieldDescription fd, ArrayList<FieldDescription> allFd, ArrayList<FieldControl> allFc){
		setFieldDescription(fd);
		//Create Field
		field = new Field();
		field.setCode(fd.getCode());
		
		createVisualControl(allFd, allFc);
	}
	
	/**
	 * Crea uno a uno los componentes visuales.
	 * @param allFields Lista con la definicion de todos los campos para que de ser necesario este campo pueda ser ligado a otro.
	 */
	public void createVisualControl(ArrayList<FieldDescription> allFd, ArrayList<FieldControl> allFc){
		vc = new VisualControl(fieldDescription, allFd, allFc);
	}
	
	public String getVcType(){
		return (fieldDescription.getVcType());
	}
	
	public VisualControl getVisualControl() {
		return vc;
	}
	public void setVisualControl(VisualControl visualControl) {
		this.vc = visualControl;
	}
	
	public String getValue() {
		String value = vc.getValue(); 
		if (value != null)
			field.setValue(value);
		return field.getValue();
	}

	public FieldDescription getFieldDescription() {
		return fieldDescription;
	}

	public void setFieldDescription(FieldDescription fieldDescription) {
		this.fieldDescription = fieldDescription;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}
	
	public void setEnabled(boolean enabled){
		vc.setEnabled(enabled);
	}
}
