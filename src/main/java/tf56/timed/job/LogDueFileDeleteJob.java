package tf56.timed.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tf56.timed.config.FileConfig;
import tf56.timed.utils.CommandUtils;
import tf56.timed.utils.DateUtils;

import java.util.Date;

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
    
    @Value("${dueFile.date}")
    public String dueDate;

    private final FileConfig fileConfig;
    /**
     * 执行作业.
     *
     * @param shardingContext 分片上下文
     */
    @Override
    public void execute(ShardingContext shardingContext) {
        deleteDueFile(shardingContext.getShardingParameter());
    }


    private void deleteDueFile(String shard) {
        if (StringUtils.startsWithIgnoreCase(System.getProperty("os.name"), "win")) {
            return;
        }
        String cmd = "cd " + fileConfig.getSavePath() + shard;
        CommandUtils.execLinux(cmd);

        StringBuilder del = new StringBuilder("rm -rf out.log-");
        del.append(DateUtils.formatDate(DateUtils.addDays(new Date(), -NumberUtils.toInt(dueDate)), DateUtils.YYYYMMDD)).append("*");
        LOG.info("delCmd {}", del);
        CommandUtils.execLinux(del.toString());
    }
}
