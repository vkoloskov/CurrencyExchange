<%--
  Created by IntelliJ IDEA.
  User: viktor_k
  Date: 27.08.2024
  Time: 20:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/registration" method="post">
    <h1>Register</h1>
    <p>Please fill in this form to create an account.</p>
    <hr>

    <label for="email"><b>Email</b></label>
    <input type="text" placeholder="Enter Email" name="email" id="email" required>

    <label for="psw"><b>Password</b></label>
    <input type="password" placeholder="Enter Password" name="psw" id="psw" required>
    <hr>
    <select id = "role" name="role">
    <c:forEach var="role" items = "${requestScope.roles}">
        <option value="${role}">${role}</option>
        <br/>
    </c:forEach>
    </select>
    <hr>
    <c:forEach var="gender" items="${requestScope.genders}">
        <input type = "radio" name="gender" VALUE="${gender}">${gender}
        <br/>
    </c:forEach>
    <hr>
    <button type="submit" class="registerbtn">Register</button>
</form>
<c:if test="${not empty requestScope.errors}">
    <div style = "color:red">
        <c:forEach var="error" items="${requestScope.errors}">
            <span>${error.message}</span>
            <br>
        </c:forEach>
    </div>
</c:if>
</body>
</html>
