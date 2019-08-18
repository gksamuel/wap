<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - My Payments</title>
<sql:query var="rs" dataSource="jdbc/workdb">
    select * from mpesa where seekerid = ? and id = ?::integer
    <sql:param value="${userSession.id}"/>
    <sql:param value="${param.id}"/>
</sql:query>
<p>My Payments</p>
<table>
    <c:forEach items="${rs.rows}" var="row" varStatus="loop">
        <tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
            <td>
                Name:
            </td>
            <td>
                <c:out value="${row.name}"/>
            </td>
        </tr>
        <tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
            <td>
                Code:
            </td>
            <td>
                <c:out value="${row.code}"/>
            </td>
        </tr>
        <tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
            <td>
                Amount:
            </td>
            <td>
                <c:out value="${row.amount}"/>
            </td>
        </tr>
        <tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
            <td>
                Pay Time:
            </td>
            <td>
                <fmt:formatDate pattern="dd-MMM-yyyy h:mm a" value="${row.paytime}"/>
            </td>
        </tr>
        <tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
            <td>
                Receive Time:
            </td>
            <td>
                <fmt:formatDate pattern="dd-MMM-yyyy h:mm a" value="${row.receivetime}"/>
            </td>
        </tr>
        <tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
            <td>
                Process Time:
            </td>
            <td>
                <fmt:formatDate pattern="dd-MMM-yyyy h:mm a" value="${row.processtime}"/>
            </td>
        </tr>
        <tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
            <td>
                Old Expiry Date:
            </td>
            <td>
                <fmt:formatDate pattern="dd-MMM-yyyy h:mm a" value="${row.fromtime}"/>
            </td>
        </tr>
        <tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
            <td>
                New Expiry Date:
            </td>
            <td>
                <fmt:formatDate pattern="dd-MMM-yyyy h:mm a" value="${row.totime}"/>
            </td>
        </tr>
    </c:forEach>
</table>
<%@include file="../includes/menu.jsp" %>
<%@include file="../includes/footer.jsp" %>
