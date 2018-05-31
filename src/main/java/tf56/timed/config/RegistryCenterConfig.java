package tf56.timed.config;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***
 * @ClassName RegistryCenterConfig
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/15 10:29
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
@Configuration
@ConditionalOnExpression("'${regCenter.serverList}'.length() > 1")
public class RegistryCenterConfig {


    @Bean(initMethod = "init",destroyMethod = "close")
    public ZookeeperRegistryCenter regCenter(@Value("${regCenter.serverList}") String serverList,
                                             @Value("${regCenter.namespace}") String namespace) {
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList,namespace));
    }
}
