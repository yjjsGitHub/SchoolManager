package dao;

import java.sql.*;


public class BaseDao {
	public final static String DRIVCER = "com.mysql.jdbc.Driver"; //数据库驱动
	public final static String URL = "jdbc:mysql://localhost: 3306; DataBaseName=school"; //url
	public final static String DBNAME = "root"; //数据库用户名
	public final static String DBPASS = "root"; //数据库密码
	
	public Connection getConn() throws ClassNotFoundException, SQLException {
		//得到数据库连接
		Class.forName(DRIVCER);	//注册驱动
		Connection conn = DriverManager.getConnection(URL, DBNAME, DBPASS); //获得数据库连接
		return conn;	//返回连接
	}
	
	//根据SQL语句与参数集合获取结果集
	public ResultSet result(String sql, String[] param) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConn();
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < param.length; i++) {
				ps.setString(i+1, param[i]);
			}
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}
		return rs;
	}
	
	//关闭数据以及释放资源
	public void closeAll(Connection conn, PreparedStatement ps, ResultSet rs) {
		// 如果rs不为空， 关闭rs
		if(rs != null) {
			try {
				rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		// 如果 pstmt不空，关闭pstmt
		if(ps != null) {
			try {
				ps.close();
			}catch(SQLException e) {
				e.printStackTrace();
					}
			}
		// 如果conn不为空， 关闭rs
		if(conn != null) {
			try {
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
				}
		}
	}
	
	//增删改操作
	public boolean executeSQL(String sql, String[] param) {
		Connection conn = null;
		PreparedStatement ps = null;
		int num = 0;
		boolean ban = true;
		//处理DWL，执行SQL
		try {
			conn = getConn();						//得到数据库连接
			ps = conn.prepareStatement(sql);		//得到PreparedStatement对象
			if(param != null) {
				for(int i = 0; i < param.length; i++) {
					ps.setString(i+1, param[i]);	//为预编译sql设置参数
				}
			}
			num = ps.executeUpdate();				//执行SQL语句
			if(num != 1){
				ban = false;
			}else {
				ban = true;
			}
		}catch(ClassNotFoundException e) {
			e.printStackTrace();					//处理ClassNotFoundException异常
		}catch(SQLException e) {
			e.printStackTrace();					//处理SQLException异常
		}finally {
			closeAll(conn, ps, null);
		}
		return ban;
	}
}
