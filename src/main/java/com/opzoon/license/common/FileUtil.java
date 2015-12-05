package com.opzoon.license.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.springframework.util.FileCopyUtils;

import com.opzoon.license.exception.BasicException;
import com.opzoon.license.exception.custom.FileUtilException;

public class FileUtil {

	// 在path路径创建文件，并将内容写入文件
	public static void createFile(String path, byte[] data) {
		File file = new File(path);
		if(file.exists()) {
			file.delete();
		}
		try {
			FileCopyUtils.copy(data, new File(path));
		} catch (IOException e) {
			throw new FileUtilException(e);
		}
	}
	
	// 读取path路径的文件内容
	public static String readFile(String path) throws BasicException {
		File file = new File(path);
		if(!file.exists()) {
			throw new FileUtilException("file not exists");
		}
		String content = "";
		try {
			String line = "";
			BufferedReader in = new BufferedReader(new FileReader(file));
			while ((line = in.readLine()) != null) {
				content += "\r\n" + line;
			}
		} catch (Exception e) {
			throw new FileUtilException(e);
		}
		
		return content.replaceFirst("\r\n", "");
	}
	
	// 将data写入path路径的文件中
	public static void writeFile(String path, byte[] data) {
		
	}
	
	// 删除path路径的文件
	public static void deleteFile(String path) {
		
	}
	
	
	// 创建path路径的文件夹
	public static void createDir(String path) {
		File file = new File(path);
		if(!file.exists()) {
			file.mkdir();
		}
	}
	
	// 清空path路径文件夹下的所有文件
	public static void clearDir(String path) {
		
	}
}
