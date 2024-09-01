<%--
  Created by IntelliJ IDEA.
  User: viktor_k
  Date: 29.08.2024
  Time: 21:11
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
<form action="${pageContext.request.contextPath}/login" method="post">
    <h1>Login</h1>
    <hr>
    <label for="email"><b>Email</b></label>
    <input type="text" placeholder="Enter Email" name="email" id="email" required>
    <label for="psw"><b>Password</b></label>
    <input type="password" placeholder="Enter Password" name="psw" id="psw" required>
    <hr>
    <button type="submit" >Login</button>
    <a href="${pageContext.request.contextPath}/registration">
        <button type = "button">Register</button>
    </a>
</form>
<c:if test="${param.error != null}">
    <div style = "color:red">
            <span>Email or password is not correct</span>
            <br>
    </div>
</c:if>
</body>
</html>

