<%@include file="includes/header.jsp" %>
<title>${utilSession.title} - Login</title>
<script src="https://apis.google.com/js/platform.js" async="defer" defer="true"></script>
<script>
    function loginSeeker(googleUser) {
        onSignIn(googleUser, "seeker");
    }


    function onSignIn(googleUser, userType) {
        var profile = googleUser.getBasicProfile();
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (xhttp.readyState === 4) {
                if (xhttp.status === 200) {
                    window.location = xhttp.responseText;
                }
            }
        };
        var data = "token=" + googleUser.getAuthResponse().id_token;
        xhttp.open("POST", "/m/GoogleProcessor", true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.setRequestHeader("Content-length", data.length);
        xhttp.setRequestHeader("Connection", "close");
        xhttp.send(data);

    }
</script>
    <div class="content">
        <div class="form">
            <meta name="google-signin-client_id" content="832927615145-9mkei4omon7ir5v0qc93njq93ohi1br4.apps.googleusercontent.com"></meta>
            <div class="g-signin2"  data-width="200" data-height="30" data-longtitle="true" data-onsuccess="loginSeeker">
            </div>
            <hr/>
            <div class="form">
                <form method="post" action="login">
                    <div>
                        <span><label for="username">Username</label></span>
                        <span><input type="text" name="username" value="${seeker.username}" /></span>
                    </div>
                    <div>
                        <span><label for="password">Password</label></span>
                        <span><input type="password" name="password" value="" /></span>
                        <div>
                            <span><input class="submit" type="submit" value="Get In"/></span>
                        </div>
                    </div>
                </form>
            </div>
            <div class="three sidebar">
                <div class="widget regist">
                    <ul>
                        <li><a href="/m/registration/index.jsp">Register</a></li>
                        <li><a href="/m/registration/forgot.jsp">Forgot Password</a></li>

                    </ul>
                </div>
            </div>
            <%@include file="includes/footer.jsp" %>
