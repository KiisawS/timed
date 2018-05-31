package tf56.timed.remote.tops.bean;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.List;

/***
 * @ClassName TopsResp
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/8 15:51
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
@Data
public class TopsResp {
    private String msg;
    private Integer code;
    private DataObj data;

    public boolean isSuccess() {
        return code.equals(200);
    }


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Data
    public static class DataObj{
        private List<ContentResp> items;
        private Integer quantity;

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }
}
