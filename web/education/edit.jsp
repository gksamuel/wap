<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - Edit Education</title>
    <sql:query var="rs" dataSource="jdbc/workdb">
        select * from education where id = ?::integer order by id
        <sql:param value="${param.id}"/>
    </sql:query>
    <p>Edit Education</p>
    <span class="message">${utilSession.message}</span>
    <jsp:setProperty name="utilSession" property="message" value=""/>
    <c:forEach items="${rs.rows}" var="row">
        <div class="form">
            <form method="post" action="/m/educationActions">
                <input type="hidden" id="educationAction" name="educationAction" value="updateInstitution">
                <input type="hidden" id="id" name="id" value="${row.id}">
                    <span><label for="startmonth">Start Date</label></span>
                    <span>
                        <fmt:formatDate value="${row.startdate}" pattern="MMM" var="newdatevar" />
                        <select id="startmonth" name="startmonth">
                            <c:forEach items="${utilSession.monthList}" var="current">
                                <option value="${current.monthId}" ${current.month == newdatevar ? 'selected=selected' : ''}>${current.month}</option>
                            </c:forEach>
                        </select>
                        <fmt:formatDate value="${row.startdate}" pattern="yyyy" var="newdatevar" />
                        <select id="startyear" name="startyear">
                            <c:forEach items="${utilSession.yearList}" var="current">
                                <option value="${current.yearID}" ${current.yearID == newdatevar ? 'selected=selected' : ''}>${current.yearID}</option>
                            </c:forEach>
                        </select>
                    </span>
                    <span><label for="endmonth">End Date</label></span>
                    <span>
                        <fmt:formatDate value="${row.enddate}" pattern="MMM" var="newdatevar" />
                        <select id="endmonth" name="endmonth">
                            <c:forEach items="${utilSession.monthList}" var="current">
                                <option value="${current.monthId}" ${current.month == newdatevar ? 'selected=selected' : ''}>${current.month}</option>
                            </c:forEach>
                        </select>
                        <fmt:formatDate value="${row.enddate}" pattern="yyyy" var="newdatevar" />
                        <select id="endyear" name="endyear">
                            <c:forEach items="${utilSession.yearList}" var="current">
                                <option value="${current.yearID}" ${current.yearID == newdatevar ? 'selected=selected' : ''}>${current.yearID}</option>
                            </c:forEach>
                        </select>

                    </span>
                    <span><label for="institution">Institution *</label></span>
                    <span><input type="text" id="institution" name="institution" value="${row.institution}" maxlength="150"></span>
                    <span><label for="certification">Certification *</label></span>
                    <span><input type="text" id="certification" name="certification" value="${row.certification}" maxlength="100"></span>
                    <span><label for="website">Web Site</label></span>
                    <span><input type="text" id="website" name="website" value="${row.website}" maxlength="100"></span>
                    <span><label for="location">Location</label></span>
                    <span><input type="text" id="location" name="location" value="${row.location}" maxlength="100"></span>
                    <span><label for="refereename">Referee Name</label></span>
                    <span><input type="text" id="refereename" name="refereename" value="${row.refereename}" maxlength="100"></span>
                    <span><label for="address">Address</label></span>
                    <span><input type="text" id="address" name="address" value="${row.address}" maxlength="100"></span>
                    <span><label for="email">Email</span>
                    <span><input type="text" id="email" name="email" value="${row.email}" maxlength="100"></span>
                    <span><label for="mobileno">Mobile no</label></span>
                    <span><input type="text" id="mobileno" name="mobileno" value="${row.mobileno}"></span>
                    <span><input type="submit" value="Update" name="update" /></span>
            </form>
        </div>
    </c:forEach>
    <%@include file="../includes/menu.jsp" %>
    <%@include file="../includes/footer.jsp" %>
