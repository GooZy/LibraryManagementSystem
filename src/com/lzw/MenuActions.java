package com.lzw;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JInternalFrame;

import com.lzw.login.MainDialog;
import com.lzw.login.in.*;

public class MenuActions {
	private static Map<String,JInternalFrame> frames;		//子窗体集合
	
	//管理员表操作
	public static PasswordModiAction MODIFY_PASSWORD;		//修改密码窗体动作
	public static UserAddAction USER_ADD;					//添加管理员窗体动作
	
	//图书表操作
	public static BookSearchAction BOOK_SEARCH;				//图书搜索窗体动作
	public static BookAddAction BOOK_ADD;					//图书信息添加窗体动作
	public static BookAdd2Action BOOK_ADD2;                 //已有图书信息添加
	public static BookPriceAction BOOK_PRICE;               //图书价格修改
	
	//借阅表操作
	public static BorrowAction BORROW;						//图书借阅窗体动作
	public static ReturnAction RETURN;                      //图书归还窗体动作
	public static BorrowSearchAction BORROW_SEARCH;         //借阅信息查询
	
	//读者表操作
	public static ReaderAddAction READER_ADD;				//读者信息添加窗体动作
	public static ReaderSearchAction READER_SEARCH;         //读者信息查询
	public static ReaderOwnAction READER_OWN;               //读者欠款修改
	
	//书架表操作
	public static BookShelfAddAction BS_ADD;                 //添加书架信息
	public static BookShelfSearchAction BS_SEARCH;           //查询书架信息
	
	//数据库操作
	public static DatabaseBackupAction DATABASE_BACKUP;           //数据库备份与恢复
	
	//定义退出事件监听
	public static ExitAction EXIT;							//系统退出动作
	
	//初始化上述功能
	static {
		//初始化frames
		frames = new HashMap<String, JInternalFrame>();
		
		//管理员表操作
		MODIFY_PASSWORD = new PasswordModiAction();		//修改密码窗体动作
		USER_ADD = new UserAddAction();					//添加管理员窗体动作
		
		//图书表操作
		BOOK_SEARCH = new BookSearchAction();				//图书搜索窗体动作
		BOOK_ADD = new BookAddAction();					//图书信息添加窗体动作
		BOOK_ADD2 = new BookAdd2Action();                //添加已有图书
		BOOK_PRICE = new BookPriceAction();              //图书价格修改窗体动作
		
		//借阅表操作
		BORROW = new BorrowAction();						//图书借阅窗体动作
		RETURN = new ReturnAction();                       //图书归还
		BORROW_SEARCH = new BorrowSearchAction();           //借阅信息查询
		
		//读者表操作
		READER_ADD = new ReaderAddAction();				//读者信息添加窗体动作
		READER_SEARCH = new ReaderSearchAction();         //读者信息查询
		READER_OWN = new ReaderOwnAction();                //读者欠款修改
		
		//书架表操作
		BS_ADD = new BookShelfAddAction();                 //添加书架信息
		BS_SEARCH = new BookShelfSearchAction();           //查询书架信息
		
		//数据库操作
		DATABASE_BACKUP = new DatabaseBackupAction();           //数据库备份与恢复
		
		//定义退出事件监听
		EXIT = new ExitAction();							//系统退出动作
		
	}
	
	//---------------------管理员相关动作---------------------------------//
	//修改管理员密码
	private static class PasswordModiAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		//构造方法
		PasswordModiAction() {
			putValue(Action.NAME, "更改密码");
			putValue(Action.LONG_DESCRIPTION, "修改当前用户密码");
			putValue(Action.SHORT_DESCRIPTION, "更改密码");
		}
		
		//创建监听
		public void actionPerformed(ActionEvent e) {
			if(!frames.containsKey("更改密码")||frames.get("更改密码").isClosed()){
				ChangePw iframe = new ChangePw();
				frames.put("更改密码", iframe);
				MainDialog.addIFrame(frames.get("更改密码"));
			}
		}
	}
	
	//添加管理员
	private static class UserAddAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		//构造方法
		UserAddAction(){
			super("添加管理员",null);
			putValue(Action.LONG_DESCRIPTION,"管理员信息添加");
			putValue(Action.SHORT_DESCRIPTION, "添加管理员");
		}

		//创建一个监听
		public void actionPerformed(ActionEvent e) {
			if(!frames.containsKey("管理员信息添加")||frames.get("管理员信息添加").isClosed()){
				UserAddIFrame iframe = new UserAddIFrame();
				frames.put("管理员信息添加", iframe);
				MainDialog.addIFrame(frames.get("管理员信息添加"));
			}
		}
	}
	
	
	//---------------------图书相关动作---------------------------------//
	//查询图书
	private static class BookSearchAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		//构造函数
		BookSearchAction(){
			super("图书搜索", null);
			putValue(Action.LONG_DESCRIPTION, "搜索入库的图书信息");
			putValue(Action.SHORT_DESCRIPTION, "图书搜索");
		}
		
		//创建监听
		public void actionPerformed(ActionEvent e) {
			if (!frames.containsKey("图书查询")||frames.get("图书查询").isClosed()) {
				BookSearchIFrame iframe=new BookSearchIFrame();
				frames.put("图书查询", iframe);
				MainDialog.addIFrame(frames.get("图书查询"));
			}
		}
	}
	
	//添加图书
	private static class BookAddAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			if (!frames.containsKey("图书添加管理")||frames.get("图书添加管理").isClosed()) {
				BookAddIFrame iframe = new BookAddIFrame();
				frames.put("图书添加管理", iframe);
				MainDialog.addIFrame(frames.get("图书添加管理"));
			}
		}
		BookAddAction() {
			super("图书添加", null);
			putValue(Action.LONG_DESCRIPTION, "添加新增的图书");
			putValue(Action.SHORT_DESCRIPTION, "图书添加");
		}
	}
	
	// 已有图书信息添加
	private static class BookAdd2Action extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			if (!frames.containsKey("添加已有图书")||frames.get("添加已有图书").isClosed()) {
				BookAdd2IFrame iframe = new BookAdd2IFrame();
				frames.put("添加已有图书", iframe);
				MainDialog.addIFrame(frames.get("添加已有图书"));
			}
		}
		BookAdd2Action() {
			super("添加已有图书", null);
			putValue(Action.LONG_DESCRIPTION, "添加已有图书");
			putValue(Action.SHORT_DESCRIPTION, "添加已有图书");
		}
	}
	
	//修改图书价格
	private static class BookPriceAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;
		BookPriceAction(){
			super("图书价格修改", null);
			putValue(Action.LONG_DESCRIPTION, "图书价格修改");
			putValue(Action.SHORT_DESCRIPTION, "图书价格修改");
		}

		public void actionPerformed(ActionEvent e) {
			if (!frames.containsKey("价格修改")||frames.get("价格修改").isClosed()) {
				BookPriceIFrame iframe = new BookPriceIFrame();
				frames.put("价格修改", iframe);
				MainDialog.addIFrame(frames.get("价格修改"));
			}
		}
	}
	
	//---------------------借阅相关动作---------------------------------//
	//添加借阅信息
	public static class BorrowAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			if (!frames.containsKey("借阅信息添加")||frames.get("借阅信息添加").isClosed()) {
				BorrowIFrame iframe = new BorrowIFrame();
				frames.put("借阅信息添加", iframe);
				MainDialog.addIFrame(frames.get("借阅信息添加"));
			}
		}
		BorrowAction() {
			super("借阅信息添加", null);
			putValue(Action.LONG_DESCRIPTION, "添加新的借阅记录");
			putValue(Action.SHORT_DESCRIPTION, "借阅信息添加");
		}
	}
	
	//添加归还信息
	public static class ReturnAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			if (!frames.containsKey("归还信息添加")||frames.get("归还信息添加").isClosed()) {
				ReturnIFrame iframe = new ReturnIFrame();
				frames.put("归还信息添加", iframe);
				MainDialog.addIFrame(frames.get("归还信息添加"));
			}
		}
		ReturnAction() {
			super("归还信息添加", null);
			putValue(Action.LONG_DESCRIPTION, "添加新的归还记录");
			putValue(Action.SHORT_DESCRIPTION, "归还信息添加");
		}
	}
	
	//查询借阅信息
	public static class BorrowSearchAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		BorrowSearchAction(){
			super("借阅信息搜索", null);
			putValue(Action.LONG_DESCRIPTION, "搜索读者借阅信息");
			putValue(Action.SHORT_DESCRIPTION, "借阅信息搜索");
		}
		
		//创建监听
		public void actionPerformed(ActionEvent e) {
			if (!frames.containsKey("借阅查询")||frames.get("借阅查询").isClosed()) {
				BorrowSearchIFrame iframe=new BorrowSearchIFrame();
				frames.put("借阅查询", iframe);
				MainDialog.addIFrame(frames.get("借阅查询"));
			}
		}
	}
	
	
	//---------------------读者相关动作---------------------------------//
	//添加读者信息
	public static class ReaderAddAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			if (!frames.containsKey("读者信息添加")||frames.get("读者信息添加").isClosed()) {
				ReaderAddIFrame iframe = new ReaderAddIFrame();
				frames.put("读者信息添加", iframe);
				MainDialog.addIFrame(frames.get("读者信息添加"));
			}
		}
		ReaderAddAction() {
			super("读者信息添加", null);
			putValue(Action.LONG_DESCRIPTION, "添加新的读者信息");
			putValue(Action.SHORT_DESCRIPTION, "读者信息添加");
		}
	}
	
	//查询读者信息
	public static class ReaderSearchAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ReaderSearchAction(){
			super("读者信息查询", null);
			putValue(Action.LONG_DESCRIPTION, "查询读者的信息");
			putValue(Action.SHORT_DESCRIPTION, "读者信息查询");
		}
		
		//创建监听
		public void actionPerformed(ActionEvent e) {
			if (!frames.containsKey("读者查询")||frames.get("读者查询").isClosed()) {
				ReaderSearchIFrame iframe=new ReaderSearchIFrame();
				frames.put("读者查询", iframe);
				MainDialog.addIFrame(frames.get("读者查询"));
			}
		}
	}
	
	//读者欠款修改
	public static class ReaderOwnAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		ReaderOwnAction(){
			super("读者欠款修改", null);
			putValue(Action.LONG_DESCRIPTION, "读者欠款修改");
			putValue(Action.SHORT_DESCRIPTION, "读者欠款修改");
		}

		public void actionPerformed(ActionEvent e) {
			if (!frames.containsKey("欠款修改")||frames.get("欠款修改").isClosed()) {
				ReaderOwnIFrame iframe = new ReaderOwnIFrame();
				frames.put("欠款修改", iframe);
				MainDialog.addIFrame(frames.get("欠款修改"));
			}
		}
	}
	
	//---------------------书架相关动作---------------------------------//
	//添加书架操作
	public static class BookShelfAddAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		BookShelfAddAction(){
			super("添加书架", null);
			putValue(Action.LONG_DESCRIPTION, "添加书架");
			putValue(Action.SHORT_DESCRIPTION, "添加书架");
		}
		
		//创建监听
		public void actionPerformed(ActionEvent e) {
			if (!frames.containsKey("添加书架")||frames.get("添加书架").isClosed()) {
				BSAddIFrame iframe=new BSAddIFrame();
				frames.put("添加书架", iframe);
				MainDialog.addIFrame(frames.get("添加书架"));
			}
		}
	}
	
	//查询书架操作
	public static class BookShelfSearchAction extends AbstractAction{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		BookShelfSearchAction(){
			super("书架信息查询", null);
			putValue(Action.LONG_DESCRIPTION, "查询书架的信息");
			putValue(Action.SHORT_DESCRIPTION, "书架信息查询");
		}
		
		//创建监听
		public void actionPerformed(ActionEvent e) {
			if (!frames.containsKey("书架查询")||frames.get("书架查询").isClosed()) {
				BSSearchIFrame iframe=new BSSearchIFrame();
				frames.put("书架查询", iframe);
				MainDialog.addIFrame(frames.get("书架查询"));
			}
		}
	}
	
	
	//---------------------系统相关动作---------------------------------//
	
	//数据库备份与恢复
	public static class DatabaseBackupAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		//构造方法
		public DatabaseBackupAction(){
			super("数据库备份/恢复");
			putValue(Action.LONG_DESCRIPTION, "数据库备份/恢复");
			putValue(Action.SHORT_DESCRIPTION, "数据库备份/恢复");
		}
		public void actionPerformed(ActionEvent e) {
			if (!frames.containsKey("数据库备份/恢复")||frames.get("数据库备份/恢复").isClosed()) {
				DatabaseIFrame iframe=new DatabaseIFrame();
				frames.put("数据库备份/恢复", iframe);
				MainDialog.addIFrame(frames.get("数据库备份/恢复"));
			}
		}
	}
	
	//退出操作
	public static class ExitAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
		//构造方法
		public ExitAction(){
			super("退出系统");
			putValue(Action.LONG_DESCRIPTION, "退出图书馆管理系统");
			putValue(Action.SHORT_DESCRIPTION, "退出系统");
		}
	}
}
