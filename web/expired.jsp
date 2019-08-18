<%@include file="includes/header.jsp"%>
<title>${utilSession.title} - Membership Expired</title>
    <div class="content">
        <p>Membership Expired</p>
        <span class="message">${utilSession.message}</span>
        <jsp:setProperty name="utilSession" property="message" value=""/>
        <div class="one sidebar">
            <div class="widget">
                <ul>
                    <li class="customBody">
                        <sql:query var="rs" dataSource="jdbc/workdb">
                            select * from content where id = 5;
                        </sql:query>
                        <c:forEach items="${rs.rows}" var="row" varStatus="loop">
                            ${row.content}
                        </c:forEach>
                    </li>
            </div>
        </div>
        <div class="form">
            <form method="post" action="/m/MpesaActions">
                <input type="hidden" id="mpesaAction" name="mpesaAction" value="getPayment">
                <input type="hidden" id="id" name="id" value="${userSession.id}">
                <span><input type="text" id="mobileno" name="mobileno" value="${userSession.mobile}"/></span>
                <span><input type="submit" id="submit" name="submit" value="Submit"/></span>
            </form>
        </div>
        <sql:query var="rs" dataSource="jdbc/workdb">
            select * from mpesatarrifs;
        </sql:query>
        <div class="rTable">
            <div class="rTableHeading">
                <div class="rTableHead"><strong>Membership</strong></div>
                <div class="rTableHead"><strong>Amount</strong></div>
            </div>
            <c:forEach items="${rs.rows}" var="row" varStatus="loop">
                <div class="rTableRow">
                    <div class="rTableCell">${row.description}</a></div>
                    <div class="rTableCell">Ksh: ${row.membership}</div>
                </div>
            </c:forEach>
        </div>
    </div>
    <%@include file="includes/menu.jsp" %>
    <%@include file="includes/footer.jsp" %>
