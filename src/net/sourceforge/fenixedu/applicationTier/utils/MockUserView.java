package net.sourceforge.fenixedu.applicationTier.utils;

import java.util.Collection;

import net.sourceforge.fenixedu.applicationTier.ICandidateView;
import net.sourceforge.fenixedu.applicationTier.IUserView;
import net.sourceforge.fenixedu.domain.Person;
import net.sourceforge.fenixedu.domain.person.RoleType;

public class MockUserView implements IUserView {

	final private Person person;

	final private String username;

	final private Collection roles;

	public MockUserView(final String username, final Collection roles, final Person person) {
		this.username = username;
		this.roles = roles;
		this.person = person;
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

	public Person getPerson() {
		return person;
	}
	
	public boolean isPublicRequester()
	{
		return false;
	}

}
