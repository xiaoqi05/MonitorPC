package cn.edu.cuit.monitorpc.utils;

import cn.edu.cuit.monitorpc.CrontabJob;
import cn.edu.cuit.monitorpc.WifiClientDetail;
import com.jcraft.jsch.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * 
 * 类似ssh执行命令的小工具
 * 
 * @author walker
 * 
 */
public class SSH {
	private String userName;
	private String password;
	private String host;

	public SSH(String host, String userName, String password) {
		this.host = host;
		this.userName = userName;
		this.password = password;
	}

	private Session getSession() {
		JSch jsch = new JSch();
		Session session = null;

		try {
			session = jsch.getSession(userName, host);
			session.setPassword(password);

			Properties props = new Properties();
			props.put("StrictHostKeyChecking", "no");
			session.setConfig(props);
			session.connect();
		} catch (JSchException e) {
			e.printStackTrace();
		}
		return session;
	}

	public String exec(String[] cmds) {
		StringBuffer cmdBuffer = new StringBuffer();

		for (int i = 0; i < cmds.length; i++) {
			if (i != 3) {
				cmdBuffer.append(" ");
			}
			cmdBuffer.append(cmds[i]);
		}
		return exec(cmdBuffer.toString());
	}

	// private static final int BYTE_LENGTH = 512;

	public String exec(String cmd) {
		Session session = getSession();
		Channel channel = null;
		InputStream is = null;
		StringWriter out = new StringWriter();
		BufferedReader in = null;
		try {
			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(cmd);
			((ChannelExec) channel).setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err); // 获取执行错误的信息
			is = channel.getInputStream();
			in = new BufferedReader(new InputStreamReader(is));
			channel.connect();
			String line;
			while ((line = in.readLine()) != null || !channel.isClosed()) {
				if (line != null) {
					out.write(line + '\n');
					out.flush();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			if (is != null) {
				try {
					in.close();
					is.close();
				} catch (IOException e) {
				}
			}
			// 关闭连接
			channel.disconnect();
			session.disconnect();
		}

		return out.toString();
	}

	private static String getWifiClientDetailFromWR() {
		// 192.168.1.1 root qwerty123 iw dev wlan0 station dump |grep Station
		// |wc -l
		String ipAdd = "192.168.1.1";
		String username = "root";
		String password = "qwerty123";
		String cmd = "iw dev wlan0 station dump";
		SSH ssh = new SSH(ipAdd, username, password);
		return ssh.exec(cmd);
	}

	private static ArrayList<WifiClientDetail> generateWifiClientDetail(
	        String dataFromWR) {
		WifiClientDetail aClient = new WifiClientDetail();
		ArrayList<WifiClientDetail> clients = new ArrayList<WifiClientDetail>();
		String[] lines = dataFromWR.split("\n");
		for (String item : lines) {
			String myline = item.trim();
			aClient.setNoise(-1);
			if (myline.startsWith("Station")) {
				String macAdd = Regex.matchToString(myline,
				        "([0-9a-f]{2}:){5}[0-9a-f]{2}");
				aClient.setMacAdd(macAdd);
				if (macAdd.equalsIgnoreCase(CrontabJob.LOCAL_MAC_ADDRESS)) {
					aClient.setIsself(1);
				} else
					aClient.setIsself(0);
			}
			if (myline.startsWith("signal:")) {
				String signal = Regex.matchToString(myline, "-\\d+(?=\\s\\[)");

				aClient.setSigals(Integer.parseInt(signal));
			}
			if (myline.startsWith("tx bitrate:")) {
				String txRate = Regex.matchToString(myline, "\\d+\\.\\d+");
				aClient.setTx(Double.parseDouble(txRate));
			}
			if (myline.startsWith("rx bitrate:")) {
				String rxRate = Regex.matchToString(myline, "\\d+\\.\\d+");
				aClient.setRx(Double.parseDouble(rxRate));

				clients.add(aClient);
				aClient = new WifiClientDetail();
			}

		}

		return clients;
	}

	public static ArrayList<WifiClientDetail> getWifiDetails() {
		String allWRInfo = getWifiClientDetailFromWR();
		// System.out.println(allWRInfo);
		ArrayList<WifiClientDetail> list = generateWifiClientDetail(allWRInfo);

//		for (WifiClientDetail item : list) {
//			System.out.println(item.getMacAdd());
//			System.out.println(item.getSigals());
//			System.out.println(item.getTx());
//			System.out.println(item.getTx());
//			System.out.println("====");
//
//		}
//		System.out.println(list.size());

		return list;
	}

}
