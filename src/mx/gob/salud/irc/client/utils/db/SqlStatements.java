package mx.gob.salud.irc.client.utils.db;

public class SqlStatements {

	public static final int OPERATION_SELECT = 100;
	public static final int OPERATION_SELECT_ALL = 110;
	public static final int OPERATION_SELECT_CATALOG = 120;
	public static final int OPERATION_INSERT = 200;
	public static final int OPERATION_DELETE = 300;
	public static final int OPERATION_UPDATE = 400;
	
	public static final String EMPTY_VALUE = "none";
	
	public static String generate(TableDefinition tdef){
		StringBuffer sql = new StringBuffer();
		
		switch(tdef.getOperation()){
		case SqlStatements.OPERATION_SELECT:
			sql.append("SELECT ");
			if (tdef.getColumns().trim().length() <= 0)
				sql.append(" * ");
			else
				sql.append(tdef.getColumns());
			sql.append(" FROM "+tdef.getName());
			if (tdef.getContitions().trim().length() > 0)
				sql.append(" WHERE "+tdef.getContitions());
			break;
		case SqlStatements.OPERATION_SELECT_ALL:
			sql.append("SELECT ");
			if (tdef.getColumns().trim().length() <= 0)
				sql.append(" * ");
			else
				sql.append(tdef.getColumns());
			sql.append(" FROM "+tdef.getName());
			break;
		case SqlStatements.OPERATION_SELECT_CATALOG:
			sql.append("SELECT ");
			sql.append(tdef.getPkColumns()+ ", ");
			if (!tdef.getFkColumns(TableDefinition.DEFAULT_FK_NAME).isEmpty())
				sql.append(tdef.getFkColumns(TableDefinition.DEFAULT_FK_NAME)+ ", ");
			sql.append(tdef.getColumnDescription());
			sql.append(" FROM "+tdef.getName());
			break;
		case SqlStatements.OPERATION_INSERT:
			break;
		case SqlStatements.OPERATION_DELETE:
			break;
		case SqlStatements.OPERATION_UPDATE:
			break;
		}
		return (sql.toString());
	}
}
