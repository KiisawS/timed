package tf56.timed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tf56.timed.service.LogService;

/***
 * @ClassName LogController
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/24 15:13
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/down")
    public void down(@RequestParam String name, String date) {
        logService.logDownLoad(name, date);
    }

    @GetMapping("/delete/{name}")
    public void deleteDueFile(@PathVariable("name") String name) {
        logService.deleteDueFile(name);
    }
}
