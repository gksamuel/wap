<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - Edit Page</title>

<sql:query var="rs" dataSource="jdbc/workdb">
    select * from contact where id = ?::integer
    <sql:param value="${param.id}"/>
</sql:query>

<p>Edit Pages</p>
<span class="message">${utilSession.message}</span>
<jsp:setProperty name="utilSession" property="message" value=""/>
<c:forEach items="${rs.rows}" var="row">
    <div class="form">
        <span><label>Enquiry</label></span>
        <span>
            <strong><fmt:formatDate pattern="dd-MMM-yy h:mm a" value="${row.commentdate}"/></strong><br/>
            ${row.comment}<br/>
        </span>
        <span>
            ${row.response}<br/>
        </span>
    </div>
</c:forEach>
<%@include file="../includes/menu.jsp" %>
<%@include file="../includes/footer.jsp" %>
