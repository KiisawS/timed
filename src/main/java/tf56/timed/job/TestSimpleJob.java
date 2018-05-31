package tf56.timed.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/***
 * @ClassName TestSimpleJob
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/21 16:27
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
@Component
public class TestSimpleJob implements SimpleJob{

    private static final Logger LOG = LoggerFactory.getLogger(TestSimpleJob.class);
    /**
     * 执行作业.
     *
     * @param shardingContext 分片上下文
     */
    @Override
    public void execute(ShardingContext shardingContext) {
        LOG.info("参数({})",shardingContext);
        LOG.info("简单测试~~~~玩,{}",shardingContext.getShardingParameter());
    }
}
