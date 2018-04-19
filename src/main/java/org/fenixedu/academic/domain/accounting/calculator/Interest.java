package org.fenixedu.academic.domain.accounting.calculator;

import java.math.BigDecimal;

import org.fenixedu.academic.domain.accounting.InterestRate.InterestRateBean;
import org.fenixedu.academic.domain.accounting.calculator.Interest.View.Detailed;
import org.fenixedu.academic.domain.accounting.calculator.Interest.View.Simple;
import org.joda.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * Created by Sérgio Silva (hello@fenixedu.org).
 */
@JsonPropertyOrder({"type", "date", "amount","origin"})
class Interest extends DebtEntry {
    interface View {
        interface Simple extends DebtEntry.View.Simple {}
        interface Detailed extends Simple, DebtEntry.View.Detailed {}
    }

    @JsonView(Simple.class)
    private final LocalDate date;

    @JsonView(Detailed.class)
    private final CreditEntry origin;


    @JsonView(Simple.class)
    private final InterestRateBean interestRateBean;
    
    public Interest(LocalDate date, BigDecimal amount, CreditEntry origin, InterestRateBean interestRateBean) {
        super(amount);
        this.date = date;
        this.origin = origin;
        this.interestRateBean = interestRateBean;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Interest)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        Interest interest = (Interest) o;

        return date != null ? date.equals(interest.date) : interest.date == null;
    }

    public CreditEntry getOrigin() {
        return origin;
    }

    public InterestRateBean getInterestRateBean() {
        return interestRateBean;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Interest{" + "date=" + date + "} " + super.toString();
    }

    @Override
    boolean isToDeposit(CreditEntry creditEntry) {
        return creditEntry.isForInterest();
    }
}
