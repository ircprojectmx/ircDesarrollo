package mx.gob.salud.irc.client.utils;

import mx.gob.salud.irc.client.Resources;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CommonPanel extends VerticalPanel {
	private Label title = null;
	private Label subTitle = null;
	
	protected HorizontalPanel subTitlePanel = null;
	
	public CommonPanel(String tit, String sub){
		title = new Label(tit);
		subTitle = new Label(sub);
		VerticalPanel head = new VerticalPanel();
		subTitlePanel = new HorizontalPanel();
		
		setWidth("100%");
		head.setWidth("100%");
		title.setWidth("100%");
		subTitle.setWidth("100%");
		subTitlePanel.setWidth("100%");
		title.setStyleName(Resources.msg.styleTitlePanel());
		head.add(title);
		subTitlePanel.setStyleName(Resources.msg.styleSubTitlePanel());
		subTitle.setStyleName(Resources.msg.styleSubTitlePanel());
		subTitlePanel.add(subTitle);
		head.add(subTitlePanel);
		
		add(head);
	}
}
