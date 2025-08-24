<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Dashboard</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            background-color: #f4f6f9;
        }
        header {
            background: #2c3e50;
            color: white;
            padding: 15px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        header h2 {
            margin: 0;
        }
        header a {
            color: white;
            text-decoration: none;
            background: #e74c3c;
            padding: 8px 16px;
            border-radius: 6px;
            font-weight: bold;
        }
        header a:hover {
            background: #c0392b;
        }
        main {
            padding: 30px;
        }
        h3 {
            color: #34495e;
        }
        .container {
            background: white;
            padding: 20px;
            margin: 15px 0;
            border-radius: 10px;
            box-shadow: 0px 3px 8px rgba(0,0,0,0.1);
        }
        a.button {
            display: inline-block;
            background: #3498db;
            color: white;
            padding: 12px 20px;
            border-radius: 6px;
            text-decoration: none;
            font-weight: bold;
            transition: background 0.3s;
        }
        a.button:hover {
            background: #2980b9;
        }
    </style>
</head>
<body>

    <!-- Dashboard Header -->
    <header>
        <h2>Admin Dashboard</h2>
        <a href="logout">Logout</a>
    </header>

    <!-- Main Content -->
    <main>
        <h3>Welcome, ${sessionScope.adminUser}</h3>

        <div class="container">
            <a href="createQueue.jsp" class="button">➕ Create Queue</a>
        </div>

        <div class="container">
            <a href="viewQueueList" class="button">📋 View Queues</a>
        </div>
    </main>

</body>
</html>
