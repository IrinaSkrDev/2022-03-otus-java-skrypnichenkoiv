package ru.otus.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CounterTwoThreads {
    private static final Logger logger = LoggerFactory.getLogger(CounterTwoThreads.class);
    private String last = "Thread2:";
    private int schet=0;
    private boolean schettens=false;
    private synchronized void action(String message) {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                //spurious wakeup https://en.wikipedia.org/wiki/Spurious_wakeup
                //поэтому не if
                while (last.equals(message)) {
                    this.wait();
                }
                if(last.equals("Thread2:")) {
                    if (schet==10){schettens=true;}
                    if (schet==1){schettens=false;}
                    if (schettens){schet--;}
                    else{

                    schet++;}

                }
                logger.info(message+schet);
                last = message;

                sleep();
                notifyAll();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        CounterTwoThreads counterTwoThreads = new CounterTwoThreads();
        new Thread(() -> counterTwoThreads.action("Thread1:")).start();
        new Thread(() -> counterTwoThreads.action("Thread2:")).start();
    }

    private static void sleep() {
        try {
            Thread.sleep(1_300);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
