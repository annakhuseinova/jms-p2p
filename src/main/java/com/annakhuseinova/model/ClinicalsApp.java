package com.annakhuseinova.model;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.InitialContext;

public class ClinicalsApp {

    public static void main(String[] args) throws Exception{
        InitialContext initialContext = new InitialContext();
        try(ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
            JMSContext jmsContext = connectionFactory.createContext()){
            Queue requestQueue = (Queue) initialContext.lookup("requestQueue/requestQueue");
            JMSProducer producer = jmsContext.createProducer();
            ObjectMessage objectMessage = jmsContext.createObjectMessage();
            Patient patient = new Patient();
            patient.setId(123);
            patient.setName("Bob");
            patient.setInsuranceProvider("Blue Cross Blue Shield");
            patient.setCopay(30d);
            patient.setAmountToBePaid(500d);
            objectMessage.setObject(patient);
            producer.send(requestQueue, objectMessage);
        }
    }
}
