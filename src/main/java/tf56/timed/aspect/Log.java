package tf56.timed.aspect;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/***
 * @ClassName Log
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/25 13:19
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
@Target({METHOD})
@Retention(RUNTIME)
@Documented
public @interface Log {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

}
