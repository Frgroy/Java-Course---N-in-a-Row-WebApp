<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>N in a Row</title>
    <link rel="stylesheet" href="../style/loginStyle.css">
    <script src="../utils/JQuery3.3.1min.js"></script>
    <script src="../utils/context-path-helper.js"></script>

</head>
<body>
<div class="login-page">
    <div class="form">
        <h1>N in a Row</h1>
        <form method="GET" action="login">
            <span><input type ="radio" name = "playerType" value="human" checked> Human </span>
            <span><input type ="radio" name = "playerType" value="computer"> Computer </span>
            <input type="text" name="username" required placeholder="User Name:"/>
            <button type = "submit">Login</button>
            <% Object error = request.getAttribute("error"); %>
            <h2><%=error%></h2>
        </form>
    </div>
</div>
</body>
</html>