<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - Job Details</title>
    <sql:query var="rs" dataSource="jdbc/workdb">
        select * from jobs where id = ?::integer
        <sql:param value="${param.id}"/>
    </sql:query>
    <c:if test="${utilSession.maxJob > param.id}">
        <a href="next.jsp?dir=back&jid=${param.id}" class="row save">&lt;&lt;Prev</a>
    </c:if>
    <c:if test="${param.id > utilSession.minJob}">
        <a href="next.jsp?dir=forward&jid=${param.id}" class="row save">Next&gt;&gt;</a>
    </c:if>
    <div class="clearFloat"></div>
    <p>Job Details - ${rs.getRowsByIndex()[0][4]}</p>
    <div class="one sidebar">
        <div class="widget customBody">
            <c:forEach items="${rs.rows}" var="row">
                <span><strong>${row.title}</strong></span><br/>
                <span>${row.jobdescription}</span><br/><br/>
                <span>${row.experience}</span><br/><br/>
                <span>${row.instructions}</span><br/><br/>
                <span><a href="/m/jobs/apply.jsp?id=${row.id}" class="row save">Apply For This Job</a></span>
            </c:forEach>
        </div>
    </div>
    <%@include file="../includes/menu.jsp" %>
    <%@include file="../includes/footer.jsp" %>
