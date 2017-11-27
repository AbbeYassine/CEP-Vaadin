package cep.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cep.event.LoanEvent;
import cep.subscriber.StatementSubscriber;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

/**
 * This class handles incoming Temperature Events. It processes them through the EPService, to which
 * it has attached the 3 queries.
 */
@Component
@Scope(value = "singleton")
public class LoanEventHandler implements InitializingBean{

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(LoanEventHandler.class);

    /** Esper service */
    private EPServiceProvider epService;
    private EPStatement smallSumEventStatement;
    private EPStatement averageSumEventStatement;
    private EPStatement bigSumEventStatement;

    @Autowired
    @Qualifier("smallSumEventSubscriber")
    private StatementSubscriber smallSumEventSubscriber;

    @Autowired
    @Qualifier("averageSumEventSubscriber")
    private StatementSubscriber averageSumEventSubscriber;

    @Autowired
    @Qualifier("bigSumEventSubscriber")
    private StatementSubscriber bigSumEventSubscriber;

    /**
     * Configure Esper Statement(s).
     */
    public void initService() {

        LOG.debug("Initializing Servcie ..");
        Configuration config = new Configuration();
        config.addEventTypeAutoName("cep.event");
        epService = EPServiceProviderManager.getDefaultProvider(config);

        createSmallSumCheckExpression();
        createAverageSumCheckExpression();
        createBigSumExpression();

    }


    private void createSmallSumCheckExpression() {
        
        LOG.debug("create small sum  Check Expression");
        smallSumEventStatement = epService.getEPAdministrator().createEPL(smallSumEventSubscriber.getStatement());
        smallSumEventStatement.setSubscriber(smallSumEventSubscriber);
    }


    private void createAverageSumCheckExpression() {

        LOG.debug("create average sum Check Expression");
        averageSumEventStatement = epService.getEPAdministrator().createEPL(averageSumEventSubscriber.getStatement());
        averageSumEventStatement.setSubscriber(averageSumEventSubscriber);
    }


    private void createBigSumExpression() {

        LOG.debug("create big sum  Check Expression");
        bigSumEventStatement = epService.getEPAdministrator().createEPL(bigSumEventSubscriber.getStatement());
        bigSumEventStatement.setSubscriber(bigSumEventSubscriber);
    }

    /**
     * Handle the incoming LoanEvent.
     */
    public void handle(LoanEvent event) {

        LOG.debug(event.toString());
        epService.getEPRuntime().sendEvent(event);

    }

    @Override
    public void afterPropertiesSet() {
        
        LOG.debug("Configuring..");
        initService();
    }
}
