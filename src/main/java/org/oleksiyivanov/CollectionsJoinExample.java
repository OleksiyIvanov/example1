package org.oleksiyivanov;

import com.mongodb.*;
import java.net.*;
import java.util.*;

public class CollectionsJoinExample {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient("localhost" , 27017);
        DB database = client.getDB("bank");

        DBCollection accountsCollection = database.getCollection("accounts");
        DBCursor accountsCursor = accountsCollection.find();
        DBObject account;
        int accountID;
// Suppose we can fit all accounts into memory
        Map<Integer, DBObject> accountsMap = new HashMap<Integer, DBObject>();
        try {
            while(accountsCursor.hasNext()) {
                account = accountsCursor.next();
                accountID = ((Number)account.get("account_id")).intValue();
                accountsMap.put(new Integer(accountID), account);
            }
        } finally {
            accountsCursor.close();
        }

        DBCollection tradesCollection = database.getCollection("trades");
// Let's find all trades for Aug 29, 2014 or newer
        System.out.println("List of trades for Aug 29, 2014 or newer :");
        BasicDBObject query = new BasicDBObject("date", new BasicDBObject("$gte", "2014-08-29"));
        DBCursor tradesCursor = tradesCollection.find(query);
        DBObject trade;
        try {
            while(tradesCursor.hasNext()) {
                trade = tradesCursor.next();
                accountID = ((Number)trade.get("account_id")).intValue();
// Here we lookup for account information at HashMap
                account = accountsMap.get(accountID);
                System.out.println("Trade date: " + trade.get("date") + "  Account number: " + ((Number)account.get("account_number")).intValue()  + "  Account owner: "+account.get("owner") + "  Amount: " + trade.get("amount"));
            }
        } finally {
            tradesCursor.close();
        }
    }
}
