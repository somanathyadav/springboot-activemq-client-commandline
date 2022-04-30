package com.javaxp.testjmsclient;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * The type Shutdown manager.
 */
@Component
public class ShutdownManager implements ApplicationContextAware {
    private ApplicationContext appContext;

    /**
     * Initiate shutdown.
     * Invoke with `0` to indicate no error or different code to indicate
     * abnormal exit. es: shutdownManager.initiateShutdown(0);
     *
     * @param returnCode the return code
     */
    public void initiateShutdown(int returnCode){
        System.out.println("Shut down manager called!!" + LocalDateTime.now());
        try {
            JmsListenerEndpointRegistry jmsListenerEndpointRegistry = appContext.getBean(JmsListenerEndpointRegistry.class);
            Collection<MessageListenerContainer> col = jmsListenerEndpointRegistry
                    .getListenerContainers();
            for (MessageListenerContainer cont : col) {
                cont.stop();
            }
        } catch (Exception e) {
            //Do nothing
        }
        System.out.println("JMS Stopped!!" + LocalDateTime.now());
        SpringApplication.exit(appContext, () -> returnCode);
        System.out.println("Shut down done!");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
    }
}
