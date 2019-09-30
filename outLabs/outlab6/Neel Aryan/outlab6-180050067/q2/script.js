var turn = 1,
    moves = 0,
    count = 0,
    score1 = 0,
    score2 = 0,
    state = 1,
    t = [],
    tx = 5,
    to = 4,
    play = 1;
var table, row, cell1, cell2, cell3, cell4, out;
var arr = [
    [0, 0, 0],
    [0, 0, 0],
    [0, 0, 0]
];
table = '<div class="table-responsive">';
table += '<table id="table" class="table table-bordered table-hover-cells">';
table += '<thead><tr>';
table += '<th>Serial no.</th><th>Result</th><th>Player 1 (X)</th><th>Player 2 (O)</th>';
table += '</tr></thead><tbody>';
var end = '</tbody></table></div>';

function func(x, y) {
    if (state == 0) {
        reset();
        return;
    }
    if (arr[x][y] == 0) {
        if (turn) {
            document.getElementById("c" + x.toString(10) + y.toString(10)).src = "x.png";
            arr[x][y] = 1;
            turn = 0;
            moves++;
            tx--;
            document.getElementById("turn").innerHTML = "Turn : O";
        } else {
            document.getElementById("c" + x.toString(10) + y.toString(10)).src = "o.png";
            arr[x][y] = 2;
            turn = 1;
            moves++;
            to--;
            document.getElementById("turn").innerHTML = "Turn : X";
        }
    }
        var k = naive_check();
        if (k == 1) {
            if (play == 1) update("1");
            else update("2");
        } else if (k == 2) {
            if (play == 0) update("1");
            else update("2");
        } else if (moves == 9) {
            update("T");
        }
}

function update(s) {
    if (state == 0) {
        reset();
        return;
    }
    count++
    if (s == "1")
        score1 = score1 + 4;
    else if (s == "2")
        score2 = score2 + 4;
    else {
        score1++;
        score2++;
    }
    table += '<tr><th>' + count.toString(10) + '</th><td>' + s + '</td><td>' + score1.toString(10) + '</td><td>' + score2.toString(10) + '</td></tr>';
    document.getElementById("table").innerHTML = table + end;
    if (s == "T") {
        out = "Match tied";
    } else {
        out = "Player " + s + " won";
    }
    state = 0;
    timedText(out);
}

function timedText(x) {
    var ti = document.getElementById("timed");
    var x1 = "! Starting a new game in ";
    var x2 = " seconds...";
    t.push(setTimeout(function() {
        ti.innerHTML = x + x1 + "5" + x2;
    }, 0));
    t.push(setTimeout(function() {
        ti.innerHTML = x + x1 + "4" + x2;
    }, 1000));
    t.push(setTimeout(function() {
        ti.innerHTML = x + x1 + "3" + x2;
    }, 2000));
    t.push(setTimeout(function() {
        ti.innerHTML = x + x1 + "2" + x2;
    }, 3000));
    t.push(setTimeout(function() {
        ti.innerHTML = x + x1 + "1" + x2;
    }, 4000));
    t.push(setTimeout(function() {
        ti.innerHTML = "";
    }, 5000));
    t.push(setTimeout(function() {
        reset()
    }, 5000));
}

function reset() {
    for (var i = 0; i < t.length; i++) {
        clearTimeout(t[i]);
    }
    play = (play + 1) % 2;
    t = [];
    document.getElementById("turn").innerHTML = "Turn : X";
    if (play == 1) {
        document.getElementById("turn1").innerHTML = "Player 1 : X";
        document.getElementById("turn2").innerHTML = "Player 2 : O";
    }
    else {
        document.getElementById("turn1").innerHTML = "Player 1 : O";
        document.getElementById("turn2").innerHTML = "Player 2 : X";
    }
    state = 1;
    document.getElementById("timed").innerHTML = "";
    for (var i = 0; i < 3; i++) {
        for (var j = 0; j < 3; j++) {
            document.getElementById("c" + i.toString(10) + j.toString(10)).src = "empty.png";
            arr[i][j] = 0;
        }
    }
    turn = 1;
    moves = 0;
    tx = 5;
    to = 4;
}

function naive_check() {
    for (var i = 0; i < 3; i++) {
        if (arr[i][0] == arr[i][1] && arr[i][1] == arr[i][2] && arr[i][2] != 0) {
            return arr[i][2];
        }
        if (arr[0][i] == arr[1][i] && arr[1][i] == arr[2][i] && arr[2][i] != 0) {
            return arr[2][i];
        }
    }
    if (arr[0][0] == arr[1][1] && arr[1][1] == arr[2][2] && arr[1][1] != 0) {
        return arr[2][2];
    }
    if (arr[0][2] == arr[1][1] && arr[1][1] == arr[2][0] && arr[1][1] != 0) {
        return arr[1][1];
    }
    return 3;
}