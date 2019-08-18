<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - Password Reset</title>
    <div class="content">
        <p>Password Reset</p>
        <span class="message">${utilSession.message}</span>
        <jsp:setProperty name="utilSession" property="message" value=""/>
        <div class="form">
            <form method="post" action="/m/registration">
                <input type="hidden" id="registerActions" name="registerActions" value="completeReset">
                <input type="hidden" id="code" name="code" value="${param.asdewedhyfse}">

                <div>
                    <span><label for="username">Username</label></span>
                    <span><input type="username" name="username" value="" /></span>
                </div>                
                <div>

                    <span><label for="password">New Password</label></span>
                    <span><input type="password" name="password" value="" /></span>
                </div>                
                <div>    

                    <span><label for="password2">Confirm Password</label></span>
                    <span><input type="password" name="password2" value="" /></span>

                </div>                
                <div>    
                    <span><input type="submit" value="submit" /></span>
                </div>                

            </form>
        </div>                
    </div>                
    <%@include file="../includes/footer.jsp" %>
