package tf56.timed.timer;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import tf56.timed.utils.*;
import tf56.timed.watcher.LogDownLoadDTO;
import tf56.timed.watcher.event.LogDownLoadEvent;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/***
 * @ClassName LogDownLoadTimerTask
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/14 14:14
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
@Deprecated
public class LogDownLoadTimerTask extends TimerTask {

    private String name;

    private String savePath;

    private String date;

    private Timer timer;

    private List<String> ips;

    private ApplicationContext applicationContext;

    public LogDownLoadTimerTask(String name, String savePath, String date, Timer timer, List<String> ips,ApplicationContext applicationContext) {
        this.name = name;
        this.savePath = savePath;
        this.date = date;
        this.timer = timer;
        this.ips = ips;
        this.applicationContext = applicationContext;
    }

    @Override
    public void run() {
        for (String ip : ips) {
            String remoteUrl = buildUrl(ip);
            DownLoadUtils.downloadFile(remoteUrl, savePath);
        }
        timer.cancel();
//        applicationContext.publishEvent(new LogDownLoadEvent(this, new LogDownLoadDTO(name, date)));
    }


    /**
     * "http://10.33.119.31/logs_sc/20180509/ehuodiKA/out.log-20180509_10.33.35.62"
     * @param ip
     * @return
     */
    private String buildUrl(String ip) {
        return new StringBuilder("http://10.33.119.31/logs_sc/")
                .append(date).append("/").append(name).append("/out.log-").append(date).append("_").append(ip)
                .append(StringUtils.equalsIgnoreCase(date, DateUtils.formatDate(new Date(),DateUtils.YYYYMMDD))?"":".gz")
                .toString();
    }
}
