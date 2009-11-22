package mx.gob.salud.irc.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SplashPanel extends VerticalPanel {

	public SplashPanel(){
		setStyleName(Resources.msg.styleTitle());
		HorizontalPanel body = new HorizontalPanel();
		body.setStyleName(Resources.msg.styleTitle());
		Image logo = new Image(GWT.getModuleBaseURL()+"images/FIRMA.JPG");
	    body.add(logo);
	    body.add(new Label(Resources.msg.appNameLongName()));
	    add(body);
	}
}
