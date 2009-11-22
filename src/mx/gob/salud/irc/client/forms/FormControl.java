package mx.gob.salud.irc.client.forms;

import java.util.ArrayList;

import mx.gob.salud.irc.client.Resources;
import mx.gob.salud.irc.client.forms.fields.FieldDescription;
import mx.gob.salud.irc.client.services.CommonCallBack;
import mx.gob.salud.irc.client.services.remote.FormAsync;
import mx.gob.salud.irc.client.statics.Codes;
import mx.gob.salud.irc.client.utils.Results;
import mx.gob.salud.irc.client.utils.UserMessage;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FormControl {

	private Form form = null;
	
	private FormDescription formDesc = null;
	
	private ArrayList<SectionControl> sections = null;
	
	private VisualControl vc = null;
	
	private CommonCallBack response = null;
	
	public FormControl(){
		sections = new ArrayList<SectionControl>();
	}
	
	public void load(String ap, final String nm, CommonCallBack ccb){
		response = ccb;
		FormAsync frm = GWT.create(mx.gob.salud.irc.client.services.remote.Form.class);
		UserMessage.wait(Resources.msg.formTitle(), Resources.msg.loadingData("Form:"+nm), Resources.msg.readingDataBase());
		frm.getFormDescription(ap, nm, false, new AsyncCallback<FormDescription>() {
					public void onFailure(Throwable caught) {
						response.setCode(Codes.ERROR_NETWORK);
						response.setMessage(Resources.msg.errorNetWork());
						response.setDetail(caught.getMessage());
						response.setMethodName("load");
						response.onFailure();
						UserMessage.error(Resources.msg.formTitle(), Resources.msg.errorNetWork(), caught.getMessage(), response);
						UserMessage.waitHide();
					}
					public void onSuccess(FormDescription result) {
						if (result == null){
							response.setCode(Codes.ERROR_DB);
							response.setMessage(Resources.msg.errorNotExistData(formDesc.getName()));
							response.setMethodName("load");
							response.onFailure();
							UserMessage.waitHide();
							UserMessage.error(Resources.msg.formTitle(), Resources.msg.errorNotExistData(nm), null, null);
						}
						else{
							formDesc = result;
							GWT.log(formDesc.toString(), null);
							//UserMessage.ok("FORM",formDesc.toString(),null);
							//Log.debug(formDesc.toString());
							generateFromDescription();
							response.onSuccess();
						}
						UserMessage.waitHide();
					}
				});
	}
	
	private void generateFromDescription(){
		try{
			//Create Form
			form = new Form();
			form.setApplication(formDesc.getApplication());
			form.setCode(formDesc.getName());
			//Create Control
			FieldDescription newVc = new FieldDescription();
			newVc.setCode(formDesc.getName());
			newVc.setVcType(formDesc.getVcType());
			vc = new VisualControl(newVc,null, null);
			Log.debug("VisualControl Creado.");
			//Create Sections Control
			int index = 0, size = formDesc.getNumSections();
			SectionControl sc = null;
			
			
			for (index = 0; index < size; index++){
				sc = new SectionControl();
				Log.debug("FormControl, Generando Seccion:"+formDesc.getSection(index).getCode());
				sc.generateFromDescription(formDesc.getSection(index));
				sections.add(sc);
				form.addSection(sc.getSection());
				VisualControl.addSection(this, sc);
			}
		}catch(Exception e){
			UserMessage.error(Resources.msg.formTitle(), Resources.msg.errorBuilding("Form, fromDescription"), "Detail:"+e.getMessage(), null);
		}
	}
	
	
	public VisualControl getVisualControl() {
		return vc;
	}
	
	public SectionControl getSectionControl(String secName){
		return (getSectionControl(formDesc.getSectionIndex(secName)));
	}
	
	public int getNumSectionsControls(){
		return (sections.size());
	}
	
	public SectionControl getSectionControl(int index){
		return (sections.get(index));
	}
	
	public FormDescription getFormDescription(){
		return (formDesc);
	}
	
	public void save(CommonCallBack ccb){
		response = ccb;
		UserMessage.wait(Resources.msg.formTitle(), Resources.msg.runningProc("Save"), Resources.msg.validating());
		if (validate()){
			UserMessage.wait(Resources.msg.formTitle(), Resources.msg.runningProc("Save"), Resources.msg.generatingData());
			if (beforeSave())
				send();
			else{
				response.setCode(Codes.ERROR);
				response.setMessage(Resources.msg.errorRuningLocalProc("BeforeSave"));
				response.setMethodName("save");
				response.onFailure();
				UserMessage.waitHide();
			}
		}
		else{
			response.setCode(Codes.ERROR_VALIDATING);
			response.setMessage(Resources.msg.errorRuningLocalProc("Validate"));
			response.setMethodName("save");
			response.onFailure();
			UserMessage.waitHide();
		}
				
	}
	
	public boolean validate(){
		int index = 0, size = formDesc.getNumSections();
		
		for (index = 0; index < size; index++){
			if (!sections.get(index).validate())
				return false;
		}
		return true;
	}
	
	protected boolean beforeSave(){
		generateCalculatedValues(sections);
		return true;
	}
	
	private void send(){
		Form data = getForm();
		FormAsync frm = GWT.create(mx.gob.salud.irc.client.services.remote.Form.class);
		UserMessage.wait(Resources.msg.formTitle(), Resources.msg.runningProc("Save"), Resources.msg.sending());
		
		frm.insert(null, data, new AsyncCallback<Results>() {
					public void onFailure(Throwable caught) {
						response.setCode(Codes.ERROR_NETWORK);
						response.setMessage(Resources.msg.errorNetWork());
						response.setDetail(caught.getMessage());
						response.setMethodName("send");
						response.onFailure();
						UserMessage.error(Resources.msg.formTitle(), Resources.msg.errorNetWork(), caught.getMessage(), response);
						UserMessage.waitHide();
					}
					public void onSuccess(Results result) {
						if (result.getCode() == Codes.OK){
							response.onSuccess();
						}
						else{
							response.setCode(result.getCode());
							response.setMessage(result.getMessage());
							response.setMethodName(result.getMethodName());
							response.setDetail(result.getDetail());
							response.onFailure();
						}
						UserMessage.waitHide();
					}
				});
	}
	
	private void generateCalculatedValues(ArrayList<SectionControl> sec){
		int index = 0, size = formDesc.getNumSections();
		
		for (index = 0; index < size; index++){
			sec.get(index).generateCalculatedValues();
		}
	}
	
	public void setEnabledSection(String sectionName, boolean status){
		SectionControl sec = getSectionControl(sectionName);
		sec.setEnabled(status);
	}
	public boolean isEnabledSection(String sectionName){
		SectionControl sec = getSectionControl(sectionName);
		return (sec.isEnabled());
	}
	
	/**
	 * Recupera los datos para mandarlos al servidor.
	 * @return Form Datos.
	 */
	private Form getForm(){
		Form ret = null;
		Section sec = null;
		int index = 0, size = formDesc.getNumSections();
		
		ret = form.getCopy(false);
		for (index = 0; index < size; index++){
			sec = sections.get(index).getSections();
			if (sec != null)
				ret.addSection(sec);
		}
		return (ret);
	}
}
