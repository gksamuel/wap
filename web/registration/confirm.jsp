<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - Registration</title>
    <div class="content">
        <p>Registration</p>
     <span class="message">${utilSession.message}</span>
        <jsp:setProperty name="utilSession" property="message" value=""/>
        <div class="form">
            <form method="post" action="/m/registration">
                <input type="hidden" id="registerActions" name="registerActions" value="completeRegistration">
                <input type="hidden" id="code" name="code" value="${param.asdewedhyfse}">
                <input type="hidden" name="username" value="${param.username}"/>
                <div>                                
                    <span><label>Username</label></span>
                    <span>${param.username}</span>
                </div>                                
                <div>                                
                    <span><label for="email">Password</label></span>
                    <span><input type="password" name="password" value="" /></span>
                </div>                                

                <div>                                
                    <span><input type="submit" value="submit" /></span>
                </div>                                
            </form>
        </div>
    </div>
    <%@include file="../includes/footer.jsp" %>
