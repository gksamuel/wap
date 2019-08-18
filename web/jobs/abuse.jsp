<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - Create Page</title>
<p>Report Fraud</p>
  <span class="message">${utilSession.message}</span>
<jsp:setProperty name="utilSession" property="message" value=""/>
<div class="form">
    <form method="post" action="/m/jobActions">
        <input type="hidden" id="jobAction" name="jobAction" value="reportAbuse">
        <input type="hidden" id="jobid" name="jobid" value="${param.id}">
        <span><label for="comment">Your Comment</label></span>
        <span><textarea id="comment" name="comment" maxlength="250">${row.comment}</textarea></span>
        <span><input type="submit" value="Submit" name="create"/></span>
    </form>
</div>
<%@include file="../includes/menu.jsp" %>
<%@include file="../includes/footer.jsp" %>
