package tf56.timed.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil implements ApplicationContextAware {

	private static final Logger LOG = LoggerFactory.getLogger(SpringContextUtil.class);
	
	private static ApplicationContext applicationContext; // Spring应用上下文环境
	
	public SpringContextUtil() {
		LOG.info("spring 初始化");
	}


    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    	if(SpringContextUtil.applicationContext == null) {
          SpringContextUtil.applicationContext = applicationContext;
    	}
    }



    public static ApplicationContext getApplicationContext() {
           return applicationContext;
    }


     @SuppressWarnings("unchecked")
     public static <T> T getBean(String name) throws BeansException {
                return (T) applicationContext.getBean(name);
      }
     
     public static <T> T getBean(Class<T> clzz) {
    	 return applicationContext.getBean(clzz);
     }
     
}
