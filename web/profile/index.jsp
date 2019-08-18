<%@include file="../includes/header.jsp" %>
<title>${utilSession.title} - My Profile</title>
    <sql:query var="rs" dataSource="jdbc/workdb">
        select * from seekers where id = ?
        <sql:param value="${userSession.id}"/>
    </sql:query>
    <p>My Profile</p>
    <span class="message">${utilSession.message}</span>
    <jsp:setProperty name="utilSession" property="message" value=""/>
    <c:forEach items="${rs.rows}" var="row">
        <div class="form">
            <form method="post" action="/m/seekerActions">
                <input type="hidden" id="profileAction" name="seekerAction" value="updateProfile">
                <input type="hidden" id="id" name="id" value="${row.id}">
                <span><label for="email">Email</label></span>
                <span><input type="text" id="email" name="email" value="${row.email}" disabled="disabled"></span>
                <span><label for="firstname">First Name *</label></span>
                <span><input type="text" id="firstname" name="firstname" value="${row.firstname}" maxlength="20"></span>
                <span><label for="lastname">Last Name *</label></span>
                <span><input type="text" id="lastname" name="lastname" value="${row.lastname}" maxlength="20"></span>
                <span><label for="mobile">Mobile</label></span>
                <span><input type="text" id="mobile" name="mobile" value="${row.mobile}"></span>
                <span><label for="othernumber">Other Number</label></span>
                <span><input type="text" id="othernumber" name="othernumber" value="${row.othernumber}"></span>
                <span><label for="middlename">Middle Name</label></label></span>
                <span><input type="text" id="middlename" name="middlename" value="${row.middlename}"></span>
                <span><label for="address">Address</label></span>
                <span><input type="text" id="address" name="address" value="${row.address}"></span>
                <span><label for="birthmonth">Date of Birth</label></span>
                <span>
                    <fmt:formatDate value="${row.dateofbirth}" pattern="MMM" var="newdatevar" />
                    <select id="birthmonth" name="birthmonth">
                        <c:forEach items="${utilSession.monthList}" var="current">
                            <option value="${current.monthId}" ${current.month == newdatevar ? 'selected=selected' : ''}>${current.month}</option>
                        </c:forEach>
                    </select>
                    <fmt:formatDate value="${row.dateofbirth}" pattern="yyyy" var="newdatevar" />
                    <select id="birthyear" name="birthyear">
                        <c:forEach items="${utilSession.yearList}" var="current">
                            <option value="${current.yearID}" ${current.yearID == newdatevar ? 'selected=selected' : ''}>${current.yearID}</option>
                        </c:forEach>
                    </select>
                </span>
                <span><label for="gender">Gender</label></span>
                <span>
                    <input type="radio" name="gender" value="true" ${row.gender == true ? 'checked' : ''}> Male<br>
                    <input type="radio" name="gender" value="false" ${row.gender == false ? 'checked' : ''}> Female
                </span>
                <span><label for="website">Web Site</label></span>
                <span><input type="text" id="website" name="website" value="${row.website}"></span>
                <span><label for="maritalstatus">Marital Status</label></span>
                <span><input type="text" id="maritalstatus" name="maritalstatus" value="${row.maritalstatus}"></span>
                <span><label for="idnumber">ID Number</label></span>
                <span><input type="text" id="idnumber" name="idnumber" value="${row.idnumber}"></span>
                <span><label for="pinnumber">Pin Number</label></span>
                <span><input type="text" id="pinnumber" name="pinnumber" value="${row.pinnumber}"></span>
                <span><label for="passport">Passport</label></span>
                <span><input type="text" id="passport" name="passport" value="${row.passport}"></span>
                <span><label for="driverslicence">Drivers Licence</label></span>
                <span><input type="text" id="driverslicence" name="driverslicence" value="${row.driverslicence}"></span>
                <span><label for="salaryid">Salary</label></span>
                <span>
                    <sql:query var="sal" dataSource="jdbc/workdb">
                        select * from salary;
                    </sql:query>
                    <select id="salaryid" name="salaryid">
                        <c:forEach items="${sal.rows}" var="record">
                            <option value="${record.id}"  ${record.id == row.salaryid ? 'selected=selected' : ''}><c:out value="${record.salaryrange}" /></option>
                        </c:forEach>
                    </select>
                </span>
                <span><input type="submit" value="Update" name="update" /></span>
            </form>
        </div>
    </c:forEach>
    <%@include file="../includes/menu.jsp" %>
    <%@include file="../includes/footer.jsp" %>
