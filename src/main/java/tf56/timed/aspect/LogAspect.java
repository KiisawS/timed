package tf56.timed.aspect;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/***
 * @ClassName LogAspect
 * <p>Description:
 * 只是用来学习使用</p>
 * @author TF015582
 * @date 2018/5/25 13:22
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
@Component
@Aspect
@Order(-10)
public class LogAspect {
    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);
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

    private Logger getLogger(JoinPoint joinPoint) {
        Class clzz = joinPoint.getSignature().getDeclaringType();
        return LoggerFactory.getLogger(clzz);
    }
}
