package tf56.timed.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tf56.timed.service.LogService;
import tf56.timed.utils.DateUtils;

import java.util.Date;
import java.util.List;

/***
 * @ClassName LogDownLoadJob
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/15 14:52
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LogDownLoadJob extends MyDataflowJob<String>{

    private static final Logger LOG = LoggerFactory.getLogger(LogDownLoadJob.class);

    private final LogService logService;

    /**
     * 获取待处理数据.
     *
     * @param shardingContext 分片上下文
     * @return 待处理的数据集合
     */
    @Override
    public List<String> fetchData(ShardingContext shardingContext) {
        String shard = shardingContext.getShardingParameter();
        LOG.info("shard:{}, shardingContext:{}",shard, shardingContext);
        List<String> data = logService.getIps(shard);
        LOG.info("data:{}",data);
        return data;
    }

    /**
     * 处理数据.
     *
     * @param shardingContext 分片上下文
     * @param data            待处理数据集合
     */
    @Override
    public void processData(ShardingContext shardingContext, List<String> data) {
        String shard = shardingContext.getShardingParameter();
        LOG.info("shard:{}, shardingContext:{}, data:{}",shard, shardingContext, data);
        //下载前一天的日志
        logService.executeDownLoad(shard, data, DateUtils.formatDate(DateUtils.addDays(new Date(), -1)));
    }


}
