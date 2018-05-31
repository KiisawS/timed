package tf56.timed.config;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/***
 * @ClassName FileConfig
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/7 15:13
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
@Configuration
@ConfigurationProperties(prefix = "file")
@Data
@RefreshScope
public class FileConfig {

    private String savePath;

    private String names;

    public List<String> getNameList() {
        return Lists.newArrayList(names.split(","));
    }
}
