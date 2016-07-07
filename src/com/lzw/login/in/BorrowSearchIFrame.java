package com.lzw.login.in;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.dao.Dao;
import com.dao.model.Borrow;
import com.lzw.Item;

public class BorrowSearchIFrame extends JInternalFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField textField_1;

	private JTable table_1, table_2;

	private JComboBox<String> choice;

	private JScrollPane scrollPane, scrollPane_1;


	String booksearch[] = { "借阅日期", "归还日期", "编号", "图书编号", "读者编号", "管理员用户名"};

	private Object[][] getselect(List<?> list) {
		Object[][] s = new Object[list.size()][6];
		for (int i = 0; i < list.size(); i++) {
			Borrow borrow = (Borrow) list.get(i);
			s[i][0] = borrow.getBorrowDate();
			s[i][1] = borrow.getReturnDate();
			s[i][2] = borrow.getId();
			s[i][3] = borrow.getBid();
			s[i][4] = borrow.getRid();
			s[i][5] = borrow.getName();
		}
		return s;
	}

	public BorrowSearchIFrame() {
		super();
		setIconifiable(true);
		setClosable(true);
		setTitle("借阅查询");
		setBounds(100, 50, 500, 400);

		
		setVisible(true);

		final JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setPreferredSize(new Dimension(0, 50));
		getContentPane().add(tabbedPane);

		final JPanel panel_1 = new JPanel();
		panel_1.setLayout(new BorderLayout());
		tabbedPane.addTab("条件查询", null, panel_1, null);

		final JPanel panel_1_1 = new JPanel();
		panel_1_1.setBorder(new TitledBorder(null, "请选择查询项目", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		panel_1_1.setPreferredSize(new Dimension(0, 50));
		panel_1.add(panel_1_1, BorderLayout.NORTH);
		choice = new JComboBox<String>();
        choice.addItem("读者编号");
        choice.addItem("借阅编号");
		panel_1_1.add(choice);
		textField_1 = new JTextField();
		textField_1.setColumns(20);
		panel_1_1.add(textField_1);
        
		final JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "查询结果显示", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		panel_1.add(panel);

		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setPreferredSize(new Dimension(400, 200));
		panel.add(scrollPane_1);

		final JPanel panel_2_1 = new JPanel();
		panel_2_1.setPreferredSize(new Dimension(0, 50));
		panel_1.add(panel_2_1, BorderLayout.SOUTH);

		final JButton button = new JButton();
		button.setText("查询");
		panel_2_1.add(button);
		
		textField_1.addKeyListener(new KeyAdapter(){
			 public void keyPressed(final KeyEvent e) {
				 if(e.getKeyChar() == '\n'){
					 button.doClick();
				 }
			 }
		});

		button.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent arg0) {
					String name=(String)choice.getSelectedItem();
					if(name.equals("读者编号")){
						
						Object[][] results=getselect(Dao.getBorrowInfo(new Item(null,textField_1.getText())));
						table_2 = new JTable(results,booksearch);
						
						scrollPane_1.setViewportView(table_2);
					}
					else if(name.equals("借阅编号")){
						
						Object[][] results=getselect(Dao.getBorrowInfo(new Item(textField_1.getText(), null)));
						table_2 = new JTable(results,booksearch);
						
						scrollPane_1.setViewportView(table_2);
					}
				}
	        	
	        });
		
		
		final JButton button_1 = new JButton();
		button_1.setText("返回");
		panel_2_1.add(button_1);
		button_1.addActionListener(new CloseActionListener());
		
		setVisible(true);
		
		final JPanel panel_2 = new JPanel();
		tabbedPane.addTab("显示全部借阅信息", null, panel_2, null);
         
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(450, 250));
		panel_2.add(scrollPane);
		
		Object[][] results = getselect(Dao.getAllBorrowInfo());
		String booksearch[] = { "借阅日期", "归还日期", "编号", "图书编号", "读者编号", "管理员用户名"};
		table_1 = new JTable(results,booksearch);
		
		scrollPane.setViewportView(table_1);
		
	}
	class CloseActionListener implements ActionListener {			// 添加关闭按钮的事件监听器
		public void actionPerformed(final ActionEvent e) {
			doDefaultCloseAction();
		}
	}
}
