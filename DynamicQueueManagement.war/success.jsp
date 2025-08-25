<%@ page import="java.sql.*,com.queue.util.DBConnection" %>
<%
    String userName = (String) session.getAttribute("userName");
    Integer tokenId = (Integer) session.getAttribute("tokenId");
    Integer queueId = (Integer) session.getAttribute("queueId");
    Integer position = null;

    try(Connection conn = DBConnection.getConnection()) {
        String posQuery = "SELECT position FROM Tokens WHERE token_id = ?";
        PreparedStatement pst = conn.prepareStatement(posQuery);
        pst.setInt(1, tokenId);
        ResultSet rs = pst.executeQuery();
        if(rs.next()) {
            position = rs.getInt("position");
        }
    } catch(Exception e) {
        e.printStackTrace();
    }
%>
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
        h2 { color: #2c3e50; margin-bottom: 20px; }
        p { font-size: 16px; margin: 10px 0; }
        b { color: #34495e; }
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
        a.button:hover { background: #2980b9; }
    </style>
</head>
<body>
    <div class="card">
        <h2>Token Booked Successfully</h2>
        <p><b>User:</b> <%= userName %></p>
        <p><b>Token ID:</b> <%= tokenId %></p>
        <p><b>Queue:</b> <%= queueId %></p>
        <p><b>Current Position:</b> <%= position != null ? position : "N/A" %></p>

        <a href="viewQueue?queueId=<%= queueId %>" class="button">View Current Queue</a>
    </div>
</body>
</html>
