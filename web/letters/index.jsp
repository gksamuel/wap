<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - My Letters</title>
    <sql:query var="rs" dataSource="jdbc/workdb">
        select * from coverletters where seekerid = ? order by id
        <sql:param value="${userSession.id}"/>
    </sql:query>
    <c:if test="${rs.getRowCount() == 0}">
        <jsp:setProperty name="utilSession" property="message" value="Prepare your application letters in advance here"/>
    </c:if>
    <p>My Letters</p>
    <span class="message">${utilSession.message}</span>
    <jsp:setProperty name="utilSession" property="message" value=""/>
    <div class="content">
        <a href="create.jsp" class="row save">New Entry</a>
    </div>
    <div class="clearFloat"></div>
    <div class="rTable">
        <c:forEach items="${rs.rows}" var="row" varStatus="loop">
            <div class="rTableRow">
                <div class="rTableCell"><a href="edit.jsp?id=${row.id}"><c:out value="${row.name}"/></a></div>
                <div class="rTableCell"><a href="delete.jsp?id=${row.id}">Delete</a></div>
            </div>
        </c:forEach>
    </div>
    <%@include file="../includes/menu.jsp" %>
    <%@include file="../includes/footer.jsp" %>
