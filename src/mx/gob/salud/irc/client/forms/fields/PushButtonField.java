package mx.gob.salud.irc.client.forms.fields;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;

public class PushButtonField extends PushButton {

	private String internalId = null;
	
	public PushButtonField(Image image) {
		super(image);
	}

	public void setCmd(final Command cmd) {
		this.addClickHandler(new ClickHandler() {
	          public void onClick(ClickEvent event) {
	        	  cmd.execute(); 
	          }
	        });
	}

	public String getInternalId() {
		return internalId;
	}

	public void setInternalId(String internalId) {
		this.internalId = internalId;
	}
	
	
}
