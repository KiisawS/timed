package tf56.timed.config.job;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/***
 * @ClassName ElasticJobConfig
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/15 10:40
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
@Configuration
@ConfigurationProperties(prefix = "regCenter")
@Data
public class ElasticJobConfig {

    private String serverList;

    private String namespace;

}
