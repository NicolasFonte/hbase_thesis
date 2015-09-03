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
        <title>JSP Page</title>
    </head>
    <body>
    <h1>Lista de Empregados</h1>    
        <table width="300" border="1">            
                <tr>
                    <td>Nome</td>
                    <td>Email</td>
                    <td>Telefone</td>
                    <td>Departmento</td>                    
                </tr>
             <c:forEach items="${employees}" var="employee"> 
                <tr>
                    <td>${employee.name }</td>
                    <td>${employee.email }</td>
                    <td>${employee.phoneNumber }</td>
                    <td>${employee.departmentName }</td>                    
                    <td><a href="updatePage?email=${employee.email }">Editar</a></td> 
                    <td><a href="remove?email=${employee.email }">Remover</a></td>                    
                </tr>
             </c:forEach>        
    </table>

        <a href="/hbase/employee/form"> Adicionar </a>
        <br/>
        <a href="/hbase"> Voltar </a>
        
</body>
</html>
