<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - Edit Page</title>

    <sql:query var="rs" dataSource="jdbc/workdb">
        select * from pages where id = ?::integer
        <sql:param value="${param.id}"/>
    </sql:query>

    <p>Edit Pages</p>
<span class="message">${utilSession.message}</span>
    <jsp:setProperty name="utilSession" property="message" value=""/>
    <c:forEach items="${rs.rows}" var="row">
        <div class="form">
            <form method="post" action="/m/pageActions">
                <input type="hidden" id="pageAction" name="pageAction" value="updatePage">
                <input type="hidden" id="id" name="id" value="${row.id}">
                <span><label for="title">Title</label></span>
                <span><input type="text" id="title" name="title" value="${row.title}" maxlength="100"></span>
                <span><label for="content">Content</label></span>
                <span><textarea id="content" name="content">${row.content}</textarea></span>
                <span><input type="submit" value="Update" name="update" /></span>
            </form>
        </div>
    </c:forEach>
    <%@include file="../includes/menu.jsp" %>
    <%@include file="../includes/footer.jsp" %>
