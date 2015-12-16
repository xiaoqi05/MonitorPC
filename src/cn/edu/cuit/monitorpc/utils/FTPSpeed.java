package cn.edu.cuit.monitorpc.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * 
 * ftp工具类
 * 
 */

public class FTPSpeed {
    // constants

    public static final int DATA_TIMEOUT = 20000;
    public static final int CONNECT_TIMEOUT = 20000;

    // instance variable
    FTPClient ftpClient = new FTPClient(); // 这个类在commons-net-3.3.jar中
    boolean status = false; // 连接状态

    public String username; // 用户名
    public String password;  // 密码
    public String serverIp; // IP
    public int port;

    public FTPSpeed(String username, String password, String serverIp, int port) {
        this.username = username;
        this.password = password;
        this.serverIp = serverIp;
        this.port = port;
    }

    public boolean connect() {

        try {
            System.out.println("trying to connect to ftp server.");
            ftpClient.connect(serverIp, port);

            // int reply = ftpClient.getReply();
            // if (!FTPReply.isPositiveCompletion(reply)) {
            // ftpClient.disconnect();
            // status = false;
            // return status;
            // }
            ftpClient.login(username, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setDataTimeout(DATA_TIMEOUT);
            ftpClient.setConnectTimeout(CONNECT_TIMEOUT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        status = true;
        return status;
    }

    public boolean disconnect() throws IOException {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                return ftpClient.logout();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            } finally {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        return true;
    }

    public long getFileSize(String filePath) throws IOException {
        FTPFile[] files = ftpClient.listFiles(filePath);
        if (files.length == 1 && files[0].isFile()) {
            return files[0].getSize();
        }
        return -1;
    }

    // 下载
    public boolean download(String localPath, String remoteFile) {
        boolean success = false;
        if (status == true) {
            try {
                String remoteFileName = remoteFile.substring(remoteFile
                        .lastIndexOf("/") + 1, remoteFile.length());
                File localFile = new File(localPath + File.separatorChar
                        + remoteFileName);
                OutputStream outputStream1 = new BufferedOutputStream(
                        new FileOutputStream(localFile));
                success = ftpClient.retrieveFile(remoteFile, outputStream1);
                outputStream1.close();
                if (success) {
                    System.out
                            .println("File has been downloaded successfully.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    /**
     * @author
     * @class ItemFtp
     * @title upload
     * @Description :
     * @time 2013 2013-11-27
     * @return void
     * @exception :(Error note)
     * @param file
     *            上传的本地文件或文件夹
     * @param path
     *            上传的文件的远程FTP服务器路径
     * @throws IOException
     * @throws Exception
     */
    public boolean upload(File file, String path) throws IOException {
        boolean success = false;
        if (status != true) {
            return success;
        }
        if (file.isDirectory()) {
            ftpClient.makeDirectory(file.getName());
            ftpClient.changeWorkingDirectory(file.getName());
            String[] files = file.list();
            for (int i = 0; i < files.length; i++) {
                File file1 = new File(file.getPath() + File.separator
                        + files[i]);
                if (file1.isDirectory()) {
                    upload(file1, path);
                    ftpClient.changeToParentDirectory();
                } else {
                    File file2 = new File(file.getPath() + File.separator
                            + files[i]);
                    FileInputStream input = new FileInputStream(file2);
                    success = ftpClient.storeFile(file2.getName(), input);
                    input.close();
                }
            }
        } else {
            File file2 = new File(file.getPath());

            InputStream input = new FileInputStream(file2);

            ftpClient.makeDirectory(path);
            ftpClient.changeWorkingDirectory(path);

            success = ftpClient.storeFile(file2.getName(), input);

            input.close();  // 关闭输入流
            ftpClient.logout();  // 退出连接
        }
        if (success) {
            System.out.println("File has been uploaded successfully.");
        }
        return success;
    }

}
