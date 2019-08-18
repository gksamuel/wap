<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - Job Categories</title>
    <c:if test="${param.id >= 0}">
        <jsp:setProperty name="utilSession" property="jobCategory" value="${param.id}"/>
        <jsp:setProperty name="utilSession" property="pageNo" value="0"/>
        <c:choose>
            <c:when test="${param.id == 0}">
                <jsp:setProperty name="utilSession" property="symbol" value=">"/>
            </c:when>
            <c:when test="${param.id > 0}">
                <jsp:setProperty name="utilSession" property="symbol" value="="/>
            </c:when>
        </c:choose>
        <sql:query var="mj" dataSource="jdbc/workdb">
            select max(id) from jobs j where j.categoryid ${utilSession.symbol} ?::integer;
            <sql:param value="${utilSession.jobCategory}"/>
        </sql:query>
        <jsp:setProperty name="utilSession" property="maxJob" value="${mj.getRowsByIndex()[0][0]}"/>

        <sql:query var="mj" dataSource="jdbc/workdb">
            select min(id) from jobs j where j.categoryid ${utilSession.symbol} ?::integer;
            <sql:param value="${utilSession.jobCategory}"/>
        </sql:query>
        <jsp:setProperty name="utilSession" property="minJob" value="${mj.getRowsByIndex()[0][0]}"/>
    </c:if>
    <sql:query var="rc" dataSource="jdbc/workdb">
        select count(*) from jobs j join category c on j.categoryid = c.id where j.categoryid ${utilSession.symbol} ?::integer;
        <sql:param value="${utilSession.jobCategory}"/>
    </sql:query>
    <jsp:setProperty name="utilSession" property="pageCount" value="${rc.getRowsByIndex()[0][0]/10}"/>
    <c:if test="${param.nav == 'next'}">
        ${utilSession.addPageNo()}
    </c:if>
    <c:if test="${param.nav == 'back'}">
        ${utilSession.subPageNo()}
    </c:if>

    <sql:query var="rs" dataSource="jdbc/workdb">
        select c.name, j.id, j.title from jobs j join category c on j.categoryid = c.id where j.categoryid ${utilSession.symbol}  ?::integer order by j.id desc limit 10 offset ?::integer*10;
        <sql:param value="${utilSession.jobCategory}"/>
        <sql:param value="${utilSession.pageNo}"/>
    </sql:query>
    <p>${rs.getRowsByIndex()[1][0]}</p>
    <div class="rTable">
        <c:forEach items="${rs.rows}" var="row" varStatus="loop">
            <div class="rTableRow">
                <div class="rTableCell"><a href="job.jsp?id=${row.id}">${row.title}</a></div>
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
