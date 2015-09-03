<%-- 
    Document   : update
    Created on : 30/05/2013, 13:45:04
    Author     : nicolas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="update">
            <fieldset>
                <legend>Editar Empregado</legend>
                
                <input type="hidden" name="employee.email" value="${employee.email }" />
                
                <label for="name">Nome:</label>
                <input id="name" type="text" name="employee.name" value="${employee.name }"/>
                
                <label for="phoneNumber">phoneNumber:</label>
                <input id="phoneNumber" type="text" name="employee.phoneNumber" value="${employee.phoneNumber }"/>

                <button type="submit">Enviar</button>
            </fieldset>
        </form>

    </body>
</html>
