package cn.edu.cuit.monitorpc.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * ping指定地址，返回延迟及丢包率
 * 
 */

public class UserPing {
	// 指定ping的地址及ping的次数
	public static String pingDes(String address, int count) {
		String res = "";
		Process p;
		try {
			String lost = "";
			String delay = "";
		//	System.out.println("ping -c " + count + " " + address);
			p = Runtime.getRuntime().exec("ping -n " + count + " " + address);
			BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream(), "GBK"));
			String str = new String();
			while ((str = buf.readLine()) != null) {
			    res+=str+"\n";
//				if (str.contains("packet loss")) {
//					int i = str.indexOf("received");
//					int j = str.indexOf("%");
//					System.out.println("丢包率:" + str.substring(i + 10, j + 1));
//					lost = str.substring(i + 10, j + 1);
//					res = res + "lost: " + lost + " ";
//				}
//				if (str.contains("avg")) {
//					int i = str.indexOf("/", 20);
//					int j = str.indexOf(".", i);
//					System.out.println("延迟:" + str.substring(i + 1, j));
//					delay = str.substring(i + 1, j);
//					delay = delay + "ms";
//					res = res + "delay: " + delay;
//				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
}
