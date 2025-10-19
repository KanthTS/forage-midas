package com.jpmc.midascore;

import com.jpmc.midascore.foundation.Balance;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class TaskFiveTests {

    static final Logger logger = LoggerFactory.getLogger(TaskFiveTests.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private UserPopulator userPopulator;

    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private BalanceQuerier balanceQuerier;

    @Test
    void task_five_verifier() throws InterruptedException {

        // Step 1: Populate users
        userPopulator.populate();

        // Step 2: Load transactions from file and send to Kafka
        String[] transactionLines = fileLoader.loadStrings("/test_data/rueiwoqp.tyruei");
        for (String transactionLine : transactionLines) {
            kafkaProducer.send(transactionLine);
        }

        // Step 3: Wait for Kafka to process messages
        Thread.sleep(2000);

        // Step 4: Query balances from database
        StringBuilder output = new StringBuilder();
        output.append("---begin output ---").append("\n");

        for (int i = 0; i < 13; i++) {
            Balance balance = balanceQuerier.query((long) i);
            output.append(balance.toString()).append("\n");
        }

        output.append("---end output ---");

        // Step 5: Store output in variable test5 and print to console
        String test5 = output.toString();
        System.out.println(test5); // console output
        logger.info(test5);        // also logs via logger
    }
}
