package mx.gob.salud.irc.client.ficha;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;

import mx.gob.salud.irc.client.Resources;

import mx.gob.salud.irc.client.forms.FormControl;
import mx.gob.salud.irc.client.services.CommonCallBack;
import mx.gob.salud.irc.client.utils.CommonPanel;
import mx.gob.salud.irc.client.utils.UserMessage;

public class FichaAlta extends CommonPanel {

	private FormControl form = null;
	
	public FichaAlta(){
		super(Resources.msg.fhaTitle(),Resources.msg.fhaSubTitle());
		form = new FormControl();
		
		PushButton btnSave = new PushButton(new Image(GWT.getModuleBaseURL()+"images/ok_mini.png"),
		        new ClickHandler() {
		          public void onClick(ClickEvent event) {
		        	  form.save(new CommonCallBack(){
		      			@Override
		      			public void onFailure() {
		      				UserMessage.error(Resources.msg.formTitle(), Resources.msg.errorRuningLocalProc("Save"), this.getFullMessage(), null);
		      			}
		      			@Override
		      			public void onSuccess() {
		      				UserMessage.ok(Resources.msg.formTitle(), Resources.msg.okProc(), null);
		      			}
		      		}); 
		          }
		        });
		btnSave.setWidth("22px");
		subTitlePanel.add(btnSave);
		subTitlePanel.setCellHorizontalAlignment(btnSave, HasHorizontalAlignment.ALIGN_RIGHT);
		
		form.load(Resources.msg.appCode(),"FICHA_ALTA", new CommonCallBack(){
			@Override
			public void onFailure() {
				UserMessage.error(Resources.msg.formTitle(), Resources.msg.errorBuilding("Form:FICHA_ALTA"), this.getFullMessage(), null);
			}
			@Override
			public void onSuccess() {
				add(form.getVisualControl().getControl());
				form.setEnabledSection("HISTO_CLIN", false);
				form.setEnabledSection("DATOS_NACIM", false);
				form.setEnabledSection("ENFERMEDADES", false);
			}
		});
	}
}
