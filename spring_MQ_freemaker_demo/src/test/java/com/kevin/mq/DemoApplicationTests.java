package com.kevin.mq;

import com.kevin.mq.producer.Producer;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.Destination;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
	@Autowired
	private Producer producer;

	@Test
	public void contextLoads() throws InterruptedException {
		Destination queueDestination = new ActiveMQQueue("kevin_test_mq");
		Destination topicDestination  = new ActiveMQTopic("kevin_test_topic");
		for (int i = 0; i < 100; i++){
			producer.sendMessage(queueDestination,"love you "+ i + (new Random(2)).toString());
			producer.sendMessage(topicDestination,"you are my everything" + i);
		}
	}

}
