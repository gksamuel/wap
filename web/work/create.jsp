<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - Add Employment</title>
    <p>Add Employment</p>
 <span class="message">${utilSession.message}</span>
    <div class="form">
        <jsp:setProperty name="utilSession" property="message" value=""/>
        <form method="post" action="/m/workActions">
            <input type="hidden" id="profileAction" name="workActions" value="createWork">
            <input type="hidden" id="id" name="id" value="${userSession.id}">
            <span><label for="name">Start Date</label></span>
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
            <span><label for="name">End Date</label></span>
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

            <span><label for="company">Company *</label></span>
            <span><input type="text" id="company" name="company" value="" maxlength="100"></span>
            <span><label for="position">Job Title *</span>
            <span><input type="text" id="position" name="position" value="" maxlength="100"></span>
            <span><label for="website">Web Site</label></span>
            <span><input type="text" id="website" name="website" value="" maxlength="100"></span>
            <span><label for="address">Address</label></span>
            <span><input type="text" id="address" name="address" value="" maxlength="100"></span>
            <span><label for="companyphone">Company Phone</label></span>
            <span><input type="text" id="companyphone" name="companyphone" value="" maxlength="15"></span>
            <span><label for="location">Location</label></span>
            <span><input type="text" id="location" name="location" value="" maxlength="100"></span>
            <span><label for="jobdescription">Job Description</label></span>
            <span><input type="text" id="jobdescription" name="jobdescription" value="" maxlength="100"></span>
            <span><label for="refereename">Referee Name</label></span>
            <span><input type="text" id="refereename" name="refereename" value="" maxlength="100"></span>
            <span><label for="refereeemail">Referee Email</label></span>
            <span><input type="text" id="refereeemail" name="refereeemail" value="" maxlength="100"></span>
            <span><label for="refereemobile">Referee Mobile</label></span>
            <span><input type="text" id="refereemobile" name="refereemobile" value="" maxlength="15"></span>
            <span><input type="submit" value="Create" name="Create" /></span>
        </form>
    </div>
    <%@include file="../includes/menu.jsp" %>
    <%@include file="../includes/footer.jsp" %>
