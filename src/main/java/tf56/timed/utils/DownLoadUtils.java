package tf56.timed.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/***
 * @ClassName DownLoadUtils
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/8 16:58
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
public class DownLoadUtils {

    private static final Logger LOG = LoggerFactory.getLogger(DownLoadUtils.class);



    public static void openDownLoadPage(String name, String time){
        boolean isWin = SystemUtils.isWindows();
        String command = command(name, time, isWin);
        LOG.info("command:{}",command);
        String result;
        if (isWin) {
            result = CommandUtils.execWindows(command);
        } else {
            result = CommandUtils.execLinux(command);
        }
        LOG.info("执行结果{}", result);

    }

    private static String command(String name, String time,boolean isWin) {
        String command;
        if (isWin) {
            command = "rundll32 url.dll,FileProtocolHandler "
                    + path(name ,time);
        } else {
            command = "wget "
                    + "'" + path(name, time) + "'";
        }
        return command;
    }

    public static String path(String name, String time) {
        return "http://10.33.119.31/cgi/downlog?warname=" + name
                + "&logname=out.log&date=" + time;
    }


    private static void linuxOpenPage(String type, String downloadTime) {
        String command = "wget "
                + "'http://10.33.119.31/cgi/downlog?warname=" + type
                + "&logname=out.log&date=" + downloadTime + "'";
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
//            LOG.error("linux异常:",e);
//            e.printStackTrace();
        }

    }

    public static void downloadFile(String remoteUrl, String localPath) {
        LOG.info("远程文件url:[{}],本地保存地址:[{}]",remoteUrl,localPath);
        int nStartPos = 0;
        int nRead = 0;
        String[] urlName = remoteUrl.split("/");
        String uName = urlName[urlName.length - 1];
        try {
            URL url = new URL(remoteUrl);
            //打开连接
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            //获取文件长度
            long nEndPos = getFileSize(remoteUrl);
            if (nEndPos < -1) {
                LOG.info("远程文件不存在或无内容");
                return;
            }
            //将远程文件下载保存的路径
            RandomAccessFile oSavedFile= new RandomAccessFile(localPath+"/"+uName, "rw");  //"rw"读写
            httpConnection.setRequestProperty("User-Agent", "Internet Explorer");
            //告诉服务器，从nStartPos字节开始传
            String sProperty = "bytes = " + nStartPos + "-";
            httpConnection.setRequestProperty("RANGE", sProperty);
            //获取输入流
            InputStream input = httpConnection.getInputStream();
            byte[] b = new byte[1024];
            //读取网络文件,写入指定的文件中
            while ((nRead = input.read(b,0,1024)) > 0
                    && nStartPos < nEndPos) {
                oSavedFile.write(b,0,nRead);
                nStartPos += nRead;
            }
            httpConnection.disconnect();
            oSavedFile.close();
            LOG.info("下载完成:[{}]","IT`S OVER!");
        } catch (IOException e) {
            LOG.info("文件下载异常", e);
        }
    }
    /**
     * //  获得远程文件长度
     * @param remoteUrl	远程文件url
     * @return
     */
    private static long getFileSize(String remoteUrl) {
        int nFileLength = -1;
        try {
            URL url = new URL(remoteUrl);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestProperty("User-Agent", "Internet Explorer");

            int responseCode = httpConnection.getResponseCode();
            if (responseCode >= 400) {
                System.out.println("Error Code :" + responseCode);
                return -2;//-2 represent access is error
            }
            String sHeader;
            for (int i =1; ; i++) {
                sHeader = httpConnection.getHeaderFieldKey(i);
                if (sHeader != null) {
                    if (sHeader.equals("Content-Length")) {
                        nFileLength = Integer.parseInt(httpConnection.getHeaderField(sHeader));
                        break;
                    }
                } else
                    break;
            }
        } catch (IOException e) {
            LOG.info("获取长度异常", e);
        }
        LOG.info("文件长度:[{}]",nFileLength);
        return nFileLength;
    }
}
