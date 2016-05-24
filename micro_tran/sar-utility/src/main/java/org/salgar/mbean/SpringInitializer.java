package org.salgar.mbean;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SpringInitializer implements SpringInitializerMBean {
	private static final Log LOG = LogFactory.getLog(SpringInitializer.class);
	private static final String SPRING_CTX = "classpath:/META-INF/jboss-spring.xml";
	private Object /*ConfigurableApplicationContext*/ ctx;
	
	public SpringInitializer() {
		System.out.println("starting");
	}
	
	public void start() throws Exception {
		System.out.println("starting");
		installApplicationContext();
	}
	
	public void stop() throws Exception {
		closeApplicationContext();
	}
	
	@SuppressWarnings("rawtypes")
	private void installApplicationContext() {
        try {
            Class contextClass = Class.forName("org.springframework.context.support.ClassPathXmlApplicationContext");

            @SuppressWarnings("unchecked")
			Constructor constructor = contextClass.getConstructor(new Class[] {String.class});
            Object tmpCtx = constructor.newInstance(new Object[] { SPRING_CTX });
            
            //ConfigurableApplicationContext tmpCtx = new ClassPathXmlApplicationContext(SPRING_CTX);
            if (tmpCtx != null) {
                //log(this.serviceName+" activate new applicationContext");
                ctx = tmpCtx;
            }
        } catch (Throwable e) {
            LOG.error(" Unable to load applicationContext '" + SPRING_CTX + "'. keeping existing context. Reason: " + e.getMessage(), e);
        }
    }

    private void closeApplicationContext() {
        if (ctx != null) {
            try {
                Method close = ctx.getClass().getMethod("close", null);
                close.invoke(ctx, null); //ctx.close();
                //log("applicationContext closed.");
            } catch (Throwable e) {
                //log.error("Unable to close applicationContext '" + SPRING_CTX + "'. Reason: " + e
                //        + ". Restart jboss if possible.");
            }
        }
    }

	@Override
	public void test() {
		System.out.println("starting 3");
		
	}
}
