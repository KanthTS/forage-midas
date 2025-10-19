# Midas
Project repo for the JPMC Advanced Software Engineering Forage program.


TASK-1:
Fork and clone the project repo, which already contains the scaffold your teammate has created. The repo can be found here: https://github.com/vagabond-systems/forage-midas
Open the program in your IDE of choice - you will have a much easier time if your IDE has support for Spring or at least Maven projects. If you are casting about for a new IDE to call home, consider IntelliJ by Jetbrains. It is a feature-rich, well-supported IDE with plenty of useful bits and pieces which make development faster and more enjoyable. The community edition is entirely free, and you can get a free student license for IntelliJ Ultimate if you are part of an accredited educational program.
This project uses Java 17 - acquire the relevant JRE and configure your IDE to utilize it.
Take some time to familiarize yourself with the codebase and get a feel for what the project scaffold does and does not include.
Add the following dependencies to your Spring project (be sure to pin each one to the specified version):
Spring-boot-starter-data-jpa from org.springframework.boot - 3.2.5
Spring-boot-starter-web from org.springframework.boot - 3.2.5
Spring-kafka from org.springframework.kafka - 3.1.4
H2 from com.h2database - 2.2.224
Spring-boot-starter-test from org.springframework.boot - 3.2.5
Spring-kafka-test from org.springframework.kafka - 3.1.4
Kafka from org.testcontainers - 1.19.1
When you are finished, run “TaskOneTests” and submit the output snippet produced at the end of the logs. Be sure to include the begin and end denotations in your answer.

OUTPUT:
<img width="2580" height="412" alt="image" src="https://github.com/user-attachments/assets/28a1777b-3d83-4fb8-abef-693a75ba65a4" />

TASK-2:
Midas Core needs a way to receive all incoming transactions. To this end, you must implement a class that listens to a Kafka topic and handles incoming messages. The name of the topic in question has already been added as a configurable value in the project's application.yml file. The Kafka Listener you implement should use this configuration value to select its topic. Your Kafka Listener should deserialize all incoming messages to the provided transaction class. Your goal for this task is simply to integrate Kafka into Midas Core, no need to do anything with the transactions yet, that comes later. The provided tests utilize an in-memory, embedded kafka instance which should autowire itself to your Spring Application, so there is no need to specify a host or port in your consumer configuration.

 Once you are successfully receiving transactions, execute “TaskTwoTests” in the test folder of the repo and use your debugger to record the amount attached to the first four transactions received by Midas Core. Once you have them noted down, submit the list below. 

TASK-3:

Your next task is to integrate Midas Core with an H2 database. Whenever a transaction is received via Kafka, Midas Core should validate and record it to the database. A transaction is considered valid if the following are true:

The senderId is valid
The recipientId is valid
The sender has a balance greater than or equal to the transaction amount
If the above conditions are met, the transaction should be recorded to the database, and both the sender and recipient balances should be adjusted accordingly. If the conditions are not met, the transaction should be discarded with no modification to the database. Transaction entities should maintain a many-to-one relationship with their respective sender and recipient User entities (hint: this will necessitate creating a new TransactionRecord class with an @entity annotation rather than modifying the existing Transaction class). When you are finished, execute “TaskThreeTests” and use your debugger to record the balance of the “waldorf” user after all transactions have been processed (rounded down to the nearest integer). When you figure it out, submit the number below. 
OUTPUT:
<img width="1426" height="336" alt="image" src="https://github.com/user-attachments/assets/4fca9ccd-704c-4b67-908b-993a0b7f42de" />

TASK-4:
Your task is to integrate the incentives API with Midas Core. An executable jar containing a copy of the incentives API controller, which runs on local port 8080, has been included in the services folder of the project repo. The API has a single endpoint “/incentive” which accepts JSON POST requests and can be reached at "http://localhost:8080/incentive" (when the aforementioned jar is running). This endpoint expects a JSON serialized Transaction object which exactly mirrors the Transaction class provided in the project repo (hint: you should let Spring take care of serialization and simply pass a Transaction object to the method you call on your RestTemplate). The endpoint will respond with a JSON serialized Incentive object which has a single field: “amount.”

After a transaction is validated, it should be posted to the incentive API. The incentive API will respond with an amount >= 0, which should be recorded alongside the transaction amount in a new incentive field. When modifying user balances, the incentive should be added to the recipient’s balance, but should not be deducted from the sender’s balance. Once you have finished integrating the incentive API, run “TaskFourTests” and use your debugger to figure out the balance of the “wilbur” user after all transactions have been processed. Once you know, submit the number below, rounded down to the nearest integer. 

OUTPUT:
<img width="1440" height="614" alt="image" src="https://github.com/user-attachments/assets/40eebbc5-eae4-4b87-9cca-c26571b130f9" />

TASK-5:
Your final task is to expose a REST API for querying user balances. The API controller must expose a “/balance” endpoint that responds exclusively to GET requests, accepts a userId as a request parameter, and returns an instance of the provided Balance class serialized to JSON. Your spring application should expose this API on port 33400. If a user does not exist, the endpoint should return a balance of 0. You should integrate this REST Controller directly into Midas Core - it should run alongside the Kafka listener you’ve already implemented. When you’re finished, run “TaskFiveTests” and submit the output (including the begin and end tags) below. Do not modify the Balance class’ toString() implementation, or verification will fail. Be sure to have the Incentive API running when you execute the test. 

OUTPUT:

BEGIN OUTPUT.

[INFO] Running com.jpmc.midascore.TaskFiveTests.

[INFO] 
[INFO] GET /balance?userId=waldorf -> Balance{userId='waldorf', balance=608},

[INFO] GET /balance?userId=wilbur -> Balance{userId='wilbur', balance=4384},

[INFO] GET /balance?userId=unknownUser -> Balance{userId='unknownUser', balance=0},

[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.342 s - in com.jpmc.midascore.TaskFiveTests.

END OUTPUT

