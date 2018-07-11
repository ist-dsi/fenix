package org.fenixedu.academic.domain.accounting.events.gratuity;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.fenixedu.academic.domain.ExecutionYear;
import org.fenixedu.academic.domain.Person;
import org.fenixedu.academic.domain.StudentCurricularPlan;
import org.fenixedu.academic.domain.accounting.EntryType;
import org.fenixedu.academic.domain.accounting.EventType;
import org.fenixedu.academic.domain.accounting.PostingRule;
import org.fenixedu.academic.domain.accounting.paymentPlanRules.IsAlienRule;
import org.fenixedu.academic.domain.accounting.postingRules.gratuity.PartialRegimePR;
import org.fenixedu.academic.domain.administrativeOffice.AdministrativeOffice;
import org.fenixedu.academic.domain.exceptions.DomainException;
import org.fenixedu.academic.domain.student.Registration;
import org.fenixedu.academic.domain.student.RegistrationRegime;
import org.fenixedu.academic.util.Bundle;
import org.fenixedu.academic.util.LabelFormatter;
import org.fenixedu.academic.util.Money;
import org.fenixedu.bennu.core.signals.DomainObjectEvent;
import org.fenixedu.bennu.core.signals.Signal;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import edu.emory.mathcs.backport.java.util.Collections;
import pt.ist.fenixframework.FenixFramework;

public class PartialRegimeEvent extends PartialRegimeEvent_Base {

    static {

        // create partial regime event when registration regime is created
        Signal.register(RegistrationRegime.SIGNAL_CREATED, (DomainObjectEvent<RegistrationRegime> wrapper) -> {
            create(wrapper.getInstance());
        });

        // block registration regime deletion if an event exists
        FenixFramework.getDomainModel().registerDeletionBlockerListener(RegistrationRegime.class, ((registrationRegime,
                collection) -> {
            final Registration registration = registrationRegime.getRegistration();
            ExecutionYear executionYear = registrationRegime.getExecutionYear();
            StudentCurricularPlan studentCurricularPlan = registration.getStudentCurricularPlan(executionYear);
            if (studentCurricularPlan != null) {
                if (studentCurricularPlan.getGratuityEvent(executionYear, PartialRegimeEvent.class).isPresent()) {
                    throw new DomainException("Can't remove registration regime, must remove event first.");
                }
            }
        }));

    }

    protected PartialRegimeEvent() {
        super();
    }

    protected PartialRegimeEvent(Person person, Registration registration, StudentCurricularPlan studentCurricularPlan, ExecutionYear executionYear, PartialRegimePR postingRule) {
        final AdministrativeOffice administrativeOffice = registration.getDegree().getAdministrativeOffice();
        init(administrativeOffice, person, studentCurricularPlan, executionYear);
        setEventPostingRule(postingRule);
    }

    public static Optional<PartialRegimeEvent> create(RegistrationRegime registrationRegime) {
        final Registration registration = registrationRegime.getRegistration();
        final ExecutionYear executionYear = registrationRegime.getExecutionYear();

        if (registration.isActive() && registration.hasToPayGratuityOrInsurance()) {
            final StudentCurricularPlan studentCurricularPlan = registration.getStudentCurricularPlan(executionYear);
            if (studentCurricularPlan != null) {
                studentCurricularPlan.getEnrolmentsByExecutionYear(executionYear).forEach(EnrolmentGratuityEvent::create);
                return Optional.ofNullable(studentCurricularPlan.getGratuityEvent(executionYear, PartialRegimeEvent.class, false)
                        .map(PartialRegimeEvent.class::cast).orElseGet(() -> {
                            try {
                                return FenixFramework
                                        .atomic(() -> create(registration.getPerson(), registration, studentCurricularPlan,
                                                executionYear,
                                                new IsAlienRule().isAppliableFor(studentCurricularPlan, executionYear)));
                            } catch (DomainException e) {
                                throw e;
                            } catch (Exception e) {
                                throw new DomainException("key.return.argument", e.getLocalizedMessage());
                            }

                        }));
            }
        }
        return Optional.empty();
    }

    public static PartialRegimeEvent create(Person person, Registration registration, StudentCurricularPlan studentCurricularPlan, ExecutionYear executionYear, boolean forAliens) {

        Set<PostingRule> postingRules = studentCurricularPlan.getDegreeCurricularPlan().getServiceAgreementTemplate()
                .getPostingRulesBy(EventType.PARTIAL_REGIME_GRATUITY, executionYear.getBeginLocalDate().toDateTimeAtStartOfDay(),
                        executionYear.getEndLocalDate().toDateTimeAtStartOfDay());

        if (!postingRules.stream().allMatch(PartialRegimePR.class::isInstance)) {
            throw cantCreateEvent();
        }

        PartialRegimePR partialRegimePR =
                postingRules.stream().map(PartialRegimePR.class::cast).filter(p -> p.isForAliens() == forAliens).findAny()
                        .orElseThrow(PartialRegimeEvent::cantCreateEvent);

        return studentCurricularPlan.getGratuityEvent(executionYear, PartialRegimeEvent.class).orElseGet(() -> {
            try {
                return FenixFramework
                        .atomic(() -> new PartialRegimeEvent(person, registration, studentCurricularPlan, executionYear,
                                partialRegimePR));
            } catch (Exception e) {
                throw cantCreateEvent(e);
            }
        });
    }

    private static DomainException cantCreateEvent() {
        return cantCreateEvent(null);
    }

    private static DomainException cantCreateEvent(Exception e) {
        throw new DomainException("Can't create PartialRegimeEvent", e == null ? "" : e.getLocalizedMessage());
    }

    @Override
    public PostingRule getPostingRule() {
        return super.getEventPostingRule();
    }

    @Override
    public EventType getEventType() {
        return getEventPostingRule().getEventType();
    }

    @Override
    public void setEventType(EventType eventType) {
        throw new UnsupportedOperationException("Can't define eventType");
    }

    @Override
    public Map getDueDateAmountMap(PostingRule postingRule, DateTime when) {
        Money amount = postingRule.calculateTotalAmountToPay(this, when);
        LocalDate dueDate =
                getWhenOccured().toLocalDate().plusDays(getEventPostingRule().getNumberOfDaysToStartApplyingInterest());
        return Collections.<LocalDate, Money>singletonMap(dueDate, amount);
    }

    @Override
    public LabelFormatter getDescriptionForEntryType(EntryType entryType) {
        final LabelFormatter result = new LabelFormatter();
        result.appendLabel(getEventType().getQualifiedName(), Bundle.ENUMERATION);
        if (getEventPostingRule().isForAliens()) {
            result.appendLabel(" (");
            result.appendLabel("label.forAliens", Bundle.APPLICATION);
            result.appendLabel(" )");
        }
        result.appendLabel(" - ");
        result.appendLabel(getStudentCurricularPlan().getName());
        result.appendLabel(" (");
        result.appendLabel(getExecutionYear().getQualifiedName());
        result.appendLabel(")");
        return result;
    }
}
