<%@include file="includes/header.jsp"%>
<title>${utilSession.title} - Welcome</title>
<c:if test="${userSession.status == 1}">
    <c:set target="${utilSession}" property="message" value="<a href='/m/validate'>Validate your email for better service.</a>" />
</c:if>
<span class="message">${utilSession.message}</span>
<jsp:setProperty name="utilSession" property="message" value=""/>
<div class="one sidebar">
    <div class="widget">
        <ul>
            <li class="customBody">
                Hi ${userSession.username},<br/>
                Welcome to Africa's most advanced jobs portal.<br/>
                Here we provide all the tools necessary to aid you to get you the job that you deserve.<br/>
                Feel free to let us know how we can improve the service.<br/>
                <br/>
                Kind regards,<br/>
                MyJob.co.ke Team
            </li>
    </div>
</div>
<%@include file="includes/menu.jsp" %>
<%@include file="includes/footer.jsp" %>
