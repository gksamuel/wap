<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - New Enquiry</title>
    <p>Create Page</p>
 <span class="message">${utilSession.message}</span>
    <jsp:setProperty name="utilSession" property="message" value=""/>
    <div class="form">
        <form method="post" action="/m/contactActions">
            <input type="hidden" id="pageAction" name="contactAction" value="createComment">
            <input type="hidden" id="id" name="id" value="${userSession.id}">
            <span><label for="comment">How can we help?</label></span>
            <span><textarea id="content" name="comment" maxlength="250">${row.content}</textarea></span>
            <span><input type="submit" value="Create" name="create" /></span>
        </form>
    </div>
    <%@include file="../includes/menu.jsp" %>
    <%@include file="../includes/footer.jsp" %>
