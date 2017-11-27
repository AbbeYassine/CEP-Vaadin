package cep.util;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cep.data.ConfigDataBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cep.event.LoanEvent;

import cep.handler.LoanEventHandler;

/**
 * Just a simple class to create a number of Random LoanEvents and pass them off to the
 * LoanEventHandler.
 */
@Component
public class RandomLoanEventGenerator {

    /**
     * Logger
     */
    private static Logger LOG = LoggerFactory.getLogger(RandomLoanEventGenerator.class);

    @Autowired
    private LoanEventHandler loanEventHandler;


    public void startSendingLoanReadings(final long noOfLoanEvents) {

        ConfigDataBase.getConnection();
        //ConfigDataBase.addDataToDB(new LoanEvent("Applicant number " + 1, new Date(), new Random().nextDouble() * 200000));

        ExecutorService xrayExecutor = Executors.newSingleThreadExecutor();

        xrayExecutor.submit(new Runnable() {
            public void run() {

                LOG.debug(getStartingMessage());

                int count = 0;
                while (count < noOfLoanEvents) {
                    LoanEvent ve = new LoanEvent("Applicant number " + count, new Date(), new Random().nextDouble() * 200000);
                    ConfigDataBase.addDataToDB(ve);
                    loanEventHandler.handle(ve);
                    count++;
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        LOG.error("Thread Interrupted", e);
                    }
                }

            }
        });
    }


    private String getStartingMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n************************************************************");
        sb.append("\n* STARTING - ");
        sb.append("\n* PLEASE WAIT - LOANS ARE RANDOM SO MAY TAKE");
        sb.append("\n************************************************************\n");
        return sb.toString();
    }
}
