package net.sourceforge.fenixedu.applicationTier.Servico.manager;


import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.FenixServiceException;
import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.InvalidArgumentsServiceException;
import net.sourceforge.fenixedu.dataTransferObject.InfoDegreeCurricularPlanEditor;
import net.sourceforge.fenixedu.domain.Degree;
import pt.ist.fenixWebFramework.security.accessControl.Checked;
import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.FenixFramework;

public class InsertDegreeCurricularPlan {

    @Checked("RolePredicates.MANAGER_OR_OPERATOR_PREDICATE")
    @Atomic
    public static void run(InfoDegreeCurricularPlanEditor infoDcp) throws FenixServiceException {

        if (infoDcp.getInfoDegree().getExternalId() == null || infoDcp.getName() == null || infoDcp.getInitialDate() == null
                || infoDcp.getDegreeDuration() == null || infoDcp.getMinimalYearForOptionalCourses() == null) {
            throw new InvalidArgumentsServiceException();
        }

        final Degree degree = FenixFramework.getDomainObject(infoDcp.getInfoDegree().getExternalId());
        if (degree == null) {
            throw new FenixServiceException("error.degreeCurricularPlan.non.existing.degree");
        }

        degree.createPreBolonhaDegreeCurricularPlan(infoDcp.getName(), infoDcp.getState(), infoDcp.getInitialDate(),
                infoDcp.getEndDate(), infoDcp.getDegreeDuration(), infoDcp.getMinimalYearForOptionalCourses(),
                infoDcp.getNeededCredits(), infoDcp.getMarkType(), infoDcp.getNumerusClausus(), infoDcp.getAnotation(),
                infoDcp.getGradeScale());
    }

}