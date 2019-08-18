<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - Job Categories</title>
    <sql:query var="rs" dataSource="jdbc/workdb">
        select c.id, c.name from jobs j join category c on c.id = j.categoryid where j.enddate > current_date group by c.id;
    </sql:query>
    <p>Job Categories</p>
    <div class="rTable">
        <div class="rTableRow">
            <div class="rTableCell"><a href="category.jsp?id=0"><c:out value="ALL JOBS"/></a></div>
        </div>
        <c:forEach items="${rs.rows}" var="row" varStatus="loop">
            <div class="rTableRow">
                <div class="rTableCell"><a href="category.jsp?id=${row.id}"><c:out value="${row.name}"/></a></div>
            </div>
        </c:forEach>
    </div>
    <%@include file="../includes/menu.jsp" %>
    <%@include file="../includes/footer.jsp" %>
