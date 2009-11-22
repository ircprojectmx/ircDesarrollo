package mx.gob.salud.irc.server.utils;

import javax.servlet.ServletException;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/** 
 * Es un servlet, abre el archivo config.properties y lee las propiedades 
 * 
 * @author Dario Vasconcelos (dario.vasconcelos@gmail.com)
 * @version 
 */
public final class InitProperties extends HttpServlet {

	private static final long serialVersionUID = 1111L;
	
	/**
	 * Coloca en una variable el nombre del campo "Descripción"
	 */
	public static final String DESCRIPCION = "descripcion";
	
	/**
	 * Coloca en una varialbe el nombre del campo "Valor"
	 */
	public static final String VALOR = "valor";
	
	/**
	 * Almacena los datos de los catalogos
	 */
	public static HashMap<String, ArrayList<HashMap<String, String>>> catalogosSencillos = new HashMap<String, ArrayList<HashMap<String,String>>>();
	
	/**
	 * Almacena el nombre de las columnas de las tablas
	 */
	public static HashMap<String, HashMap<String, String>> dicCatalogosSencillos = new HashMap<String, HashMap<String,String>>();
	
	/**
	 * Es el nombre de los catalogos
	 */
	public static String[] nombreCatalogos;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		 throws IOException, ServletException {
	 }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 }

    public void init(ServletConfig config) throws ServletException {
		 String archivo = config.getInitParameter("config.properties");
		 System.out.println(":initParam: " + archivo);
		 if (archivo == null){
			 archivo = "config.properties";
			 System.out.println("From Code:initParam: " + archivo);
		 }

		 // Esto del getResourceAsStream lo hago porque puede que la aplicación
		 // se esté ejecutando desde un .war
		 System.out.println("Inicializando propiedades ...");
		 Resources.properties.inicializa(config.getServletContext().
				 getResourceAsStream("/WEB-INF/" + archivo));

		 // Pongo disponible la ruta WEB-INF para futuros beans que necesiten este
		 // dato y no tengan acceso al ServletContext.
		 Resources.properties.put("web-inf", config.getServletContext().getRealPath("WEB-INF"));
		 //Propiedades.inicializa( ruta + archivo );
	 }
}
