var GAME_DATA_URL = buildUrlWithContextPath("gamedata");
var GAME_REQUEST_URL = buildUrlWithContextPath("gamerequest");
var BOARD_UPDATER_URL = buildUrlWithContextPath("boardupdater");
var QUIT_REQUEST_URL = buildUrlWithContextPath("quitgame");
var gameVersion = 0;

$(function() {
    $.ajax({
        url: GAME_DATA_URL,
        method: "GET",
        success: function(gameData) {
            $('<h2 id="gameName">' + gameData.name + '</h2>').appendTo($("#gameheader"));
            $('<h3>' + "Variant: " + gameData.variant + " | Target: " + gameData.target + '</h3>').appendTo($("#gameheader"));
            if (gameData.variant === "Popout") {
                $("#popin").css("visibility", "visible");
                $("#popout").css("visibility", "visible");
                $("#popinlabel").css("visibility", "visible");
                $("#popoutlabel").css("visibility", "visible");
            }
            var board = $(document.createElement('table'));
            board.attr('class', "board");
            for (var row = 0; row < gameData.row; row++) {
                var tr = $(document.createElement('tr'));
                for (var col = 0; col < gameData.col; col++) {
                    var td = $(document.createElement('td'));
                     td.attr('row', row);
                    td.attr('col', col);
                    tr.append(td);
                    td.on("click",function() {
                        $.ajax({
                                data:{ "column": this.getAttribute('col').valueOf(), "gameName": gameData.name, "isPopout": document.getElementById("popout").checked },
                                url: GAME_REQUEST_URL,
                                method: "GET"
                            }
                        )
                    });
                }
                board.append(tr);
            }
            board.appendTo($("#boardcontainer"));
        }
    });

    setInterval(ajaxBoardData, 2000);
});

function ajaxBoardData() {
    $.ajax({
        data: "name=" + $("#gameName").html(),
        url: BOARD_UPDATER_URL,
        method: "GET",
        success: function(game) {
            if (game.version !== gameVersion) {
                gameVersion = game.version;
                updateBoard(game.board);
                updatePlayers(game.playerList);
                if (game.status.valueOf() === "NotStarted") {
                    $("#status").text("Waiting for players");
                } else if (game.status.valueOf() === "InProgress") {
                    $("#status").text("Current Turn: " + game.currentPlayer);
                } else if (game.status.valueOf() === "EndWithWin") {
                    $("#status").text("Winner: " + game.currentPlayer);
                } else if (game.status.valueOf() === "NoHuman") {
                    $("#status").text("Illegal game. No human player.");
                }
            }
        }
    });
}

function updatePlayers(playerList) {
    $("#playersList").empty();
    $.each(playerList || [], function (index, player) {
        var tr = $(document.createElement('tr'));
        var name = $(document.createElement('td')).text(player.playerName);
        var turnsPlayed = $(document.createElement('td')).text(player.numOfPlays);
        var color = $(document.createElement('td'));
        if (player.playerColor === 1) {
            $(color).css("background-color", "red");
        } else if (player.playerColor === 2) {
            $(color).css("background-color", "yellowgreen");
        } else if (player.playerColor === 3) {
            $(color).css("background-color", "orange");
        } else if (player.playerColor === 4) {
            $(color).css("background-color", "blue");
        } else if (player.playerColor === 5) {
            $(color).css("background-color", "purple");
        } else if (player.playerColor === 6) {
            $(color).css("background-color", "brown");
        }
        $(color).attr('color', index);
        var type = $(document.createElement('td')).text(player.playerMode);
        tr.append(name);
        tr.append(turnsPlayed);
        tr.append(color);
        tr.append(type);
        $("#playersList").append(tr);
    })
}

function updateBoard(board) {
    for (var i = 0; i < board.length; i++) {
        for (var j = 0; j < board[i].length; j++) {
             if (board[i][j] === 1) {
                $("td[col=" + j + "][row=" + i + "]").css("background-color", "red");
            } else if (board[i][j] === 2) {
                 $("td[col=" + j + "][row=" + i + "]").css("background-color", "yellowgreen");
            } else if (board[i][j] === 3) {
                $("td[col=" + j + "][row=" + i + "]").css("background-color", "orange");
            } else if (board[i][j] === 4) {
                $("td[col=" + j + "][row=" + i + "]").css("background-color", "blue");
            } else if (board[i][j] === 5) {
                $("td[col=" + j + "][row=" + i + "]").css("background-color", "purple");
            } else if (board[i][j] === 6) {
                $("td[col=" + j + "][row=" + i + "]").css("background-color", "brown");
            } else {
                 $("td[col=" + j + "][row=" + i + "]").css("background-color", "white");
             }
        }
    }
}