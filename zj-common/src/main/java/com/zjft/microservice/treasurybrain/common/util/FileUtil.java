package com.zjft.microservice.treasurybrain.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FileUtil {

	private static Log log = LogFactory.getLog(FileUtil.class);


	/**
	 * 下载文件服务器上的文件
	 *
	 * @param fileName 下载后保存的文件名
	 * @param fileSrc  文件绝对路径(例如/shepherd/file/upload/version/10001_10001.zip)
	 * @param delFlag  删除原文件标志 true:删除 false:不删除
	 * @param zipFlag  是否需要压缩下载
	 * @return 下载成功与失败的标志
	 */
	public static boolean downLoadResponseFile(HttpServletRequest request, HttpServletResponse response, String fileName, String fileSrc, boolean delFlag, boolean zipFlag) {
		File downFile = new File(fileSrc);
		File zipFile = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			if (!downFile.exists())
				return false;

			if (zipFlag) {
				String zipPath = createZipDirectory();
				if (zipPath == null)
					return false;

				if (fileName.lastIndexOf(".") == -1) {
					fileName += ".zip";
				} else {
					fileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".zip";
				}

				if (!ZipUtil.zipFileDoc(fileSrc, zipPath + System.getProperty("file.separator") + fileName)) {
					return false;
				} else {
					zipFile = new File(zipPath + System.getProperty("file.separator") + fileName);
				}
			}

			response.setContentType("application/octet-stream");
			response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.setHeader("Content-Length", "" + downFile.length());

			bis = new BufferedInputStream(new FileInputStream(zipFlag ? zipFile : downFile));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			bos.flush();
			return true;
		} catch (Exception e) {
			log.error("下载文件失败", e);
			return false;
		} finally {
			try {
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
				if (delFlag) {
					downFile.delete();
				}
				if (zipFlag) {
					zipFile.delete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 在浏览器中内嵌显示服务器上的图片
	 *
	 * @param fileName 下载后保存的文件名
	 * @param fileSrc  文件绝对路径(例如/shepherd/file/upload/version/ads/10001/v10032_20151014133711_1.jpg)
	 * @param delFlag  删除原文件标志 true:删除 false:不删除
	 * @return 下载成功与失败的标志
	 */
	public static boolean downLoadResponseImage(HttpServletRequest request, HttpServletResponse response, String fileName, String fileSrc, boolean delFlag) {
		File downFile = new File(fileSrc);
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			if (!downFile.exists()) return false;

			response.setContentType("image/pjpeg");
			response.setHeader("Content-Disposition", "inline;filename=" + new String(fileName.getBytes("gbk"), "iso8859-1"));
			response.setHeader("Content-Length", "" + downFile.length());

			bis = new BufferedInputStream(new FileInputStream(downFile));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			bos.flush();
			return true;
		} catch (Exception e) {
			log.error("下载文件失败", e);
			return false;
		} finally {
			try {
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
				if (delFlag) {
					downFile.delete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 删除指定目录中的所有内容。(递归)
	 *
	 * @param dir 要删除的目录
	 * @return 删除成功时返回true，否则返回false。
	 */
	public static boolean deleteDirectory(File dir) {
		if ((dir == null) || !dir.isDirectory()) {
			return false;
		}
		boolean delResult = true;
		try {
			File[] entries = dir.listFiles();
			int sz = entries.length;

			for (int i = 0; i < sz; i++) {
				if (entries[i].isDirectory()) {
					if (!deleteDirectory(entries[i])) {
						delResult = false;
					}
				} else {
					if (!entries[i].delete()) {
						delResult = false;
					}
				}
			}

			if (!dir.delete()) {
				delResult = false;
			}
		} catch (Exception e) {
			log.error("删除临时目录失败", e);
		}
		return delResult;
	}

	public static String getTempDirectory() {
		StringBuffer filePath = new StringBuffer(CfgProperty.getProperty("tmpFilePath"));

		filePath.append(System.getProperty("file.separator"));
		filePath.append("file");
		filePath.append(System.getProperty("file.separator"));
		filePath.append("download");
		filePath.append(System.getProperty("file.separator"));
		filePath.append("temp");

		File directory = new File(filePath.toString());
		if (!directory.exists()) {
			directory.mkdirs();
		}
		return filePath.toString();
	}

	public static String createZipDirectory() {
		StringBuffer zipPath = new StringBuffer(CfgProperty.getProperty("tmpFilePath"));
		zipPath.append(System.getProperty("file.separator"));
		zipPath.append("file");
		zipPath.append(System.getProperty("file.separator"));
		zipPath.append("zip");
		File dir = new File(zipPath.toString());
		/*如果文件名目录不存在则生成*/
		if (!dir.isDirectory() && !dir.mkdirs()) {
			return null;
		}
		return zipPath.toString();
	}


	/**
	 * 从远程服务器下载文件到本地
	 *
	 * @param ip         远程服务器IP
	 * @param port       端口
	 * @param localFile  下载到本地的文件（包括文件名跟路径）
	 * @param remoteFile 远程服务器文件（包括文件名跟路径）
	 * @param type       文件类型
	 * @param connTime
	 * @param soTime
	 * @param packetLen  每包发送长度
	 * @param zipType    压缩方式
	 * @return
	 */
	public static String downLoadRemoteFile(String ip, int port, String localFile, String remoteFile, int type, int connTime, int soTime, int packetLen, int zipType) {
		Socket s = null;
		DataInputStream r = null;
		DataOutputStream w = null;
		RandomAccessFile raFile = null;

		try {
			s = new Socket();
			InetSocketAddress isa = new InetSocketAddress(ip, port);
			s.connect(isa, connTime * 1000);
			s.setSoTimeout(soTime * 1000);
			w = new DataOutputStream(s.getOutputStream());
			r = new DataInputStream(s.getInputStream());

			/*文件请求*/
			String firstSend = new StringBuffer("<?xml version=\"1.0\" encoding=\"GB2312\" ?>")
					.append("<root>")
					.append("<downfiletype>").append(type).append("</downfiletype>")
					.append("<hostinfo>")
					.append("<termno>").append("notdevice").append("</termno>")
					.append("<hostip>").append(ip).append("</hostip>")
					.append("</hostinfo>")
					.append("<cmdid>0101</cmdid>")
					.append("<filename>").append(remoteFile).append("</filename>")
					.append("</root>").toString();

			raFile = new RandomAccessFile(localFile, "rw");
			StringBuffer retMsg = new StringBuffer();
			if (!SocketUtil.sendMessage(w, firstSend.getBytes(), packetLen, zipType, retMsg)) {
				return retMsg.toString();
			}
			byte[] retData = SocketUtil.readSocket(r);
			String retCode = readRetCode(retData);
			byte[] content = readRetBody(retData);
			if (content == null) {
				return "接收文件失败";
			}
			if (!retCode.equals("00")) {
				return "接收文件失败，失败原因:" + tranCode(retCode);
			}

			int maxfileLength = 0;

			try {

				String[] messageArray = (new String(content, 0, content.length)).split("\\|");
				maxfileLength = Integer.parseInt(messageArray[4].replace(" ", ""));
			} catch (Exception e) {
				log.error("解析文件属性失败", e);
				return "解析文件属性失败";
			}

			long position = 0;

			/*接受文件*/
			while (true) {
				StringBuffer secondSend = new StringBuffer();
				secondSend.append("<?xml version=\"1.0\" encoding=\"GB2312\" ?>")
						.append("<root><cmdid>0102</cmdid>")
						.append("<startpos>").append(position).append("</startpos>")
						.append("</root>");


				retMsg.setLength(0);
				if (!SocketUtil.sendMessage(w, secondSend.toString().getBytes(), packetLen, zipType, retMsg)) {
					return retMsg.toString();
				}
				retData = SocketUtil.readSocket(r);
				retCode = readRetCode(retData);
				content = readRetBody(retData);
				if (content == null) {
					return "接收文件失败";
				}

				if (retCode.equals("21") && (position <= maxfileLength)) {

					if (fileWrite(raFile, content, position, content.length) == -1) {
						return "写本地文件错误";
					}
					position += content.length;
				} else if (retCode.equals("04") || retCode.equals("20")) {
					break;
				} else if (retCode.equals("01")) {
					return "接收文件失败，，失败原因:" + tranCode(retCode);
				} else {
					break;
				}
			}
		} catch (IOException e) {
			log.error("下载文件失败", e);
			return "操作失败：" + e.getMessage();
		} catch (Exception e) {
			log.error("下载文件失败", e);
			return "操作失败：" + e.getMessage();
		} finally {
			try {
				if (r != null)
					r.close();
				if (w != null)
					w.close();
				if (s != null)
					s.close();
				if (raFile != null)
					raFile.close();
			} catch (Exception e) {

			}
		}
		return "00";
	}

	private static String readRetCode(byte[] data) {
		return new String(data, 1, 2);
	}

	private static byte[] readRetBody(byte[] data) {
		int contentLength = data.length - 4;
		byte[] contentByte = new byte[contentLength];
		System.arraycopy(data, 4, contentByte, 0, contentLength);
		return contentByte;
	}

	/**
	 * 解析文件传输返回代码
	 */
	public static String tranCode(String retCode) {
		if (retCode.equals("01")) return "打开文件失败";
		if (retCode.equals("02")) return "定位服务器端文件到指定的文件位置失败";
		if (retCode.equals("04")) return "服务器端文件不能再继续读取数据，文件已经结束";
		if (retCode.equals("30")) return "报文解析错误，指定的XML字段在请求报文中不存在";
		if (retCode.equals("20")) return "服务器端文件发送结束";
		if (retCode.equals("21")) return "报文的返回数据为文件数据，数据的长度为(报文长度-8)";
		if (retCode.equals("00")) return "服务器端文件准备完毕";
		if (retCode.equals("05")) return "文件已被更名或文件不存在,获取文件信息失败";
		if (retCode.equals("06")) return "其他错误";
		return retCode;
	}

	/**
	 * 从断点开始写文件，写失败返回-1，否则返回写入的长度
	 */
	public static synchronized int fileWrite(RandomAccessFile oSavedFile, byte[] b, long startPos, int nLen) {
		int n = -1;
		try {
			oSavedFile.seek(startPos);//定位断点
			log.info("startPos=" + startPos);
			log.info("nLen=" + nLen);
			log.info("bSize=" + b.length);
			oSavedFile.write(b, 0, nLen);
			n = nLen;
		} catch (IOException e) {
			log.error("写文件失败", e);
		}
		return n;
	}

	/**
	 * 上传文件
	 *
	 * @param content     文件内容
	 * @param fileName    文件名
	 * @param desDoc      要上传到的路径
	 * @param desFileName 目标文件
	 * @return 返回文件上传成功后或失败信息
	 * 返回码成功为"00"时,返回信息为上传后的文件名包括文件路径
	 * 否则为上传失败的提示信息
	 */
	public static Map<String, String> upLoadFile(byte[] content, String fileName, String desDoc, String desFileName) {

		Map<String, String> retMap = new HashMap<String, String>();
		retMap.put("retCode", "99");
		try {
			// 如果文件不存在或者文件为空
			if (content == null || content.length == 0) {
				retMap.put("retMsg", "文件不存在或文件为空！");
				return retMap;
			}

			if (desFileName == null) {
				desFileName = UUID.randomUUID().toString();
			}
			if (desFileName.lastIndexOf(".") == -1 && fileName.lastIndexOf(".") > 0) {
				desFileName += fileName.substring(fileName.lastIndexOf("."), fileName.length());
			}
			String desFilePath = desDoc + System.getProperty("file.separator") + desFileName;
			File uploadedFile = new File(desFilePath);
			FileCopyUtils.copy(content, uploadedFile);
			retMap.put("retCode", "00");
			retMap.put("retMsg", desFilePath);
		} catch (IOException e) {
			log.error("上传文件失败", e);
			retMap.put("retMsg", "上传文件失败");
		} catch (Exception ex) {
			log.error("上传文件失败", ex);
			retMap.put("retMsg", "上传文件失败");
		}
		return retMap;
	}

	public static String getFileType(String fileName) {
		if (fileName == null || fileName.lastIndexOf(".") == -1 || fileName.lastIndexOf(".") == fileName.length() - 1)
			return "";
		return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	}

	/**
	 * 获取Web工程的根目录
	 *
	 * @return
	 */
	public static String getBasePath() {
		try {
			String basePath = "";

			String path = System.getProperty("user.dir");
			File upload = new File(path, "resources/");
			if (!upload.exists()) {
				upload.mkdirs();
			}
			basePath = upload.getAbsolutePath();
			log.info("basePath=" + basePath);
			return basePath;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取WEB工程根目录出错    ", e);
			return null;
		}
	}

	public static void createNewFile(File sourcFile) throws IOException {
		if (!sourcFile.getParentFile().exists()) {
			sourcFile.getParentFile().mkdirs();
		}
		sourcFile.createNewFile();
	}

}
