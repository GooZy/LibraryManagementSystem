package com.lzw.login;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SettingDialog extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static JTextField ip;       // ip地址
	private static JTextField port;     // 端口号
	private static JTextField user;     // 数据库登陆名
	private static JTextField pwd;       // 数据库登陆密码
	private static JButton ok;           // 确认按钮
	private static JButton exit;         // 返回按钮

	public SettingDialog() {
		super("修改配置文件");
		
		//获取屏幕大小
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screensize.getWidth();
		int height = (int)screensize.getHeight();
		this.setBounds(width / 2 - 180, height/ 2 - 130, 255, 190);
		
		//创建主面板
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.getContentPane().add(mainPanel);
		
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(2, 2));
		
		mainPanel.add(textPanel, BorderLayout.CENTER);
		
		//创建四个文本框
		JPanel userPanel = new JPanel();
		JPanel pwdPanel = new JPanel();
		JPanel ipPanel = new JPanel();
		JPanel portPanel = new JPanel();
		
		// ip框
		final JLabel label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);//设置组件水平对齐方式
		label.setText("ip地址: ");
		ipPanel.add(label);
		ip = new JTextField(10);
		ipPanel.add(ip);
		textPanel.add(ipPanel);
		
		// port框
		final JLabel label_1 = new JLabel();
		label_1.setHorizontalAlignment(SwingConstants.CENTER);//设置组件水平对齐方式
		label_1.setText("端口号: ");
		portPanel.add(label_1);
		port = new JTextField(10);
		portPanel.add(port);
		textPanel.add(portPanel);
		
		// user框
		final JLabel label_2 = new JLabel();
		label_2.setHorizontalAlignment(SwingConstants.CENTER);//设置组件水平对齐方式
		label_2.setText("数据库登陆名: ");
		userPanel.add(label_2);
		user = new JTextField(10);
		userPanel.add(user);
		textPanel.add(userPanel);
		
		// pwd框
		final JLabel label_3 = new JLabel();
		label_3.setHorizontalAlignment(SwingConstants.CENTER);//设置组件水平对齐方式
		label_3.setText("数据库密码: ");
		pwdPanel.add(label_3);
		pwd = new JTextField(10);
		pwdPanel.add(pwd);
		textPanel.add(pwdPanel);
		
		JPanel buttonPanel = new JPanel();
		// 创建确认按钮
		ok = new JButton();
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (ip.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(null, "ip不能为空");
						return;
					}
					if (port.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(null, "端口号不能为空");
						return;
					}
					if (user.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(null, "数据库登陆名不能为空");
						return;
					}
					PrintWriter output = new PrintWriter("setting.txt");
					output.println(ip.getText().trim());
					output.println(port.getText().trim());
					output.println(user.getText().trim());
					output.println(pwd.getText().trim());
					System.out.println(ip.getText().trim());
					output.close();
					JOptionPane.showMessageDialog(null, "修改配置文件成功");
					setVisible(false);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		ok.setText("确认");
		buttonPanel.add(ok);
				
		// 创建退出按钮
		exit = new JButton();
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		exit.setText("退出");
		buttonPanel.add(exit);
		
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		
		this.setVisible(true);
	}
}
