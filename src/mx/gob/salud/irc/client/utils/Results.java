package mx.gob.salud.irc.client.utils;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;

import mx.gob.salud.irc.client.Resources;
import mx.gob.salud.irc.client.statics.Codes;


public class Results implements Serializable{

	private static final long serialVersionUID = -5051094022179252314L;
	private int code = Codes.OK;
	private String message = "";
	private String methodName = "";
	private String detail = "";
	private ArrayList<HashMap<String,String>> results = null;
	
	public String getFullMessage(){
		StringBuffer msg = new StringBuffer();
		if (methodName != null && methodName.length() > 0)
			msg.append(methodName+", ");
		msg.append(message);
		if (detail != null && detail.length() > 0)
			msg.append(", "+detail);
			
		return (msg.toString());
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public ArrayList<HashMap<String, String>> getResults() {
		return results;
	}
	public void setResults(ArrayList<HashMap<String, String>> results) {
		this.results = results;
	}
	
	
}
