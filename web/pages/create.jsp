<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - Create Page</title>
    <p>Create Page</p>
     <span class="message">${utilSession.message}</span>
    <jsp:setProperty name="utilSession" property="message" value=""/>
    <div class="form">
        <form method="post" action="/m/pageActions">
            <input type="hidden" id="pageAction" name="pageAction" value="createPage">
            <input type="hidden" id="id" name="id" value="${userSession.id}">
            <span><label for="title">Title</label></span>
            <span><input type="text" id="title" name="title" value="${row.title}" maxlength="100"></span>
            <span><label for="content">Content</label></span>
            <span><textarea id="content" name="content">${row.content}</textarea></span>
            <span><input type="submit" value="Create" name="create" /></span>
        </form>
    </div>
    <%@include file="../includes/menu.jsp" %>
    <%@include file="../includes/footer.jsp" %>
