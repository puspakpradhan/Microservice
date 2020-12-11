<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<link rel="stylesheet" href="/resources/css/style.css">
<script type="text/javascript" src="/resources/js/login.js"></script>

<title>Login Page</title>
</head>
<body>
This is my Login Page..Welcome to MVC..


<style>

form{
background-color: skyblue;
width: 400px;
height: 300px;
border-style: solid;
border-color:blue;
padding: 10px;
}

</style>



<div class="form">
    <form action="/login" method="post">
        <div>
        User ID:
        <input type="text" id="userID" name="userID" value ="" >
        </div>
        <div><br></div>
       <div>
        Password:
        <input type="password" id="password" name="password" value ="" >
        </div>
        <div><br></div>
        
        <div>
        <input type="submit"  value ="Login" style="background-color: lime;" >
        </div>
        <div><br></div>
        
        <div>
        <input type="button"  value ="Register" style="background-color: orange;" >
        </div>
        <div><br></div>
         
    </form>
  </div>

</body>
</html>