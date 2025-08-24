<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Token Confirmation</title>
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
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            text-align: center;
            width: 400px;
        }
        h2 {
            color: #2c3e50;
            margin-bottom: 20px;
        }
        p {
            font-size: 16px;
            margin: 10px 0;
        }
        b {
            color: #34495e;
        }
        a.button {
            display: inline-block;
            margin-top: 20px;
            padding: 12px 20px;
            background: #3498db;
            color: white;
            border-radius: 8px;
            text-decoration: none;
            font-weight: bold;
            transition: 0.3s;
        }
        a.button:hover {
            background: #2980b9;
        }
    </style>
</head>
<body>
    <div class="card">
        <h2>✅ Token Booked Successfully</h2>
        <p><b>User:</b> ${userName}</p>
        <p><b>Token ID:</b> ${tokenId}</p>
        <p><b>Queue:</b> ${queueId}</p>

        <a href="viewQueue?queueId=${queueId}" class="button">View Current Queue</a>
    </div>
</body>
</html>
