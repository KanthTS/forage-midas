package com.jpmc.midascore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRecordRepository;

@Service
public class TransactionConsumerService {
     @Autowired
	private UserRecordRepository u;
     @Autowired
	private TransactionRecordRepository t;
     @Autowired
     private IncentiveService i;
     @Transactional
     @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-core-group")
     public void listen(Transaction tx) {
         if (tx == null) {
             return;
         }
         
         UserRecord sender = u.findById(tx.getSenderId()).orElse(null);
         UserRecord recipient = u.findById(tx.getRecipientId()).orElse(null);
         if (sender == null || recipient == null) {
             return;
         }
         if (sender.getBalance() < tx.getAmount()) {
             return;
         }
         sender.setBalance(sender.getBalance() - tx.getAmount());
         Incentive incentive=i.inc(tx);
         recipient.setBalance(recipient.getBalance() + tx.getAmount()+incentive.getAmount());

         u.save(sender);
         u.save(recipient);
         System.out.println("Incentive applied: " + incentive);
         System.out.println("Transaction processed successfully: " + tx);
         TransactionRecord rec = new TransactionRecord();
         rec.setAmount(tx.getAmount());
         rec.setSender(sender);
         rec.setRecipient(recipient);
         t.save(rec);
     }

	
}
