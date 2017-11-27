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
public class AverageSumEventSubscriber implements StatementSubscriber {

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(AverageSumEventSubscriber.class);
    private static final String AverageSum_EVENT_THRESHOLD_min = "10000";
    private static final String AverageSum_EVENT_THRESHOLD_max = "100000";


    
    /**
     * {@inheritDoc}
     */
    public String getStatement() {
        
        // Example using 'Match Recognise' syntax.
        String averageSumEventExpression = "select loanSum as loan from LoanEvent where loanSum > "+ AverageSum_EVENT_THRESHOLD_min+ "and loanSum <="+AverageSum_EVENT_THRESHOLD_max;
                
        
        return averageSumEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, Double> eventMap) {
    	

    	Double event = (Double) eventMap.get("loan");


        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* An Average Sum Loan Event detected! ");
        sb.append("\n* A sum of " + event );
        sb.append("\n***************************************");
        

        LOG.debug(sb.toString());
    }

    
}
