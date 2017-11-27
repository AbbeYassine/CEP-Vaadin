package hello;

import hello.Data.LoanEvent;
import hello.Data.LoanEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Abbes on 27/11/2017.
 */
@RestController
public class LoanEventController {

    @Autowired
    private LoanEventRepository loanEventRepository;

    @GetMapping(path = "/getCEPData")
    public @ResponseBody
    Iterable<LoanEvent> getAllEvents() {
        return loanEventRepository.findAll();
    }
}
