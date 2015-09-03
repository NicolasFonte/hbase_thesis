<%-- 
    Document   : employeesjsp
    Created on : 18/05/2013, 16:31:57
    Author     : nicolas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://s3.amazonaws.com/sd_css1/style.css"
                                 type="text/css" media="screen">
        <title>Departments</title>
    </head>
    <body>
    <h1> Lista de Departamentos </h1>
        <table width="300" border="1">            
                <tr>
                    <td>Id</td>
                    <td>Nome</td>
                </tr>
             <c:forEach items="${departments}" var="department"> 
                <tr>
                    <td>${department.identifier }</td>
                    <td>${department.name }</td>
                    <td><a href="updatePage?identifier=${department.identifier }">Editar</a></td> 
                    <td><a href="remove?identifier=${department.identifier }">Remover</a></td>                    
                </tr>
             </c:forEach>        
    </table>

        <a href="/hbase/department/form"> Adicionar </a>
        <br/>
        <a href="/hbase"> Voltar </a>
</body>
</html>
