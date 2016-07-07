package com.lzw;

import java.net.URL;

import javax.swing.ImageIcon;

import com.lzw.login.*;


public class CreateIcon {
	public static ImageIcon add(String ImageName){
		URL IconUrl = MainDialog.class.getResource("/res/"+ImageName);
		ImageIcon icon = new ImageIcon(IconUrl);
		return icon;
	}
}

