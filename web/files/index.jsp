<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - My Files</title>
    <sql:query var="rs" dataSource="jdbc/workdb">
        select * from files where seekerid = ? order by id
        <sql:param value="${userSession.id}"/>
    </sql:query>
    <c:if test="${rs.getRowCount() == 0}">
        <jsp:setProperty name="utilSession" property="message" value="Upload your c.v, photos, certificates, sample work, etc here!"/>
    </c:if>
    <p>My Files</p>
    <span class="message">${utilSession.message}</span>
    <jsp:setProperty name="utilSession" property="message" value=""/>
    <div class="content">
        <a href="create.jsp" class="row save">New Entry</a>
    </div>
    <div class="clearFloat"></div>

    <div class="rTable">
        <c:forEach items="${rs.rows}" var="row" varStatus="loop">
            <div class="rTableRow">
                <div class="rTableCell"><c:out value="${row.originalfilename}"/></div>
                <div class="rTableCell"><a href="delete.jsp?id=${row.id}">Delete</a></div>
            </div>
        </c:forEach>
    </div>
    <%@include file="../includes/menu.jsp" %>
    <%@include file="../includes/footer.jsp" %>
