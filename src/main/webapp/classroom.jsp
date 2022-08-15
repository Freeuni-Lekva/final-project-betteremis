<%--
  Created by IntelliJ IDEA.
  User: dito
  Date: 15.08.22
  Time: 15:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="classroomStyle.scss">
</head>
<body >
<div style="width: 1200px; margin: 0 auto;">
    <form id="addPost" action="ServletAddPost" method="post">
        <input type="text" placeholder="Write Post Title" name="title" style="width: 1200px">
        <textarea form="addPost" type="text" placeholder="Write Post Content" name="content" style="width: 1200px ; height: 100px"></textarea>
        </br>
        <button class='btn col-3' type="submit" style="width: 200px; background-color: #016ba8; color: white"> Post </button>
    </form>
</div>
<div class="container" style="width: 1200px; margin: 0 auto;">
    <div class="post pb-4">
        <div class="right">
            <h1>Header</h1>
            <div class='d-flex'>
                <div class="author">
                    <h2>John Doe</h2>
                </div>
                <div class="date">
                    <h2>12 Jan, 2020</h2>
                </div>
            </div>
            <div class="separator"></div>
            <p>
            <textarea type="text"  name="content" style="width: 1130px ; height: 100px" disabled> Post content</textarea>
            </p>
            <div class="separator"></div>
            <div>
                <form id="addComment" action="ServletAddComment" method="post">
                    <textarea name="comment" form="addComment" type="text" class="col-9" placeholder="Write a comment" style="width: 500px"></textarea>
                    <button class='btn' type="submit">Add Comment</button>
                </form>
            </div>
            <div class="separator"></div>
            <div class="comments">
                <div class="single-comment">
                    <p class="mb-0 pt-0 name">Some Random Guy1 , 4 Days ago</p>
                    <p><textarea type="text"  name="content" style="width: 1130px ; height: 12px" disabled> Comment content</textarea></p>
                </div>
                <div class="single-comment">
                    <p class="mb-0 pt-0 name">Some Random Guy2 , 4 Days ago</p>
                    <p><textarea type="text"  name="content" style="width: 1130px ; height: 12px" disabled> Comment content2</textarea></p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>