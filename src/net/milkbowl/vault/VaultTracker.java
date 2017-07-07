package net.milkbowl.vault;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

/**
 * Created by taggertv on 7/7/17.
 */
public class VaultTracker {

    public static VaultTracker instance;

    private static MongoClient mongoClient;
    private static DB cqDB;
    private static DBCollection transactions;

    public static void initialize() {
        if (instance == null) {
            instance = new VaultTracker();
        }
    }

    private VaultTracker() {
        try {
            mongoClient = new MongoClient();
        } catch (UnknownHostException e) {
            throw new Error("Couldn't connect to database.");
        }
        cqDB = mongoClient.getDB("Conquestia");
        transactions = cqDB.getCollection("Transactions");
    }

    public void logTransaction(String playerName, String sourceName, double amount, double balance) {

        BasicDBObject transaction = new BasicDBObject("Transaction", playerName + "_" + System.currentTimeMillis());

        transaction.put("Player", playerName);
        if (sourceName != null) {
            transaction.put("Source", sourceName);
        }
        transaction.put("Amount", amount);
        transaction.put("Balance", balance);

        transactions.insert(transaction);
    }
}
