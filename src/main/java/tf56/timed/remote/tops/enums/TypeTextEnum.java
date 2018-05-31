package tf56.timed.remote.tops.enums;

import lombok.Getter;

/***
 * @ClassName TypeText
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/8 16:07
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
@Getter
public enum TypeTextEnum {

    SERVER("服务器");

    private String name;

    TypeTextEnum(String name) {
        this.name = name;
    }

}
