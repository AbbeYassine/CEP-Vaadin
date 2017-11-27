package cep.event;

import java.util.Date;

public class LoanEvent {

    /** Applicant name */
    private String applicant;
    
    /** Time loan event reading was taken. */
    private Date timeOfReading;
    private Double loanSum;
    
    /**
     * Single value constructor.
     * @param value Applicant .
     */
    /**
     * @param applicant Applicant 
     * @param timeOfReading Time of Reading
     */
    public LoanEvent(String applicant, Date timeOfReading, Double loanSum) {
        this.applicant = applicant;
        this.timeOfReading = timeOfReading;
        this.loanSum = loanSum;
    }


    public String getApplicant() {
        return applicant;
    }
       
  
    public Date getTimeOfReading() {
        return timeOfReading;
    }
    
    public Double getLoanSum() {
        return loanSum;
    }
      

    @Override
    public String toString() {
        return "New Loan application [" + applicant + "C] for a loan of "+loanSum+ "dinars";
    }

}
