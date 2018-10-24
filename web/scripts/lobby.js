var USER_LIST_URL = buildUrlWithContextPath("userslist");
var GAME_LIST_URL = buildUrlWithContextPath("gameslist");
var LOAD_GAME_URL = buildUrlWithContextPath("loadgame");
var LOG_OUT_URL = buildUrlWithContextPath("logout");

$(function() {
    $("#uploadform").submit(function() {

        var file = this[0].files[0];

        var formData = new FormData();
        formData.append("file", file);

        $.ajax({
            method:'POST',
            data: formData,
            url: LOAD_GAME_URL,
            processData: false,
            contentType: false,
            error: function(e) {
                alert("File not uploaded succesfully.")
            },
            success: function(r) {
                if (r.valueOf() === "") {
                    alert("File uploaded succesfully.");
                } else {
                    alert(r.valueOf());
                }
            }
        });
        return false;
    })

    setInterval(ajaxUsersList, 2000);
    setInterval(ajaxGamesList, 2000);
})

$(document).ready(function() {
    ajaxUsersList();
    ajaxGamesList();
})

function ajaxUsersList() {
    $.ajax({
        url: USER_LIST_URL,
        success: function(users) {
            refreshUsersList(users);
        }
    });
}

function ajaxGamesList() {
    $.ajax({
        url: GAME_LIST_URL,
        success: function(games) {
            refreshGamesList(games);
        }
    });
}

function refreshUsersList(users) {
    $("#userslist").empty();
    $.each(users || [], function(index, username) {
        $('<li>' + username + '</li>').appendTo($("#userslist"));
    });
}

function refreshGamesList(games) {
    $("#gameslist").empty();
    $.each(games || [], function (index, game) {
        var tr = $(document.createElement('tr'));
        var name = $(document.createElement('td')).text(game.name);
        var creator = $(document.createElement('td')).text(game.creator);
        var status = $(document.createElement('td')).text(game.status);
        var board = $(document.createElement('td')).text(game.row + " X " + game.col);
        var target = $(document.createElement('td')).text(game.target);
        var variant = $(document.createElement('td')).text(game.variant);
        var players = $(document.createElement('td')).text(game.activePlayers + " / " + game.requiredPlayers);
        var join = $(document.createElement('td'));
        var button = document.createElement('input');
        button.setAttribute('type', 'submit');
        button.setAttribute('name', "button");
        button.setAttribute('value', "Join " + game.name);
        join.append(button);
        tr.append(name);
        tr.append(creator);
        tr.append(status);
        tr.append(board);
        tr.append(target);
        tr.append(variant);
        tr.append(players);
        tr.append(join);
        tr.appendTo($("#gameslist"));
    });
}

