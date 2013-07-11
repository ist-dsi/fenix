package net.sourceforge.fenixedu.domain.internship;

import net.sourceforge.fenixedu.domain.RootDomainObject;

import org.joda.time.Interval;

import pt.ist.fenixframework.Atomic;

public class InternshipCandidacySession extends InternshipCandidacySession_Base {
    public InternshipCandidacySession(Interval interval) {
        super();
        setCandidacyInterval(interval);
        setRootDomainObject(RootDomainObject.getInstance());
    }

    /**
     * This will deep erase the candidacy session, all candidacies created in
     * this session will be wiped out.
     */
    @Atomic
    public void delete() {
        for (; hasAnyInternshipCandidacy(); getInternshipCandidacy().get(0).delete()) {
            ;
        }
        setRootDomainObject(null);
        super.deleteDomainObject();
    }

    public String getPresentationName() {
        return getCandidacyInterval().getStart().toLocalDate() + "/" + getCandidacyInterval().getEnd().toLocalDate();
    }

    public static InternshipCandidacySession getMostRecentCandidacySession() {
        InternshipCandidacySession current = null;
        for (InternshipCandidacySession session : RootDomainObject.getInstance().getInternshipCandidacySessionSet()) {
            if (current == null || session.getCandidacyInterval().isAfter(current.getCandidacyInterval())) {
                current = session;
            }
        }
        return current;
    }
}
