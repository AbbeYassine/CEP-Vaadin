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
public class SmallSumEventSubscriber implements StatementSubscriber {

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(BigSumEventSubscriber.class);
    private static final String SmallSum_EVENT_THRESHOLD = "10000";


    
    /**
     * {@inheritDoc}
     */
    public String getStatement() {
        
        // Example using 'Match Recognise' syntax.
        String smallSumEventExpression = "select loanSum as loan from LoanEvent where loanSum <= "+ SmallSum_EVENT_THRESHOLD;
                
        
        return smallSumEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Double> eventMap) {

    	Double eventSum = (Double) eventMap.get("loan");


        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* A SMALL Sum Loan Event detected! ");
        sb.append("\n* A sum of " + eventSum );
        sb.append("\n***************************************");

        LOG.debug(sb.toString());
    }

    
}
