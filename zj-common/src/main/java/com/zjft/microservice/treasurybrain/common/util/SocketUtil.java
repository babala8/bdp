package com.zjft.microservice.treasurybrain.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.zip.ZipException;

/**
 * socket操作实用类
 * 
 * @author hpshen
 * @since 2007.04.10
 */

public class SocketUtil {

	private static Log log = LogFactory.getLog(SocketUtil.class);

	/**
	 * 从socket通道上读取数据，并将读到的数据解压缩，将返回码赋给
	 */
	public static byte[] readSocket(DataInputStream in)throws IOException, ZipException {

		if (in == null){
			throw new IllegalArgumentException("readSocket输入参数为空");
		}
		
		byte[] retData = new byte[1 * 1024];
		int retDataLength = 0;

		String zipFlag = "0";
		while (true) {
			
			byte[] headSection = new byte[8];
			int charsRead;

			charsRead = in.read(headSection, 0, 8);
			log.info("返回报文头："+new String(headSection));
			if (charsRead == -1) {
				log.error("从socket中没读到内容");
				return null;
			}

			int packetLength = 0;
			try {
				packetLength = Integer.parseInt(new String(headSection, 0, 4)) - 8;
			} catch (NumberFormatException e) {
				log.error("获取报文正文长度出错");
				return null;
			}

			//读取报文数据段
			if(retData.length < retDataLength + packetLength) {
				byte[] tmp = new byte[retData.length + packetLength];
				System.arraycopy(retData, 0, tmp, 0, retData.length);
				retData = tmp;
			}
				
			int readLength = 0;
			while (readLength < packetLength) {
				charsRead = in.read(retData, retDataLength, packetLength - readLength);
				if (charsRead == -1 || charsRead == 0) {
					log.error("从socket中没读到内容");
					return null;
				}
				readLength += charsRead;
				retDataLength += charsRead;
			}
			String endFlag = new String(headSection, 4, 1);
			zipFlag = new String(headSection, 5, 1);
			if (endFlag.equalsIgnoreCase("T")) {
				break;
			} else {
				continue;
			}
		}
		
		byte[] beforeUnzip = new byte[retDataLength];
		System.arraycopy(retData, 0, beforeUnzip, 0, retDataLength);
		
		byte[] byteUnzip = null;
		switch (Integer.valueOf(zipFlag)) {
		case 0:// 非压缩方式
			byteUnzip = beforeUnzip;
			break;
		case 1:// 1： LZARI压缩方式
			break;
		case 2:// 2： ZIP压缩方式
			byteUnzip = ZipUtil.unzip(beforeUnzip);
			break;
		case 3:// 3： GZIP压缩方式
			byteUnzip = ZipUtil.ungzip(beforeUnzip);
			break;
		default:
			break;
		}
		return byteUnzip;
	}
	
	
	/**
	 * 发送命令信息到RVS服务器或Remote客户端并接收其返回内容(采用多包传输的方式)
	 * @param sndBody 发送内容(采用XML报文格式)
	 * @param ip ip地址
	 * @param retBody 返回内容
	 * @param packetLen v端每包传输大小
	 * @param vzipType v端报文压缩方式
	 * @return true:发送成功 false:发送失败
	 */
	public static boolean sendCmdToRvcMutil(String sndBody, String ip, int port,StringBuffer retBody,int connTime,int soTime,int packetLen,int vzipType) {
		if (sndBody == null || ip == null || retBody == null) {
			throw new IllegalArgumentException("参数非法");
		}
		log.info("发送报文=["+sndBody+"]");
		byte[] byteSnd = sndBody.getBytes();
		byte[] byteZip = byteSnd;
		try {
			switch (vzipType) {
			case 0://非压缩方式
				byteZip = byteSnd;
				break;
			case 1://1： LZARI压缩方式
				break;
			case 2://2： ZIP压缩方式
				byteZip = ZipUtil.zip(byteSnd);
				break;
			case 3://3： GZIP压缩方式
				byteZip = ZipUtil.gzip(byteSnd);
				break;
			default:
				retBody.append("压缩方式错误");
				return false;
			}
		} catch (IOException e) {
			log.error("发送消息前压缩失败", e);
			retBody.append("发送消息前压缩失败");
			return false;
		}
		Socket s = null;
		DataInputStream r = null;
		OutputStream out = null;
		
		try {
			s = new Socket();
			InetSocketAddress isa=new InetSocketAddress(ip, port);
			s.connect(isa, connTime*1000);//设置连接超时
			s.setSoTimeout(soTime*1000);//设置超时时间1分钟,add by ssli 2008-06-23
			out = s.getOutputStream();
			
			r = new DataInputStream(s.getInputStream());
			
			if(!sendMessage(out, byteZip, packetLen, 0, retBody))
				return false;
			byte[] read = readSocket(r);
			log.info("返回报文："+new String(read));
			String rcvStr = (new String(read)).trim();
			retBody.append(rcvStr);
		} catch (IOException e) {
			retBody.append("IO异常");
			return false;
		} catch (Exception e) {
			retBody.append("异常");
			return false;
		} finally {
			try {
				if (r != null) 
					r.close();
				if (out != null) 
					out.close();
				if (s != null) 
					s.close();
			} catch (Exception e) {
				retBody.append("异常");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 发送报文
	 * @param sendBody 发送内容(采用XML报文格式)
	 * @param packetLen 每包传输大小
	 * @param vzipType 报文压缩方式
	 * @return true:发送成功 false:发送失败
	 */
	public static Boolean sendMessage(OutputStream out,byte[] sendBody,int packetLen,int vzipType,StringBuffer retMsg){
		byte[] byteZip = sendBody;
		try {
			switch (vzipType) {
			case 0:// 非压缩方式
				byteZip = sendBody;
				break;
			case 1:// 1： LZARI压缩方式
				break;
			case 2:// 2： ZIP压缩方式
				byteZip = ZipUtil.zip(sendBody);
				break;
			case 3:// 3： GZIP压缩方式
				byteZip = ZipUtil.gzip(sendBody);
				break;
			default:
				retMsg.append("压缩方式错误");
				return false;
			}
		} catch (IOException e) {
			retMsg.append("发送消息前压缩失败");
			return false;
		}
		// 组织发送报文
		StringBuffer head = new StringBuffer();
		int sendTimes = (byteZip.length / packetLen);// 发包次数
		if (byteZip.length % packetLen > 0) {
			sendTimes++;
		}
		try {
			int startLen=0;//截取压缩包开始字节数
			int endLen=0; //截取压缩包结束字节数
			int zipLen=0;//每次发送压缩包的字节数
			for (int i = 1; i <= sendTimes; i++) {
				if (i == sendTimes) {
					endLen = byteZip.length;
					zipLen = endLen - startLen;
					head.append(String.format("%04d", zipLen + 8)); // 数据长度
					head.append("T"); // 报文结束
				} else {
					zipLen = packetLen;
					endLen += zipLen;
					head.append(String.format("%04d", zipLen + 8)); // 数据长度
					head.append("N"); // 报文未结束
				}
				
				head.append(vzipType); // 压缩方式
				head.append("00"); // 保留位
				log.info("发送报文头head："+head);
				
				byte[] byteSend = new byte[8 + zipLen];
				
				System.arraycopy(head.toString().getBytes(), 0, byteSend, 0, 8);
				System.arraycopy(byteZip, startLen, byteSend, 8, zipLen);
				
				out.write(byteSend);
				out.flush();
				startLen = endLen;
				head.delete(0, head.capacity());//清除StringBuffer中的内容
			}
			return true;
		} catch (Exception e) {
			log.error("发送报文失败", e);
			retMsg.append("发送报文失败");
			return false;
		}
	}
}
