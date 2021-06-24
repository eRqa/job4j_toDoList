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
<script>
    $(document).ready(function () {
        fillInTable();
        fillInTableCategories();
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

    function fillInTableCategories() {
        $.ajax({
            type: 'GET',
            url: "http://localhost:8080/job4j_toDoList/categories.do",
            dataType: 'json',
            success: function (data) {
                $("#cIds").empty();
                for (let i = 0; i <= data.length; i++) {
                    addCategoryToTable(data[i]);
                }
            }
        });
    }

    function fillInTableCompletedOnly() {
        $.ajax({
            type: 'GET',
            url: "http://localhost:8080/job4j_toDoList/index.do",
            data: { completedOnly : $("#completedOnly").is(':checked') },
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
        let htmlText = '';
        htmlText += '<tr>'
            + '<td>' + data.description + '</td>'
            + '<td>' + doneOrNot(data.done, data.id) + '</td>'
            + '<td>' + data.author.name + '</td>';
        htmlText += '<td>';
        for (let i = 0; i < data['categories'].length; i++) {
            htmlText += data['categories'][i]['name'] + ' ';
        }
        htmlText += '</td>';
        htmlText += '</tr>';
        $('#idBodyItems').append(htmlText);
    }

    function addCategoryToTable(data) {
        $('#cIds').append(
            '<option value=' + data.id + '>' + data.name + '</option>'
        )
    }

    function revertCompletedOnly() {
        if ($("#completedOnly").is(':checked')) {
            fillInTableCompletedOnly();
        } else {
            fillInTable();
        }
    }

    function doneOrNot(done, id) {
        if (done) {
            return '<input type="checkbox" id="' + id + '" name="' + id + '" onchange="revertDone(id)" checked>'
        } else {
            return '<input type="checkbox" id="' + id + '" name="' + id + '" onchange="revertDone(id)">'
        }
    }

    function revertDone(id) {
        $.ajax({
            type: 'POST',
            url: "http://localhost:8080/job4j_toDoList/revert.do",
            data: { idToRevert : id },
            dataType: 'json',
        });
    }

    function addTask() {
        $.ajax({
            method: 'POST',
            url: "http://localhost:8080/job4j_toDoList/addItem.do",
            data: {descriptionNewTask: $("#descriptionNewTask").val(), categoryIds: $('#cIds').val()},
            dataType: 'json'
        });
        location.reload()
    }
</script>
<div class="container pt-3">
    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <c:choose>
                    <c:when test="${user.name!=null}">
                        <a class="nav-link" href="<c:url value='/login.jsp'/>">
                            <c:out value="${user.name}"/> | Выйти</a>
                    </c:when>
                    <c:otherwise>
                        <a class="nav-link" href="<c:url value='/login.jsp'/>">Войти</a>
                    </c:otherwise>
                </c:choose>
            </li>
        </ul>
        <div class="card" style="width: 100%">
            <div class="card-header">
                Новая задача
            </div>
            <div class="card-body">
                <form>
                    <div class="form-group">
                        <label>Описание</label>
                        <input type="text" class="form-control" name="descriptionNewTask" id="descriptionNewTask">
                        <label>Категории</label>
                        <select class="form-control" name="cIds" id="cIds" multiple>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary" onclick="addTask()">Добавить</button>
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
                    <p><label for="completedOnly"></label>
                        <input type="checkbox" id="completedOnly"
                               onclick="revertCompletedOnly()">Только выполненные</p>
                    <tr>
                        <th scope="col">Описание</th>
                        <th scope="col">Выполнено</th>
                        <th scope="col">Автор</th>
                        <th scope="col">Категории</th>
                    </tr>
                    </thead>
                    <tbody id="idBodyItems">
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>