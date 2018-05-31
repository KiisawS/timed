package tf56.timed.job.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/***
 * @ClassName TestSimpleJobListener
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/25 11:18
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
@Component
public class TestSimpleJobListener implements ElasticJobListener{
    private static final Logger LOG = LoggerFactory.getLogger(TestSimpleJobListener.class);
    /**
     * 作业执行前的执行的方法.
     *
     * @param shardingContexts 分片上下文
     */
    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        LOG.info("作业执行前的执行的方法{}",shardingContexts);
    }

    /**
     * 作业执行后的执行的方法.
     *
     * @param shardingContexts 分片上下文
     */
    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        LOG.info("作业执行后的执行的方法{}",shardingContexts);
    }
}
