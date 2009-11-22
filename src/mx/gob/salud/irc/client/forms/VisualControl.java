package mx.gob.salud.irc.client.forms;
/**
 * Encargada de determinar el tipo de control visual en el que se insertará el campo, de la Bd TSYS_FROMS_FIELDS, 
 * toma el valor contenido en el campo CD_VISUAL_CONTROL.
 * En caso de que la cadena recuperada del campo CD_VISUAL_CONTROL no este definida en esta clase no importa, simplemente
 * no se crea un objeto visual para ese campo, pero en la seccion a la que pertenece existe.
 */
import java.util.ArrayList;
import mx.gob.salud.irc.client.Resources;
import mx.gob.salud.irc.client.catalogos.Catalog;
import mx.gob.salud.irc.client.forms.fields.ButtonField;
import mx.gob.salud.irc.client.forms.fields.CheckBoxField;
import mx.gob.salud.irc.client.forms.fields.DateBoxField;
import mx.gob.salud.irc.client.forms.fields.FieldControl;
import mx.gob.salud.irc.client.forms.fields.FieldDescription;
import mx.gob.salud.irc.client.forms.fields.LabelField;
import mx.gob.salud.irc.client.forms.fields.ListBoxField;
import mx.gob.salud.irc.client.forms.fields.PasswordField;
import mx.gob.salud.irc.client.forms.fields.PushButtonField;
import mx.gob.salud.irc.client.forms.fields.RadioButtonHField;
import mx.gob.salud.irc.client.forms.fields.RadioButtonVField;
import mx.gob.salud.irc.client.forms.fields.SuggestBoxField;
import mx.gob.salud.irc.client.forms.fields.TextAreaField;
import mx.gob.salud.irc.client.forms.fields.TextBoxField;
import mx.gob.salud.irc.client.forms.fields.ToggleButtonField;
import mx.gob.salud.irc.client.utils.UserMessage;
import mx.gob.salud.irc.client.utils.db.TableDefinition;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.VerticalSplitPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class VisualControl{
	public static String VC_BUTTON="BUTTON";
	public static String VC_BUTTON_IMG="BUTTON_IMG";
	public static String VC_BUTTON_TGL="BUTTON_TGL";
	public static String VC_CALENDAR="CALENDAR";
	public static String VC_CHECK_BTN="CHECK_BTN";
	public static String VC_LABEL="LABEL";
	public static String VC_LB_CHILD="LB_CHILD";
	public static String VC_LB_MASTER="LB_MASTER";
	public static String VC_LIST_BOX="LIST_BOX";
	public static String VC_PNL_FLOW="PNL_FLOW";
	public static String VC_PNL_HORIZ="PNL_HORIZ";
	public static String VC_PNL_HSPLIT="PNL_HSPLIT";
	public static String VC_PNL_STACK="PNL_STACK";
	public static String VC_PNL_TAB="PNL_TAB";
	public static String VC_PNL_VERT="PNL_VERT";
	public static String VC_PNL_VSPLIT="PNL_VSPLIT";
	public static String VC_PWD_BOX="PWD_BOX";
	public static String VC_RADIO_BTNH="RADIO_BTNH";
	public static String VC_RADIO_BTNV="RADIO_BTNV";
	public static String VC_SUGEST_BOX="SUGEST_BOX";
	public static String VC_TEXT_AREA="TEXT_AREA";
	public static String VC_TEXT_BOX="TEXT_BOX";
	public static String VC_SECAUT="PNL_SECAUT";
	
	
	public static String VC_EMPTY="EMPTY";

	private DecoratedPopupPanel prompt = null;
	private Widget control = null;
	private String type = null;
	
	public VisualControl(FieldDescription d, ArrayList<FieldDescription> allFd, ArrayList<FieldControl> allFc){
		type = d.getVcType();
		createWidget(d, allFd, allFc);
	}
	
	private void createWidget(FieldDescription desc, ArrayList<FieldDescription> allFd, ArrayList<FieldControl> allFc){
		if (type.equals(VisualControl.VC_TEXT_BOX)){
			control = new TextBoxField();
			setPrompt(desc);
		}
		else if (type.equals(VisualControl.VC_SUGEST_BOX)){
			control = new SuggestBoxField();
			setPrompt(desc);
		}
		else if (type.equals(VisualControl.VC_LABEL)){
			control = new LabelField(desc.getName());
		}
		else if (type.equals(VisualControl.VC_LB_CHILD)){
			ListBoxField newControl = new ListBoxField(), tmp = null;
			newControl.setStyleName(Resources.msg.styleFormData());
			FieldControl fieldMaster = null;
			fieldMaster = searchFCById(allFc, desc.getMinValue());
			if (fieldMaster != null){
				tmp = (ListBoxField) fieldMaster.getVisualControl().getControl();
				tmp.setChild(newControl);
			}
			else
				UserMessage.warning(Resources.msg.formTitle(), Resources.msg.errorBuilding("ListBoxChild:"+desc.getCode()), null);
			control = newControl;
			setPrompt(desc);
		}
		else if (type.equals(VisualControl.VC_LB_MASTER)){
			ListBoxField tmp = new ListBoxField();
			//Log.debug(desc.toString());
			tmp.setStyleName(Resources.msg.styleFormData());
			FieldDescription fieldChild = null;
			Catalog cat = Resources.cat.get(desc.getDynamicValues());
			Log.debug("Catalogo Recuperado.");
			if (cat != null){
				tmp.setData(cat.getData(), cat.getTdef().getPkColumns(), cat.getTdef().getColumnDescription());
				Log.debug("ListBox data Seted.");
				fieldChild = searchFDById(allFd, desc.getDependentIdField());
				Log.debug("Searched.");
				if (fieldChild != null){
					Catalog catChild = Resources.cat.get(fieldChild.getDynamicValues());
					catChild.getTdef();
					Log.debug("SetDataChild");
					tmp.setDataChild(catChild.getFkPkDescData(true), catChild.getTdef().getPkColumns(), catChild.getTdef().getColumnDescription(), catChild.getTdef().getFkColumns(TableDefinition.DEFAULT_FK_NAME));
				}
				else
					UserMessage.warning(Resources.msg.formTitle(), Resources.msg.errorBuilding("Master-ListBoxChild:"+desc.getCode()), null);
			}
			else
				UserMessage.error(Resources.msg.formTitle(), Resources.msg.errorBuilding("ListBoxMaster:"+desc.getCode()), Resources.msg.errorNotExistData(desc.getDynamicValues()), null);
			control = tmp;
			setPrompt(desc);
		}
		else if (type.equals(VisualControl.VC_LIST_BOX)){
			ListBoxField tmp = new ListBoxField();
			tmp.setStyleName(Resources.msg.styleFormData());
			if (desc.getStaticValues().trim().length() <= 0){
				Catalog cat = Resources.cat.get(desc.getDynamicValues());
				if (cat != null)
					tmp.setData(cat.getData(), cat.getTdef().getPkColumns(), cat.getTdef().getColumnDescription());
				else
					UserMessage.error(Resources.msg.formTitle(), Resources.msg.errorBuilding("ListBox:"+desc.getCode()), Resources.msg.errorNotExistData(desc.getDynamicValues()), null);
			}
			else{
				tmp.setData(desc.getDynamicValues(), desc.getStaticValues());
			}
			control = tmp;
			setPrompt(desc);
		}
		else if (type.equals(VisualControl.VC_CALENDAR)){
			control = new DateBoxField();
			setPrompt(desc);
		}
		else if (type.equals(VisualControl.VC_CHECK_BTN)){
			control = new CheckBoxField();
			control.setStyleName(Resources.msg.styleFormLabel());
			setPrompt(desc);
		}
		else if (type.equals(VisualControl.VC_RADIO_BTNH)){
			if (desc.getStaticValues().length() > 0){
				control = new RadioButtonHField(desc.getCode(),desc.getStaticValues(), desc.getDynamicValues());
				setPrompt(desc);
			}
		}
		else if (type.equals(VisualControl.VC_RADIO_BTNV)){
			if (desc.getStaticValues().length() > 0){
				control = new RadioButtonVField(desc.getCode(),desc.getStaticValues(), desc.getDynamicValues());
				setPrompt(desc);
			}
		}
		else if (type.equals(VisualControl.VC_PWD_BOX)){
			control = new PasswordField();
			setPrompt(desc);
		}
		else if (type.equals(VisualControl.VC_TEXT_AREA)){
			control = new TextAreaField();
			setPrompt(desc);
		}
		else if (type.equals(VisualControl.VC_BUTTON)){
			control = new ButtonField();
			setPrompt(desc);
		}
		else if (type.equals(VisualControl.VC_BUTTON_IMG)){
			control = new PushButtonField(new Image(GWT.getModuleBaseURL()+desc.getStaticValues()));
			setPrompt(desc);
		}
		else if (type.equals(VisualControl.VC_BUTTON_TGL)){
			control = new ToggleButtonField();
			setPrompt(desc);
		}
		else if (type.equals(VisualControl.VC_PNL_FLOW)){
			control = new FlowPanel();
		}
		else if (type.equals(VisualControl.VC_PNL_HORIZ)){
			control = new HorizontalPanel();
		}
		else if (type.equals(VisualControl.VC_PNL_HSPLIT)){
			control = new HorizontalSplitPanel();
		}
		else if (type.equals(VisualControl.VC_PNL_STACK)){
			control = new StackPanel();
		}
		else if (type.equals(VisualControl.VC_PNL_TAB)){
			DecoratedTabPanel tabPanel = new DecoratedTabPanel();
		    tabPanel.setWidth("100%");
		    tabPanel.setAnimationEnabled(true);
			control = tabPanel;
		}
		else if (type.equals(VisualControl.VC_PNL_VERT)){
			control = new VerticalPanel();
		}
		else if (type.equals(VisualControl.VC_PNL_VSPLIT)){
			control = new VerticalSplitPanel();
		}
		else if (type.equals(VisualControl.VC_SECAUT)){
			control = new AutoSection();
		}
		
		if (control != null){
			if (desc.getWidth() > 0)
				control.setWidth(desc.getWidth()+" px");
		}
	}
	
	private FieldDescription searchFDById(ArrayList<FieldDescription> allFields, int id){
		Log.debug("searchFieldById, ArrayList<FieldDescription>");
		FieldDescription tmp = null;
		int index = 0, size = allFields.size();
		Log.debug("Size:"+size);
		for (index = 0; index < size; index++){
			Log.debug("Reaiding index:"+index+", size:"+allFields.size());
			tmp = allFields.get(index);
			if (tmp.getId() == id)
				break;
			else
				tmp = null;
		}
		
		return (tmp);
	}
	
	private FieldControl searchFCById(ArrayList<FieldControl> allFields, int id){
		Log.debug("searchFieldById, ArrayList<FieldControl>");
		FieldControl tmp = null;
		int index = 0, size = allFields.size();
		
		for (index = 0; index < size; index++){
			tmp = allFields.get(index);
			if (tmp.getFieldDescription().getId() == id)
				break;
			else
				tmp = null;
		}
		
		return (tmp);
	}

	public Widget getControl() {
		return control;
	}

	public void setControl(Widget control) {
		this.control = control;
	}
	
	public static void addSection(FormControl form, SectionControl sec){
		if (form.getFormDescription().getVcType().equals(VisualControl.VC_PNL_TAB)){
			DecoratedTabPanel tabPanel = (DecoratedTabPanel) form.getVisualControl().getControl();
		    tabPanel.add(sec.getVisualControl().getControl(), sec.getSectionDescription().getDescription());
		}
		else{
			Panel panel = (Panel) form.getVisualControl().getControl();
			panel.add(sec.getVisualControl().getControl());
		}
	}
	
	private void setPrompt(FieldDescription desc){
		FocusWidget fw = null;
		SuggestBox sb = null;
		RadioButtonHField rbH = null;
		RadioButtonVField rbV = null;
		DateBox db = null;
		
		if (desc.getPrompt().trim().length() > 0){
			if (type.equals(VisualControl.VC_SUGEST_BOX)){
				sb = (SuggestBox) control;
				fw = (FocusWidget) sb.getTextBox();
				setPrompt(fw, desc.getPrompt());
			}
			else if (type.equals(VisualControl.VC_CALENDAR)){
				db = (DateBox) control;
				fw = (FocusWidget) db.getTextBox();
				setPrompt(fw, desc.getPrompt());
			}
			else if (type.equals(VisualControl.VC_RADIO_BTNH)){
				rbH = (RadioButtonHField) control;
				
				for (int inx = 0; inx < rbH.getWidgetCount(); inx++){
					fw = (FocusWidget) rbH.getWidget(inx);
					setPrompt(fw, desc.getPrompt());
				}
			}
			else if (type.equals(VisualControl.VC_RADIO_BTNV)){
				rbV = (RadioButtonVField) control;
				
				for (int inx = 0; inx < rbV.getWidgetCount(); inx++){
					fw = (FocusWidget) rbV.getWidget(inx);
					setPrompt(fw, desc.getPrompt());
				}
			}
			else{
				fw = (FocusWidget) control;
				setPrompt(fw, desc.getPrompt());
			}
		}
	}
	private void setPrompt(final FocusWidget fw, String text){
		prompt = new DecoratedPopupPanel(true);
		Label lab = new Label(text);
		lab.setStyleName(Resources.msg.stylePrompt());
		prompt.setStyleName(Resources.msg.stylePrompt());
		prompt.add(lab);
		fw.addMouseOverHandler(new MouseOverHandler(){
			public void onMouseOver(MouseOverEvent event) {
				prompt.setPopupPosition(fw.getAbsoluteLeft(),fw.getAbsoluteTop()+fw.getOffsetHeight());
				prompt.show();
			}});
		fw.addMouseOutHandler(new MouseOutHandler(){
			public void onMouseOut(MouseOutEvent event) {
				prompt.hide();
			}});
	}
	
	/**
	 * Only for a Sections panels
	 * @param sec
	 */
	public void setSection(SectionControl sec){
		try{
			if (sec.getSectionDescription().getTypeVisualControl().equalsIgnoreCase(VisualControl.VC_SECAUT)){
				AutoSection aut = (AutoSection)getControl();
				aut.setSection(sec);
			}
			else{
				Panel pnl = (Panel)getControl();
				pnl.add(sec.getBody());
			}
		}catch(Exception e){
			UserMessage.error(Resources.msg.formTitle(), Resources.msg.errorBuilding("VisualControl"), e.getMessage(), null);
		}
	}

	public String getType() {
		return type;
	}
	
	/**
	 * Para agregar el panel de una sub seccion al panel de otra Seccion.
	 * @param sec
	 */
	public void addSection(SectionControl sec){
		try{
			Panel pnl = (Panel)getControl();
			Panel pnlSub = (Panel)sec.getVisualControl().getControl();
			pnl.add(pnlSub);
		}catch(Exception e){
			UserMessage.error(Resources.msg.formTitle(), Resources.msg.errorBuilding("VisualControl"), e.getMessage(), null);
		}
	}
	
	public String getValue(){
		String ret = null;
		if (type.equals(VisualControl.VC_TEXT_BOX)){
			TextBoxField tmp = (TextBoxField)control;
			ret = tmp.getText();
		}
		else if (type.equals(VisualControl.VC_SUGEST_BOX)){
			SuggestBoxField tmp = (SuggestBoxField)control;
			ret = tmp.getText();
		}
		else if (type.equals(VisualControl.VC_LABEL)){
			LabelField tmp = (LabelField)control;
			ret = tmp.getText();
		}
		else if (type.equals(VisualControl.VC_LB_CHILD)){
			ListBoxField tmp = (ListBoxField)control;
			ret = tmp.getValue(tmp.getSelectedIndex());
		}
		else if (type.equals(VisualControl.VC_LB_MASTER)){
			ListBoxField tmp = (ListBoxField)control;
			ret = tmp.getValue(tmp.getSelectedIndex());
		}
		else if (type.equals(VisualControl.VC_LIST_BOX)){
			ListBoxField tmp = (ListBoxField)control;
			ret = tmp.getValue(tmp.getSelectedIndex());
		}
		else if (type.equals(VisualControl.VC_CALENDAR)){
			DateBoxField tmp = (DateBoxField)control;
			ret = tmp.getTextBox().getText();
		}
		else if (type.equals(VisualControl.VC_CHECK_BTN)){
			CheckBoxField tmp = (CheckBoxField)control;
			ret = String.valueOf(tmp.getValue());
		}
		else if (type.equals(VisualControl.VC_RADIO_BTNH)){
			RadioButtonHField tmp = (RadioButtonHField)control;
			ret = tmp.getValue();
		}
		else if (type.equals(VisualControl.VC_RADIO_BTNV)){
			RadioButtonVField tmp = (RadioButtonVField)control;
			ret = tmp.getValue();
		}
		else if (type.equals(VisualControl.VC_PWD_BOX)){
			PasswordField tmp = (PasswordField)control;
			ret = tmp.getText();
		}
		else if (type.equals(VisualControl.VC_TEXT_AREA)){
			TextAreaField tmp = (TextAreaField)control;
			ret = tmp.getText();
		}
		/*
		else if (type.equals(VisualControl.VC_BUTTON)){
		}
		else if (type.equals(VisualControl.VC_BUTTON_IMG)){
		}
		else if (type.equals(VisualControl.VC_BUTTON_TGL)){
		}
		else if (type.equals(VisualControl.VC_PNL_FLOW)){
		}
		else if (type.equals(VisualControl.VC_PNL_HORIZ)){
		}
		else if (type.equals(VisualControl.VC_PNL_HSPLIT)){
		}
		else if (type.equals(VisualControl.VC_PNL_STACK)){
		}
		else if (type.equals(VisualControl.VC_PNL_TAB)){
		}
		else if (type.equals(VisualControl.VC_PNL_VERT)){
		}
		else if (type.equals(VisualControl.VC_PNL_VSPLIT)){
		}
		else if (type.equals(VisualControl.VC_SECAUT)){
		}*/
		return (ret);
	}
	
	public void setEnabled(boolean enabled){
		FocusWidget fw = null;
		
		if (type.equals(VisualControl.VC_TEXT_BOX) ||
			type.equals(VisualControl.VC_LB_CHILD) || 
			type.equals(VisualControl.VC_LB_MASTER) ||
			type.equals(VisualControl.VC_LIST_BOX) || 
			type.equals(VisualControl.VC_CHECK_BTN) ||
			type.equals(VisualControl.VC_PWD_BOX) ||
			type.equals(VisualControl.VC_TEXT_AREA) ||
			type.equals(VisualControl.VC_BUTTON) ||
			type.equals(VisualControl.VC_BUTTON_IMG) ||
			type.equals(VisualControl.VC_BUTTON_TGL) ){
			fw = (FocusWidget) control;
			fw.setEnabled(enabled);
		}
		else if (type.equals(VisualControl.VC_SUGEST_BOX)){
			SuggestBoxField tmp = (SuggestBoxField)control;
			tmp.getTextBox().setEnabled(enabled);
			
		}
		else if (type.equals(VisualControl.VC_CALENDAR)){
			DateBoxField tmp = (DateBoxField)control;
			tmp.getTextBox().setEnabled(enabled);
		}
		else if (type.equals(VisualControl.VC_RADIO_BTNH)){
			RadioButtonHField tmp = (RadioButtonHField)control;
			tmp.setEnabled(enabled);
		}
		else if (type.equals(VisualControl.VC_RADIO_BTNV)){
			RadioButtonVField tmp = (RadioButtonVField)control;
			tmp.setEnabled(enabled);
		}
	}
	
	public static boolean isVisualControl(String vcType){
		if (VisualControl.VC_BUTTON.equals(vcType) ||
		VisualControl.VC_BUTTON_IMG.equals(vcType) ||
		VisualControl.VC_BUTTON_TGL.equals(vcType) ||
		VisualControl.VC_CALENDAR.equals(vcType) ||
		VisualControl.VC_CHECK_BTN.equals(vcType) ||
		VisualControl.VC_LABEL.equals(vcType) ||
		VisualControl.VC_LB_CHILD.equals(vcType) ||
		VisualControl.VC_LB_MASTER.equals(vcType) ||
		VisualControl.VC_LIST_BOX.equals(vcType) ||
		VisualControl.VC_PNL_FLOW.equals(vcType) ||
		VisualControl.VC_PNL_HORIZ.equals(vcType) ||
		VisualControl.VC_PNL_HSPLIT.equals(vcType) ||
		VisualControl.VC_PNL_STACK.equals(vcType) ||
		VisualControl.VC_PNL_TAB.equals(vcType) ||
		VisualControl.VC_PNL_VERT.equals(vcType) ||
		VisualControl.VC_PNL_VSPLIT.equals(vcType) ||
		VisualControl.VC_PWD_BOX.equals(vcType) ||
		VisualControl.VC_RADIO_BTNH.equals(vcType) ||
		VisualControl.VC_RADIO_BTNV.equals(vcType) ||
		VisualControl.VC_SUGEST_BOX.equals(vcType) ||
		VisualControl.VC_TEXT_AREA.equals(vcType) ||
		VisualControl.VC_TEXT_BOX.equals(vcType) ||
		VisualControl.VC_SECAUT.equals(vcType) ||
		VisualControl.VC_EMPTY.equals(vcType) )
			return (true);
		else
			return (false);
	}
}
