package tf56.timed.aspect;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/***
 * @ClassName LogAspect
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/25 13:22
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
@Component
@Aspect
@Order(-10)
public class LogAspect {

    @Pointcut("@annotation(log)")
    public void logPoint(Log log) {

    }

    @Before("logPoint(log)")
    public void before(JoinPoint joinPoint, Log log) {
        Logger logger = getLogger(joinPoint);
        logger.info("CLASS_METHOD=>{}.{}, PARAMS=>{}", joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "logPoint(log)", returning = "ret")
    public void after(JoinPoint joinPoint, Log log, Object ret) {
        Logger logger = getLogger(joinPoint);
        logger.info("CLASS_METHOD=>{}.{}, RESPONSE=>{}",joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName(), JSON.toJSONString(ret==null?"void":ret));
    }

    @AfterThrowing(pointcut = "logPoint(log)", throwing = "throwable")
    public void throwing(JoinPoint joinPoint, Log log, Throwable throwable) {
        Logger logger = getLogger(joinPoint);
        logger.info("CLASS_METHOD=>{}.{}, EXCEPTION=>{}",joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName(), throwable.getMessage(), throwable);
    }

//    @Around("logPoint(log)")
//    public void around(Log log) {
//
//    }

    private Logger getLogger(JoinPoint joinPoint) {
        Class clzz = joinPoint.getSignature().getDeclaringType();
        return LoggerFactory.getLogger(clzz);
    }
}
