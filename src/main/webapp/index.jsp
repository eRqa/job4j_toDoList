<%@ page import="ru.job4j.models.Item" %>
<%@ page import="ru.job4j.HbmToDoList" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>

    <title>Список задач</title>
</head>
<body>
<script src="https://code.jquery.com/jquery-3.4.1.min.js" ></script>
<%
    String id = request.getParameter("id");
    Item item = new Item(0, "", new Date(), false);
    if (id != null) {
        item = HbmToDoList.instOf().findItemById(Integer.parseInt(id));
    }
%>
<script>
    $(document).ready(function () {
        fillInTable()
    });

    function fillInTable() {
        $.ajax({
            type: 'GET',
            url: "http://localhost:8080/job4j_toDoList/index.do",
            dataType: 'json',
            success: function (data) {
                $("#idBodyItems").empty();
                for (let i = 0; i <= data.length; i++) {
                    addItemToTable(data[i]);
                }
            }
        });
    }
    function addItemToTable(data) {
        $('#idBodyItems').append(
            '<tr>\n'
            + '<td>' + data.description + '</td>\n'
            + '<td>' + data.done + '</td>\n'
            + '</tr>\n"')
    }

</script>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Новая задача
            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/index.do?id=<%=item.getId()%>" method="post">
                    <div class="form-group">
                        <label>Описание</label>
                        <input type="text" class="form-control" name="name">
                    </div>
                    <button type="submit" class="btn btn-primary">Добавить</button>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Тудушки
            </div>
            <div class="card-body">
                <table class="table table-sm top-buffer">
                    <thead>
                    <tr>
                        <th scope="col">Описание</th>
                        <th scope="col">Выполнено</th>
                    </tr>
                    </thead>
                    <tbody id="idBodyItems">
                    <%--                    <c:forEach items="${tasks}" var="task">--%>
                    <%--                        <tr>--%>
                    <%--                            <td>--%>
                    <%--                                <c:out value="${task.description}"/>--%>
                    <%--                            </td>--%>
                    <%--                            <td>--%>
                    <%--                                <c:out value="${task.done}"/>--%>
                    <%--                            </td>--%>
                    <%--                        </tr>--%>
                    <%--                    </c:forEach>--%>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>