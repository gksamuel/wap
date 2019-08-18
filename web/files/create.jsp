<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - Create Letter</title>

    <p>Create Letter</p>
 <span class="message">${utilSession.message}</span>
    <div class="form">
        <jsp:setProperty name="utilSession" property="message" value=""/>
        <form name="fileupload" id="fileupload" action="/m/upload" method="post" enctype="multipart/form-data">
            <input type="hidden" id="id" name="id" value="${userSession.id}">
            <span><label for="description">Description</label></span>
            <span><input type="text" name="description" size="50" /></span>
            <span><label for="file">Select File</label></span>
            <span><input type="file" name="file" size="50" /></span>
            <span><label for="filecategoryid">File Type</label></span>
            <span>
                <sql:query var="fc" dataSource="jdbc/workdb">
                    select * from fileCategory;
                </sql:query>
                <select id="filecategoryid" name="filecategoryid">
                    <c:forEach items="${fc.rows}" var="record">
                        <option value="${record.id}"><c:out value="${record.name}" /></option>
                    </c:forEach>
                </select>
            </span>
            <span><label for="viewable">Public</label></span>
            <span>
                <select id="viewable" name="viewable">
                    <option value="yes"><c:out value="Public" /></option>
                    <option value="no"><c:out value="Private" /></option>
                </select>
            </span>
            <span> <input type="submit" value="Upload File"/></span>
        </form>
    </div>
    <%@include file="../includes/menu.jsp" %>
    <%@include file="../includes/footer.jsp" %>
