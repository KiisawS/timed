package tf56.timed.remote.tops.bean;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/***
 * @ClassName ContentReq
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/8 10:41
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
@Data
public class ContentReq {
    private String data;
    private Integer singlePageSize;
    private String type;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
