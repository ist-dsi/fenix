<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@page import="net.sourceforge.fenixedu.domain.phd.PhdIndividualProgramDocumentType"%>
<%@page import="net.sourceforge.fenixedu.domain.phd.thesis.PhdThesisProcessBean"%>
<%@page import="net.sourceforge.fenixedu.domain.phd.PhdProgramDocumentUploadBean"%><html:xhtml/>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr" %>


<logic:present role="ACADEMIC_ADMINISTRATIVE_OFFICE">
<bean:define id="processId" name="process" property="externalId" />

<%-- ### Title #### --%>
<em><bean:message  key="label.phd.academicAdminOffice.breadcrumb" bundle="PHD_RESOURCES"/></em>
<h2><bean:message key="label.phd.request.public.thesis.presentation" bundle="PHD_RESOURCES" /></h2>
<%-- ### End of Title ### --%>


<%--  ###  Return Links / Steps Information(for multistep forms)  ### --%>
<html:link action="<%= "/phdIndividualProgramProcess.do?method=viewProcess&processId=" + processId.toString() %>">
	<bean:message bundle="PHD_RESOURCES" key="label.back"/>
</html:link>
<br/><br/>
<%--  ### Return Links / Steps Information (for multistep forms)  ### --%>

<%--  ### Error Messages  ### --%>
<jsp:include page="/phd/errorsAndMessages.jsp" />
<%--  ### End of Error Messages  ### --%>


<%--  ### Context Information (e.g. Person Information, Registration Information)  ### --%>
<strong><bean:message  key="label.phd.process" bundle="PHD_RESOURCES"/></strong>
<fr:view schema="AcademicAdminOffice.PhdIndividualProgramProcess.view" name="process">
	<fr:layout name="tabular">
		<fr:property name="classes" value="tstyle2 thlight mtop15" />
	</fr:layout>
</fr:view>

<br/>


<div class="warning1 mbottom05" style="width: 700px;">
	<table>
		<tr>
			<td><bean:message  key="label.phd.publicPresentationSeminar" bundle="PHD_RESOURCES"/>: </td>
			<td class="acenter">
				<html:img src="<%= request.getContextPath() + ((Boolean) request.getAttribute("hasPublicPresentationSeminar") ?  "/images/correct.gif"  : "/images/incorrect.gif" )%>"/>
			</td>
		</tr>
		<tr>
			<td><bean:message  key="label.phd.public.presentation.seminar.report.document" bundle="PHD_RESOURCES"/> <bean:message  key="label.phd.publicPresentationSeminar" bundle="PHD_RESOURCES"/>: </td>
			<td class="acenter">
				<html:img src="<%= request.getContextPath() + ((Boolean) request.getAttribute("hasPublicPresentationSeminarReport") ?  "/images/correct.gif"  : "/images/incorrect.gif" )%>"/>
			</td>
		</tr>
		<tr>
			<td><bean:message  key="label.phd.qualification.exams" bundle="PHD_RESOURCES"/>: </td>
			<td class="acenter">
				<html:img src="<%= request.getContextPath() + ((Boolean) request.getAttribute("hasQualificationExamsToPerform") ?  "/images/incorrect.gif"  : "/images/correct.gif" )%>"/>
			</td>
		</tr>
		<tr>
			<td><bean:message  key="label.phd.school.part" bundle="PHD_RESOURCES"/>: </td>
			<td class="acenter">
				<html:img src="<%= request.getContextPath() + ((Boolean) request.getAttribute("hasSchoolPartConcluded") ?  "/images/correct.gif"  : "/images/incorrect.gif" )%>"/>
			</td>
		</tr>
	</table>
</div>

<%--  ### End Of Context Information  ### --%>

<br/>

<%--  ### Operation Area (e.g. Create Candidacy)  ### --%>

<bean:define id="requestPublicThesisPresentation" name="requestPublicThesisPresentation" />

<fr:form action="<%="/phdIndividualProgramProcess.do?processId=" + processId.toString() %>" encoding="multipart/form-data">

<input type="hidden" name="method" value="" />

<fr:edit id="requestPublicThesisPresentation" name="requestPublicThesisPresentation" visible="false" />

<fr:edit id="requestPublicThesisPresentation.edit.documents" name="requestPublicThesisPresentation" property="documents">
	
	<fr:schema type="<%= PhdProgramDocumentUploadBean.class.getName() %>" bundle="PHD_RESOURCES">
		<fr:slot name="type" readOnly="true" key="label.net.sourceforge.fenixedu.domain.phd.PhdProgramDocumentUploadBean.type"/>
		<fr:slot name="file" key="label.net.sourceforge.fenixedu.domain.phd.PhdProgramDocumentUploadBean.file">
			<fr:property name="fileNameSlot" value="filename"/>
			<fr:property name="size" value="20"/>
		</fr:slot>
	</fr:schema>
	
	<fr:layout name="tabular-editable">
		<fr:property name="classes" value="tstyle5 thlight thright mtop05" />
		<fr:property name="columnClasses" value=",,tdclear tderror1" />
		<fr:destination name="invalid" path="<%="/phdIndividualProgramProcess.do?method=prepareRequestPublicThesisPresentationInvalid&processId=" + processId.toString() %>" />
	</fr:layout>
</fr:edit>

<fr:edit id="requestPublicThesisPresentation.edit.remarks" name="requestPublicThesisPresentation">
	
	<fr:schema type="<%= PhdThesisProcessBean.class.getName() %>" bundle="PHD_RESOURCES">
		<fr:slot name="remarks" layout="longText">
			<fr:property name="columns" value="80"/>
			<fr:property name="rows" value="8"/>
		</fr:slot>
	</fr:schema>
	
	<fr:layout name="tabular">
		<fr:property name="classes" value="tstyle5 thlight thright mtop05" />
		<fr:property name="columnClasses" value=",,tdclear tderror1" />
		<fr:destination name="invalid" path="<%="/phdIndividualProgramProcess.do?method=prepareRequestPublicThesisPresentationInvalid&processId=" + processId.toString() %>" />
	</fr:layout>
</fr:edit>



<html:submit bundle="HTMLALT_RESOURCES" altKey="submit.submit" onclick="this.form.method.value='requestPublicThesisPresentation';"><bean:message bundle="PHD_RESOURCES" key="label.submit"/></html:submit>
<html:cancel bundle="HTMLALT_RESOURCES" altKey="cancel.cancel" onclick="this.form.method.value='viewProcess';"><bean:message bundle="PHD_RESOURCES" key="label.cancel"/></html:cancel>

</fr:form>

<%--  ### End of Operation Area  ### --%>



<%--  ### Buttons (e.g. Submit)  ### --%>

<%--  ### End of Buttons (e.g. Submit)  ### --%>


</logic:present>
