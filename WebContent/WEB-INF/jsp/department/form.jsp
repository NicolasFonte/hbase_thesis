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
            
                <legend>Adicionar Departamento</legend>
                
                <label for="name">Nome:</label>
                <input id="name" type="text" name="department.name"/>
                
                <label for="identifier">Id:</label>
                <input id="identifier" type="text" name="department.identifier"/>           
                
                
                <button type="submit">Enviar</button>
            
            </fieldset>
        </form>

    </body>
</html>
