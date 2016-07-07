package com.lzw.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import com.lzw.CreateIcon;
import com.lzw.MenuActions;

public class MainDialog extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final JDesktopPane DESKTOP_PANE = new JDesktopPane();
	
	// 添加子窗体
	public static void addIFrame(JInternalFrame iframe) {
		DESKTOP_PANE.add(iframe);
	}
	
	// 初始化主窗口
	public MainDialog() {
		super("图书管管理系统");
		// 设置关闭方式
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// 设置窗口下次可见时出现位置
		setLocationByPlatform(true);
		
		// 获取屏幕大小
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screensize.getWidth();
		int height = (int)screensize.getHeight();
		this.setBounds(width / 2 - 400, height/ 2 - 300, 800, 600);
		
		// 创建菜单并添加
		JMenuBar menuBar = this.createMenu();
		this.setJMenuBar(menuBar);
		
		// 创建工具栏并添加
		JToolBar toolBar = this.createToolBar();
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		// 定义窗口背景的标签
		final JLabel label = new JLabel();
		label.setBounds(0, 0, 0, 0);
		label.setIcon(null);
		
		// 添加背景
		DESKTOP_PANE.addComponentListener(new ComponentAdapter() {
			public void componentResized(final ComponentEvent e) {
				Dimension size = e.getComponent().getSize();
				label.setSize(size);
				ImageIcon loginIcon = CreateIcon.add("back.jpg");
				label.setIcon(loginIcon);
				label.setOpaque(true); // 在部件的所有像素上绘制
				label.setBackground(Color.white);
			}
		});
		
		DESKTOP_PANE.add(label, new Integer(Integer.MIN_VALUE));
		getContentPane().add(DESKTOP_PANE);
	}
	
	// 创建菜单栏
	private JMenuBar createMenu() {
		JMenuBar menuBar = new JMenuBar();
		
		//创建书籍管理
		JMenu bookMenu = new JMenu("书籍管理(B)");
		bookMenu.setMnemonic(KeyEvent.VK_B);
		bookMenu.add(MenuActions.BOOK_ADD);
		bookMenu.add(MenuActions.BOOK_ADD2);
		bookMenu.add(MenuActions.BOOK_PRICE);
		bookMenu.add(MenuActions.BOOK_SEARCH);
		
		//创建借书证管理
		JMenu readerMenu = new JMenu("借书证管理(R)");
		readerMenu.setMnemonic(KeyEvent.VK_R);
		readerMenu.add(MenuActions.READER_ADD);
		readerMenu.add(MenuActions.READER_OWN);
		readerMenu.add(MenuActions.READER_SEARCH);
		
		//创建书架管理
		JMenu bsMenu = new JMenu("书架管理(S)");
		bsMenu.setMnemonic(KeyEvent.VK_S);
		bsMenu.add(MenuActions.BS_ADD);
		bsMenu.add(MenuActions.BS_SEARCH);
		
		//创建一个图书借阅管理的菜单项，并分别绑定事件
		JMenu borrowManageMenu=new JMenu();
		borrowManageMenu.setText("借阅管理(O)");
		borrowManageMenu.setMnemonic(KeyEvent.VK_O);
		borrowManageMenu.add(MenuActions.BORROW);
		borrowManageMenu.add(MenuActions.RETURN);
		borrowManageMenu.add(MenuActions.BORROW_SEARCH);
		
		//创建一个系统管理菜单项及一个用户管理子项
		JMenu sysManageMenu = new JMenu();
		sysManageMenu.setText("系统管理(M)");
		sysManageMenu.setMnemonic(KeyEvent.VK_M);
		
		JMenu userManageMItem=new JMenu("用户管理");
		userManageMItem.add(MenuActions.USER_ADD);
		userManageMItem.add(MenuActions.MODIFY_PASSWORD);
		
		sysManageMenu.add(userManageMItem);
		sysManageMenu.add(MenuActions.DATABASE_BACKUP);
		sysManageMenu.add(MenuActions.EXIT);

		//将菜单项放入菜单栏
		menuBar.add(bookMenu);
		menuBar.add(readerMenu);
		menuBar.add(borrowManageMenu);
		menuBar.add(bsMenu);
		menuBar.add(sysManageMenu);
		
		return menuBar;
	}
	
	//创建工具栏
	private JToolBar createToolBar(){
		JToolBar toolBar = new JToolBar();
		// 设置工具栏是否可以移动
		toolBar.setFloatable(false);
		
		// 设置 添加图书信息 按钮
		JButton bookAddButton = new JButton(MenuActions.BOOK_ADD);
		ImageIcon icon = CreateIcon.add("addBook.jpg");
		bookAddButton.setIcon(icon);
		bookAddButton.setHideActionText(true);
		toolBar.add(bookAddButton);
		
		// 在工具栏中添加 图书信息查询 按钮
		JButton bookButton = new JButton(MenuActions.BOOK_SEARCH);
		ImageIcon bookIcon = CreateIcon.add("bookInfo.jpg");
		bookButton.setIcon(bookIcon);
		bookButton.setHideActionText(true);
		toolBar.add(bookButton);
		
		// 在工具栏中添加 图书借阅信息 按钮
		JButton bookBorrowButton = new JButton(MenuActions.BORROW);
		ImageIcon bookBorrowIcon = CreateIcon.add("borrowInfo.jpg");
		bookBorrowButton.setIcon(bookBorrowIcon);
		bookBorrowButton.setHideActionText(true);
		toolBar.add(bookBorrowButton);
		
		// 在工具栏中创建读者添加按钮
		JButton readerAddButton = new JButton(MenuActions.READER_ADD);
		ImageIcon readerAddIcon = CreateIcon.add("addReader.jpg");
		readerAddButton.setIcon(readerAddIcon);
		readerAddButton.setHideActionText(true);
		toolBar.add(readerAddButton);
		
		// 在工具栏中创建书架添加按钮
		JButton bsAddButton = new JButton(MenuActions.BS_ADD);
		ImageIcon ReaderSearchIcon = CreateIcon.add("addLibrary.jpg");
		bsAddButton.setIcon(ReaderSearchIcon);
		bsAddButton.setHideActionText(true);
		toolBar.add(bsAddButton);
		
		// 在工具栏中刚添加退出按钮
		JButton ExitButton = new JButton(MenuActions.EXIT);
		ImageIcon ExitIcon = CreateIcon.add("exit.jpg");
		ExitButton.setIcon(ExitIcon);
		ExitButton.setHideActionText(true);
		toolBar.add(ExitButton);
		
		return toolBar;
	}
}
