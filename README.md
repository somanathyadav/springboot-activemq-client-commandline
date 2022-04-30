# springboot-avtivemq-command-line
This project creates a SpringBoot ActiveMQ client
1. Runs on command line
2. Do  not exit/fail in case of JMS infrastructure goes down. It retries(evey 5 seconds) till JMS infrastructure comes up
3. It will end gracefully when intended message is arrived

It creates around 16MB Jar artifact, which is runnable using below command:

### ActiveMQ start
    docker pull rmohr/activemq
    docker run -p 61616:61616 -p 8161:8161 rmohr/activemq

Start activeMQ server using above docker commands on your machine.

### Stop ActiveMQ
    docker ps
    docker stop <<containerId>>

### To run the client 
    java -jar activemq-jms-client-1.0.1.jar --last.msg.key.lookup=AllDone
