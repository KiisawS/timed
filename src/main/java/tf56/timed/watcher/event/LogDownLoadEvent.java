package tf56.timed.watcher.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import tf56.timed.watcher.LogDownLoadDTO;

/***
 * @ClassName LogDownLoadEvent
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/23 10:14
 * <p>Company: 杭州传化货嘀科技有公司</p>
 */
@Getter
@Setter
public class LogDownLoadEvent extends ApplicationEvent{

    private LogDownLoadDTO dto;

    public LogDownLoadEvent(Object source) {
        super(source);
    }

    public LogDownLoadEvent(Object source, LogDownLoadDTO dto) {
        super(source);
        this.dto = dto;
    }
}
