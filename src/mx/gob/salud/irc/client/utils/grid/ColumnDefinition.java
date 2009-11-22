package mx.gob.salud.irc.client.utils.grid;

import java.util.HashMap;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;

public class ColumnDefinition {
	
	private String codeName = null;
	private String displayName = null;
	private boolean hiden = false;
	private int width = 0;
	private HorizontalAlignmentConstant align = HasHorizontalAlignment.ALIGN_DEFAULT;
	private String styleFormat = null;
	private HashMap<Object,Object> valueMap = null;
	private String type = null;
	private String prompt = null;
	private String format = null;
	
	
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public boolean isHiden() {
		return hiden;
	}
	public void setHiden(boolean hiden) {
		this.hiden = hiden;
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
	public String getStyleFormat() {
		return styleFormat;
	}
	public void setStyleFormat(String styleFormat) {
		this.styleFormat = styleFormat;
	}
	public HashMap<Object, Object> getValueMap() {
		return valueMap;
	}
	public void setValueMap(HashMap<Object, Object> valueMap) {
		this.valueMap = valueMap;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPrompt() {
		return prompt;
	}
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	
	
}
