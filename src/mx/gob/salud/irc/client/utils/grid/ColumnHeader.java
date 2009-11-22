package mx.gob.salud.irc.client.utils.grid;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ToggleButton;

public class ColumnHeader extends HorizontalPanel {

	public static int WIDTH = 80;
	public static int HEIGHT = 15;
	private String display = null;
	private int width = ColumnHeader.WIDTH;
	private int height = ColumnHeader.HEIGHT;
	private HorizontalAlignmentConstant align = HasHorizontalAlignment.ALIGN_DEFAULT;
	private String prompt = null;
	private ToggleButton header = null;
	
	public ColumnHeader(String displayLegend){
		header = new ToggleButton(displayLegend);
		display = displayLegend;
		
		add(header);
	}
	
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public HorizontalAlignmentConstant getAlign() {
		return align;
	}
	public void setAlign(HorizontalAlignmentConstant align) {
		this.align = align;
	}
	public String getPrompt() {
		return prompt;
	}
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	
}
