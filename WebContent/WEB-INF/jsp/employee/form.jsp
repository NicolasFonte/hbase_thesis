<%-- 
    Document   : addEmployee
    Created on : 18/05/2013, 16:40:07
    Author     : nicolas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://s3.amazonaws.com/sd_css2/styleform.css"
                                 type="text/css" media="screen">
    </head>
    <body>
        <form action="added">
            <fieldset>
            
                <legend>Adicionar Empregado</legend>
                
                <label for="name">Nome:</label>
                <input id="name" type="text" name="employee.name"/>
                
                <label for="email">Email:</label>
                <input id="email" name="employee.email"/>
                
                <label for="phoneNumber">Telefone:</label>
                <input id="phoneNumber" type="text" name="employee.phoneNumber"/>
                
                <c:forEach items="${departments}" var="department"> 
                    ${department.name}: <input id="departmentName" type="radio" name="employee.departmentName" value="${department.name}"/>  
                </c:forEach>   
                
                <button type="submit">Enviar</button>
            
            </fieldset>
        </form>

    </body>
</html>
