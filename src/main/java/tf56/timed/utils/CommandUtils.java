package tf56.timed.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/***
 * @ClassName CommandUtils
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/18 10:55
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
public class CommandUtils {

    /**
     * linux 执行命令并返回结果值
     * @param cmd
     * @return
     */
    public static String execLinux(String cmd) {
        try {
            String[] cmdA = { "/bin/sh", "-c", cmd };
            Process process = Runtime.getRuntime().exec(cmdA);
            LineNumberReader br = new LineNumberReader(new InputStreamReader(
                    process.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String execWindows(String cmd) {
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            LineNumberReader br = new LineNumberReader(new InputStreamReader(
                    process.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
