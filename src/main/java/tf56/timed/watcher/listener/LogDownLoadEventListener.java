package tf56.timed.watcher.listener;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import tf56.timed.utils.CommandUtils;
import tf56.timed.utils.DateUtils;
import tf56.timed.watcher.LogDownLoadDTO;
import tf56.timed.watcher.event.LogDownLoadEvent;

import java.util.Date;


/***
 * @ClassName LogDownLoadEventListener
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/23 10:19
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
@Component
public class LogDownLoadEventListener implements ApplicationListener<LogDownLoadEvent>{

    @Async
    @Override
    public void onApplicationEvent(LogDownLoadEvent event) {
        if (StringUtils.startsWithIgnoreCase(System.getProperty("os.name"),"win")) {
            return;
        }
        LogDownLoadDTO dto = event.getDto();
        if (null == dto) {
            return;
        }
        String cmdCD = "cd "+ dto.getName();
        CommandUtils.execLinux(cmdCD);

        StringBuilder $cmdSb;

        String date = dto.getDate();
        if (StringUtils.equalsIgnoreCase(date, DateUtils.formatDate(new Date()))) {
            $cmdSb = new StringBuilder("grep ");
        } else {
            $cmdSb = new StringBuilder("zgrep ");
        }
        $cmdSb.append(" -5i ").append("").append(" out.log* ").append(" -> ");
        String fileName = dto.getDate() + ".txt";
        $cmdSb.append(fileName);
        CommandUtils.execLinux($cmdSb.toString());
    }
}
