package cn.edu.cuit.monitorpc.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * 
 * 调用Servlet的工具类
 * 
 */
public class ServletCall {
	// 指定访问地址
	// type speed basic otherap
	public String servletAccess(String servletURL, String type, String testId) {// servletURL
		                                                                        // 传
		// baseURL
		String output = "";
		try {
			URL url = new URL(servletURL);
			// 后面这段参数准备得专门写一个方法，以适应不同的参数调用
			String str1 = "type"; // 测试类型
			String str0 = "testId";
			// String str2 = "speed"; //速度测试
			String para = URLEncoder.encode(str1, "UTF-8") + "="
			        + URLEncoder.encode(type, "UTF-8") + "&"
			        + URLEncoder.encode(str0, "UTF-8") + "="
			        + URLEncoder.encode(testId, "UTF-8");
			// System.out.println("para++++" + para);
			URLConnection uc = url.openConnection();
			uc.setDoOutput(true);
			uc.setDoInput(true);
			uc.setUseCaches(false);
			uc.setRequestProperty("Content-type",
			        "application/x-www-form-urlencoded");
			OutputStreamWriter outputLine = new OutputStreamWriter(
			        uc.getOutputStream());
			outputLine.write(para);
			outputLine.flush();
			BufferedReader streamReader = new BufferedReader(
			        new InputStreamReader(uc.getInputStream()));
			String line;
			while ((line = streamReader.readLine()) != null) {
				output = output + line;
			}
			outputLine.close();
			streamReader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}

	/**
	 * 
	 * 
	 * @param uploadurl
	 *            上传路径
	 * @param downlink
	 *            下载速度
	 * @param uplink
	 *            上传速度
	 * @return 返回状态
	 */

	public String servletupload(String uploadurl,  String testId,//String seqno,
	        String downlink, String uplink) {
		String output = "";
		String str = "seq";

		String str0 = "testId";
		String str1 = "down"; // 测试类型
		String str2 = "up";
		try {
			URL url = new URL(uploadurl);
//			URLEncoder.encode(str, "UTF-8") + "="
//	        + URLEncoder.encode(seqno, "UTF-8") + "&"+
			String para = 
			         URLEncoder.encode(str0, "UTF-8") + "="
			        + URLEncoder.encode(testId, "UTF-8") + "&"
			        + URLEncoder.encode(str1, "UTF-8") + "="
			        + URLEncoder.encode(downlink, "UTF-8") + "&"
			        + URLEncoder.encode(str2, "UTF-8") + "="
			        + URLEncoder.encode(uplink, "UTF-8");
			// System.out.println("para++++" + para);
			URLConnection uc = url.openConnection();
			uc.setDoOutput(true);
			uc.setDoInput(true);
			uc.setUseCaches(false);
			uc.setRequestProperty("Content-type",
			        "application/x-www-form-urlencoded");
			OutputStreamWriter outputLine = new OutputStreamWriter(
			        uc.getOutputStream());
			outputLine.write(para);
			outputLine.flush();
			BufferedReader streamReader = new BufferedReader(
			        new InputStreamReader(uc.getInputStream()));
			String line;
			while ((line = streamReader.readLine()) != null) {
				output = output + line;
			}
			outputLine.close();
			streamReader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}

	public String servletWifi(String WifiInfourl,  String testId,//String seq,
	        String ssid, String rssi, String power, String channel) {
		String output = "";
		String str = "seq";

		String str0 = "testId";
		String str1 = "ssid"; // 测试类型
		String str2 = "rssi";
		String str3 = "power";
		String str4 = "channel";
		try {
			URL url = new URL(WifiInfourl);
//			URLEncoder.encode(str, "UTF-8") + "="
//	        + URLEncoder.encode(seq, "UTF-8") + "&"
//	        +
			String para =  URLEncoder.encode(str0, "UTF-8") + "="
			        + URLEncoder.encode(testId, "UTF-8") + "&"
			        + URLEncoder.encode(str1, "UTF-8") + "="
			        + URLEncoder.encode(ssid, "UTF-8") + "&"
			        + URLEncoder.encode(str2, "UTF-8") + "="
			        + URLEncoder.encode(rssi, "UTF-8") + "&"
			        + URLEncoder.encode(str3, "UTF-8") + "="
			        + URLEncoder.encode(power, "UTF-8") + "&"
			        + URLEncoder.encode(str4, "UTF-8") + "="
			        + URLEncoder.encode(channel, "UTF-8");
			// System.out.println("para++++" + para);
			URLConnection uc = url.openConnection();
			uc.setDoOutput(true);
			uc.setDoInput(true);
			uc.setUseCaches(false);
			uc.setRequestProperty("Content-type",
			        "application/x-www-form-urlencoded");
			OutputStreamWriter outputLine = new OutputStreamWriter(
			        uc.getOutputStream());
			outputLine.write(para);
			outputLine.flush();
			BufferedReader streamReader = new BufferedReader(
			        new InputStreamReader(uc.getInputStream()));
			String line;
			while ((line = streamReader.readLine()) != null) {
				output = output + line;
			}
			outputLine.close();
			streamReader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}

	public String servletping(String pinginfourl, String testId,//String seqno, 
	        String lost, String delay) {
		String output = "";
		String str = "seq";
		String str0 = "testId";
		String str1 = "loss"; // 测试类型
		String str2 = "delay";
		try {
			URL url = new URL(pinginfourl);
//			URLEncoder.encode(str, "UTF-8") + "="
//	        + URLEncoder.encode(seqno, "UTF-8") + "&"
//	        +
			String para =  URLEncoder.encode(str0, "UTF-8") + "="
			        + URLEncoder.encode(testId, "UTF-8") + "&"
			        + URLEncoder.encode(str1, "UTF-8") + "="
			        + URLEncoder.encode(lost, "UTF-8") + "&"
			        + URLEncoder.encode(str2, "UTF-8") + "="
			        + URLEncoder.encode(delay, "UTF-8");
			// System.out.println("para++++" + para);
			URLConnection uc = url.openConnection();
			uc.setDoOutput(true);
			uc.setDoInput(true);
			uc.setUseCaches(false);
			uc.setRequestProperty("Content-type",
			        "application/x-www-form-urlencoded");
			OutputStreamWriter outputLine = new OutputStreamWriter(
			        uc.getOutputStream());
			outputLine.write(para);
			outputLine.flush();
			BufferedReader streamReader = new BufferedReader(
			        new InputStreamReader(uc.getInputStream()));
			String line;
			while ((line = streamReader.readLine()) != null) {
				output = output + line;
			}
			outputLine.close();
			streamReader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}

	public String servletClientCount(String clientCountUrl,//String seqno,
	        String testId, String clientCount) {
		String output = "";
		String str = "seq";
		String str0 = "testId";
		String str1 = "clientcount"; // 测试类型
		try {
			URL url = new URL(clientCountUrl);
//			URLEncoder.encode(str, "UTF-8") + "="
//	        + URLEncoder.encode(seqno, "UTF-8") + "&"
//	        + 
			String para = URLEncoder.encode(str0, "UTF-8") + "="
			        + URLEncoder.encode(testId, "UTF-8") + "&"
			        + URLEncoder.encode(str1, "UTF-8") + "="
			        + URLEncoder.encode(clientCount, "UTF-8");
			URLConnection uc = url.openConnection();
			uc.setDoOutput(true);
			uc.setDoInput(true);
			uc.setUseCaches(false);
			uc.setRequestProperty("Content-type",
			        "application/x-www-form-urlencoded");
			OutputStreamWriter outputLine = new OutputStreamWriter(
			        uc.getOutputStream());
			outputLine.write(para);
			outputLine.flush();
			BufferedReader streamReader = new BufferedReader(
			        new InputStreamReader(uc.getInputStream()));
			String line;
			while ((line = streamReader.readLine()) != null) {
				output = output + line;
			}
			outputLine.close();
			streamReader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}

	public String servletClientDetail(String clientDetailUrl, //String seqno,
	        String testId, String mac, String signal, String tx,//String noise, 
	        String rx, String isself) {
		String output = "";
		String str = "seq";
		String str0 = "testId";
		String str1 = "mac"; // 测试类型
		String str2 = "sigals"; // 测试类型
		String str3 = "noise"; // 测试类型
		String str4 = "tx"; // 测试类型
		String str5 = "rx"; // 测试类型
		String str6 = "isself"; // 测试类型

		try {
			URL url = new URL(clientDetailUrl);
//			URLEncoder.encode(str, "UTF-8") + "="
//	        + URLEncoder.encode(seqno, "UTF-8") + "&"
//	        + 
			
//			URLEncoder.encode(str3, "UTF-8") + "="
//	        + URLEncoder.encode(noise, "UTF-8") + "&"
//	        +
			String para = URLEncoder.encode(str0, "UTF-8") + "="
			        + URLEncoder.encode(testId, "UTF-8") + "&"
			        + URLEncoder.encode(str1, "UTF-8") + "="
			        + URLEncoder.encode(mac, "UTF-8") + "&"
			        + URLEncoder.encode(str2, "UTF-8") + "="
			        + URLEncoder.encode(signal, "UTF-8") + "&"
			        +  URLEncoder.encode(str4, "UTF-8") + "="
			        + URLEncoder.encode(tx, "UTF-8") + "&"
			        + URLEncoder.encode(str5, "UTF-8") + "="
			        + URLEncoder.encode(rx, "UTF-8") + "&"
			        + URLEncoder.encode(str6, "UTF-8") + "="
			        + URLEncoder.encode(isself, "UTF-8");
			URLConnection uc = url.openConnection();
			uc.setDoOutput(true);
			uc.setDoInput(true);
			uc.setUseCaches(false);
			uc.setRequestProperty("Content-type",
			        "application/x-www-form-urlencoded");
			OutputStreamWriter outputLine = new OutputStreamWriter(
			        uc.getOutputStream());
			outputLine.write(para);
			outputLine.flush();
			BufferedReader streamReader = new BufferedReader(
			        new InputStreamReader(uc.getInputStream()));
			String line;
			while ((line = streamReader.readLine()) != null) {
				output = output + line;
			}
			outputLine.close();
			streamReader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}
}
