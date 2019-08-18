<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - Remove Cover Letters</title>
    <sql:query var="rs" dataSource="jdbc/workdb">
        select * from coverletters where id = ?::integer and seekerid = ?::integer
        <sql:param value="${param.id}"/>
        <sql:param value=" ${userSession.id}"/>
    </sql:query>
    <p>Remove Cover Letters</p>
    <c:choose>
        <c:when test="${rs.rowCount == 1}">
            <sql:update var="rs" dataSource="jdbc/workdb">
                delete from coverletters where id = ?::integer and seekerid = ?::integer
                <sql:param value="${param.id}"/>
                <sql:param value=" ${userSession.id}"/>
            </sql:update>
            <c:redirect url="index.jsp"/>
        </c:when>
        <c:otherwise>
            Could not complete the operation
        </c:otherwise>
    </c:choose>
    <%@include file="../includes/menu.jsp" %>
    <%@include file="../includes/footer.jsp" %>
