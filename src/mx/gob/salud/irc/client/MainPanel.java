package mx.gob.salud.irc.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainPanel extends DockPanel {

	private Widget centerPanel = null;
	
	public MainPanel(){
		setWidth("100%");
		HorizontalPanel head = new HorizontalPanel();
		head.setStyleName(Resources.msg.styleTitleLight());
		Image logo = new Image(GWT.getModuleBaseURL()+"images/FIRMA.JPG");
	    head.add(logo);
	    VerticalPanel menuArea = new VerticalPanel();
	    Label title = new Label(Resources.msg.appNameLongName());
	    title.setStyleName(Resources.msg.styleTitle());
	    title.setHeight("65px");
	    menuArea.add(title);
	    MainMenu menu = new MainMenu();
	    menuArea.add(menu);
	    head.add(menuArea);
	    Image leftImg = new Image(GWT.getModuleBaseURL()+"images/MEXICO.GIF");
	    head.add(leftImg);
	    head.setWidth("100%");
	    add(head, DockPanel.NORTH);
	}
	
	public void open(Widget newWidget){
		if (centerPanel != null){
			remove(centerPanel);
		}
		add(newWidget, DockPanel.CENTER);
		centerPanel = newWidget;
	}
}
