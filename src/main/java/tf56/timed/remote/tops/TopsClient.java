package tf56.timed.remote.tops;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tf56.timed.remote.tops.bean.ContentReq;
import tf56.timed.remote.tops.bean.TopsResp;

/***
 * @ClassName TopsClient
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/7 15:28
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
@FeignClient(value = "topsClient",url = "http://tops.tf56.lo/api")
public interface TopsClient {

    @RequestMapping(value = "/search/getAllContent/",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    TopsResp getAllContent(@RequestBody ContentReq req);
}
