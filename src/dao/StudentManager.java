package dao;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class StudentManager extends AbstractTableModel {
	private BaseDao base;
	private ResultSet rs = null;
	private Vector rows,colu; //创建表格的相关数据
	
	//获取表格中列的数据
	@Override
	public int getColumnCount() {
		return this.colu.size();
	}

	//获取表格中行的数量
	@Override
	public int getRowCount() {
		return this.rows.size();
	}

	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return ((Vector) this.rows.get(rowIndex)).get(columnIndex);
	}
	
	public String getColu(int column) {
		return (String) this.colu.get(column);
	}
	
	public void addCourse(String sql, String[] param) {
		colu = new Vector();
		//添加列名
		colu.add("学号");
		colu.add("姓名");
		colu.add("性别");
		colu.add("年龄");
		colu.add("生日");
		colu.add("电话");
		rows = new Vector();
		try {
			base = new BaseDao();
			rs = base.result(sql, param);
			while(rs.next()) {
				Vector row = new Vector();
				row.add(rs.getString(1));
				row.add(rs.getString(2));
				row.add(rs.getString(3));
				row.add(rs.getString(4));
				row.add(rs.getString(5));
				row.add(rs.getString(6));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			base.closeAll(null, null, null);
		}
	}
	
	public boolean updateStudent(String sql, String[] param) {
		base = new BaseDao();					//创建BaseDao类的对象
		return base.executeSQL(sql, param);		//调用executeSQl方法
	}
}
