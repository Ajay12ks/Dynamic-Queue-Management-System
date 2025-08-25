<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<html>
<head>
    <title>Current Queue</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f8f9fa;
            margin: 30px;
        }
        h2 {
            color: #2c3e50;
        }
        .container {
            background: white;
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            max-width: 900px;
            margin: auto;
        }
        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        tr:nth-child(even) { background-color: #f2f2f2; }
        .btn {
            padding: 8px 16px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 14px;
        }
        .btn-call {
            background: #28a745;
            color: white;
        }
        .btn-call:hover {
            background: #218838;
        }
        .btn-cancel {
            background: #dc3545;
            color: white;
        }
        .btn-cancel:hover {
            background: #c82333;
        }
        .btn-row {
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>📋 Current Queue</h2>

    <%
        List<Map<String,Object>> tokenList = (List<Map<String,Object>>) request.getAttribute("tokenList");
    %>

    <table>
        <tr>
            <th>Position</th>
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
            <td><%= t.get("position") %></td>
            <td><%= t.get("tokenId") %></td>
            <td><%= t.get("userName") %></td>
            <td>
                <%
                    String status = (String) t.get("status");
                    if("CALLED".equals(status)){
                %>
                    <span style="color: green; font-weight: bold;"><%= status %></span>
                <% } else if("COMPLETED".equals(status)) { %>
                    <span style="color: gray;"><%= status %></span>
                <% } else if("CANCELLED".equals(status)) { %>
                    <span style="color: red; font-weight: bold;"><%= status %></span>
                <% } else { %>
                    <%= status %>
                <% } %>
            </td>
            <td><%= t.get("issuedTime") %></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="5">No tokens found!</td>
        </tr>
        <% } %>
    </table>

    <!-- ✅ Call Next Button moved to bottom -->
    <div class="btn-row">
        <form action="callNext" method="post">
            <input type="hidden" name="queueId" value="${queueId}">
            <button type="submit" class="btn btn-call">📞 Call Next</button>
        </form>
    </div>
</div>
</body>
</html>
