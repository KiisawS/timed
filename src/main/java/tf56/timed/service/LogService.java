package tf56.timed.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tf56.timed.config.FileConfig;
import tf56.timed.remote.tops.TopsClient;
import tf56.timed.remote.tops.bean.ContentReq;
import tf56.timed.remote.tops.bean.ContentResp;
import tf56.timed.remote.tops.bean.TopsResp;
import tf56.timed.utils.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/***
 * @ClassName LogService
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/8 16:00
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LogService {
    private static final Logger LOG = LoggerFactory.getLogger(LogService.class);
    private final TopsClient topsClient;
    private final FileConfig fileConfig;
    private final ApplicationContext applicationContext;

    @Value("${dueFile.date}")
    public String dueDate;

    @Autowired
    private ScheduledExecutorService scheduledExecutorService;

    private List<ContentResp> getContent(String name) {
        ContentReq req = new ContentReq();
        req.setData(name);
        req.setSinglePageSize(20);
        req.setType("Server2");
        TopsResp topsResp = topsClient.getAllContent(req);
        if (topsResp.isSuccess()) {
            return topsResp.getData().getItems();
        }
        return Lists.newArrayList();
    }

    public List<String> getIps(String name) {
        List<ContentResp> contentRespList = getContent(name);
        List<String> ips = Lists.newArrayListWithCapacity(4);
        for (ContentResp contentResp : contentRespList) {
            String source = contentResp.getSource();
            if (!source.contains("生产")) {
                continue;
            }
            int location = source.indexOf("ip地址");
            int start = source.indexOf(":",location);
            int end = source.indexOf(",", location);
            String ip = source.substring(start+1,end).trim();
            ips.add(ip);
        }
        return ips;
    }

    private Map<String, List<String>> buildServers() {
        List<String> names = fileConfig.getNameList();
        Map<String, List<String>> maps = Maps.newHashMap();
        for (String name : names) {
            List<String> ips = getIps(name);
            if (ips.isEmpty()) {
                continue;
            }
            maps.put(name,ips);
        }
        return maps;
    }

//    public void logDownLoad() {
//        Map<String, List<String>> servers = buildServers();
//        for (Map.Entry<String, List<String>> entry : servers.entrySet()) {
//            executeDownLoad(entry.getKey(), entry.getValue(), DateUtils.formatDate(DateUtils.addDate(new Date(), -1),DateUtils.YYYYMMDD));
//        }
//    }


    public void logDownLoad(String name, String date) {
        List<String> ips = getIps(name);
        executeDownLoad(name, ips, date);
    }

    @Async
    public void executeDownLoad(final String name, final List<String> ips, String date) {
        if (CollectionUtils.isEmpty(ips)) {
            return;
        }
        if (StringUtils.isEmpty(date)) {
            date = DateUtils.formatDate(new Date());
        }
        date = DateUtils.formatDate(DateUtils.parseDate(date), DateUtils.YYYYMMDD);
        final String savePath = fileConfig.getSavePath() + name;
        DownLoadUtils.openDownLoadPage(name, date);
        DirUtils.getOrCreateLocalPath(savePath);
//        Timer timer = new Timer();
//        //60s后执行下载
        long delay = 1000*60;
//        timer.schedule(new LogDownLoadTimerTask(name, savePath, date, timer, ips, applicationContext), delay);


        final String finalDate = date;
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                for (String ip : ips) {
                    String remoteUrl = buildUrl(ip, finalDate, name);
                    DownLoadUtils.downloadFile(remoteUrl, savePath);
                }
            }
        }, delay, TimeUnit.MILLISECONDS);
    }

    private String buildUrl(String ip,String date, String name) {
        return new StringBuilder("http://10.33.119.31/logs_sc/")
                .append(date).append("/").append(name).append("/out.log-").append(date).append("_").append(ip)
                .append(StringUtils.equalsIgnoreCase(date, DateUtils.formatDate(new Date(),DateUtils.YYYYMMDD))?"":".gz")
                .toString();
    }

    public void deleteDueFile(String name) {
//        if (SystemUtils.isWindows()) {
//            return;
//        }

        String filePath = fileConfig.getSavePath() + name;
        StringBuilder del = new StringBuilder("rm -rf  ");
        String date = DateUtils.formatDate(DateUtils.addDays(new Date(), -NumberUtils.toInt(dueDate)), DateUtils.YYYYMMDD);
        del.append(filePath)
                .append("/out.log-")
                .append(date)
                .append("*");
        LOG.info("delCmd {}", del.toString());
        CommandUtils.execLinux(del.toString());
    }
}
