package tf56.timed.job.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tf56.timed.utils.CommandUtils;

/***
 * @ClassName LogDownLoadJobListener
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/24 15:15
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
@Component
public class LogDownLoadJobListener implements ElasticJobListener{

    private static final Logger LOG = LoggerFactory.getLogger(LogDownLoadJobListener.class);

    /**
     * 作业执行前的执行的方法.
     *
     * @param shardingContexts 分片上下文
     */
    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        LOG.info("作业执行前的执行的方法({})",shardingContexts);
    }

    /**
     * 作业执行后的执行的方法.
     *
     * @param shardingContexts 分片上下文
     */
    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        LOG.info("作业执行后的执行的方法({})",shardingContexts);
        deleteFile();
    }


    private void deleteFile() {
        if (StringUtils.startsWithIgnoreCase(System.getProperty("os.name"),"win")) {
            return;
        }
        //删除无用文件 linux
        String cmd = "rm -rf downlog?warname*";
        CommandUtils.execLinux(cmd);
    }
}


