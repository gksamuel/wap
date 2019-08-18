<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - Registration</title>
    <p>Registration</p>
    <span class="message">${utilSession.message}</span>
    <jsp:setProperty name="utilSession" property="message" value=""/>
    <div class="form">
        <form method="post" action="/m/registration">
            <input type="hidden" id="registerActions" name="registerActions" value="forgotPassword">
            <div>
                <span><label for="username">Email</label></span>
                <span><input type="text" name="email" value="" /></span>
            </div>                
            <div>
                <span><input type="submit" value="submit" /></span>
            </div>   
        </form>
    </div>
    <%@include file="../includes/footer.jsp" %>
