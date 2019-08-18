<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - My Education</title>
    <sql:query var="rs" dataSource="jdbc/workdb">
        select * from content where id = ?::integer limit 1;
        <sql:param value="${param.id}"/>
    </sql:query>
    <div class="one sidebar">
        <div class="widget">
            <ul>
                <li class="customBody">
                    <c:if test="${rs.rowCount == 1}">
                        <p>${rs.getRowsByIndex()[0][1]}</p>
                        ${rs.getRowsByIndex()[0][2]}
                    </c:if>
                </li>
        </div>
    </div>
    <%@include file="../includes/menu.jsp" %>
    <%@include file="../includes/footer.jsp" %>
