<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - My Applications</title>
    <sql:query var="rs" dataSource="jdbc/workdb">
        select a.id, j.title , a.datemodified, a.viewed from applications a join jobs j on a.jobid = j.id where a.id = ?::integer and a.seekerid = ? limit 1
        <sql:param value="${param.id}"/>
        <sql:param value="${userSession.id}"/>
    </sql:query>
    <p>My Applications</p>

    <c:if test="${rs.rowCount == 1}">
        <c:forEach items="${rs.rows}" var="row">
            <c:if test="${row.viewed == false}">
                <span>Confirm you want to withdraw the following application</span>
                <div class="rTable">
                    <div class="rTableRow ${row.viewed == true ? 'green' : ''}">
                        <div class="rTableCell"><a href="applicationDetails.jsp?id=${row.id}"><c:out value="${row.title}"/></div>
                        <div class="rTableCell"><a href="withdrawApplication.jsp?id=${row.id}">Withdraw</a></div>
                    </div>
                </div>
            </c:if>
            <c:if test="${row.viewed == true}">
                Cannot withdraw application that has been viewed
            </c:if>
        </c:forEach>
    </c:if>
    <%@include file="../includes/menu.jsp" %>
    <%@include file="../includes/footer.jsp" %>
