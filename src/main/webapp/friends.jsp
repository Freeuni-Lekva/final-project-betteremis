<%@ page import="DAO.Interfaces.FriendsDAO" %>
<%@ page import="static DAO.Mapping.FRIENDS_DAO" %>
<%@ page import="static DAO.Mapping.*" %>
<%@ page import="Model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="DAO.Mapping" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%
        List<User> data;
        User currentUser = (User) request.getSession().getAttribute(USER_OBJECT);
        FriendsDAO dao = (FriendsDAO) request.getServletContext().getAttribute(FRIENDS_DAO);
        data = dao.GetAllFriends(currentUser, true);
//        data.add(new User("hello@freeuni.edu.ge", "passw", USERTYPE.ADMIN));
//        for(int i=0; i<100; i++){
//            data.add(new User("hello"+i, "passw"+i, USERTYPE.ADMIN));
//        }
        if(request.getParameter("search") != null){
            String key = request.getParameter("search");
            data = data.stream().filter( (user) -> {
                if(user.getEmail().contains(key)) return true;
                return false;
            }).collect(Collectors.toList());
        }
        int ITEM_PER_PAGE = 16;
    %>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>User List</title>
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
          integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
    <link rel="stylesheet" href="css/friends.scss">
</head>
<body>
<!-- navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <a class="navbar-brand" href="/index.html">People List</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="index.jsp">Home
                </a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="friends.jsp">Friends
                    <span class="sr-only">(current)</span>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="studentPages/friend-requests.jsp">Friend requests
                </a>
            </li>
        </ul>
    </div>
</nav>

<!-- search bar -->
<div class="container mt-5">
    <div class="row" id="search-bar">
        <form class="form-inline" action="friends.jsp" method="get">
            <label class="sr-only">Name</label>
            <input type="text" class="form-control mb-2 mr-sm-2" id="search" name="search" placeholder="search name ...">
            <input type="submit" class="btn btn-primary mb-2" id="submit-search">
        </form>
    </div>
</div>

<!-- data-panel -->
<div class="container">
    <div class="row" id="data-panel">
        <%
            int offset = 0;
            if(request.getParameter("page") != null){
                offset =  Integer.parseInt(request.getParameter("page")) - 1;
            }
            offset *= ITEM_PER_PAGE;
            for(int i = offset; i < Math.min(offset + ITEM_PER_PAGE, data.size()); i++){
                User item = data.get(i);
        %>
        <div class="col-sm-3">
            <div class="card mb-2" id="card-list">
                <img class="card-img-top show-photo" data-toggle="modal" data-target="#show-photo-modal" data-id="<%=i%>"
                     data-email="<%=item.getEmail()%>" data-type="<%=item.getType()%>" src="https://randomuser.me/api/portraits/women/72.jpg" title="<%=item.getEmail()%>" alt="Card image cap">
                <div class="card-body">
                    <h6><%=item.getEmail()%></h6>
                    <form action="ServletSendMessage" method="post">
                        <input type = "hidden" name = <%=Mapping.SENDER%> value= <%=currentUser.getEmail()%> >
                        <input type = "hidden"  name = <%=Mapping.RECEIVER%> value=<%=item.getEmail()%> >
                        <input type="submit" class="btn btn-info btn-add-friend" data-id="<%=i%>" value="Send Message"></input>
                    </form>
                </div>
            </div>
        </div>
        <%
            }
        %>
    </div>
</div>

<!-- modal -->
<div class="modal fade" id="show-photo-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="show-photo-title"> Friend Info</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" id="show-photo-body">
                <div class="column">
                    <div class="col-sm-12" id="show-photo-image">
                    </div>
                    <p></p>
                    <div class="col-sm-12" id="font-style">
                        <p id="show-photo-gender"></p>
                        <p id="show-photo-age"></p>
                        <p id="show-photo-region"></p>
                        <p id="show-photo-birthday"></p>
                        <p id="show-photo-email"></p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<nav aria-label="Page navigation">
    <ul class="pagination justify-content-center">
        <li class="prev">
            <a class="page-link" href="<%=request.getContextPath()+"/friends.jsp"%>" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>

            </a>
        </li>
        <ul class="pagination" id="pagination">
            <%
                int totalPages =  ((int)Math.ceil(data.size() / (double)ITEM_PER_PAGE));
                if(totalPages == 0) totalPages = 1;
                for (int i = 0; i < totalPages; i++) {%>
                <li class="page-item">
                    <a class="page-link" <%
                        if(request.getParameter("search") != null){%>
                            href="<%="?search="+request.getParameter("search")+"&page="+(i+1)%>"
                       <%} else {%>href="<%="?page="+(i+1)%>"
                            <%}%>
                       data-page="<%=(i+1)%>>"><%=(i+1)%></a>
                </li>
            <%}%>
        </ul>
        <li class="next">
            <a class="page-link" <%
                    if(request.getParameter("search") != null){%>
                    href="<%="?search="+request.getParameter("search")+"&page="+totalPages%>"
                    <%} else {%>href="<%="?page="+totalPages%>"
               <%}%>
               aria-label="next">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    </ul>
</nav>

<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"
        integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"
        integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm"
        crossorigin="anonymous"></script>
<script src="friends.js" type="text/javascript"></script>
</body>
</html>