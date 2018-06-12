package com.key.common.plugs.data.mysql;

import java.io.File;
import java.io.IOException;

/**
 * MySQL数据库备份
 * 
 * @author GaoHuanjie
 */
public class MySQLDatabaseBackup {

	/**
	 * Java代码实现MySQL数据库导出
	 * 
	 * @author GaoHuanjie
	 * @param hostIP MySQL数据库所在服务器地址IP
	 * @param userName 进入数据库所需要的用户名
	 * @param password 进入数据库所需要的密码
	 * @param savePath 数据库导出文件保存路径
	 * @param fileName 数据库导出文件文件名
	 * @param databaseName 要导出的数据库名
	 * @return 返回true表示导出成功，否则返回false。
	 */
	public static boolean exportDatabaseTool(String hostIP, String userName, String password, String savePath, String fileName,	String databaseName) {
		File saveFile = new File(savePath);
		if (!saveFile.exists()) {// If the directory does not exist
			saveFile.mkdirs();// Create a folder

		}
		if (!savePath.endsWith(File.separator)) {
			savePath = savePath + File.separator;
		}

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("mysqldump").append(" --opt").append(" -h").append(hostIP);
		stringBuilder.append(" --user=").append(userName) .append(" --password=").append(password).append(" --lock-all-tables=true");
		stringBuilder.append(" --result-file=").append(savePath + fileName).append(" --default-character-set=utf8 ").append(databaseName);
		try {
			Process process = Runtime.getRuntime().exec(stringBuilder.toString());
			if (process.waitFor() == 0) {//0 means that the thread terminates normally.
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) throws InterruptedException {
		if (exportDatabaseTool("localhost", "root", "", "/Users/keyuan/Documents/", "2016.sql", "test")) {
			System.out.println("Database backup succeeded！！！");
		} else {
			System.out.println("Database backup failed！！！");
		}
	}
}
