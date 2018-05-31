package tf56.timed.controller;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import tf56.timed.aspect.Log;
import tf56.timed.remote.tops.bean.ContentReq;
import tf56.timed.config.FileConfig;
import tf56.timed.remote.tops.TopsClient;
import tf56.timed.remote.tops.bean.TopsResp;
import tf56.timed.service.LogService;
import tf56.timed.service.TestService;
import tf56.timed.utils.DateUtils;
import tf56.timed.watcher.LogDownLoadDTO;

import java.util.Date;
import java.util.Map;

/***
 * @ClassName TestController
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/7 16:02
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
@RestController
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestController {
    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    private final TopsClient topsClient;
    private final TestService testService;

    @Autowired
    private FileConfig fileConfig;

    @Value("${file.savePath}")
    private String savePath;

    @RequestMapping("/test")
    public void test() {
        LOG.info("存储地址比较{}",StringUtils.equals(savePath, fileConfig.getSavePath()));
        LOG.info("存储地址:{}",fileConfig.getSavePath());
        LOG.info("names:{}",fileConfig.getNames());
    }

    @RequestMapping("/getAll")
    public void getAllContent() {

        ContentReq req = new ContentReq();
        req.setData("ehuodiKA");
        req.setSinglePageSize(20);
        req.setType("all");
        TopsResp res = topsClient.getAllContent(req);
        LOG.info("参数{}", req);

        LOG.info("返回值{}", res.toString());

    }

    @RequestMapping("/testBody")
    public void testBody(@RequestBody int i){
        System.out.println(i);

    }

    @RequestMapping("/testBody2")
    public void testBody2(@RequestBody LogDownLoadDTO downLoadDTO,@RequestParam(required = false) String hello) {
        LOG.info("参数({},{})",downLoadDTO, hello);
        LOG.info("hello.length({})", hello.length());

    }

    @RequestMapping("/testBody3")
    @Log
    public void testBody3(@RequestParam(required = false) String hello) {
        LOG.info("参数({})", hello);
        LOG.info("hello.length({})", hello.length());

    }

    @RequestMapping("/logRet")
    @Log
    public Map<String, String> logRet() {
        Map<String, String> retMap = Maps.newHashMap();
        retMap.put("name", "钟建民");
        retMap.put("jobCard", "15582");
        retMap.put("date", DateUtils.formatDate(new Date()));
        return retMap;
    }

    @RequestMapping("/logThrow")
    @Log
    public void logThrow() throws Exception {
        throw new Exception("异常~~~");
    }

    @RequestMapping("/scTest")
    @Log
    public void scheduledTest(Integer type) throws Exception {
        if (null == type) {
            LOG.info("无对应方法");
            return;
        }
        switch (type) {
            case 1:
                testService.scheduledTest1();
                break;
            case 2:
                testService.scheduledTest2();
                break;
            case 3:
                testService.scheduledTest3();
                break;
            default:
                LOG.info("无对应方法");
                break;
        }
    }

    @RequestMapping("/main")
    @Log
    public String scheduledTest4() {
        testService.scheduledTest4();
        return "{\"success\":\"ok\", \"code\":\"200\"}";
    }
}

