package com.lzw;

import com.lzw.login.*;

public class Main{
    public static void main(String[] args) {
    	try {
    		//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    		new LoginDialog();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}