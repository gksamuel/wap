<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - My Password</title>
    <sql:query var="rs" dataSource="jdbc/workdb">
        select * from seekers where id = ? 
        <sql:param value="${userSession.id}"/>
    </sql:query>
    <p>My Password</p>
    <span class="message">${utilSession.message}</span>
    <jsp:setProperty name="utilSession" property="message" value=""/>
    <c:forEach items="${rs.rows}" var="row">
        <img src="https://myjob.co.ke:81/img/${row.photo}">
        <div class="form">
            <form method="post" action="/m/photo" enctype='multipart/form-data'>
                <input type="hidden" id="id" name="id" value="${row.id}">
                <span><label for="file">Select Photo</label></span>
                <span><input type="file" id="file" name="file"></span>
                <span><input type="submit" value="Upload" name="update" /></span>
            </form>
        </div>

    </c:forEach>
    <%@include file="../includes/menu.jsp" %>
    <%@include file="../includes/footer.jsp" %>
