package cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cep.event.LoanEvent;

/**
 * Wraps Esper Statement and Listener. No dependency on Esper libraries.
 */
@Component
public class BigSumEventSubscriber implements StatementSubscriber {

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(BigSumEventSubscriber.class);
    private static final String BigSum_EVENT_THRESHOLD = "100000";


    
    /**
     * {@inheritDoc}
     */
    public String getStatement() {
        
        // Example using 'Match Recognise' syntax.
        String bigSumEventExpression = "select loanSum as loan from LoanEvent where loanSum > "+ BigSum_EVENT_THRESHOLD;
                
        
        return bigSumEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Double> eventMap) {

    	Double event = (Double) eventMap.get("loan");


        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* A BIG Sum Loan Event detected! ");
        sb.append("\n* A sum of " + event );
        sb.append("\n***************************************");

        LOG.debug(sb.toString());
    }

    
}
