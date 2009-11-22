package mx.gob.salud.irc.server.utils;

import java.util.Enumeration;
import java.io.*;

public class Properties {
	private java.util.Properties props = new java.util.Properties();

	public void inicializa(String archivo) {
		try {
			System.out.println("Inicializando archivo " + archivo);
			props.load(new FileInputStream(archivo));
		} catch (IOException e) {
			System.out.println("Error abriendo archivo " + archivo + 
							", mensaje: " + e.getMessage());
		}
	}

	/** 
	 * Recibe un InputStream en vez de un String 
	 * 
	 * @param archivo 
	 */
	public void inicializa(InputStream archivo) {
		try {
			System.out.println("Inicializando archivo " + archivo);
			props.load(archivo);
		} catch (IOException e) {
			System.out.println("Error abriendo archivo " + archivo + 
							", mensaje: " + e.getMessage());
		}
	}
	public String get(String propiedad) {
		if (props == null)
			props = new java.util.Properties();
		return props.getProperty(propiedad);
	}
	public Enumeration getPropertyNames() {
		if (props == null)
			props = new java.util.Properties();
		return props.propertyNames();
	}

	/**
	 * Creado para poder inicializar valores directamente desde codigo, y no
	 * en el archivo de configuracion (por ejemplo, la ruta hacia el directorio
	 * WEB-INF)
	 * @param nombre
	 * @param valor
	 */
	public void put(String nombre, String valor) {
		if (props == null)
			props = new java.util.Properties();
		props.put(nombre, valor);
	}
}
