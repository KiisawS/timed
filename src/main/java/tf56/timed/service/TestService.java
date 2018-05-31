package tf56.timed.service;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.StartDocument;
import java.util.List;
import java.util.concurrent.*;

/***
 * @ClassName TestService
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/30 17:46
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestService {

    private static final Logger LOG = LoggerFactory.getLogger(TestService.class);

    /**
     * 这样适合只是延迟执行的任务，不shutdown线程池
     * 如果要执行周期性任务且需要shutdown的，则使用Executors.newScheduledThreadPool(3)
     */
    private final ScheduledExecutorService scheduledExecutorService;

    /**
     * 只是延迟执行
     */
    public void scheduledTest1() {
        LOG.info("执行前：{}", System.currentTimeMillis());
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                LOG.info("执行时：{},{}", System.currentTimeMillis(),scheduledExecutorService);
            }
        }, 4000, TimeUnit.MILLISECONDS);
        LOG.info("scheduledThreadPoolExecutor:{}",scheduledExecutorService);
    }

    /**
     * 周期性执行
     * 第一次shutdown后 其他使用scheduledThreadPoolExecutor 都会报错。
     * java.util.concurrent.RejectedExecutionException:
     * Task java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask@20e5bae0
     * rejected from java.util.concurrent.ScheduledThreadPoolExecutor@dbd8689
     * [Terminated, pool size = 0, active threads = 0, queued tasks = 0, completed tasks = 5]
     */
    public void scheduledTest2() {
        final long preTime = System.currentTimeMillis();
        LOG.info("执行前：{}", preTime);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                long execTime = System.currentTimeMillis();
                LOG.info("执行时：{},{}", execTime,scheduledExecutorService);
                if (execTime-preTime >= 4000L) {
                    LOG.info("终止任务");
                    scheduledExecutorService.shutdown();
                }
            }
        }, 1000,1000, TimeUnit.MILLISECONDS);
        LOG.info("scheduledThreadPoolExecutor:{}",scheduledExecutorService);
    }

    /**
     * 每次执行后延迟ns执行下次
     */
    public void scheduledTest3() {
        final long preTime = System.currentTimeMillis();
        LOG.info("执行前：{}", preTime);
        final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(3, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "FixedDelay");
            }
        });
        scheduledThreadPoolExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                long execTime = System.currentTimeMillis();
                LOG.info("执行时：{}", execTime);
                if (execTime-preTime >= 4000L) {
                    LOG.info("终止任务");
                    scheduledThreadPoolExecutor.shutdown();
                }
            }
        }, 1000,1000, TimeUnit.MILLISECONDS);

        LOG.info("scheduledThreadPoolExecutor:{}",scheduledThreadPoolExecutor);
    }

    /**
     * 周期性执行任务，并终止任务
     * 每10个数据打印一次，直到打印完数据，终止任务
     */
    public void scheduledTest4(final int size,final int page) {

        final List<Integer> data = Lists.newArrayList();
        for (int i = 0; i < size; i++) {
            data.add(i);
        }
        final ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
        LOG.info("start");
        final Param param = new Param();
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (param.isStop) {
                    LOG.info("任务终止");
                    executor.shutdown();
                } else {
                    int start = param.value;
                    int end = start + page;
                    if (end >= size) {
                        end = size;
                        param.isStop = true;
                    }
                    param.value = end;
                    List<Integer> subData = data.subList(start, end);
                    LOG.info("subData:{}",subData);
                }
            }
        },1000,1000, TimeUnit.MILLISECONDS);
        LOG.info("over");
    }

    /**
     * 用来存储参数
     */
    class Param {
        int value;
        boolean isStop;
    }
}
