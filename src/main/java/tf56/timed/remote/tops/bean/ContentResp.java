package tf56.timed.remote.tops.bean;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/***
 * @ClassName ContentResp
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/8 15:54
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
@Data
public class ContentResp {
    private String index;
    private String title;
    private String typeText;
    private String source;
    private String type;
    private String id;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
