package mx.gob.salud.irc.client.forms.fields;

import java.io.Serializable;

public class Field implements Serializable{

	private static final long serialVersionUID = -2347487479235889436L;
	private String code = "";
	protected String value = "";
	
	public Field clone(){
		Field f = new Field();
		f.setCode(code);
		return (f);
	}
	
	public Field getCopy(){
		Field f = new Field();
		f.setCode(code);
		f.setValue(value);
		return (f);
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString(){
		return (code+" = "+value);
	}
}
