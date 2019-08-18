<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - Edit Employment</title>
    <sql:query var="rs" dataSource="jdbc/workdb">
        select * from work where id = ?::integer
        <sql:param value="${param.id}"/>
    </sql:query>
    <p>Edit Employment</p>
    <span class="message">${utilSession.message}</span>
    <jsp:setProperty name="utilSession" property="message" value=""/>
    <c:forEach items="${rs.rows}" var="row">
        <div class="form">
            <form method="post" action="/m/workActions">
                <input type="hidden" id="profileAction" name="workActions" value="updateWork">
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
                <span><label for="company">Company *</label></span>
                <span><input type="text" id="company" name="company" value="${row.company}" maxlength="100"></span>
                <span><label for="position">Job Title *</label></span>
                <span><input type="text" id="position" name="position" value="${row.position}" maxlength="100"></span>
                <span><label for="website">Web Site</label></span>
                <span><input type="text" id="website" name="website" value="${row.website}" maxlength="100"></span>
                <span><label for="address">Address</label></span>
                <span><input type="text" id="address" name="address" value="${row.address}" maxlength="100"></span>
                <span><label for="companyphone">Company Phone</label></span>
                <span><input type="text" id="companyphone" name="companyphone" value="${row.companyphone}" maxlength="15"></span>
                <span><label for="location">Location</label></span>
                <span><input type="text" id="location" name="location" value="${row.location}" maxlength="100"></span>
                <span><label for="jobdescription">Job Description</label></span>
                <span><input type="text" id="jobdescription" name="jobdescription" value="${row.jobdescription}" maxlength="100"></span>
                <span><label for="refereename">Referee Name</label></span>
                <span><input type="text" id="refereename" name="refereename" value="${row.refereename}" maxlength="100"></span>
                <span><label for="refereeemail">Referee Email</label></span>
                <span><input type="text" id="refereeemail" name="refereeemail" value="${row.refereeemail}" maxlength="100"></span>
                <span><label for="refereemobile">Referee Mobile</label></span>
                <span><input type="text" id="refereemobile" name="refereemobile" value="${row.refereemobile}" maxlength="15"></span>
                <span><input type="submit" value="Update" name="update" /></span>
            </form>
            <div class="form">
            </c:forEach>
            <%@include file="../includes/menu.jsp" %>
            <%@include file="../includes/footer.jsp" %>
