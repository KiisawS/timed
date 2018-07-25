package tf56.timed.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tf56.timed.service.LogService;

/***
 * @ClassName LogDueFileDeleteJob
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/6/22 9:49
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LogDueFileDeleteJob implements SimpleJob{

    private static final Logger LOG = LoggerFactory.getLogger(LogDueFileDeleteJob.class);

    private final LogService logService;
    /**
     * 执行作业.
     *
     * @param shardingContext 分片上下文
     */
    @Override
    public void execute(ShardingContext shardingContext) {
        logService.deleteDueFile(shardingContext.getShardingParameter());
    }

}
