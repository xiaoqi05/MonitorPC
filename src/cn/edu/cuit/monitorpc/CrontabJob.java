package cn.edu.cuit.monitorpc;

import cn.edu.cuit.monitorpc.utils.*;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.util.*;

public class CrontabJob implements Job {
	public static final String LOCAL_MAC_ADDRESS = "5C:63:BF:0A:8F:63";//38:59:F9:E1:AF:52
	/**********************************************************/
	// Constants
	// date's format to generate output file
	public static final String DATE_FORMAT = "yyyyMMddHHmmss";
	// web site to ping
	// 实际应设为同一AP下的主机IP
	public static final String PING_SITE = "www.baidu.com";
	// ping times
	public static final int PING_TIMES = 4;

	// FTP account info
	public static final String USERNAME = "xiaoqi"; // 用户名
	public static final String PASSWORD = "xiaoqi";  // 密码
	public static final String SERVER_IP = "222.18.158.206"; // IP
	public static final int PORT = 21;
	// 下载文件名
	public static final String DOWNLOAD_FILE_NAME = "/PcUpload/downloadfile.bin";
	public static final String UPLOAD_FILE_NAME = "uploadfile.bin";
	public static final String SERVER_UPLOAD_DIRECTORY = "/PcUpload";
	public static final int UPLOAD_FILE_SIZE = 1024 * 1024;// 1MB

	public static final String CONFIG_FILE_PATH = "H:\\exetimes.txt";
	/**********************************************************/
	// Variables
	public static int exeTimes;
	// Paths
	// program's root path
	public String programRootPath;
	// WirelessNetView's directory
	public static final String WIRELESS_NET_VIEW_PATH = File.separatorChar
	        + "WirelessNetView";
	// exe's full path
	public String exeFullPath;
	// current output file name
	public String outputFileName;

	// 上传文件名
	public String uploadFileFullPath;
	public static String activityName;

	/**********************************************************/

	public void initVars() {
		// 读取该文件 获取上次执行了多少次
		String content = MyFileUtils.readFileContent(CONFIG_FILE_PATH, "GBK");
		exeTimes = Integer.valueOf(content.trim());

		activityName = "com.example.testam/.MainActivity";
		// programRootPath = Utils.getAppPath(CrontabJob.class).substring(1)
		// .replace('/', File.separatorChar);
		programRootPath = "H:\\WirelessNetView";
		exeFullPath =  "H:\\WirelessNetView";
		outputFileName = exeFullPath + File.separatorChar + "output.txt";
		uploadFileFullPath = programRootPath + File.separator
		        + UPLOAD_FILE_NAME;

		if (!new File(uploadFileFullPath).exists()) {
			try {
				long vstart = System.currentTimeMillis();
				RandomAccessFile raf = new RandomAccessFile(
				        uploadFileFullPath, "rw");
				raf.setLength(UPLOAD_FILE_SIZE);
				raf.close();
				System.out.println("Create upload file consume "
				        + (System.currentTimeMillis() - vstart) + "ms.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		long jobStartTime = System.currentTimeMillis();

		exeTimes++;
		MyFileUtils.string2File(String.valueOf(exeTimes), CONFIG_FILE_PATH);

		System.out.println("execute times: " + exeTimes);
		System.out.println("Time: "
		        + DateFormat.getDateTimeInstance().format(new Date()));

		initVars();

		// start android activity
		// com.example.testam.MainActivity
		// adb shell am start -n com.example.testam/.MainActivity
		// Utils.runShell(new String[] {
		// "cmd",
		// "/c",
		// "adb shell am start -n " + activityName
		// }, new File("D:"), "GBK");
		Utils.runShell(new String[] {
		        "cmd",
		        "/c",
		        "WirelessNetView.exe /stext " + outputFileName
		}, new File(exeFullPath), "GBK");

		List<String> fileContent = MyFileUtils.readFileByLines(outputFileName);

		// AP around
		List<APInfo> apInfoObjs = null;//generateAPInfoList(fileContent);

		// //////////////////////////////////////////////////////////////

		System.out.println("-----");

		String pingResult = pingTask();
		// packet Loss Rate
		String packetLossRate = Regex.matchToString(Regex
		        .matchToString(pingResult, "(\\d{1,3}% 丢失)"), "\\d{1,3}");
		System.out.println("丢包率: " + packetLossRate + "%");
		// average delay
		String avgDelay = Regex.matchToString(Regex.matchToString(pingResult,
		        "平均 = \\d{1,4}ms"), "\\d{1,4}");
		System.out.println("平均延迟: " + avgDelay + "ms");
		System.out.println("-----");
		// upload speed
		double uploadSpeed = uploadTask();
		System.out.println("Upload Speed: " + uploadSpeed + "kB");
		System.out.println("-----");
		// download speed
		double downloadSpeed = downloadTask();
		System.out.println("Download Speed: " + downloadSpeed + "kB");

		// /////////////////////////////////////////////////////////////////
		// submit data to server
		ServletCall sc = new ServletCall();
		String out = "";
		/********************************************/
		// 发送上传下载速度
		out = sc.servletAccess(Constant.logURL,
		        "speed",
		        String.valueOf(exeTimes));
		sc.servletupload(Constant.addspeedinfo,
		     //   out,
		        String.valueOf(exeTimes),
		        String.valueOf(downloadSpeed),
		        String.valueOf(uploadSpeed));
		/********************************************/
		// 发送每个AP的信息
		for (APInfo item : apInfoObjs) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String url = "";
			if (item.isConnected()) {
				//System.out.println(item.getMacAddress());
				out = sc.servletAccess(Constant.logURL,
				        "basic",
				        String.valueOf(exeTimes));
				url = Constant.addbasicinfo;
			} else {
				out = sc.servletAccess(Constant.logURL,
				        "otherap",
				        String.valueOf(exeTimes));
				url = Constant.addotherapinfo;
			}

			sc.servletWifi(url,

        //    out,
        String.valueOf(exeTimes),
                item.getSsid(),
                String.valueOf(item.getRssi()),
                String.valueOf(item.getMaximumSpeed()),
                String.valueOf(item.getChannelNumber()));
    }
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/********************************************/
		// 发送ping的信息
		out = sc.servletAccess(Constant.logURL,
		        "ping",
		        String.valueOf(exeTimes));
		sc.servletping(Constant.addpinginfo,
				//  out,
		        String.valueOf(exeTimes),
		        packetLossRate + "%",
		        avgDelay + "ms");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/********************************************/
		// 发送连接到当前路由器的Wifi Client的信息
		ArrayList<WifiClientDetail> list = SSH.getWifiDetails();
		out = sc.servletAccess(Constant.logURL,
		        "tclient",
		        String.valueOf(exeTimes));
		sc.servletClientCount(Constant.wificlientcountinfo,
				//   out,
		        String.valueOf(exeTimes),
		        String.valueOf(list.size()));
		/********************************************/
		// 发送连接到当前路由器的Wifi Client的信息
		for (WifiClientDetail item : list) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out = sc.servletAccess(Constant.logURL,
			        "tclientdetail",
			        String.valueOf(exeTimes));
			//System.out.println(item.getMacAdd());
			sc.servletClientDetail(Constant.clientdetailsinfo,
					//      out,
			        String.valueOf(exeTimes),
			        item.getMacAdd(),
			        String.valueOf(item.getSigals()),
			      //  String.valueOf(item.getNoise()),
			        String.valueOf(item.getTx()),
			        String.valueOf(item.getRx()),
			        String.valueOf(item.getIsself())
			        
					);

		}
		/********************************************/
		long jobFinishTime = System.currentTimeMillis();
		System.out.println("Job consume "
		        + ((jobFinishTime - jobStartTime) / 1000) + "s");
		System.out.println("=============================");
	}

	public List<APInfo> generateAPInfoList(List<String> fileContent) {
		List<APInfo> apInfoObjs = new ArrayList<APInfo>();
		Map<String, String> obj = new HashMap<String, String>();
		for (String item : fileContent) {
			if (obj.size() == 19) {
				apInfoObjs.add(APInfo.generateAPInfo(obj));
				obj.clear();
			}
			if (item.trim().endsWith("=") || item.trim().length() == 0) {
				continue;
			}
			String[] split = item.split(":");
			String key = split[0].trim();
			String value = split[1].trim();
			// System.out.println("key: " + key + "\tvalue: " + value);
			obj.put(key, value);
		}
		return apInfoObjs;
	}

	public String pingTask() {
		return UserPing.pingDes(PING_SITE, PING_TIMES);

	}

	public double downloadTask() {
		FTPSpeed ftpSpeed = new FTPSpeed(USERNAME, PASSWORD, SERVER_IP, PORT);
		ftpSpeed.connect();

		long startTime = System.currentTimeMillis();
		boolean isDownloadSuccess = ftpSpeed.download(programRootPath,
		        DOWNLOAD_FILE_NAME);

		// consume time in second
		double consumeTime = (System.currentTimeMillis() - startTime) / 1000.0;
		System.out.println("download consume time: " + consumeTime);
		// upload speed (now the file size is 1024KB)
		double downloadSpeed = 0;
		try {
			// size of downloading file(byte)
			long fileSize = ftpSpeed.getFileSize(DOWNLOAD_FILE_NAME);
			System.out.println("downloaded file's size: " + (fileSize / 1024)
			        + "kB");
			downloadSpeed = fileSize / 1024 / consumeTime;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Utils.doubleSetScale(downloadSpeed, 2);
	}

	public double uploadTask() {
		// 本APP的files文件夹下，后面可以考虑改成存到sdcard中
		FTPSpeed ftpSpeed = new FTPSpeed(USERNAME, PASSWORD, SERVER_IP, PORT);
		ftpSpeed.connect();

		boolean isUploadSuccess = false;
		// calculate upload rate (now the file size is 1024KB)
		double uploadSpeed = 0;
		// calculate consume time in second
		double consumeTime = 0;
		try {
			File uploadFile = new File(uploadFileFullPath);
			long startTime = System.currentTimeMillis();
			// starting upload
			isUploadSuccess = ftpSpeed.upload(uploadFile,
			        SERVER_UPLOAD_DIRECTORY);

			consumeTime = (System.currentTimeMillis() - startTime) / 1000.0;
			System.out.println("upload consume time: " + consumeTime);
			System.out.println("uploaded file's size: "
			        + (UPLOAD_FILE_SIZE / 1024) + "kB");
			uploadSpeed = UPLOAD_FILE_SIZE / 1024 / consumeTime;

		} catch (IOException e) {
			e.printStackTrace();
		}
		// return upload speed in kB/s
		return Utils.doubleSetScale(uploadSpeed, 2);
	}
}
