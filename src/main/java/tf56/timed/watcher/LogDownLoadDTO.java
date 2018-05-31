package tf56.timed.watcher;

import com.alibaba.fastjson.JSON;
import lombok.*;

/***
 * @ClassName LogDownLoadDTO
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/23 10:16
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
@Getter
@Setter
@RequiredArgsConstructor
public class LogDownLoadDTO {

    private final String name;

    private final String date;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
