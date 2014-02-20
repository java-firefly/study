package jdbc;

import io.In;
import io.Out;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class JDBC {
	private String driverPath = "net.sourceforge.jtds.jdbc.Driver";
	private String dbUrl = "jdbc:jtds:sqlserver://172.16.1.3:1433/RDSYSCASV120011";
	private String username = "sa";
	private String password = "eplugger";
	public JDBC() {}
	public JDBC(String driverPath, String dbUrl, 
			String username, String password) {
		this.driverPath = driverPath;
		this.dbUrl = dbUrl;
		this.username = username;
		this.password = password;
	}
	StringBuffer sb = new StringBuffer();
	public void getResultBySql(String sql){
		try {
			Class.forName(driverPath);
			Connection con = DriverManager.getConnection(dbUrl,username,password);
			Statement ps = con.createStatement();
			ResultSet set = ps.executeQuery(sql);
			ResultSetMetaData setMetaData = set.getMetaData();
			setMetaData.getColumnName(1);
			while(set.next()){
				String str1 = set.getString(1);
				String str2 = set.getString(2);
				if(str1 != null && str2 != null){
					if(str1.contains("学校") || str2.contains("学校")){
						sb.append(str1);
						sb.append("\t");
						sb.append(str2);
						sb.append("\t\t");
						sb.append(sql);
						sb.append("\r\n");
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void getAllResultOfSqls(){
		List<String> sqls = getSql();
		for (String sql : sqls) {
			getResultBySql(sql);
		}
		new Out().out("c:/test/text2.txt", sb.toString());
	}
	public List<String> getSql(){
		In in = new In();
		List<String> sqls = in.getFileStrAsLine("c:/sql2.txt");
		sqls = handleSql(sqls);
		return sqls;
	}
	private List<String> handleSql(List<String> sqls){
		int length = sqls.size();
		String str = "";
		for (int i = 0; i < length; i++) {
			str = sqls.get(i);
			if(str.contains("?")){
				str = str.replaceAll("= *[?]", " like '%'");
				sqls.set(i, str);
			}
		}
		return sqls;
	}
	public static void main(String[] args) {
		//String sql = "SELECT ID,DISPLAYNAME FROM CFG_OBJECT_META WHERE VALIDFLAG='1' AND DISPLAYTYPE='LIST' AND SEARCHFLAG IN('1','2');";
		//new JDBC().getAllResultOfSqls();
		//new JDBC().getSql();
		JDBC jdbc = new JDBC();
		jdbc.getResultBySql("select id,name from s_project");
	}
}
