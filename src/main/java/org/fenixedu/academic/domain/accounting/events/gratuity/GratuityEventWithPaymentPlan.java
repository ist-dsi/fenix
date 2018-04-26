/**
 * Copyright © 2002 Instituto Superior Técnico
 *
 * This file is part of FenixEdu Academic.
 *
 * FenixEdu Academic is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FenixEdu Academic is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FenixEdu Academic.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.fenixedu.academic.domain.accounting.events.gratuity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.fenixedu.academic.domain.DegreeCurricularPlan;
import org.fenixedu.academic.domain.ExecutionDegree;
import org.fenixedu.academic.domain.ExecutionYear;
import org.fenixedu.academic.domain.Person;
import org.fenixedu.academic.domain.StudentCurricularPlan;
import org.fenixedu.academic.domain.accounting.AccountingTransaction;
import org.fenixedu.academic.domain.accounting.Entry;
import org.fenixedu.academic.domain.accounting.EntryType;
import org.fenixedu.academic.domain.accounting.Exemption;
import org.fenixedu.academic.domain.accounting.Installment;
import org.fenixedu.academic.domain.accounting.PaymentCode;
import org.fenixedu.academic.domain.accounting.PaymentCodeState;
import org.fenixedu.academic.domain.accounting.PaymentCodeType;
import org.fenixedu.academic.domain.accounting.PaymentPlan;
import org.fenixedu.academic.domain.accounting.events.gratuity.exemption.penalty.InstallmentPenaltyExemption;
import org.fenixedu.academic.domain.accounting.paymentCodes.AccountingEventPaymentCode;
import org.fenixedu.academic.domain.accounting.paymentCodes.InstallmentPaymentCode;
import org.fenixedu.academic.domain.accounting.paymentPlans.CustomGratuityPaymentPlan;
import org.fenixedu.academic.domain.accounting.paymentPlans.GratuityPaymentPlan;
import org.fenixedu.academic.domain.accounting.postingRules.gratuity.GratuityWithPaymentPlanPR;
import org.fenixedu.academic.domain.accounting.serviceAgreementTemplates.DegreeCurricularPlanServiceAgreementTemplate;
import org.fenixedu.academic.domain.accounting.serviceAgreements.DegreeCurricularPlanServiceAgreement;
import org.fenixedu.academic.domain.administrativeOffice.AdministrativeOffice;
import org.fenixedu.academic.domain.candidacy.StudentCandidacy;
import org.fenixedu.academic.domain.exceptions.DomainException;
import org.fenixedu.academic.domain.student.Student;
import org.fenixedu.academic.dto.accounting.EntryDTO;
import org.fenixedu.academic.dto.accounting.EntryWithInstallmentDTO;
import org.fenixedu.academic.dto.accounting.SibsTransactionDetailDTO;
import org.fenixedu.academic.util.Money;
import org.fenixedu.bennu.core.domain.User;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.YearMonthDay;

public class GratuityEventWithPaymentPlan extends GratuityEventWithPaymentPlan_Base {

    protected GratuityEventWithPaymentPlan() {
        super();
    }

    public GratuityEventWithPaymentPlan(AdministrativeOffice administrativeOffice, Person person,
            StudentCurricularPlan studentCurricularPlan, ExecutionYear executionYear) {
        this();
        init(administrativeOffice, person, studentCurricularPlan, executionYear);

    }

    @Override
    protected void init(AdministrativeOffice administrativeOffice, Person person, StudentCurricularPlan studentCurricularPlan,
            ExecutionYear executionYear) {
        super.init(administrativeOffice, person, studentCurricularPlan, executionYear);

        configuratePaymentPlan();
    }

    @Override
    public void setGratuityPaymentPlan(final PaymentPlan gratuityPaymentPlan) {
        throw new DomainException("error.GratuityEventWithPaymentPlan.do.not.use.this.method");
    }

    public void changeGratuityPaymentPlan(final PaymentPlan paymentPlan) {
        if (paymentPlan instanceof GratuityPaymentPlan) {
            setGratuityPaymentPlan((GratuityPaymentPlan) paymentPlan);
        } else if (paymentPlan instanceof CustomGratuityPaymentPlan) {
            setGratuityPaymentPlan((CustomGratuityPaymentPlan) paymentPlan);
        } else {
            throw new DomainException("error.GratuityEventWithPaymentPlan.unexpected.payment.plan.type");
        }
    }

    public void setGratuityPaymentPlan(final GratuityPaymentPlan paymentPlan) {
        if (paymentPlan != null) {
            ensureServiceAgreement();
            super.setGratuityPaymentPlan(paymentPlan);
        }
    }

    public void setGratuityPaymentPlan(final CustomGratuityPaymentPlan paymentPlan) {
        if (paymentPlan != null) {
            ensureServiceAgreement();
            super.setGratuityPaymentPlan(paymentPlan);
        }
    }

    public void configuratePaymentPlan() {
        ensureServiceAgreement();

        if (getGratuityPaymentPlan() == null) {
            super.setGratuityPaymentPlan(getDegreeCurricularPlanServiceAgreement().getGratuityPaymentPlanFor(
                    getStudentCurricularPlan(), getExecutionYear()));
        }

        if (getGratuityPaymentPlan() == null) {
            throw new DomainException("error.accounting.events.gratuity.paymentPlan.has.to.exist");
        }
    }

    private void ensureServiceAgreement() {
        if (getDegreeCurricularPlanServiceAgreement() == null) {
            new DegreeCurricularPlanServiceAgreement(getPerson(),
                    DegreeCurricularPlanServiceAgreementTemplate.readByDegreeCurricularPlan(getStudentCurricularPlan()
                            .getDegreeCurricularPlan()));
        }
    }

    public void configurateCustomPaymentPlan() {
        if (!hasCustomGratuityPaymentPlan()) {
            ensureServiceAgreement();
            super.setGratuityPaymentPlan(new CustomGratuityPaymentPlan(getExecutionYear(),
                    getDegreeCurricularPlanServiceAgreement()));
        }
    }

    public void configurateDefaultPaymentPlan() {
        if (!hasDefaultGratuityPaymentPlan()) {
            ensureServiceAgreement();
            final GratuityPaymentPlan paymentPlan =
                    getDegreeCurricularPlanServiceAgreement().getDefaultGratuityPaymentPlan(getExecutionYear());
            if (paymentPlan == null) {
                throw new DomainException("error.GratuityEventWithPaymentPlan.cannot.set.null.payment.plan");
            }
            super.setGratuityPaymentPlan(paymentPlan);
        }
    }

    @Override
    protected List<AccountingEventPaymentCode> createPaymentCodes() {
        return createMissingPaymentCodes();
    }

    private List<AccountingEventPaymentCode> createMissingPaymentCodes() {
        final List<AccountingEventPaymentCode> result = new ArrayList<AccountingEventPaymentCode>();
        for (final EntryDTO entryDTO : calculateEntries()) {

            if (!hasAnyNonProcessedPaymentCodeFor(entryDTO)) {
                if (entryDTO instanceof EntryWithInstallmentDTO) {
                    result.add(createInstallmentPaymentCode((EntryWithInstallmentDTO) entryDTO, getPerson().getStudent()));
                } else {
                    result.add(createAccountingEventPaymentCode(entryDTO, getPerson().getStudent()));
                }
            }

        }
        return result;
    }

    private boolean hasAnyNonProcessedPaymentCodeFor(final EntryDTO entryDTO) {
        for (final AccountingEventPaymentCode paymentCode : getNonProcessedPaymentCodes()) {
            if (paymentCode instanceof InstallmentPaymentCode) {
                if (entryDTO instanceof EntryWithInstallmentDTO) {
                    final InstallmentPaymentCode installmentPaymentCode = (InstallmentPaymentCode) paymentCode;

                    if (installmentPaymentCode.getInstallment() == ((EntryWithInstallmentDTO) entryDTO).getInstallment()) {
                        return true;
                    }
                }
            } else {
                if (!(entryDTO instanceof EntryWithInstallmentDTO)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    protected List<AccountingEventPaymentCode> updatePaymentCodes() {
        createMissingPaymentCodes();

        final List<EntryDTO> entryDTOs = calculateEntries();
        final List<AccountingEventPaymentCode> result = new ArrayList<AccountingEventPaymentCode>();

        for (final AccountingEventPaymentCode paymentCode : getNonProcessedPaymentCodes()) {
            final EntryDTO entryDTO = findEntryDTOForPaymentCode(entryDTOs, paymentCode);
            if (entryDTO == null) {
                paymentCode.cancel();
                continue;
            }

            if (paymentCode instanceof InstallmentPaymentCode) {
                final InstallmentPaymentCode installmentPaymentCode = (InstallmentPaymentCode) paymentCode;
                paymentCode.update(new YearMonthDay(),
                        calculateInstallmentPaymentCodeEndDate(installmentPaymentCode.getInstallment()),
                        entryDTO.getAmountToPay(), entryDTO.getAmountToPay());
                result.add(paymentCode);
            } else {
                paymentCode.update(new YearMonthDay(), calculateFullPaymentCodeEndDate(), entryDTO.getAmountToPay(),
                        entryDTO.getAmountToPay());

                result.add(paymentCode);
            }

        }

        return result;
    }

    private YearMonthDay calculateInstallmentPaymentCodeEndDate(final Installment installment) {
        final YearMonthDay today = new YearMonthDay();
        final YearMonthDay installmentEndDate = new YearMonthDay(installment.getEndDate(this));
        return today.isBefore(installmentEndDate) ? installmentEndDate : calculateNextEndDate(today);
    }

    private YearMonthDay calculateFullPaymentCodeEndDate() {
        final YearMonthDay today = new YearMonthDay();
        final LocalDate endDate = getFirstInstallment().getEndDate(this);
        final YearMonthDay totalEndDate =
                new YearMonthDay(getFirstInstallment().getEndDate(this).getYear(), getFirstInstallment().getEndDate(this)
                        .getMonthOfYear(), getFirstInstallment().getEndDate(this).getDayOfMonth());
        return today.isBefore(totalEndDate) ? totalEndDate : calculateNextEndDate(today);
    }

    private EntryDTO findEntryDTOForPaymentCode(List<EntryDTO> entryDTOs, AccountingEventPaymentCode paymentCode) {

        if (paymentCode instanceof InstallmentPaymentCode) {
            for (final EntryDTO entryDTO : entryDTOs) {
                if (entryDTO instanceof EntryWithInstallmentDTO) {
                    if (((InstallmentPaymentCode) paymentCode).getInstallment() == ((EntryWithInstallmentDTO) entryDTO)
                            .getInstallment()) {
                        return entryDTO;
                    }
                }
            }

        } else {
            for (final EntryDTO entryDTO : entryDTOs) {
                if (!(entryDTO instanceof EntryWithInstallmentDTO)) {
                    return entryDTO;
                }
            }
        }

        return null;

        // throw new DomainException(
        // "error.accounting.events.gratuity.GratuityEventWithPaymentPlan.paymentCode.does.not.have.corresponding.entryDTO.because.data.is.corrupted"
        // );

    }

    public void changeGratuityTotalPaymentCodeState(final PaymentCodeState paymentCodeState) {
        for (final AccountingEventPaymentCode accountingEventPaymentCode : getNonProcessedPaymentCodes()) {
            if (!(accountingEventPaymentCode instanceof InstallmentPaymentCode)) {
                accountingEventPaymentCode.setState(paymentCodeState);
            }
        }
    }

    public void changeInstallmentPaymentCodeState(final Installment installment, final PaymentCodeState paymentCodeState) {
        for (final AccountingEventPaymentCode paymentCode : getNonProcessedPaymentCodes()) {
            if (paymentCode instanceof InstallmentPaymentCode
                    && ((InstallmentPaymentCode) paymentCode).getInstallment() == installment) {
                paymentCode.setState(paymentCodeState);
            }
        }

        // If at least one installment is payed, we assume that the payment
        // will be based on installments
        changeGratuityTotalPaymentCodeState(PaymentCodeState.CANCELLED);
    }

    private AccountingEventPaymentCode createAccountingEventPaymentCode(final EntryDTO entryDTO, final Student student) {
        AccountingEventPaymentCode accountingEventPaymentCode =
                findEquivalentPaymentCodeInStudentCandidacy(entryDTO, AccountingEventPaymentCode.class, student);

        if (accountingEventPaymentCode != null) {
            accountingEventPaymentCode.setAccountingEvent(this);
            return accountingEventPaymentCode;
        }

        return AccountingEventPaymentCode.create(PaymentCodeType.GRATUITY_FIRST_INSTALLMENT, new YearMonthDay(),
                calculateFullPaymentCodeEndDate(), this, entryDTO.getAmountToPay(), entryDTO.getAmountToPay(),
                student.getPerson());
    }

    private InstallmentPaymentCode createInstallmentPaymentCode(final EntryWithInstallmentDTO entry, final Student student) {
        AccountingEventPaymentCode accountingEventPaymentCode =
                findEquivalentPaymentCodeInStudentCandidacy(entry, InstallmentPaymentCode.class, student);

        if (accountingEventPaymentCode != null) {
            accountingEventPaymentCode.setAccountingEvent(this);
            return (InstallmentPaymentCode) accountingEventPaymentCode;
        }

        return InstallmentPaymentCode.create(PaymentCodeType.GRATUITY_FIRST_INSTALLMENT, new YearMonthDay(),
                calculateInstallmentPaymentCodeEndDate(entry.getInstallment()), this, entry.getInstallment(),
                entry.getAmountToPay(), entry.getAmountToPay(), student);
    }

    private AccountingEventPaymentCode findEquivalentPaymentCodeInStudentCandidacy(final EntryDTO entry,
            final Class<? extends AccountingEventPaymentCode> whatForClazz, final Student student) {
        DegreeCurricularPlan degreeCurricularPlan = this.getStudentCurricularPlan().getDegreeCurricularPlan();
        ExecutionDegree executionDegree =
                degreeCurricularPlan.getExecutionDegreeByAcademicInterval(getExecutionYear().getAcademicInterval());
        StudentCandidacy studentCandidacy = student.getPerson().getStudentCandidacyForExecutionDegree(executionDegree);

        if (studentCandidacy == null) {
            return null;
        }

        for (PaymentCode paymentCode : studentCandidacy.getAvailablePaymentCodesSet()) {
            if (!paymentCode.isNew()) {
                continue;
            }

            if (!PaymentCodeType.GRATUITY_FIRST_INSTALLMENT.equals(paymentCode.getType())) {
                continue;
            }

            if (!whatForClazz.equals(paymentCode.getClass())) {
                continue;
            }

            AccountingEventPaymentCode accountingEventPaymentCode = (AccountingEventPaymentCode) paymentCode;
            if (accountingEventPaymentCode.getAccountingEvent() != null) {
                continue;
            }

            if (!(accountingEventPaymentCode instanceof InstallmentPaymentCode)) {
                return accountingEventPaymentCode;
            }

            InstallmentPaymentCode installmentPaymentCode = (InstallmentPaymentCode) accountingEventPaymentCode;
            EntryWithInstallmentDTO installmentEntryDTO = (EntryWithInstallmentDTO) entry;

            if (installmentPaymentCode.getInstallment() == installmentEntryDTO.getInstallment()) {
                return installmentPaymentCode;
            }

        }

        return null;
    }

    private Installment getFirstInstallment() {
        return getGratuityPaymentPlan().getFirstInstallment();
    }

    public DegreeCurricularPlanServiceAgreement getDegreeCurricularPlanServiceAgreement() {
        return (DegreeCurricularPlanServiceAgreement) getPerson().getServiceAgreementFor(getServiceAgreementTemplate());

    }

    @Override
    public boolean hasInstallments() {
        return true;
    }

    public InstallmentPaymentCode getInstallmentPaymentCodeFor(final Installment installment) {
        for (final AccountingEventPaymentCode paymentCode : calculatePaymentCodes()) {
            if (paymentCode instanceof InstallmentPaymentCode
                    && ((InstallmentPaymentCode) paymentCode).getInstallment() == installment) {
                return (InstallmentPaymentCode) paymentCode;
            }
        }

        return null;
    }

    public AccountingEventPaymentCode getTotalPaymentCode() {
        for (final AccountingEventPaymentCode paymentCode : calculatePaymentCodes()) {
            if (!(paymentCode instanceof InstallmentPaymentCode)) {
                return paymentCode;
            }
        }

        return null;
    }

    @Override
    protected Set<Entry> internalProcess(User responsibleUser, AccountingEventPaymentCode paymentCode, Money amountToPay,
            SibsTransactionDetailDTO transactionDetail) {
        return internalProcess(responsibleUser, Collections.singletonList(buildEntryDTOFrom(paymentCode, amountToPay)),
                transactionDetail);
    }

    private EntryDTO buildEntryDTOFrom(final AccountingEventPaymentCode paymentCode, final Money amountToPay) {
        if (paymentCode instanceof InstallmentPaymentCode) {
            return new EntryWithInstallmentDTO(EntryType.GRATUITY_FEE, this, amountToPay,
                    ((InstallmentPaymentCode) paymentCode).getInstallment());
        } else {
            return new EntryDTO(EntryType.GRATUITY_FEE, this, amountToPay);
        }
    }

    private boolean installmentIsInDebtToday(Installment installment) {
        return installmentIsInDebt(installment) && new YearMonthDay().isAfter(installment.getEndDate());
    }

    private boolean installmentIsInDebt(Installment installment) {
        return getGratuityPaymentPlan().isInstallmentInDebt(installment, this, new DateTime(),
                calculateDiscountPercentage(getGratuityPaymentPlan().calculateOriginalTotalAmount()));
    }

    private boolean hasAnyInstallmentInDebtToday() {
        for (final Installment installment : getInstallments()) {
            if (installmentIsInDebtToday(installment)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isInDebt() {
        return isOpen() && hasAnyInstallmentInDebtToday();
    }

    public InstallmentPenaltyExemption getInstallmentPenaltyExemptionFor(final Installment installment) {
        for (final Exemption exemption : getExemptionsSet()) {
            if (exemption instanceof InstallmentPenaltyExemption) {
                final InstallmentPenaltyExemption installmentPenaltyExemption = (InstallmentPenaltyExemption) exemption;
                if (installmentPenaltyExemption.getInstallment() == installment) {
                    return installmentPenaltyExemption;
                }
            }
        }

        return null;
    }

    public boolean hasPenaltyExemptionFor(final Installment installment) {
        for (final Exemption exemption : getExemptionsSet()) {
            if (exemption instanceof InstallmentPenaltyExemption) {
                if (((InstallmentPenaltyExemption) exemption).getInstallment() == installment) {
                    return true;
                }

            }
        }

        return false;
    }

    public List<Installment> getInstallments() {
        return getGratuityPaymentPlan().getInstallmentsSortedByEndDate();
    }

    @Override
    public boolean isExemptionAppliable() {
        return true;
    }

    public List<InstallmentPenaltyExemption> getInstallmentPenaltyExemptions() {
        final List<InstallmentPenaltyExemption> result = new ArrayList<InstallmentPenaltyExemption>();
        for (final Exemption exemption : getExemptionsSet()) {
            if (exemption instanceof InstallmentPenaltyExemption) {
                result.add((InstallmentPenaltyExemption) exemption);
            }
        }

        return result;
    }

    @Override
    public boolean isOtherPartiesPaymentsSupported() {
        return true;
    }

    @Override
    protected void disconnect() {
        if (hasCustomGratuityPaymentPlan()) {
            ((CustomGratuityPaymentPlan) super.getGratuityPaymentPlan()).delete();
        }
        super.setGratuityPaymentPlan(null);
        super.disconnect();
    }

    @Override
    public boolean isGratuityEventWithPaymentPlan() {
        return true;
    }

    public boolean hasCustomGratuityPaymentPlan() {
        return getGratuityPaymentPlan() != null && getGratuityPaymentPlan().isCustomGratuityPaymentPlan();
    }

    public boolean hasDefaultGratuityPaymentPlan() {
        return getGratuityPaymentPlan() != null && getGratuityPaymentPlan().isDefault();
    }

    public Money getPayedAmountLessPenalty() {
        if (isCancelled()) {
            throw new DomainException("error.accounting.Event.cannot.calculatePayedAmount.on.invalid.events");
        }

        final DateTime now = new DateTime();
        Money result = Money.ZERO;

        for (final Installment installment : getGratuityPaymentPlan().getInstallmentsSet()) {
            if (!getGratuityPaymentPlan().isInstallmentInDebt(installment, this, now, BigDecimal.ZERO)) {
                result = result.add(installment.getAmount());
            }
        }

        if (result.isPositive()) {
            result = result.subtract(getTotalDiscount());
        }

        return result.isPositive() ? result.add(getPayedAmountLessInstallments()) : getPayedAmountLessInstallments();
    }

    private Money getPayedAmountLessInstallments() {
        Money payedAmount = Money.ZERO;
        for (final AccountingTransaction transaction : getNonAdjustingTransactions()) {
            if (!transaction.isInstallment()) {
                payedAmount = payedAmount.add(transaction.getToAccountEntry().getAmountWithAdjustment());
            }
        }
        return payedAmount;
    }

    @Override
    public GratuityWithPaymentPlanPR getPostingRule() {
        return (GratuityWithPaymentPlanPR) super.getPostingRule();
    }

    @Override
    public Set<EntryType> getPossibleEntryTypesForDeposit() {
        return Collections.singleton(EntryType.GRATUITY_FEE);
    }

    @Override
    public boolean isPaymentPlanChangeAllowed() {
        return true;
    }

    @Override
    protected Map<LocalDate, Money> getDueDateAmountMap(DateTime when) {
        return getGratuityPaymentPlan().getInstallmentsSet().stream().filter( i -> i.calculateBaseAmount(this).greaterThan(Money.ZERO))
                   .collect(Collectors.toMap(i -> i.getEndDate(this), i -> i.calculateBaseAmount(this)));
    }

    @Override
    public Map<LocalDate, Boolean> getDueDatePenaltyExemptionMap(DateTime when) {
            return getPenaltyExemptionsFor(InstallmentPenaltyExemption.class).stream().map(InstallmentPenaltyExemption.class::cast)
                       .collect(Collectors.toMap(p1 -> p1.getInstallment().getEndDate(this), p -> Boolean.FALSE, (u,v) -> Boolean.FALSE));
    }

    @Override
    public PaymentPlan getPaymentPlan() {
        return getGratuityPaymentPlan();
    }
}
