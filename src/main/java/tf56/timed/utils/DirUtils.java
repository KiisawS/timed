package tf56.timed.utils;

import java.io.File;

/***
 * @ClassName DirUtils
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/8 17:14
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
public class DirUtils {

    public static void getOrCreateLocalPath(String savePath){
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdir();
        }
    }
}
