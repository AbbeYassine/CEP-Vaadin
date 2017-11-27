package hello.Data;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Abbes on 27/11/2017.
 */
public interface LoanEventRepository extends CrudRepository<LoanEvent, Integer> {
}
