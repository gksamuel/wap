<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - My Applications</title>
    <sql:query var="rs" dataSource="jdbc/workdb">
        select a.id, j.title , a.datemodified, a.viewed from applications a join jobs j on a.jobid = j.id where a.seekerid = ? order by id desc
        <sql:param value="${userSession.id}"/>
    </sql:query>
    <c:if test="${rs.getRowCount() == 0}">
        <jsp:setProperty name="utilSession" property="message" value="You have not applied for any jobs"/>
    </c:if>
    <p>My Applications</p>
    <span class="message">${utilSession.message}</span>
    <c:if test="${rs.getRowCount() > 0}">
        <span class="apgreen">Green means somebody has looked at your application</span>
    </c:if>
    <jsp:setProperty name="utilSession" property="message" value=""/>

    <div class="clearFloat"></div>
    <div class="rTable">
        <c:forEach items="${rs.rows}" var="row" varStatus="loop">
            <div class="rTableRow ${row.viewed == true ? 'green' : ''}">
                <div class="rTableCell"><a href="applicationDetails.jsp?id=${row.id}"><c:out value="${row.title}"/></a></div>
                <div class="rTableCell">                   
                   <fmt:formatDate pattern="dd-MMM-yy h:mm a" value="${row.datemodified}"/>
                </div>
                <div class="rTableCell"><a href="confirmWithdraw.jsp?id=${row.id}">Withdraw</a></div>
            </div>
        </c:forEach>
    </div>
    <%@include file="../includes/menu.jsp" %>
    <%@include file="../includes/footer.jsp" %>
