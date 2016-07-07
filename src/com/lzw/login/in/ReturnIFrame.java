package com.lzw.login.in;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.dao.Dao;
import com.dao.model.Borrow;
import com.lzw.CreateIcon;
import com.lzw.MyDocument;

public class ReturnIFrame extends JInternalFrame{
	private static final long serialVersionUID = 1L;
	private JTextField id;
	private JFormattedTextField return_date;
	
	public ReturnIFrame() {
		super();
		setIconifiable(true);							// 设置窗体可最小化－－－必须
		setClosable(true);								// 设置窗体可关闭－－－必须
		setTitle("添加归还信息");						// 设置窗体标题－－－必须
		setBounds(100, 50, 500, 225);

		//创建标头图片
		final JLabel logoLabel = new JLabel();
		ImageIcon readerAddIcon=CreateIcon.add("tback.jpg");
		logoLabel.setIcon(readerAddIcon);
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
		label_2.setText("借阅记录编号：");
		panel_1.add(label_2);
		id = new JTextField(10);
		id.setDocument(new MyDocument(256));
		panel_1.add(id);
		
		final JLabel label_3 = new JLabel();
		label_3.setText("归还日期：");
		panel_1.add(label_3);
		return_date = new JFormattedTextField();
		return_date.setValue("XXXX-XX-XX");
		return_date.addKeyListener(new DateListener());
		panel_1.add(return_date);
		
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
	
	//时间格式监听
	class DateListener extends KeyAdapter {
		public void focusGained(FocusEvent e) {
			return_date.setText("");
		}
	}
	
	
	//创建保存按钮监听内部类
	class ButtonAddListener implements ActionListener {
		ButtonAddListener(JRadioButton button1) {
		}

		public void actionPerformed(final ActionEvent e) {
			
			Borrow bo = new Borrow();
			
			if(id.getText().length() == 0){
				JOptionPane.showMessageDialog(null, "归还记录编号不能为空");
				return ;
			}
			if(return_date.getText().length() == 0){
				JOptionPane.showMessageDialog(null, "归还日期不能为空");
				return;
			}
			//System.out.println(id.getText());
			bo.setId(id.getText().trim());
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = sdf.parse(return_date.getText().trim());
				Date sqlDate = new Date(date.getTime());
				bo.setReturnDate(sqlDate);
				//System.out.println(t);
			} catch (ParseException e2) {
				//System.out.println("in: " + t);
				JOptionPane.showMessageDialog(null, "日期格式错误");
				e2.printStackTrace();
			}
		
			try {
				//System.out.println(bo.getBid());
				//System.out.println(bo.getReturnDate());
				if(Dao.changeReturn(bo.getId(), bo.getReturnDate())){
					JOptionPane.showMessageDialog(null, "添加成功！");
					doDefaultCloseAction();
				}
				else {
					JOptionPane.showMessageDialog(null, "添加失败！");
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
