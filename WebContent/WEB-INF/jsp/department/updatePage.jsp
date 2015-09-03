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
                <legend>Editar Departmento </legend>
                
                <input type="hidden" name="department.identifier" value="${department.identifier }" />
                
                <label for="name">Nome:</label>
                <input id="name" type="text" name="department.name" value="${department.name }"/>                
                
                <button type="submit">Enviar</button>
            </fieldset>
        </form>

    </body>
</html>
