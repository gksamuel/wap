<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - Create Letter</title>

    <p>Create Letter</p>
 <span class="message">${utilSession.message}</span>
    <div class="form">
        <jsp:setProperty name="utilSession" property="message" value=""/>
        <form method="post" action="/m/letterActions">
            <input type="hidden" id="letterAction" name="letterAction" value="createLetter">
            <input type="hidden" id="id" name="id" value="${userSession.id}">
            <span><label for="name">Name</label></span>
            <span><input type="text" id="name" name="name" value="${row.name}" maxlength="100"></span>
            <span><label for="letter">Content</label></span>
            <span><textarea id="letter" name="letter">${row.letter}</textarea></span>
            <span><input type="submit" value="Create" name="Create" /></span>
        </form>
    </div>
    <%@include file="../includes/menu.jsp" %>
    <%@include file="../includes/footer.jsp" %>
