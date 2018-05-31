package tf56.timed.job;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/***
 * @ClassName JobShardingInfo
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/15 15:17
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
@Data
@RequiredArgsConstructor
public class JobShardingInfo {

    /**
     * 定时任务名称
     */
    private String jobName;

    /**
     * cron 表达式
     */
    private String cron;

    /**
     * 总分片数量
     */
    private final Integer shardingTotalCount;

    /**
     * 分片参数  格式 0=xx,1=xx,...
     */
    private final String shardingItemParameters;
}
