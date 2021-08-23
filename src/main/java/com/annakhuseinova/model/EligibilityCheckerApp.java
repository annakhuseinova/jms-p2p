package com.annakhuseinova.model;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;

public class EligibilityCheckerApp {

    public static void main(String[] args) throws Exception{
        InitialContext initialContext = new InitialContext();
        Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");
        Queue replyQueue = (Queue) initialContext.lookup("queue/replyQueue");
        try(ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
            JMSContext jmsContext = connectionFactory.createContext()){
            JMSConsumer consumer1 = jmsContext.createConsumer(requestQueue);
            JMSConsumer consumer2 = jmsContext.createConsumer(requestQueue);
           // consumer.setMessageListener(new EligibilityCheckListener());
//            JMSConsumer jmsConsumer = jmsContext.createConsumer(replyQueue);
//            MapMessage message = (MapMessage) jmsConsumer.receive(30000);
//            System.out.println("Patient eligibility is: " + message.getString("eligible"));
//            Thread.sleep(10000);

            for (int i = 0; i < 10; i+=2) {
                System.out.println("Consumer 1: " + consumer1.receive());
                System.out.println("Consumer 2: " + consumer2.receive());
            }
        }
    }
}
