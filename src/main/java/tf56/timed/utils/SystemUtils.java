package tf56.timed.utils;

import org.apache.commons.lang.StringUtils;

/***
 * @ClassName SystemUtils
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/7/25 14:47
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
public class SystemUtils {


    public static boolean isWindows() {
        return StringUtils.startsWithIgnoreCase(System.getProperty("os.name"), "win");
    }

    public static boolean isLinux() {
        return !isWindows();
    }
}
