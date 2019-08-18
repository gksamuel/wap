<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - Create Education</title>
    <p>Create Education</p>
    $<span class="message">${utilSession.message}</span>
    <jsp:setProperty name="utilSession" property="message" value=""/>

    <div class="form">
        <form method="post" action="/m/educationActions">
            <input type="hidden" id="educationAction" name="educationAction" value="createInstitution">
            <span><input type="hidden" id="id" name="id" value="${userSession.id}"></span>
            <span><label for="startmonth">Start Date</label></span>
            <span>
                <select id="startmonth" name="startmonth">
                    <c:forEach items="${utilSession.monthList}" var="current">
                        <option value="<c:out value="${current.monthId}" />"><c:out value="${current.month}" /></option>
                    </c:forEach>
                </select>
                <select id="startyear" name="startyear">
                    <c:forEach items="${utilSession.yearList}" var="current">
                        <option value="<c:out value="${current.yearID}" />"><c:out value="${current.yearID}" /></option>
                    </c:forEach>
                </select>
            </span>
            <span><label for="endmonth">End Date</label></span>
            <span>
                <select id="endmonth" name="endmonth">
                    <c:forEach items="${utilSession.monthList}" var="current">
                        <option value="<c:out value="${current.monthId}" />"><c:out value="${current.month}" /></option>
                    </c:forEach>
                </select>
                <select id="endyear" name="endyear">
                    <c:forEach items="${utilSession.yearList}" var="current">
                        <option value="<c:out value="${current.yearID}" />"><c:out value="${current.yearID}" /></option>
                    </c:forEach>
                </select>
            </span>
            <span><label for="institution">Institution *</label></span>
            <span><input type="text" id="institution" name="institution" value="" maxlength="150"></span>
            <span><label for="certification">Certification *</label></span>
            <span><input type="text" id="certification" name="certification" value="" maxlength="100"></span>
            <span><label for="website">Web Site</label></span>
            <span><input type="text" id="website" name="website" value="" maxlength="100"></span>
            <span><label for="location">Location</label></span>
            <span><input type="text" id="location" name="location" value="" maxlength="100"></span>
            <span><label for="refereename">Referee Name</label></span>
            <span><input type="text" id="refereename" name="refereename" value="" maxlength="100"></span>
            <span><label for="address">Address</label></span>
            <span><input type="text" id="address" name="address" value="" maxlength="100"></span>
            <span><label for="email">Email</label></span>
            <span><input type="text" id="email" name="email" value="" maxlength="100"></span>
            <span><label for="mobileno">Mobile no</label></span>
            <span><input type="text" id="mobileno" name="mobileno" value="" maxlength="100"></span>
            <span><input type="submit" value="Create" name="Create" /></span>
            </table>
        </form>
    </div>
    <%@include file="../includes/menu.jsp" %>
    <%@include file="../includes/footer.jsp" %>
