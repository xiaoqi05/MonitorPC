package cn.edu.cuit.monitorpc.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MyFileUtils {
    public static String readFileContent(String filePath,
            String outputCharsetName) {

        File file = new File(filePath);

        Long fileLengthLong = file.length();
        byte[] byteContent = new byte[fileLengthLong.intValue()];
        try {
            FileInputStream inputStream = new FileInputStream(file);
            inputStream.read(byteContent);
            inputStream.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        String fileContent = "";
        try {
            fileContent = new String(byteContent, outputCharsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return fileContent;
    }
    // http://www.cnblogs.com/lovebread/archive/2009/11/23/1609122.html
    // http://docs.oracle.com/javase/1.5.0/docs/guide/intl/encoding.doc.html
    // http://www.cnblogs.com/jjtech/archive/2011/04/17/2019207.html
    // http://www.cnblogs.com/lovebread/archive/2009/11/23/1609122.html
    public static List<String> readFileByLines(String fileName) {
        List<String> lines = new ArrayList<String>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), "UTF-16LE"));

            String tempString = "";
            while ((tempString = reader.readLine()) != null) {
                // 从win7系统获取到的是GBK编码的String（别的系统未测试）
                // 但是会显示乱码
                // 所以以GBK编码提取出byte数组
                // 并且转换成UTF-8编码
                byte[] bytes = tempString.getBytes("GBK");
                tempString = new String(bytes, "UTF-8");
                lines.add(tempString);
               // System.out.println(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return lines;
    }

    /**
     * 复制文件或者目录,复制前后文件完全一样。
     * 
     * @param resFilePath
     *            源文件路径
     * @param distFolder
     *            目标文件夹
     * @IOException 当操作发生异常时抛出
     */
    public static void copyFile(String resFilePath, String distFolder)
            throws IOException {
        File resFile = new File(resFilePath);
        File distFile = new File(distFolder);
        if (resFile.isDirectory()) {
            FileUtils.copyDirectoryToDirectory(resFile, distFile);
        } else if (resFile.isFile()) {
            FileUtils.copyFileToDirectory(resFile, distFile, true);
        }
    }

    /**
     * 删除一个文件或者目录
     * 
     * @param targetPath
     *            文件或者目录路径
     * @IOException 当操作发生异常时抛出
     */
    public static void deleteFile(String targetPath) throws IOException {
        File targetFile = new File(targetPath);
        if (targetFile.isDirectory()) {
            FileUtils.deleteDirectory(targetFile);
        } else if (targetFile.isFile()) {
            targetFile.delete();
        }
    }

    /**
     * 移动文件或者目录,移动前后文件完全一样,如果目标文件夹不存在则创建。
     * 
     * @param resFilePath
     *            源文件路径
     * @param distFolder
     *            目标文件夹
     * @IOException 当操作发生异常时抛出
     */
    public static void moveFile(String resFilePath, String distFolder)
            throws IOException {
        File resFile = new File(resFilePath);
        File distFile = new File(distFolder);
        if (resFile.isDirectory()) {
            FileUtils.moveDirectoryToDirectory(resFile, distFile, true);
        } else if (resFile.isFile()) {
            FileUtils.moveFileToDirectory(resFile, distFile, true);
        }
    }

    /**
     * 重命名文件或文件夹
     * 
     * @param resFilePath
     *            源文件路径
     * @param newFileName
     *            重命名
     * @return 操作成功标识
     */
    public static boolean renameFile(String oldFileName, String newFileName) {
        File oldFile = new File(oldFileName);
        File newFile = new File(newFileName);
        return oldFile.renameTo(newFile);
    }

    /**
     * 读取文件或者目录的大小
     * 
     * @param distFilePath
     *            目标文件或者文件夹
     * @return 文件或者目录的大小，如果获取失败，则返回-1
     */
    public static long genFileSize(String distFilePath) {
        File distFile = new File(distFilePath);
        if (distFile.isFile()) {
            return distFile.length();
        } else if (distFile.isDirectory()) {
            return FileUtils.sizeOfDirectory(distFile);
        }
        return -1L;
    }

    /**
     * 判断一个文件是否存在
     * 
     * @param filePath
     *            文件路径
     * @return 存在返回true，否则返回false
     */
    public static boolean isExist(String filePath) {
        return new File(filePath).exists();
    }

    /**
     * 本地某个目录下的文件列表（不递归）
     * 
     * @param folder
     *            ftp上的某个目录
     * @param suffix
     *            文件的后缀名（比如.mov.xml)
     * @return 文件名称列表
     */
    public static String[] listFilebySuffix(String folder, String suffix) {
        IOFileFilter fileFilter1 = new SuffixFileFilter(suffix);
        IOFileFilter fileFilter2 = new NotFileFilter(
                DirectoryFileFilter.INSTANCE);
        FilenameFilter filenameFilter = new AndFileFilter(fileFilter1,
                fileFilter2);
        return new File(folder).list(filenameFilter);
    }

    /**
     * 将字符串写入指定文件(当指定的父路径中文件夹不存在时，会最大限度去创建，以保证保存成功！)
     * 
     * @param res
     *            原字符串
     * @param filePath
     *            文件路径
     * @return 成功标记
     */
    public static boolean string2File(String res, String filePath) {
        boolean flag = true;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            File distFile = new File(filePath);
            if (!distFile.getParentFile().exists())
                distFile.getParentFile().mkdirs();
            bufferedReader = new BufferedReader(new StringReader(res));
            bufferedWriter = new BufferedWriter(new FileWriter(distFile));
            char buf[] = new char[1024]; // 字符缓冲区
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }
    public static boolean string2File(String res, String filePath,
            boolean append) {
        boolean flag = true;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            File distFile = new File(filePath);
            if (!distFile.getParentFile().exists())
                distFile.getParentFile().mkdirs();
            bufferedReader = new BufferedReader(new StringReader(res));
            bufferedWriter = new BufferedWriter(new FileWriter(distFile,
                    append));
            char buf[] = new char[1024]; // 字符缓冲区
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean list2File(List<String> res, String filePath,
            boolean append) {
        StringBuilder sb = new StringBuilder();
        for (String item : res) {
            sb.append(item + "\n");
        }

        return MyFileUtils.string2File(sb.toString(), filePath, append);
    }
}
