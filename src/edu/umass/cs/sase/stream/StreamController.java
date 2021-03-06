
package edu.umass.cs.sase.stream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 * This class wraps the stream, specifies how to generate or import stream.
 *
 * @author haopeng
 */
public class StreamController {
    /**
     * The stream
     */
    Stream myStream;
    /**
     * The size of the stream
     */
    int size;

    /**
     * event id
     */
    int eventID;

    /**
     * A random number generator
     */
    Random randomGenerator;

    /**
     * Default constructor
     */
    public StreamController() {
        eventID = 0;
        randomGenerator = new Random(11);
    }

    /**
     * Constructor, specified size and event type
     *
     * @param size
     * @param eventType
     */
    public StreamController(int size, String eventType) {
        this.size = size;
        myStream = new Stream(size);
        if (eventType.equalsIgnoreCase("abcevent")) {
            this.generateABCEvents();
        }
        if (eventType.equalsIgnoreCase("stockevent")) {
            this.generateStockEvents();
        }
    }

    /**
     * Generates a series of stock events
     */

    /**
     * Generates a series of stock events
     */
    public void generateStockEventsAsConfigType() {
        if (StockStreamConfig.increaseProbability > 100) {
            Random r = new Random(StockStreamConfig.randomSeed);
            StockEvent events[] = new StockEvent[this.size];
            int id;
            int timestamp = 0;
            int symbol;
            int volume;
            int price = r.nextInt(100);
            int endTimestamp = 0;
            String eventType = "stock";


            for (int i = 0; i < size; i++) {
                id = i;
                timestamp = id;

                symbol = r.nextInt(StockStreamConfig.numOfSymbol) + 1;
                price = r.nextInt(StockStreamConfig.maxPrice) + 1;
                volume = r.nextInt(StockStreamConfig.maxVolume) + 1;
                endTimestamp = r.nextInt(10) + timestamp;
                eventType = "stock" + symbol;
                events[i] = new StockEvent(id, timestamp, symbol, price, volume, eventType, endTimestamp);
            }
            myStream.setEvents(events);

        } else {
            this.generateStockEventsWithIncreaseProbability();
        }
    }

    /**
     * Generates a series of stock events
     */
    public void generateStockEventsWithIncreaseProbability() {

        Random r = new Random();
        ArrayList<StockEvent> stockEvents = new ArrayList<>();
        StockEvent events[] = new StockEvent[this.size];
        int id;
        int timestamp = 0;
        int endTimestamp = 0;
        int symbol;
        int volume;
        int dur = 0;
        int price[] = new int[StockStreamConfig.numOfSymbol];
        for (int i = 0; i < StockStreamConfig.numOfSymbol; i++) {
            //initializes the prices of each stock
            price[i] = r.nextInt(1000);
        }

        int random = 0;
        String eventType = "stock";


        for (int i = 0; i < size; i++) {
            id = i;
            endTimestamp = r.nextInt(StockStreamConfig.streamSize * 2);
            dur = r.nextInt(15);
            timestamp = endTimestamp - dur;
            if (timestamp < 0) {
                timestamp = 0;
            }
            symbol = r.nextInt(StockStreamConfig.numOfSymbol) + 1;
            random = r.nextInt(100) + 1;

            if (random <= StockStreamConfig.increaseProbability) {
                //increase
                price[symbol - 1] += (r.nextInt(3) + 1);
            } else if (random > (100 + StockStreamConfig.increaseProbability) / 2) {
                // decrease
                price[symbol - 1] -= (r.nextInt(3) + 1);
            }


            volume = r.nextInt(StockStreamConfig.maxVolume) + 1;
            eventType = "stock";
            stockEvents.add(new StockEvent(id, timestamp, symbol, price[symbol - 1], volume, eventType, endTimestamp));
        }

//        for (Event e: events){
//            System.out.println(e.toString());
//        }
        stockEvents.sort(new Comparator<StockEvent>() {
            @Override
            public int compare(StockEvent o1, StockEvent o2) {
                if (o1.endTimestamp == o2.endTimestamp) {
                    if(o1.timestamp == o2.timestamp) return o1.id < o2.id? -1:1;
                    return o1.timestamp < o2.timestamp ? -1:1;
                }
                return o1.endTimestamp < o2.endTimestamp ? -1 : 1;
            }
        });
        stockEvents.toArray(events);
        myStream.setEvents(events);


    }

    /**
     * Generates a series of stock events
     */
    public void generateStockEvents() {
        Random r = new Random(11);
        StockEvent events[] = new StockEvent[this.size];
        int id;
        int timestamp = 0;
        int symbol;
        int volume;
        int price = r.nextInt(100);
        int random = 0;
        int endTimestamp = r.nextInt(10);
        String eventType = "stock";

        for (int i = 0; i < size; i++) {
            id = i;
            timestamp = id;
            symbol = r.nextInt(2); //0 or 1
            random = r.nextInt(100);
            if (random < 55) {
                price += r.nextInt(5);
            } else if (random >= 55 && random < 77) {
                price -= r.nextInt(5);
            }
            volume = r.nextInt(1000);
            endTimestamp += timestamp;
            events[i] = new StockEvent(id, timestamp, symbol, price, volume, endTimestamp);

        }
        myStream.setEvents(events);


    }


    /**
     * Generates ABCEvents for the stream
     */
    public void generateABCEvents() {
        // this is for correctness test
        Random r = new Random(11);
        ABCEvent events[] = new ABCEvent[this.size];
        int id;
        int timestamp = 0;
        int random = 0;
        int endTimestamp = r.nextInt(10);
        String eventType = "";
        int price = 50;
        for (int i = 0; i < size; i++) {
            id = i;
            random = r.nextInt(3);
            timestamp = i;
            switch (random) {
                case 0:
                    eventType = "a";
                    break;
                case 1:
                    eventType = "b";
                    price += 1;
                    break;
                case 2:
                    eventType = "c";
                    price += 2;
                    break;
                case 3:
                    eventType = "d";
                    price += 3;
                    break;

            }

            endTimestamp += timestamp;
            events[i] = new ABCEvent(id, timestamp, eventType, price, endTimestamp);

        }

        myStream.setEvents(events);
    }

    /**
     * @return the myStream
     */
    public Stream getMyStream() {
        return myStream;
    }

    /**
     * @param myStream the myStream to set
     */
    public void setMyStream(Stream myStream) {
        this.myStream = myStream;
        this.setSize(myStream.getSize());
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Outputs the events in the stream one by one in the console
     */
    public void printStream() {
        for (int i = 0; i < myStream.getSize(); i++) {
            System.out.println("The " + i + " event out of " + size + " events is: " + myStream.getEvents()[i].toString());
        }
    }


}
