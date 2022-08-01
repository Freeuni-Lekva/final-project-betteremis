<%--
  Created by IntelliJ IDEA.
  User: gluncho
  Date: 7/29/2022
  Time: 9:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.7.1.min.js"></script>
    <link rel="stylesheet" href="../css/friend-requests.css">
    <script type="text/javascript">
        function makeHTMLElement(tag, classes, text, attributes) {
            var d = document.createElement(tag);
            $(d).addClass(classes);
            $(d).append(text);
            for(var prop in attributes) {
                $(d).attr(prop, attributes[prop]);
            }
            return d;
        }

        $.get('https://randomuser.me/api/?results=100', function(data) {
            console.log(data)
            var friends = data.results;
            friends.map(friend => {
                var friendBox = makeHTMLElement('div', 'friend-box')
                var img = makeHTMLElement('div', 'friend-profile', '')
                $(img).css('background-image', `url(${friend.picture.thumbnail})`)


                var name = `${friend.name.first} ${friend.name.last}`;
                var nameBox = makeHTMLElement("div", 'name-box', name);
                var username = `@${friend.login.username}`;
                var unBox = makeHTMLElement('div', 'user-name-box', username);

                var level = Math.floor(Math.random()*50)
                var levelBox = makeHTMLElement('div', 'level-indicator', `Level ${level}`)

                $(friendBox).append(img, nameBox, unBox, levelBox)
                $('.friend-list').append(friendBox)
            })

        })


        /*
        Friend Request
        */
        $.get('https://randomuser.me/api/?results=20', function(data) {
            console.log(data)
            var friends = data.results;
            friends.map(friend => {
                var friendBox = makeHTMLElement('div', 'friend-box')
                var img = makeHTMLElement('div', 'friend-profile', '')
                $(img).css('background-image', `url(${friend.picture.thumbnail})`)


                var name = `Nika Glunchadze`;
                var nameBox = makeHTMLElement("div", 'name-box', name);
                var username = `@Hello`;
                var unBox = makeHTMLElement('div', 'user-name-box', username + " sent you a friend request.");
                var rBtnrow = makeHTMLElement('div', 'request-btn-row', '', {"data-username": friend.login.username})

                var accept = makeHTMLElement('button', 'friend-request accept-request', 'Accept', {"data-username": friend.login.username})
                var decline = makeHTMLElement('button', 'friend-request decline-request', 'Decline', {"data-username": friend.login.username})
                $(rBtnrow).append(accept, decline)





                $(friendBox).append(img, nameBox, unBox, rBtnrow)
                $('.friend-requests').append(friendBox)
            })

        })

        $('.friend-requests').on('click', '.friend-request', function() {
            var $elem = $(this);
            var username = $elem.attr('data-username')
            var type = $elem.hasClass('accept-request')
            console.log(username)
            var $rBtnrow = $(`.request-btn-row[data-username="${username}"]`)
            // $rBtnrow.addClass('disappear')
            if(type) {
                var message = makeHTMLElement('div', 'fr-request-pending', 'Friend request sent.')
            } else {
                var message = makeHTMLElement('div', 'fr-request-pending', 'Friend request declined.')

            }
            $rBtnrow.empty().append(message)
        })
    </script>
    <title>Title</title>
</head>
<body>
<div class="friend-requests">

</div>
<div class="friend-list"></div>
</body>
</html>
