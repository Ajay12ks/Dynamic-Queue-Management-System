<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Current Queue</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 30px;
            background-color: #f9f9f9;
        }
        h2 {
            color: #2c3e50;
        }
        table {
            border-collapse: collapse;
            width: 80%;
            margin-top: 20px;
            background: white;
            box-shadow: 0px 3px 6px rgba(0,0,0,0.1);
        }
        th, td {
            border: 1px solid #333;
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #3498db;
            color: white;
        }
        tr:nth-child(even) {
            background: #f2f2f2;
        }
        a.button {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 16px;
            background: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-weight: bold;
        }
        a.button:hover {
            background: #2980b9;
        }
    </style>
</head>
<body>


    <table>
        <tr>
            <th>Queue ID</th>
            <th>Queue Name</th>
            <th>Created At</th>
            <th>Action</th>
        </tr>

        <!-- Loop over the list -->
        <c:choose>
            <c:when test="${not empty queueList}">
                <c:forEach var="t" items="${queueList}">
                    <tr>
                        <td>${t.queueId}</td>
                        <td>${t.queueName}</td>
                        <td>${t.createdAt}</td>
                        <td><a href="viewQueue?queueId=${t.queueId}">View</a></td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="4">No Queues found!</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </table>

    <br>
    <a href="adminDashboard.jsp" class="button">⬅ Back</a>
</body>
</html>
