<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<html>
<head>
    <title>Current Queue</title>
    <style>
        table {
            border-collapse: collapse;
            width: 70%;
        }
        th, td {
            border: 1px solid #333;
            padding: 8px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h2>📋 Current Queue ${tokenid}</h2>

    <%
        List<Map<String,Object>> tokenList = (List<Map<String,Object>>) request.getAttribute("tokenList");
    %>


    <table>
        <tr>
            <th>Token ID</th>
            <th>User</th>
            <th>Status</th>
            <th>Issued Time</th>
        </tr>
        <%
            if (tokenList != null && !tokenList.isEmpty()) {
                for (Map<String,Object> t : tokenList) {
        %>
        <tr>
            <td><%= t.get("tokenId") %></td>
            <td><%= t.get("userName") %></td>
            <td><%= t.get("status") %></td>
            <td><%= t.get("issuedTime") %></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="4">No tokens found!</td>
        </tr>
        <% } %>
    </table>

    <br>
    <a href="bookToken.jsp">Book Another Token</a>
</body>
</html>
