package com.lzw.login.in;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.dao.Dao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

public class DatabaseIFrame extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField backupTextField;       // 备份数据库路径
	private JTextField restoreTextField;       // 恢复数据库路径
	
	private JPanel backupPanel = new JPanel();   // 备份功能的组件面板
	private JPanel restorePanel = new JPanel();  // 恢复功能的组件面板
	
	private JButton backupButton;              // 备份按钮
	private JButton browseButton1;             // 备份浏览按钮
	private JButton restoreButton;             // 恢复按钮
	private JButton browseButton2;             // 恢复浏览按钮
	
	public DatabaseIFrame() {
		super("数据库备份与恢复");
		this.setLayout(new GridLayout(2, 1));
		setIconifiable(true);							// 设置窗体可最小化－－－必须
		setClosable(true);								// 设置窗体可关闭－－－必须
		setBounds(100, 50, 500, 350);
		
		backupTextField = new JTextField(20);
		restoreTextField = new JTextField(20);
		
		GridLayout grid = new GridLayout(2, 1);
		grid.setVgap(10);
		backupPanel.setLayout(grid);
		backupPanel.setBorder(BorderFactory.createTitledBorder("数据库备份"));
		backupPanel.add(backupTextField);
		JPanel button1 = new JPanel();
		button1.add(getBrowseButton1());
		button1.add(getBackupButton());
		backupPanel.add(button1);
		getContentPane().add(backupPanel);
		
		restorePanel.setLayout(grid);
		restorePanel.setBorder(BorderFactory.createTitledBorder("数据库恢复"));
		JPanel button2 = new JPanel();
		restorePanel.add(restoreTextField);
		button2.add(getBrowseButton2());
		button2.add(getRestoreButton());
		restorePanel.add(button2);
		getContentPane().add(restorePanel);
		
		setVisible(true);
	}
	
	private JButton getBrowseButton1() {
		if (browseButton1 == null) {
			browseButton1 = new JButton("浏览(B)......");
			browseButton1.setMnemonic(KeyEvent.VK_B);
			
			browseButton1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser dirChooser = new JFileChooser(".");
					int option = dirChooser.showOpenDialog(DatabaseIFrame.this);
					if (option == JFileChooser.APPROVE_OPTION) {
						File selFile = dirChooser.getSelectedFile();
						backupTextField.setText(selFile.getAbsolutePath());
					}
				}
			});
		}
		return browseButton1;
	}
	
	private JButton getBrowseButton2() {
		if (browseButton2 == null) {
			browseButton2 = new JButton("浏览(W)......");
			browseButton2.setMnemonic(KeyEvent.VK_W);
			
			browseButton2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser dirChooser = new JFileChooser(".");
					int option = dirChooser.showOpenDialog(DatabaseIFrame.this);
					if (option == JFileChooser.APPROVE_OPTION) {
						File selFile = dirChooser.getSelectedFile();
						restoreTextField.setText(selFile.getAbsolutePath());
					}
				}
			});
		}
		return browseButton2;
	}

	private JButton getBackupButton() {
		if (backupButton == null) {
			backupButton = new JButton();
			backupButton.setText("备份(K)");
			backupButton.setMnemonic(KeyEvent.VK_K);
			backupButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					String path = backupTextField.getText();
					File backupFile = new File(path);
					if (path == null || path.isEmpty()) return;
					//System.out.println(backupFile.getAbsolutePath());
					String sql = "backup database Library to disk='" + backupFile.getAbsolutePath() + "'"
							+ " with init";
					try {
						int x = Dao.restoreOrBackup(sql);
						System.out.println(x);
					} catch (Exception e1) {
						e1.printStackTrace();
						String message = e1.getMessage();
						int index = message.lastIndexOf(')');
						message = message.substring(index + 1);
						JOptionPane.showMessageDialog(DatabaseIFrame.this, message);
						return;
					}
					JOptionPane.showMessageDialog(DatabaseIFrame.this, "备份成功");
				}
			});
		}
		return backupButton;
	}
	
	private JButton getRestoreButton() {
		if (restoreButton == null) {
			restoreButton = new JButton();
			restoreButton.setText("恢复(R)");
			restoreButton.setMnemonic(KeyEvent.VK_R);
			restoreButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					String path = restoreTextField.getText();
					if (path == null || path.isEmpty()) return;
					
					File restoreFile = new File(path);
					
					String sql = "use master; restore database Library from disk='" + restoreFile.getAbsolutePath()
								+ "' with replace";
					try {
						Dao.restoreOrBackup(sql);
					} catch (Exception e1) {
						e1.printStackTrace();
						String message = e1.getMessage();
						int index = message.lastIndexOf(')');
						message = message.substring(index + 1);
						JOptionPane.showMessageDialog(DatabaseIFrame.this, message);
						return;
					}
					JOptionPane.showMessageDialog(DatabaseIFrame.this, "恢复成功");
				}
			});
		}
		return restoreButton;
	}
}
