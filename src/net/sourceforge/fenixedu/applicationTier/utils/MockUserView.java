package net.sourceforge.fenixedu.applicationTier.utils;

import java.util.Collection;

import net.sourceforge.fenixedu.applicationTier.ICandidateView;
import net.sourceforge.fenixedu.applicationTier.IUserView;
import net.sourceforge.fenixedu.domain.person.RoleType;

public class MockUserView implements IUserView {

	final private Integer personOID;

	final private String username;

	final private Collection roles;

	public MockUserView(final String username, final Collection roles, final Integer personOID) {
		this.username = username;
		this.roles = roles;
		this.personOID = personOID;
	}

	public String getUtilizador() {
		return username;
	}

	public String getFullName() {
		return null;
	}

	public Collection getRoles() {
		return roles;
	}

	public ICandidateView getCandidateView() {
		return null;
	}

	public boolean hasRoleType(RoleType roleType) {
		return false;
	}

	public void setCandidateView(ICandidateView candidateView) {
	}

	public Integer getPersonOID() {
		return personOID;
	}

}
