<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - Registration</title>
    <div class="content">
        <p>Registration</p>
        <span class="message">${utilSession.message}</span>
        <jsp:setProperty name="utilSession" property="message" value=""/>
        <div class="form">
            <form method="post" action="/m/registration">
                <input type="hidden" id="registerActions" name="registerActions" value="register">
                <div>                
                    <span><label for="email">Email</label></span>
                    <span><input type="text" id="email" name="email" value="" /></span>
                </div>                
                <div>                
                    <span><label for="username">Username</label></span>
                    <span><input type="text" id="username" name="username" value="" /></span>
                </div>                
                <div>                
                    <span><label for="password">Password</label></span>
                    <span><input type="password" id="password" name="password" value="" /></span>
                </div>                
                <div>                
                    <span><label for="password2">Confirm Password</label></span>
                    <span><input type="password" id="password2" name="password2" value="" /></span>
                </div>                
                <div>                
                    <span><input type="submit" value="submit" /></span>
                </div>                
            </form>
        </div>                
    </div>                
    <%@include file="../includes/footer.jsp" %>
