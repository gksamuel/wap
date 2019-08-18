<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - Job Categories</title>
</head>
<body>
    <p>Search</p>
    <div class="rTable">
        <c:if test="${searchSession.recordCount > 0}">
            <c:forEach var="Jobs" items="${searchSession.jobList}">
                <div class="rTableRow">
                    <div class="rTableCell"><a href="searchDetails.jsp?id=${Jobs.id}"><c:out value="${Jobs.title}"/></a></div>
                </div>
            </c:forEach>
        </c:if>
        <c:if test="${searchSession.recordCount == 0}">
            <span class="message">no records found please try again</span>
        </c:if>
    </div>
    <%@include file="../includes/menu.jsp" %>
    <%@include file="../includes/footer.jsp" %>
