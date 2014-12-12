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
package org.fenixedu.academic.domain.student;

import jvstm.cps.ConsistencyPredicate;

import org.fenixedu.academic.util.StudentPersonalDataAuthorizationChoice;
import org.fenixedu.bennu.core.domain.Bennu;
import org.joda.time.DateTime;

public class StudentDataShareAuthorization extends StudentDataShareAuthorization_Base {

    public StudentDataShareAuthorization() {
        super();
    }

    public StudentDataShareAuthorization(Student student, StudentPersonalDataAuthorizationChoice authorization) {
        this();
        init(student, authorization);
    }

    protected void init(Student student, StudentPersonalDataAuthorizationChoice authorization) {
        setStudent(student);
        setAuthorizationChoice(authorization);
        setSince(new DateTime());
    }

    @ConsistencyPredicate
    public final boolean isDateNotInTheFuture() {
        return !getSince().isAfterNow();
    }

    protected Bennu getRootDomainObject() {
        return getStudent().getRootDomainObject();
    }

    public boolean isStudentDataShareAuthorization() {
        return true;
    }

}