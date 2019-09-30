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
    if (moves > 3) {
        var k = check(arr, tx, to);
        if (k == 1) {
            if (play == 1) update("1");
            else update("2");
        } else if (k == 2) {
            if (play == 0) update("1");
            else update("2");
        } else if (k == 3) {
            update("T");
        }
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

function checker(G) {
    var xwin = false;
    var owin = false;
    for (var i = 0; i < 3; i++) {
        if (G[i][0] == 1 && G[i][1] == 1 && G[i][2] == 1)
            xwin = true;
        if (G[i][0] == 2 && G[i][1] == 2 && G[i][2] == 2)
            owin = true;
        if (G[0][i] == 1 && G[1][i] == 1 && G[2][i] == 1)
            xwin = true;
        if (G[0][i] == 2 && G[1][i] == 2 && G[2][i] == 2)
            owin = true;
    }
    if (G[0][0] == 1 && G[1][1] == 1 && G[2][2] == 1)
        xwin = true;
    if (G[2][0] == 1 && G[1][1] == 1 && G[0][2] == 1)
        xwin = true;
    if (G[0][0] == 2 && G[1][1] == 2 && G[2][2] == 2)
        owin = true;
    if (G[2][0] == 2 && G[1][1] == 2 && G[0][2] == 2)
        owin = true;
    if (xwin && owin)
        return 0;
    else if (!(xwin || owin))
        return 0;
    else if (xwin)
        return 1;
    else return 2;
}

function check(G, x, y) {
    var fu = checker(G),
        m;
    if (x == 0 && y == 0) {
        if (fu == 0) return 3;
        else return fu;
    } else {
        var arra = [];
        if (fu != 0)
            return fu;
        var i, j;
        var found = true;
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                if (G[i][j] == 0) {
                    var fx = G.map(function(arrays) {
                        return arrays.slice();
                    });
                    if (x == y + 1) {
                        fx[i][j] = 1;
                        m = check(fx, x - 1, y);
                        arra.push(m);
                    } else {
                        fx[i][j] = 2;
                        m = check(fx, x, y - 1);
                        arra.push(m);
                    }
                }
            }
        }
        if (arra.length == 1)
            return arra[0];
        for (i = 1; i < arra.length; i++) {
            if (arra[i] != arra[i - 1])
                found = false;
        }
        if (found) return arra[0];
        else return 0;
    }
}