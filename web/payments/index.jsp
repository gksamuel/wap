<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - My Payments</title>
    <sql:query var="rs" dataSource="jdbc/workdb">
        select * from mpesa where seekerid = ? order by id desc
        <sql:param value="${userSession.id}"/>
    </sql:query>
    <c:if test="${rs.getRowCount() == 0}">
        <jsp:setProperty name="utilSession" property="message" value="You have not made any payments"/>
    </c:if>
    <p>My Payments</p>
    <span class="message">${utilSession.message}</span>
    <jsp:setProperty name="utilSession" property="message" value=""/>
    <a href="create.jsp" class="row save">New Payment</a>
    <div class="clearFloat"></div>
    <div class="rTable">
        <c:forEach items="${rs.rows}" var="row" varStatus="loop">
            <div class="rTableRow">
                <div class="rTableCell"><a href="view.jsp?id=${row.id}"><fmt:formatDate pattern="dd-MMM-yy h:mm a" value="${row.processtime}"/></a></div>
                <div class="rTableCell"><a href="view.jsp?id=${row.id}">View</a></div>
            </div>
        </c:forEach>
    </div>
    <%@include file="../includes/menu.jsp" %>
    <%@include file="../includes/footer.jsp" %>
