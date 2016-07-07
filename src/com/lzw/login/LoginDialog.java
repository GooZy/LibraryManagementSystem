package com.lzw.login;

import java.awt.*;
import java.awt.event.*;

import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.dao.Dao;
import com.dao.model.Manager;
import com.lzw.CreateIcon;
import com.lzw.MyDocument;

public class LoginDialog extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPasswordField password;  //创建密码框
	private JTextField username;   //创建用户框
	private JButton login;  //创建登陆按钮
	private JButton exit;  //创建退出按钮
	private JButton change; //修改配置文件
	private static Manager user;  //管理员传参数
	
	//登陆按钮事件
	class BookLoginAction implements ActionListener{
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e) {
			try {
				//System.out.println(username.getText());
				//System.out.println(password.getText());
				setUser(Dao.checkLogin(username.getText(), password.getText()));
				if(user.getName() != null){ // 为空说明未找到
					// 构建主窗体
					MainDialog frame = new MainDialog();
					// 设置主窗体可视
					frame.setVisible(true);
					// 隐藏登陆窗体
					LoginDialog.this.setVisible(false);
					// System.out.println("登陆成功啦~");
				}else{
					JOptionPane.showMessageDialog(null, "输入的用户名或密码错误，请重新输入。");
					password.setText("");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	// 退出按钮事件
	private class ExitAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	
	// 修改配置事件
	private class ChangeSetting implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new SettingDialog();
		}
	}
	
	// 得到用户
	public static Manager getUser(){
		return user;
	}
		
	public static void setUser(Manager user){
		LoginDialog.user = user;
	}
	
	public LoginDialog() {
		// 初始化登陆窗体参数
		super("图书馆管理系统登陆");
		final BorderLayout borderLayout = new BorderLayout();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(borderLayout);// 设置内容布局
		
		// 获取屏幕大小以决定窗口初始位置
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screensize.getWidth();
		int height = (int)screensize.getHeight();
		this.setBounds(width / 2 - 200, height/ 2 - 150, 285, 220);
		
		// 创建一个主面板
		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(0,0,0,0)); // 从左上角布局
		this.getContentPane().add(mainPanel);
		
		// 添加上方背景
		final JLabel backLabel = new JLabel();
		ImageIcon loginIcon = CreateIcon.add("login.jpg");
		backLabel.setIcon(loginIcon);
		backLabel.setOpaque(true); // 在部件的所有像素上绘制
		backLabel.setBackground(Color.white);
		backLabel.setPreferredSize(new Dimension(260,60)); // 长260，宽60
		mainPanel.add(backLabel, BorderLayout.NORTH);
		
		// 创建一个3x1网格布局面板，
		final JPanel panel_1 = new JPanel();
		final GridLayout gridLayout = new GridLayout(3,1);
		panel_1.setLayout(gridLayout);
		mainPanel.add(panel_1, BorderLayout.CENTER);
		
		// 创建用户名, 密码面板
		JPanel user = new JPanel();
		JPanel pwd = new JPanel();
		
		// 创建用户label标签
		final JLabel label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);//设置组件水平对齐方式
		label.setText("用  户  名 :    ");
		user.add(label);
		
		// 创建用户框
		username = new JTextField(10);
		username.setDocument(new MyDocument(20));
		user.add(username);
		
		panel_1.add(user);
		
		// 创建密码label标签
		final JLabel label_1 = new JLabel();
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setText("密         码 :    ");
		pwd.add(label_1);
		
		// 创建密码框
		password = new JPasswordField(10);
		password.setDocument(new MyDocument(15));
		password.setEchoChar('*');
		// 设置enter建
		password.addKeyListener(new KeyAdapter(){
			 public void keyPressed(final KeyEvent e) {
				 if(e.getKeyChar() == '\n'){
					 login.doClick();
				 }
			 }
		});
		pwd.add(password);
		
		panel_1.add(pwd);
		
		// 创建按钮面板
		final JPanel panel_2 = new JPanel();
		
		// 创建登陆按钮
		login = new JButton();
		login.addActionListener(new BookLoginAction());
		login.setText("登陆");
		panel_2.add(login);
		
		// 创建退出按钮
		exit = new JButton();
		exit.addActionListener(new ExitAction());
		exit.setText("退出");
		panel_2.add(exit);
		
		// 创建修改按钮
		change = new JButton();
		change.addActionListener(new ChangeSetting());
		change.setText("修改配置");
		panel_2.add(change);
		
		// 加入到网络布局中
		panel_1.add(panel_2);
		
		// 设置显示方式
		this.setVisible(true);
		this.setResizable(false);
		// 这里连接下，节省连接时间
		new Dao();
	}
}
