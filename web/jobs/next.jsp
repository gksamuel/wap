<%@include file="../includes/header.jsp" %>
${param.dir}
<c:choose>
    <c:when test="${param.dir == 'forward'}">
        <sql:query var="rs" dataSource="jdbc/workdb">
            select id from jobs j where j.categoryid ${utilSession.symbol} ?::integer and j.id < ?::integer order by id desc limit 1;
            <sql:param value="${utilSession.jobCategory}"/>
            <sql:param value="${param.jid}"/>
        </sql:query>    
    </c:when>
    <c:when test="${param.dir == 'back'}">
        <sql:query var="rs" dataSource="jdbc/workdb">
            select id from jobs j where j.categoryid ${utilSession.symbol} ?::integer and j.id > ?::integer order by id  limit 1;
            <sql:param value="${utilSession.jobCategory}"/>
            <sql:param value="${param.jid}"/>
        </sql:query>    
    </c:when>
</c:choose>
<c:redirect url="job.jsp?id=${rs.getRowsByIndex()[0][0]}"/>





