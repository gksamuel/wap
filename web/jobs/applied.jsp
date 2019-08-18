<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - Job Application</title>
    <p>Job Application</p>
    <jsp:useBean id="jobSession" scope="session" class="sessions.Work" />
    <span class="message">${jobSession.message}</span>
    <jsp:setProperty name="jobSession" property="message" value=""/>
    <%@include file="../includes/menu.jsp" %>
    <%@include file="../includes/footer.jsp" %>
