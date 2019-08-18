<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - Job Categories</title>

<sql:query var="rs" dataSource="jdbc/workdb">
    select * from abuse where jobid = ?::integer;
    <sql:param value="${param.id}"/>
</sql:query>
<p>${rs.getRowsByIndex()[1][0]}</p>
<div class="rTable">
    <c:forEach items="${rs.rows}" var="row" varStatus="loop">
        <div class="rTableRow">
            <div class="rTableCell customBody">
                <fmt:formatDate pattern="dd-MMM" value="${row.datesubmitted}"/>
                <br>
                ${row.comment}
            
            </div>
        </div>
    </c:forEach>
</div>
<c:if test="${utilSession.pageNo > 0}">
    <a href="paginate.jsp?nav=back&page=${utilSession.pageNo - 1}" class="row save">&lt;&lt;Back</a>
</c:if>
<c:if test="${utilSession.pageNo < utilSession.pageCount}">
    <a href="paginate.jsp?nav=next&page=${utilSession.pageNo + 1}" class="row save">Next&gt;&gt;</a>
</c:if>
<%@include file="../includes/menu.jsp" %>
<%@include file="../includes/footer.jsp" %>
