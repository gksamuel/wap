<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - Edit Letter</title>
<sql:query var="rs" dataSource="jdbc/workdb">
    select * from coverletters where id = ?::integer
    <sql:param value="${param.id}"/>
</sql:query>

<p>Edit Letter</p>
<span class="message">${utilSession.message}</span>
<jsp:setProperty name="utilSession" property="message" value=""/>
<c:forEach items="${rs.rows}" var="row">

    <div class="form">
        <jsp:setProperty name="utilSession" property="message" value=""/>
        <form method="post" action="/m/letterActions">
            <input type="hidden" id="letterAction" name="letterAction" value="updateLetter">
            <input type="hidden" id="id" name="id" value="${row.id}">
            <span><label for="name">Name</label></span>
            <span><input type="text" id="name" name="name" value="${row.name}" maxlength="100"></span>
            <span><label for="letter">Content</label></span>
            <span><textarea id="letter" name="letter">${row.letter}</textarea></span>
            <span><input type="submit" value="Update" name="Update" /></span>
        </form>
    </div>                







</c:forEach>
<%@include file="../includes/menu.jsp" %>
<%@include file="../includes/footer.jsp" %>
