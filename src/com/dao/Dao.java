package com.dao;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.lzw.Item;
import com.dao.model.*;

/**
 * 数据库驱动和连接
 */
public class Dao {
	// 定义数据库驱动类的名称
	protected static String dbClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	// 读入配置文件
	protected static File file;
	// 定义访问数据库的URL
	protected static String dbUrl;
	// 定义访问数据库的用户名
	protected static String dbUser;
	// 定义访问数据库的密码
	protected static String dbPwd;
	// 声明数据库连接对象
	public static Connection conn = null;
	
	// 在静态代码段中初始化Dao类，实现数据库的驱动和连接。(PS:此块只初始化一次)
	static {
		try {
			if (conn == null) {
				file = new File("setting.txt");
				if (!file.exists()) {
					JOptionPane.showMessageDialog(null, "配置文件不存在，请在当前目录创建配置文件");
					System.exit(0);
				}
				// 读入配置数据
				Scanner input = new Scanner(file);
				String ip = input.next(), port = input.next();
				dbUser = input.next();
				dbPwd = input.next();
				dbUrl = "jdbc:sqlserver://" + ip + ":" + port + ";DatabaseName=Library";
				input.close();
				
				Class.forName(dbClassName).newInstance();
				conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
			}
		} catch (Exception e) {
			String message = e.getMessage();
			int index = message.lastIndexOf(')');
			message = message.substring(index + 1);
			JOptionPane.showMessageDialog(null, message);
			e.printStackTrace();
		}
	}
	
	//--------------------数据库操作模块---------------------------//
	// 创建数据库查询
	public static ResultSet findForResultSet(String sql) {  
        if (conn == null)  
            return null;
        // long time = System.currentTimeMillis();//返回以毫秒为单位的当前时间  
        ResultSet rs = null;
        try {
        	//该常量指示可滚动但通常不受result底层数据更改影响的result对象的类型，不可更新的resultset对象的类型  
            Statement stmt = null;
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(sql);
        // second = ((System.currentTimeMillis() - time) / 1000d) + "";
        } catch (SQLException e) {
        	String message = e.getMessage();
			int index = message.lastIndexOf(')');
			message = message.substring(index + 1);
			JOptionPane.showMessageDialog(null, message);
            e.printStackTrace();
        }
        return rs;
	}
	
	// 插入数据库
	public static boolean insert(String sql) {
		boolean rs = false;
		try {
			Statement stmt= conn.createStatement();
			if (stmt.executeUpdate(sql) > 0) rs = true;
		} catch (SQLException e) {
			e.printStackTrace();
			String message = e.getMessage();
			int index = message.lastIndexOf(')');
			message = message.substring(index + 1);
			JOptionPane.showMessageDialog(null, message);
			return false;
		}
		return rs;
	}
	
	// 更新数据库
	public static boolean update(String sql) {
		return insert(sql);
	}
	
	// 删除数据库
	public static boolean delete(String sql) {
		return insert(sql);
	}
	
	// 备份/还原数据库
	public static int restoreOrBackup(String sql) throws Exception {
		int rs = 0;
		if (conn != null) {
			conn.close();
		}
		conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
		Statement stmt = conn.createStatement();
		stmt.execute(sql);
		stmt.close();
		conn.close();
		conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
		return rs;
	}
	
	// 验证用户名、密码是否合法
	public static Manager checkLogin(String name, String pwd) throws SQLException {
		//System.out.println(name);
		//System.out.println(pwd);
		Manager ret = new Manager();
		ResultSet rs = findForResultSet("select * from Manager where Mname = '" + name +
				"' and Mpassword = '" + pwd + "'");
		if (rs.next()) {
			ret.setName(rs.getString("Mname").trim());
			ret.setPassword(rs.getString("Mpassword").trim());
		}
		return ret;
	}
	
	
	//---------------------查询信息模块----------------------------//
	// 获取读者信息
	public static List<Reader> getReaderInfo(Item item) {
		List<Reader> list = new ArrayList<Reader>();
		String where = "Rname = '" + item.getName() + "'";
		if (item.getId() != null) {
			where = "Rid = '" + item.getId() + "'";
		}
		ResultSet set = findForResultSet("select * from Reader where " + where);
		try {
			while (set.next()) {
				Reader info = new Reader();
				info.setAddress(set.getString("Raddress").trim());
				info.setId(set.getString("Rid").trim());
				info.setName(set.getString("Rname").trim());
				info.setOwn(set.getDouble("Rown"));
				info.setPhone(set.getString("Rphone").trim());
				info.setSex(set.getString("Rsex").trim());
				list.add(info);
			}
		} catch (SQLException e) {
			String message = e.getMessage();
			int index = message.lastIndexOf(')');
			message = message.substring(index + 1);
			JOptionPane.showMessageDialog(null, message);
			e.printStackTrace();
		}
		return list;
	}
	
	// 获取所有读者信息
	public static List<Reader> getReaderInfo() {
		List<Reader> list = new ArrayList<Reader>();
		ResultSet set = findForResultSet("select * from Reader");
		try {
			while (set.next()) {
				Reader info = new Reader();
				info.setAddress(set.getString("Raddress").trim());
				info.setId(set.getString("Rid").trim());
				info.setName(set.getString("Rname").trim());
				info.setOwn(set.getDouble("Rown"));
				info.setPhone(set.getString("Rphone").trim());
				info.setSex(set.getString("Rsex").trim());
				list.add(info);
			}
		} catch (SQLException e) {
			String message = e.getMessage();
			int index = message.lastIndexOf(')');
			message = message.substring(index + 1);
			JOptionPane.showMessageDialog(null, message);
			e.printStackTrace();
		}
		return list;
	}
	
	// 获取书籍信息
	public static List<Book> getBookInfo(Item item) {
		List<Book> list = new ArrayList<Book>();
		String where = "Btitle = '" + item.getName() + "'";
		if (item.getId() != null) {
			where = "Bid = '" + item.getId() + "'";
		}
		ResultSet set = findForResultSet("select * from Book where " + where);
		try {
			while (set.next()) {
				Book info = new Book();
				info.setAuthor(set.getString("Bauthor").trim());
				info.setId(set.getString("Bid").trim());
				info.setIn_date(set.getDate("Bin_date"));
				info.setNow_amount(set.getInt("Bnow_amount"));
				info.setPress(set.getString("Bpress").trim());
				info.setPrice(set.getDouble("Bprice"));
				info.setSid(set.getString("BSid"));
				info.setTitle(set.getString("Btitle"));
				info.setTotal(set.getInt("Btotal"));
				list.add(info);
			}
		} catch (SQLException e) {
			String message = e.getMessage();
			int index = message.lastIndexOf(')');
			message = message.substring(index + 1);
			JOptionPane.showMessageDialog(null, message);
			e.printStackTrace();
		}
		return list;
	}
	
	// 获取所有书籍信息
	public static List<Book> getAllBookInfo() {
		List<Book> list = new ArrayList<Book>();
		ResultSet set = findForResultSet("select * from Book");
		try {
			while (set.next()) {
				Book info = new Book();
				info.setAuthor(set.getString("Bauthor").trim());
				info.setId(set.getString("Bid").trim());
				info.setIn_date(set.getDate("Bin_date"));
				//System.out.println(set.getDate("Bin_date"));
				info.setNow_amount(set.getInt("Bnow_amount"));
				info.setPress(set.getString("Bpress").trim());
				info.setPrice(set.getDouble("Bprice"));
				info.setSid(set.getString("BSid"));
				info.setTitle(set.getString("Btitle").trim());
				info.setTotal(set.getInt("Btotal"));
				list.add(info);
				//System.out.println(set.getString("Bauthor"));
			}
		} catch (SQLException e) {
			String message = e.getMessage();
			int index = message.lastIndexOf(')');
			message = message.substring(index + 1);
			JOptionPane.showMessageDialog(null, message);
			e.printStackTrace();
		}
		return list;
	}
	
	// 查询借阅信息
	public static List<Borrow> getBorrowInfo(Item item) {
		List<Borrow> list = new ArrayList<Borrow>();
		String where = "Rid = '" + item.getName() + "'";
		if (item.getId() != null) {
			where = "BOid = '" + item.getId() + "'";
		}
		ResultSet set = findForResultSet("select * from Borrow where " + where);
		try {
			while (set.next()) {
				Borrow info = new Borrow();
				info.setBid(set.getString("Bid").trim());
				info.setBorrowDate(set.getDate("BorrowDate"));
				info.setId(set.getString("BOid"));
				info.setName(set.getString("Mname").trim());
				info.setReturnDate(set.getDate("ReturnDate"));
				info.setRid(set.getString("Rid"));
				list.add(info);
			}
		} catch (SQLException e) {
			String message = e.getMessage();
			int index = message.lastIndexOf(')');
			message = message.substring(index + 1);
			JOptionPane.showMessageDialog(null, message);
			e.printStackTrace();
		}
		return list;
	}
	
	// 查询所有借阅信息
	public static List<Borrow> getAllBorrowInfo() {
		List<Borrow> list = new ArrayList<Borrow>();
		ResultSet set = findForResultSet("select * from Borrow");
		try {
			while (set.next()) {
				Borrow info = new Borrow();
				info.setBid(set.getString("Bid").trim());
				info.setBorrowDate(set.getDate("BorrowDate"));
				info.setId(set.getString("BOid"));
				info.setName(set.getString("Mname").trim());
				info.setReturnDate(set.getDate("ReturnDate"));
				info.setRid(set.getString("Rid"));
				list.add(info);
			}
		} catch (SQLException e) {
			String message = e.getMessage();
			int index = message.lastIndexOf(')');
			message = message.substring(index + 1);
			JOptionPane.showMessageDialog(null, message);
			e.printStackTrace();
		}
		return list;
	}
	
	// 获取书架信息
	public static List<BookShelf> getBsInfo(Item item) {
		List<BookShelf> list = new ArrayList<BookShelf>();
		String where = "BSposition = '" + item.getName() + "'";
		if (item.getId() != null) {
			where = "BSid = '" + item.getId() + "'";
		}
		ResultSet set = findForResultSet("select * from BookShelf where " + where);
		try {
			while (set.next()) {
				BookShelf info = new BookShelf();
				info.setId(set.getString("BSid").trim());
				info.setPosition(set.getString("BSposition"));
				list.add(info);
			}
		} catch (SQLException e) {
			String message = e.getMessage();
			int index = message.lastIndexOf(')');
			message = message.substring(index + 1);
			JOptionPane.showMessageDialog(null, message);
			e.printStackTrace();
		}
		return list;
	}
	
	// 获取所有书架信息
	public static List<BookShelf> getBsInfo() {
		List<BookShelf> list = new ArrayList<BookShelf>();
		ResultSet set = findForResultSet("select * from BookShelf");
		try {
			while (set.next()) {
				BookShelf info = new BookShelf();
				info.setId(set.getString("BSid").trim());
				info.setPosition(set.getString("BSposition"));
				list.add(info);
			}
		} catch (SQLException e) {
			String message = e.getMessage();
			int index = message.lastIndexOf(')');
			message = message.substring(index + 1);
			JOptionPane.showMessageDialog(null, message);
			e.printStackTrace();
		}
		return list;
	}
	
	// 查询管理员信息
	public static Manager getManager(String name) throws SQLException {
		//System.out.println(name);
		//System.out.println(pwd);
		Manager ret = new Manager();
		ResultSet rs = findForResultSet("select * from Manager where Mname = '" + name + "'");
		if (rs.next()) {
			ret.setName(rs.getString("Mname").trim());
			ret.setPassword(rs.getString("Mpassword").trim());
		}
		return ret;
	}
	
	
	//---------------------修改信息模块----------------------------//
	// 修改管理员密码
	public static boolean changePwd(String name, String pwd, String newPwd) {
		String sql = "update Manager set Mpassword = '" + newPwd + "' where Mname = '"
					+ name + "' and Mpassword = '" + pwd + "'";
		if (!update(sql)) 
			return false;
		return true;
	}
	
	// 修改图书价格
	public static boolean changePrice(String id, double price) {
		String sql = "update Book set Bprice = " + price + " where Bid = '"
				+ id + "'";
		if (!update(sql)) 
			return false;
		return true;
	}
	
	// 修改欠款
	public static boolean changeOwn(String id, double own) {
		String sql = "update Reader set Rown = " + own + " where Rid = '"
				+ id + "'";
		if (!update(sql)) 
			return false;
		return true;
	}
	
	// 修改借阅表
	public static boolean changeReturn(String id, Date date) {
		try {
			boolean autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			String sql = "update Borrow set ReturnDate = '" + date + "' where BOid = '"
					+ id + "'";
			if (!update(sql)) 
				return false;
			ResultSet rs = findForResultSet("select * from Borrow where BOid = '" + id + "'");
			if (rs.next()) {
				if (!update("update Book set Bnow_amount = Bnow_amount + 1 where Bid = '" 
							+ rs.getString("Bid").trim() + "'"))
					return false;
			}
			conn.commit();
			conn.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			String message = e.getMessage();
			int index = message.lastIndexOf(')');
			message = message.substring(index + 1);
			JOptionPane.showMessageDialog(null, message);
			e.printStackTrace();
		}
		return true;
	}
	
	
	//---------------------添加信息模块----------------------------//
	// 添加读者信息
	public static boolean insertReaderInfo (Reader rd) {
		try {
			boolean autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			String sql = "insert into Reader values('" + rd.getAddress()
						+ "','" + rd.getId() + "','" + rd.getName()
						+ "'," + rd.getOwn() + ",'" + rd.getPhone()
						+ "','" + rd.getSex() + "')";
			if (!insert(sql)) {
				JOptionPane.showMessageDialog(null, "插入读者信息失败，检查是否添加了重复编号");
				return false;
			}
			conn.commit();
			conn.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			String message = e.getMessage();
			int index = message.lastIndexOf(')');
			message = message.substring(index + 1);
			JOptionPane.showMessageDialog(null, message);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// 添加书籍信息
	public static boolean insertBookInfo (Book book, int num) {
		try {
			boolean autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ResultSet rs = findForResultSet("select * from Book where Bid = '" + book.getId() + "'");
			if (rs.next()) {
				String sql = "update Book set Btotal = Btotal + " + num + " , Bnow_amount = Bnow_amount + " + num + " "
						+ "where Bid = '" + rs.getString("Bid").trim() + "'";
				if (!update(sql)) {
					JOptionPane.showMessageDialog(null, "更新书籍信息失败");
					return false;
				}
			}
			else {
				if (!insert("insert into Book values('" + book.getAuthor()
							+ "','" + book.getId() + "','" + book.getSid()
							+ "','" + book.getIn_date() + "'," + book.getNow_amount()
							+ ",'" + book.getPress() + "'," + book.getPrice()
							+ ",'" + book.getTitle() + "'," + book.getTotal() + ")")) {
					JOptionPane.showMessageDialog(null, "插入书籍信息失败");
					return false;
				}
			}
			conn.commit();
			conn.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			String message = e.getMessage();
			int index = message.lastIndexOf(')');
			message = message.substring(index + 1);
			JOptionPane.showMessageDialog(null, message);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// 添加借阅记录
	public static boolean insertBorrowInfo (Borrow borrow) {
		try {
			boolean autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ResultSet rs = findForResultSet("select Bid, Bnow_amount from Book where Bid = '" + borrow.getBid() + "'");
			if (rs.next()) {
				if (rs.getInt("Bnow_amount") == 0) {
					JOptionPane.showMessageDialog(null, "该书已无库存");
					return false;
				}
				String sql = "update Book set Bnow_amount = Bnow_amount - 1 where Bid = '" + 
							rs.getString("Bid").trim() + "'";
				if (!update(sql)) {
					return false;
				}
			}
			else return false;
			
			if (!insert("insert into Borrow values('" + borrow.getBorrowDate()
					+ "', null,'" + borrow.getId()
					+ "','" + borrow.getBid() + "','" + borrow.getRid()
					+ "','" + borrow.getName() + "')"))
				return false;
			conn.commit();
			conn.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			String message = e.getMessage();
			int index = message.lastIndexOf(')');
			message = message.substring(index + 1);
			JOptionPane.showMessageDialog(null, message);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// 添加管理员
	public static boolean insertManagetInfo (Manager ma) {
		try {
			boolean autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ResultSet rs = findForResultSet("select * from Manager where Mname = '" + ma.getName() + "'");
			if (rs.next()) {
				return false;
			}
			else {
				if (!insert("insert into Manager values('" 
					+ ma.getName() + "','" + ma.getPassword() + "')"))
					return false;
			}
			conn.commit();
			conn.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			String message = e.getMessage();
			int index = message.lastIndexOf(')');
			message = message.substring(index + 1);
			JOptionPane.showMessageDialog(null, message);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// 添加书架
	public static boolean insertBSInfo (BookShelf bs) {
		try {
			boolean autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			ResultSet rs = findForResultSet("select * from BookShelf where BSid = '" + bs.getId() + "'");
			if (rs.next()) {
				return false;
			}
			else {
				if (!insert("insert into BookShelf values('" 
					+ bs.getId() + "','" + bs.getPosition() + "')"))
					return false;
			}
			conn.commit();
			conn.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			String message = e.getMessage();
			int index = message.lastIndexOf(')');
			message = message.substring(index + 1);
			JOptionPane.showMessageDialog(null, message);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	//---------------------删除信息模块----------------------------//
	// 删除管理员信息
	public static boolean deleteManager (Item item) {
		String sql = "delete from Manager where Mname = '" + item.getId() + 
						"' and Mpassword = '" + item.getName() + "'";
		if (!delete(sql)) {
			return false;
		}
		return true;
	}
	
	
}
