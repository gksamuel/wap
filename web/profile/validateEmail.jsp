<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - Welcome</title>

<c:if test="${not empty param.id}" >
    <sql:update var="rs" dataSource="jdbc/workdb">
        update seekers set statusid = 2 where id = ?::integer
        <sql:param value="${param.id}"/>
    </sql:update>
    <c:if test="${rs == 1}" >
        <c:set target="${utilSession}" property="message" value="Your email address has been validated." />
    </c:if>
</c:if>

<span class="message">${utilSession.message}</span>
<jsp:setProperty name="utilSession" property="message" value=""/>


<%@include file="../includes/menu.jsp" %>
<%@include file="../includes/footer.jsp" %>