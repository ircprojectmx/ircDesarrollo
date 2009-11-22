package mx.gob.salud.irc.server.utils;

import java.sql.*;

/** 
 * Implementa un pool de conexiones; no utilizado dentro del proyecto debido
 * a que se emplean Data Sources. 
 * 
 * @author Dario Vasconcelos (dario.vasconcelos@gmail.com)
 * @version 
 */
public class DBConnectionPool extends AbstractPool {
	private static DBConnectionPool instance = new DBConnectionPool(
			Integer.parseInt(Resources.properties.get("dbpool.min")),
			Integer.parseInt(Resources.properties.get("dbpool.max")));

	private DBConnectionPool(int min, int max) {
		super(min, max);
		System.out.println("Constructor de DBConnPool");
	}

	protected boolean beforeFreeObject(Object object) {
		return true;
	}

	protected Object createObject() {
		String cadena = null;
		String server = null;
		String port = null;
		String db     = null;
		String user   = null;
		String pwd    = null;
		
		Connection conn = null;
		try {
			Class.forName(Resources.properties.get("db.jdbc"));
			cadena = Resources.properties.get("db.cadena");
			server = Resources.properties.get("db.server");
			port = Resources.properties.get("db.port");
			db     = Resources.properties.get("db.db");
			user   = Resources.properties.get("db.user");
			pwd    = Resources.properties.get("db.pwd");
			
			if (server != null)
				cadena = replaceFirst(cadena, "#server#", server);
			if (port != null)
				cadena = replaceFirst(cadena, "#port#", port);
			if (db != null)
				cadena = replaceFirst(cadena, "#db#", db);

			System.out.println("Conectando con " + cadena);
			if (user != null && pwd != null)
				conn = DriverManager.getConnection (cadena, user, pwd);
			else 
				conn = DriverManager.getConnection (cadena);

			return conn;
		} catch (Exception e) {
			System.out.println("Init: Error inicializando conexión a base de datos, " + e.getMessage());
			System.out.println("jdbc: " + Resources.properties.get("db.jdbc") + "\ncadena: "+cadena+"\nserver:"+server+"\nport:"+port+"\ndb:"+db+"\nuser:"+user+"\npwd:"+pwd);
		}
		return null;
	}

	public static DBConnectionPool getInstance(){
		return instance;
	}

	/**
	 *  El método solo hace un llamado a la clase padre, pero es para hacer
	 *  la interfaz más clara
	 */
	public Connection getConexionDB(){
		return (Connection) super.getObject();
	}

	public Connection getConnection(){
		return (Connection) super.getObject();
	}

	public void freeConnection(Connection con) {
		super.freeObject(con);
	}

	protected boolean testObject(Object object) {
		return validateConnection((Connection)object);
	}

	private boolean validateConnection(Connection con){
		// Si ya reutilicé el objeto suficientes veces, obligo a que se refresque
		// y cierro la conexión.
		/*
		  int num = addOne(con);
		  //System.out.println("Conexión " + con + " , " + num);
		  if ((num >= maxUsos) && (Math.random() > .3333333)) {
			  resetObject( con );
			  try {
				  con.close();
				  con = null;
			  } catch (Exception e) {
				  System.out.println("Error tratando de cerrar conexión con máximo de reusos: " + e.getMessage() + " (conexion:" + con.toString() + ")");
			  }
			  return false;
		  }
		 */

		String sql = "select getdate()";
		if(con == null)
			return false;

		Statement statement = null;
		ResultSet rs        = null;

		try{
			statement = con.createStatement();
			rs = statement.executeQuery(sql);
		} catch(SQLException sqlException){
			/*  Si entramos en el catch por cualquier causa entonces la
					conexion ya está cerrada
			 */
			System.out.println("DBConnectionPool.validateConnection(Object): No se pudo ejecutar el query de validación de conexion: " + sqlException.getMessage());
			try{
				con.close();
			} catch(SQLException sqlException1){
				System.out.println("DBConnectionPool.validateConnection(Connection):con.close():" + sqlException1.getMessage());
			}
			return false;
		} finally{
			try{
				if (rs != null)
					rs.close();
				rs = null;
				if(statement != null)
					statement.close();
				statement = null;
			} catch(SQLException anotherSQLException){
				System.out.println("DBConnectionPool.validateConnection(Connection):statement.close():" + anotherSQLException.getMessage()); } }

		return true; 
	}

	/** Este método es un reemplazo para el String.replaceFirst que sólo existe
	 * a partir del JDK 1.4
	 */
	private String replaceFirst(String cadena, String search, String replace) {
		if (cadena == null || cadena.length() == 0)
			return "";
		int pos = cadena.indexOf(search);
		if (pos < 0)
			return cadena;
		return cadena.substring(0, pos) + 
		replace + 
		cadena.substring(pos+search.length(), cadena.length());
	}
}
