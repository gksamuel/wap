<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - Registration</title>
    <p>Registration</p>
    <span class="message">${utilSession.message}</span>
    <jsp:setProperty name="utilSession" property="message" value=""/>
    <%@include file="../includes/footer.jsp" %>
