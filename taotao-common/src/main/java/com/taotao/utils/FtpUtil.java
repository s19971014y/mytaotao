package com.taotao.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Vector;

import com.jcraft.jsch.*;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * ftp上传下载工具类
 */
public class FtpUtil {

	/** 
	 * Description: 向FTP服务器上传文件 
	 * @param host FTP服务器hostname 
	 * @param port FTP服务器端口 
	 * @param username FTP登录账号 
	 * @param password FTP登录密码 
	 * @param basePath FTP服务器基础目录
	 * @param filePath FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath
	 * @param filename 上传到FTP服务器上的文件名 
	 * @param input 输入流 
	 * @return 成功返回true，否则返回false 
	 */  
	public static boolean uploadFile(String host, int port, String username, String password, String basePath,
			String filePath, String filename, InputStream input) {
		boolean result = false;
		JSch jSch = new JSch();
		try {
			Session ftpuser = jSch.getSession(username, host, port);
			ftpuser.setConfig("StrictHostKeyChecking", "no");
			ftpuser.setPassword(password);
			ftpuser.connect();

			Channel channel = ftpuser.openChannel("sftp");
			channel.connect();
			ChannelSftp sftp = (ChannelSftp) channel;

			//切换到上传目录
			try {
				Vector lsList = null;
				try {
					lsList = sftp.ls(basePath + filePath);
				}catch (Exception e){
				}

				if(lsList == null){
					//如果目录不存在创建目录
					String[] dirs = filePath.split("/");
					String tempPath = basePath;
					sftp.cd(basePath);
					for (String dir:dirs){
						if(dir.length() > 0){
							try{
								sftp.cd(dir);
							}catch (SftpException e){
								sftp.mkdir(dir);
								sftp.cd(dir);
							}
						}
					}
				}

				//上传文件
				sftp.put(input,filename);
				result = true;
				try {
					input.close();
					sftp.disconnect();
					channel.disconnect();
					ftpuser.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}


			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (JSchException e) {
			e.printStackTrace();
		}

		/*FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(host, port);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return result;
			}
			//切换到上传目录
			if (!ftp.changeWorkingDirectory(basePath+filePath)) {
				//如果目录不存在创建目录
				String[] dirs = filePath.split("/");
				String tempPath = basePath;
				for (String dir : dirs) {
					if (null == dir || "".equals(dir)) continue;
					tempPath += "/" + dir;
					if (!ftp.changeWorkingDirectory(tempPath)) {
						if (!ftp.makeDirectory(tempPath)) {
							return result;
						} else {
							ftp.changeWorkingDirectory(tempPath);
						}
					}
				}
			}
			//设置上传文件的类型为二进制类型
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			//上传文件
			if (!ftp.storeFile(filename, input)) {
				return result;
			}
			input.close();
			ftp.logout();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}*/
		return result;
	}
	
	/** 
	 * Description: 从FTP服务器下载文件 
	 * @param host FTP服务器hostname 
	 * @param port FTP服务器端口 
	 * @param username FTP登录账号 
	 * @param password FTP登录密码 
	 * @param remotePath FTP服务器上的相对路径 
	 * @param fileName 要下载的文件名 
	 * @param localPath 下载后保存到本地的路径 
	 * @return 
	 */  
	public static boolean downloadFile(String host, int port, String username, String password, String remotePath,
			String fileName, String localPath) {
		boolean result = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(host, port);
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return result;
			}
			ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			FTPFile[] fs = ftp.listFiles();
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					File localFile = new File(localPath + "/" + ff.getName());

					OutputStream is = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), is);
					is.close();
				}
			}

			ftp.logout();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return result;
	}
	
	public static void main(String[] args) throws JSchException, SftpException, FileNotFoundException {
		JSch jSch = new JSch();
		Session ftpuser = jSch.getSession("ftpuser", "47.101.212.18", 22);
		ftpuser.setConfig("StrictHostKeyChecking", "no");
		ftpuser.setPassword("ftpuser");
		ftpuser.connect();
		System.out.println("连接成功");
		Channel channel = ftpuser.openChannel("sftp");
		channel.connect();
		ChannelSftp sftp = (ChannelSftp) channel;

//		sftp.mkdir("ww");
//		sftp.mkdir("ww/images");
//		sftp.cd("ww/images");
//
//		File file = new File("C:\\Users\\Sun\\Desktop\\1.jpg");
//
//		InputStream in = new FileInputStream(file);
//		sftp.put(in,"2000.jpg");
		SftpATTRS stat = null;
		try{
			 stat = sftp.stat("ww/ww/ww");
		}catch (Exception e){

		}
		if(stat == null){
			System.out.println("文件不存在");
		}
	}
}
