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
<span class="message">${utilSession.message}</span>
<jsp:setProperty name="utilSession" property="message" value=""/>
<div class="one sidebar">
    <sql:query var="as" dataSource="jdbc/workdb">
        select * from abuse where jobid = ?::integer
        <sql:param value="${param.id}"/>
    </sql:query>
    <c:if test="${as.rowCount > 0}">
        <div class="widget customBody"><a href="feedback.jsp?id=${param.id}">We have ${as.rowCount} comments for this job</a></div>
    </c:if>
    <div class="widget customBody">
        <c:forEach items="${rs.rows}" var="row">
            <span><strong>${row.title}</strong></span><hr/>
            <span><strong>Summary</strong><br/>${row.summary}</span><br/>
            <span>${row.jobdescription}</span><br/><br/>
            <span>${row.experience}</span><br/><br/>
            <span>${row.instructions}</span><br/><br/>
            <span><a href="/m/jobs/apply.jsp?id=${row.id}" class="row save apply">Apply For This Job</a></span>
            <span><a href="/m/jobs/abuse.jsp?id=${row.id}" class="row save apply">Report Fraud</a></span>
        </c:forEach>
    </div>
</div>
<%@include file="../includes/menu.jsp" %>
<%@include file="../includes/footer.jsp" %>
