package com.lzw.login.in;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.dao.Dao;
import com.dao.model.Manager;
import com.lzw.CreateIcon;
import com.lzw.MyDocument;

public class UserAddIFrame extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField name;
	private JTextField password;
	
	public UserAddIFrame() {
		super();
		setIconifiable(true);							// 设置窗体可最小化－－－必须
		setClosable(true);								// 设置窗体可关闭－－－必须
		setTitle("添加管理员");						// 设置窗体标题－－－必须
		setBounds(100, 50, 500, 225);

		//创建标头图片
		final JLabel logoLabel = new JLabel();
		ImageIcon userAddIcon=CreateIcon.add("tback.jpg");
		logoLabel.setIcon(userAddIcon);
		logoLabel.setOpaque(true);
		logoLabel.setBackground(Color.white);
		logoLabel.setPreferredSize(new Dimension(400, 60));
		getContentPane().add(logoLabel, BorderLayout.NORTH);

		//设置一个添加组件的面板
		final JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		getContentPane().add(panel);

		//在添加组件面板中嵌套面板1,用于放置非按钮组件
		final JPanel panel_1 = new JPanel();
		final GridLayout gridLayout = new GridLayout(2, 2);
		gridLayout.setVgap(20);
		panel_1.setLayout(gridLayout);
		panel.add(panel_1);

		final JLabel label_2 = new JLabel();
		label_2.setText("用户名：");
		panel_1.add(label_2);
		name = new JTextField(10);
		name.setDocument(new MyDocument(20));
		panel_1.add(name);
		
		final JLabel label_3 = new JLabel();
		label_3.setText("密码：");
		panel_1.add(label_3);
		password = new JTextField(10);
		password.setDocument(new MyDocument(15));
		panel_1.add(password);
		
		//在组件面板中嵌套一个用于放着按钮的面板
		final JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(450, 100));
		panel.add(panel_2);
		
		final JRadioButton radioButton1 = new JRadioButton();

		//创建保存面板
		final JButton submit = new JButton();
		panel_2.add(submit);
		submit.setText("提交");
		submit.addActionListener((ActionListener) new ButtonAddListener(radioButton1));
		
		//创建返回面板
		final JButton back = new JButton();
		panel_2.add(back);
		back.setText("返回");
		back.addActionListener(new CloseActionListener());

		setVisible(true);
	}
	
	
	//创建保存按钮监听内部类
	class ButtonAddListener implements ActionListener {
		ButtonAddListener(JRadioButton button1) {
		}

		public void actionPerformed(final ActionEvent e) {
			
			Manager ma = new Manager();
			
			if(name.getText().length() == 0){
				JOptionPane.showMessageDialog(null, "用户名不能为空");
				return ;
			}
			if(name.getText().length() > 20) {
				JOptionPane.showMessageDialog(null, "用户名不得长度大于20");
				name.setText("");
				return ;
			}
			if(password.getText().length() == 0){
				JOptionPane.showMessageDialog(null, "密码不能为空");
				return;
			}
			if(password.getText().length() > 15){
				JOptionPane.showMessageDialog(null, "密码长度不得大于15");
				password.setText("");
				return;
			}
			if(password.getText().length() < 6){
				JOptionPane.showMessageDialog(null, "密码长度不得小于6");
				password.setText("");
				return;
			}
			ma.setName(name.getText().trim());
			ma.setPassword(password.getText().trim());
		
			try {
				if(Dao.insertManagetInfo(ma)){
					JOptionPane.showMessageDialog(null, "添加成功！");
					doDefaultCloseAction();
				}
				else {
					JOptionPane.showMessageDialog(null, "用户名已存在");
					name.setText("");
				}
			} catch (NumberFormatException e1) {
				String message = e1.getMessage();
				int index = message.lastIndexOf(')');
				message = message.substring(index + 1);
				JOptionPane.showMessageDialog(null, message);
				e1.printStackTrace();
			} 
		}
	}
	class CloseActionListener implements ActionListener {			// 添加关闭按钮的事件监听器
		public void actionPerformed(final ActionEvent e) {
			doDefaultCloseAction();
		}
	}

}
