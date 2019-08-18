<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - Remove Page</title>
    <sql:query var="rs" dataSource="jdbc/workdb">
        select * from pages where id = ?::integer and seekerid = ?::integer
        <sql:param value="${param.id}"/>
        <sql:param value=" ${userSession.id}"/>
    </sql:query>
    <p>Remove Page</p>
    <c:choose>
        <c:when test="${rs.rowCount == 1}">
            <sql:update var="rs" dataSource="jdbc/workdb">
                delete from pages where id = ?::integer and seekerid = ?::integer
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
