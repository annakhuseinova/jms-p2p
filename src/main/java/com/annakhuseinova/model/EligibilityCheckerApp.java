package com.annakhuseinova.model;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.naming.InitialContext;

public class EligibilityCheckerApp {

    public static void main(String[] args) throws Exception{
        InitialContext initialContext = new InitialContext();
        Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");
        Queue replyQueue = (Queue) initialContext.lookup("queue/replyQueue");
        try(ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
            JMSContext jmsContext = connectionFactory.createContext()){
            JMSConsumer consumer = jmsContext.createConsumer(requestQueue);
            consumer.setMessageListener(new EligibilityCheckListener());
            JMSConsumer jmsConsumer = jmsContext.createConsumer(replyQueue);
            MapMessage message = (MapMessage) jmsConsumer.receive(30000);
            System.out.println("Patient eligibility is: " + message.getString("eligible"));
        }
    }
}
