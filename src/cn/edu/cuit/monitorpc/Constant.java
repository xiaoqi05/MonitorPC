package cn.edu.cuit.monitorpc;

public class Constant {
	public static final int REQUEST_TIMEOUT = 30 * 1000; // 请求超时30秒，初期服务器不稳定。。设大点。
	public static final int SO_TIMEOUT = 30 * 1000; // 连接超时30秒，初期服务器不稳定。。设大点。
	// 222.18.158.206
	public static String baseURL = "http://222.18.158.206:8080/PcServlet";
	public static String logURL = baseURL + "/Log";
	public static String addotherapinfo = baseURL + "/OtherAp";
	public static String addpinginfo = baseURL + "/Ping";
	public static String addspeedinfo = baseURL + "/Speed";
	public static String addbasicinfo = baseURL + "/Basic";
	public static String wificlientcountinfo = baseURL + "/servlet/Tclients";
	public static String clientdetailsinfo = baseURL + "/servlet/TclientDetails";

}
