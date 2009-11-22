package mx.gob.salud.irc.client.forms.fields;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ToggleButton;

public class ToggleButtonField extends ToggleButton {

	public void setCmd(final Command cmd) {
		this.addClickHandler(new ClickHandler() {
	          public void onClick(ClickEvent event) {
	        	  cmd.execute(); 
	          }
	        });
	}
}
