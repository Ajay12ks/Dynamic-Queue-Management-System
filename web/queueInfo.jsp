<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>

<html>
<head>
    <title>Create New Queue</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f6f9;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .card {
            background: #fff;
            padding: 30px 40px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
            text-align: center;
            width: 420px;
        }
        h2 {
            color: #2c3e50;
            margin-bottom: 20px;
        }
        p {
            font-size: 16px;
            margin: 12px 0;
        }
        b {
            color: #34495e;
        }
        .button {
            display: inline-block;
            margin-top: 25px;
            padding: 12px 24px;
            background: #3498db;
            color: white;
            border-radius: 8px;
            text-decoration: none;
            font-weight: bold;
            transition: background 0.3s;
        }
        .button:hover {
            background: #2980b9;
        }
    </style>
</head>
<body>
    <div class="card">
        <h2>🆕 Queue Created</h2>
        <p><b>Queue Name:</b> ${queueName}</p>
        <p><b>Queue ID:</b> ${queueId}</p>

        <a href="adminDashboard.jsp" class="button">⬅ Back to Dashboard</a>
    </div>
</body>
</html>
