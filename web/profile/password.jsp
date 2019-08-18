<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - My Password</title>
    <sql:query var="rs" dataSource="jdbc/workdb">
        select * from seekers where id = ? 
        <sql:param value="${userSession.id}"/>
    </sql:query>
    <p>My Password</p>
    <span class="message">${utilSession.message}</span>
    <jsp:setProperty name="utilSession" property="message" value=""/>
    <c:forEach items="${rs.rows}" var="row">
        <div class="form">
            <form method="post" action="/m/seekerActions">
                <input type="hidden" id="profileAction" name="seekerAction" value="updatePassword">
                <input type="hidden" id="id" name="id" value="${row.id}">
                <span><label for="oldPassword">Old Password</label></span>
                <span><input type="password" id="oldPassword" name="oldPassword" value="" maxlength="20"></span>
                <span><label for="newPassword">New Password</label></span>
                <span><input type="password" id="newPassword" name="newPassword" value="" maxlength="20"></span>
                <span><label for="newPassword2">Re-enter Password</label></span>
                <span><input type="password" id="newPassword2" name="newPassword2" value="" maxlength="20"></span>

                <span><input type="submit" value="Update" name="update" /></span>
            </form>
        </div>

    </c:forEach>
    <%@include file="../includes/menu.jsp" %>
    <%@include file="../includes/footer.jsp" %>
