package com.zzc.activiti;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LowerCaseInputStream extends FilterInputStream {
	public LowerCaseInputStream(InputStream in) {
		super(in);
	}

	public int read() throws IOException {
		int c = super.read();
		return (c == -1 ? c : Character.toLowerCase((char) c));
	}

	public int read(byte[] b, int offset, int len) throws IOException {
		int result = super.read(b, offset, len);
		for (int i = offset; i < offset + result; i++) {
			b[i] = (byte) Character.toLowerCase((char) b[i]);
		}
		return result;
	}
	
	public static void main(String[] strs){
		try {
			
			File file = new File("/home/zhengzhichao/桌面/11111.data");
			
			InputStream inputStream = new LowerCaseInputStream(new BufferedInputStream(new FileInputStream(file)));
			
			byte b[]=new byte[(int)file.length()];     //创建合适文件大小的数组  
			inputStream.read(b);    //读取文件中的内容到b[]数组  
			
			System.out.println(new String(b));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}