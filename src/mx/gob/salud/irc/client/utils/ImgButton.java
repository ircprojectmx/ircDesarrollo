package mx.gob.salud.irc.client.utils;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;

public class ImgButton extends PushButton {

	public ImgButton(Image upImage, Image overImage){
		super(upImage);
		addMouseOverHandler(new MouseOverHandler(){
			public void onMouseOver(MouseOverEvent event) {
				
			}});
		addMouseOutHandler(new MouseOutHandler(){
			public void onMouseOut(MouseOutEvent event) {
			}});
	}
}
