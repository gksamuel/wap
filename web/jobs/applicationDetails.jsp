<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - My Applications</title>
    <sql:query var="rs" dataSource="jdbc/workdb">
        select a.id, j.title , a.datemodified, a.viewed, a.comment, a.commentdate from applications a join jobs j on a.jobid = j.id where a.id = ?::integer and a.seekerid = ? limit 1
        <sql:param value="${param.id}"/>
        <sql:param value="${userSession.id}"/>
    </sql:query>
    <p>My Applications</p>
    <c:forEach items="${rs.rows}" var="row">
        <div class="rTable">
            <div class="rTableRow">
                <div class="rTableCell">Job:</div>
                <div class="rTableCell"><c:out value="${row.title}"/></div>
            </div>
            <div class="rTableRow">
                <div class="rTableCell">Date Modified:</div>
                <div class="rTableCell">                   
                   <fmt:formatDate pattern="dd-MMM-yyyy h:mm a" value="${row.datemodified}"/>
                </div>
            </div>
            <div class="rTableRow">
                <div class="rTableCell">Viewed:</div>
                <div class="rTableCell"><c:out value="${row.viewed == true ? 'Yes' : 'No'}"/></div>
            </div>
            <div class="rTableRow">
                <div class="rTableCell">Comment:</div>
                <div class="rTableCell"><c:out value="${row.comment}"/></div>
            </div>
            <div class="rTableRow">
                <div class="rTableCell">Comment Date:</div>
                <div class="rTableCell"><c:out value="${row.commentdate}"/></div>
            </div>
        </div>
    </c:forEach>
    <%@include file="../includes/menu.jsp" %>
    <%@include file="../includes/footer.jsp" %>
