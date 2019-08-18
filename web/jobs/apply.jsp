<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - Job Application</title>
    <sql:query var="rs" dataSource="jdbc/workdb">
        select * from jobs where id = ?::integer
        <sql:param value="${param.id}"/>
    </sql:query>
    <p>Job Application - ${rs.getRowsByIndex()[0][4]}</p>
    <c:forEach items="${rs.rows}" var="row">
        <div class="form">
            <form method="post" action="/m/jobActions">
                <input type="hidden" id="jobid" name="jobid" value="${param.id}">
                <input type="hidden" id="jobAction" name="jobAction" value="applyJob">
                <strong>${row.title}</strong>
                <span><label for="letter">Select Application Letter</label></span>
                <sql:query var="cl" dataSource="jdbc/workdb">
                    select id , name from coverletters where seekerid = ?::integer
                    <sql:param value="${userSession.id}"/>
                </sql:query>
                <select id="letter" name="letter">
                    <option value="0">Select Application Letter</option>
                    <c:forEach items="${cl.rows}" var="c">
                        <option value="${c.id}">${c.name}</option>
                    </c:forEach>
                </select>
                <input type="submit" value="Send Application" name="update" />
            </form>
        </div>
    </c:forEach>
    <%@include file="../includes/menu.jsp" %>
    <%@include file="../includes/footer.jsp" %>
