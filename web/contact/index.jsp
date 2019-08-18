<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - My Enquiries</title>
    <sql:query var="rs" dataSource="jdbc/workdb">
        select * from contact where seekerid = ? order by id
        <sql:param value="${userSession.id}"/>
    </sql:query>
    <c:if test="${rs.getRowCount() == 0}">
        <jsp:setProperty name="utilSession" property="message" value="You have not made any enquiries"/>
    </c:if>

    <p>My Enquiries</p>
    <span class="message">${utilSession.message}</span>

    <jsp:setProperty name="utilSession" property="message" value=""/>
    <div class="content">
        <a href="create.jsp" class="row save">New Enquiry</a>
    </div>
    <div class="clearFloat"></div>
    <div class="rTable">
        <c:forEach items="${rs.rows}" var="row" varStatus="loop">
            <div class="rTableRow">
                <div class="rTableCell"><a href="view.jsp?id=${row.id}"><c:out escapeXml="false" value="${row.comment}"/></a></div>
            </div>
        </c:forEach>
    </div>
    <%@include file="../includes/menu.jsp" %>
    <%@include file="../includes/footer.jsp" %>
