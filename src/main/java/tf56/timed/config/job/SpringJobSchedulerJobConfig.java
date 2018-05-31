package tf56.timed.config.job;

import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tf56.timed.config.FileConfig;
import tf56.timed.job.*;
import tf56.timed.job.listener.LogDownLoadJobListener;
import tf56.timed.job.listener.TestSimpleJobListener;

import java.util.List;

/***
 * @ClassName SpringJobSchedulerJobConfig
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/15 10:47
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
@Configuration
//@ConditionalOnBean({FileConfig.class,ZookeeperRegistryCenter.class,LogDownLoadJob.class,TestSimpleJob.class})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SpringJobSchedulerJobConfig extends BaseJobConfig {

    private final ZookeeperRegistryCenter regCenter;
    private final FileConfig fileConfig;

    //只是用于测试...注释掉
    @Bean(initMethod = "init",name = "simpleJobScheduler")
    public JobScheduler simpleJobScheduler(final TestSimpleJob testSimpleJob,
                                           final TestSimpleJobListener testSimpleJobListener,
                                           @Value("${testSimpleJob.cron}") final String cron,
                                           @Value("${testSimpleJob.shardingTotalCount}") final int shardingTotalCount,
                                           @Value("${testSimpleJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(testSimpleJob, regCenter, getLiteJobConfigurationSimple(testSimpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters),testSimpleJobListener);
    }

    /**
     * 日志下载任务配置
     * @param logDownLoadJob
     * @param cron
     * @param shardingTotalCount
     * @param shardingItemParameters
     * @return
     */
    @Bean(initMethod = "init",name = "logDownLoadScheduler")
    public JobScheduler logDownLoadScheduler(final LogDownLoadJob logDownLoadJob,
                                             final LogDownLoadJobListener logDownLoadJobListener,
                                             @Value("${logDownLoadJob.cron}") final String cron,
                                             @Value("${logDownLoadJob.shardingTotalCount}") int shardingTotalCount,
                                             @Value("${logDownLoadJob.shardingItemParameters}") String shardingItemParameters) {
        JobShardingInfo info = build();
        if (null != info) {
            shardingTotalCount = info.getShardingTotalCount();
            shardingItemParameters = info.getShardingItemParameters();
        }
        return new SpringJobScheduler(logDownLoadJob, regCenter, getLiteJobConfigurationDataflow(logDownLoadJob.getClass(), cron, shardingTotalCount, shardingItemParameters, logDownLoadJob.isStream()),logDownLoadJobListener);
    }

    private JobShardingInfo build() {
        List<String> names = fileConfig.getNameList();
        if (null == names || names.isEmpty()) {
            return null;
        }
        int size = names.size();
        StringBuilder shardingItemParameters = new StringBuilder();
        for (int i = 0; i < size; i++) {
            shardingItemParameters.append(i).append("=").append(names.get(i)).append(",");
        }
        return new JobShardingInfo(size, shardingItemParameters.substring(0,shardingItemParameters.length()-1));
    }
}
