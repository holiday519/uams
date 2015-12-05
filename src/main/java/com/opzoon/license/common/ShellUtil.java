package com.opzoon.license.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.opzoon.license.exception.BasicException;
import com.opzoon.license.exception.custom.ShellExecuteException;

public class ShellUtil {

	public static String executeShell(String shellCommand) throws BasicException {
		Process pid;
		try {
			pid = Runtime.getRuntime().exec(shellCommand);
		} catch (IOException e) {
			throw new ShellExecuteException(e);
		}
		if(pid == null) {
			throw new ShellExecuteException("process id is null");
		}
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pid.getInputStream()), 4096);
		StringBuilder stringBuilder = new StringBuilder();
		String tmpLine;
		try {
			pid.waitFor();
		} catch (InterruptedException e) {
			throw new ShellExecuteException(e);
		}
		try {
			while((tmpLine = bufferedReader.readLine()) != null) {
				stringBuilder.append(tmpLine).append("\r\n");
			}
			bufferedReader.close();
		} catch (IOException e) {
			throw new ShellExecuteException(e);
		}
		
		return stringBuilder.toString();
	}
	
	/**
	 * 检测脚本是否有执行权限
	 * @param 脚本路径
	 * @return true:可执行;false:不可执行
	 */
	public static boolean hasExecuteAuthority(String shellPath) throws BasicException {
		return 'x' == executeShell("ls -al " + shellPath).charAt(3);
	}
	
	public static void addExecuteAuthority(String shellPath) throws BasicException {
		executeShell("chmod +x " + shellPath);
	}
	
	/**
	 * dosToUnix
	 */
	public static void changeDosToUnix(String shellPath) throws BasicException {
		executeShell("sed -i s/\\r//g " + shellPath);
	} 

}
