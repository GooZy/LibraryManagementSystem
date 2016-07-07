package com.lzw.login.in;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.dao.Dao;
import com.dao.model.Manager;
import com.lzw.MyDocument;
import com.lzw.login.LoginDialog;

public class ChangePw extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPasswordField oldPass;
	private JPasswordField newPass2;
	private JPasswordField newPass1;
	private JTextField username;
	private Manager user = LoginDialog.getUser();//换取登陆管理员信息
	
	public ChangePw(){
		super();
		//设置更改密码窗体初始值
		this.setIconifiable(true);//设置内部窗口可以图标
		this.setTitle("更改密码");//设置标题
		this.setClosable(true);//设置是否可以通过某个用户关闭此JInternalFrame
		this.getContentPane().setLayout(new GridLayout(5, 1));//设置玻璃板布局
		this.setBounds(100,50,300,228);
		
		// 创建用户名, 原密码， 新密码，确认密码, 按钮面板
		JPanel juser = new JPanel();
		JPanel orgpwd = new JPanel();
		JPanel newpwd = new JPanel();
		JPanel surepwd = new JPanel();
		JPanel bt = new JPanel();
		
		//创建一个标签显示登录名
		final JLabel label_1 = new JLabel();
		label_1.setFont(new Font("",Font.PLAIN,14));
		label_1.setText(" 登 录 名:");
		juser.add(label_1);
		
		//创建一个文本框用于输入登录名
		username=new JTextField(user.getName());
		username.setEditable(false);
		username.setSize(new Dimension(150,25));
		username.setBorder(null);
		juser.add(username);
		getContentPane().add(juser);
		
		//创建一个旧密码标签
		final JLabel label_2 = new JLabel();
		label_2.setFont(new Font("",Font.PLAIN,14));
		label_2.setText("旧 密 码:");
		orgpwd.add(label_2);
		
		//创建一个用于输入旧密码的密码框
		oldPass = new JPasswordField(10);
		oldPass.setDocument(new MyDocument(15));
		orgpwd.add(oldPass);
		getContentPane().add(orgpwd);
		
		//创建一个新密码的标签
		final JLabel label_3 = new JLabel();
		label_3.setFont(new Font("",Font.PLAIN,14));
		label_3.setText("新 密 码:");
		newpwd.add(label_3);
		
		//创建一个用于输入新密码的文本框
		newPass1=new JPasswordField(10);
		newPass1.setDocument(new MyDocument(15));
		newPass1.setFont(new Font("",Font.PLAIN,14));
		newpwd.add(newPass1);
		getContentPane().add(newpwd);
		
		//用于创建一个确认新密码的标签
		final JLabel label_4 = new JLabel();
		label_4.setFont(new Font("",Font.PLAIN,14));
		label_4.setText("确认新密码:"	);
		surepwd.add(label_4);
		
		//用于创建一个用于输入确认新密码的密码框
		newPass2 =new JPasswordField(10);
		newPass2.setDocument(new MyDocument(15));
		newPass2.setFont(new Font("",Font.PLAIN,14));
		surepwd.add(newPass2);
		getContentPane().add(surepwd);
		
		//创建确认按钮监听
		final JButton button=new JButton();
		button.addActionListener(new ActionListener(){

			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if(oldPass.getText().equals(user.getPassword())){
					if(newPass1.getText().equals(newPass2.getText())){
						if (newPass1.getText().trim().length() < 6) {
							JOptionPane.showMessageDialog(getContentPane(), "新密码长度不得小于6");
							newPass1.setText(null);
							newPass2.setText(null);
							return;
						}
						Dao.changePwd(user.getName(), user.getPassword(), newPass1.getText());
						oldPass.setText(null);
						newPass1.setText(null);
						newPass2.setText(null);
						JOptionPane.showMessageDialog(getContentPane(), "密码修改成功");
						doDefaultCloseAction();
					}else{
						JOptionPane.showMessageDialog(getContentPane(), "两次密码输入不一致，请重新输入!");
						newPass1.setText("");
						newPass2.setText("");
					}
				}else{
					JOptionPane.showMessageDialog(getContentPane(), "旧密码输入错误，请确认密码!");
				}
			}
		});
		button.setText("确认");
		
		
		bt.add(button);
		
		//创建重写按钮监听
		final JButton button_1=new JButton();
		button_1.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				oldPass.setText(null);
				newPass1.setText(null);
				newPass2.setText(null);
			}
			
		});
		button_1.setText("重写");
		bt.add(button_1);
		getContentPane().add(bt);
		
		setVisible(true);
	}
	
}
