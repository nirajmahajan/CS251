var currPlayer="1";
var currSymbol="X";
var player1Plays="X";
var T = 5;
var count = 1;
var t = T;
var p1Score = 0;
var p2Score = 0;
var transition_phase = false;


function f(cell_id) {
	if(transition_phase) {
		clearTimeout(rep);
		resetBoard();
		return;
	}
	if(document.getElementById(cell_id).innerHTML == "") {
		document.getElementById(cell_id).innerHTML = currSymbol;
		document.getElementById(cell_id).style.backgroundColor = '#afafaf';
		if(currSymbol == "X") {
			currSymbol = "O";
		} else {
			currSymbol = "X";
		}
	
		if(currPlayer == "1") {
			currPlayer = "2";
		} else {
			currPlayer = "1";
		}
		document.getElementById("who_plays").innerHTML = "Player" + currPlayer + "'s move";
		validate();
	}	
}

function validate() {
	var Xboxes = [];
	var Oboxes = [];
	for (var i = 0; i < 9; i++) {
		Xboxes[i] = document.getElementById("pos"+i.toString()).innerHTML == "X";
	}
	for (var i = 0; i < 9; i++) {
		Oboxes[i] = document.getElementById("pos"+i.toString()).innerHTML == "O";
	}
	// check diagonals
	if((Oboxes[0] && Oboxes[4] && Oboxes[8]) || (Oboxes[2] && Oboxes[4] && Oboxes[6])) {
		concludeGame('win', 'N');
		return;
	}
	if((Xboxes[0] && Xboxes[4] && Xboxes[8]) || (Xboxes[2] && Xboxes[4] && Xboxes[6])) {
		concludeGame('win', 'N');
		return;
	}
	// check columns
	for(var i = 0; i < 3; i++) {

		if((Oboxes[i] && Oboxes[i+3] && Oboxes[i+6])) {
			concludeGame('win', 'N');
			return;
		}
		if((Xboxes[i] && Xboxes[i+3] && Xboxes[i+6])) {
			concludeGame('win', 'N');
			return;
		}	
	}
	// check rows
	for(var i = 0; i < 3; i++) {
		if((Oboxes[3*i] && Oboxes[3*i+1] && Oboxes[3*i+2])) {
			concludeGame('win', 'N');
			return;
		}
		if((Xboxes[3*i] && Xboxes[3*i+1] && Xboxes[3*i+2])) {
			concludeGame('win', 'N');
			return;
		}
	}

	var drawn = true;
	for (var i = 0; i < 9; i++) {
		if(document.getElementById("pos"+i.toString()).innerHTML == "") {
			drawn = false;
			break;
		}
	}
	if(drawn) {
		concludeGame('draw', 'N');
		return;
	}
	// bonus
	var Vals = [];
	for (var i = 0; i < 9; i++) {
		Vals[i] = document.getElementById("pos"+i.toString()).innerHTML;
	}
	var future = predictAhead(Vals, currSymbol);
	if(future != 'N') {
		var future_winner;
		if(player1Plays == future) {
			future_winner = '1';
		} else {
			future_winner = '2';
		}
		concludeGame('super_win', future_winner);
		return;
	}
	// bonus
}

function concludeGame(result, superwinner) {
	var res;
	if (result == 'draw') {
		document.getElementById("tv_result").innerHTML = "Round Drawn!";
		document.getElementById("who_plays").innerHTML = "";
		res = "T";
		p1Score += 1;
		p2Score += 1;
	} else if (result == 'win') {
		var winner;
		if(currPlayer == '1') {
			winner = '2';
		} else {
			winner = '1'; 
		}
		document.getElementById("who_plays").innerHTML = "";
		document.getElementById("tv_result").innerHTML = "Round Won by Player" + winner;
		res = winner;
		if(winner == '1') {
			p1Score += 4;
		} else {
			p2Score += 4;
		}
	}else if (result == 'super_win') {
		var winner = superwinner;
		document.getElementById("who_plays").innerHTML = "";
		document.getElementById("tv_result").innerHTML = "Player" + winner + " will definitely win!";
		res = winner;
		if(winner == '1') {
			p1Score += 4;
		} else {
			p2Score += 4;
		}
	}


	var resTable = document.getElementById('results');
	var row = resTable.insertRow();
	var cell1 = row.insertCell();
	var cell2 = row.insertCell();
	var cell3 = row.insertCell();
	var cell4 = row.insertCell();


	cell1.innerHTML = count;
	cell2.innerHTML = res;
	cell3.innerHTML = p1Score.toString();
	cell4.innerHTML = p2Score.toString();
	count++;

	timer();
}

function timer() {
	transition_phase = true;
	if (t > 0) {
		document.getElementById("tv_timer").innerHTML = "Starting a new game in "+ t + " seconds!"
		t--;
		rep = setTimeout(timer, 1000);
	}
	else if (t == 0) {
		document.getElementById("tv_timer").innerHTML = "Starting a new game!!"
		t--;
		rep = setTimeout(timer, 1000);
	} else {
		clearTimeout(rep);
		resetBoard();
	}
}

function resetBoard() {
	t = T;
	transition_phase = false;
	currSymbol = "X";
	for (var i = 0; i < 9; i++) {
		document.getElementById("pos"+i.toString()).innerHTML = "";
		document.getElementById("pos"+i.toString()).style.backgroundColor = 'transparent';
	}
	document.getElementById("tv_timer").innerHTML = "";
	document.getElementById("tv_result").innerHTML = "";
	if(player1Plays == "X") {
		player1Plays = "O";
		document.getElementById("player1_item").innerHTML = "Player1 : O";
		document.getElementById("player2_item").innerHTML = "Player2 : X";
		document.getElementById("who_plays").innerHTML = "Player2's move";
	} else {
		player1Plays = "X";
		document.getElementById("player1_item").innerHTML = "Player1 : X";
		document.getElementById("player2_item").innerHTML = "Player2 : O";
		document.getElementById("who_plays").innerHTML = "Player1's move";
	}
}

//////////////////////// bonus ahead

function predictAhead(Vals, currMove) {
	var empties = [];
	for(var i = 0; i < 9; i++) {
		if(Vals[i] == "") {
			empties.push(i);
		} 
	}

	var nextMove;
	if(currMove == 'X') {
		nextMove = 'O';
	} else {
		nextMove = 'X';
	}

	var init_ans = validate_arrs(Vals);
	if(init_ans != "N" || empties.length == 0) {
		return init_ans;
	} else {
		var x = empties.map(function (temp) {
			var temparr = Vals.slice();
			temparr[temp] = currMove;
			return predictAhead(temparr, nextMove);
		});
		for (var i = 0; i < x.length; i++) {
			if (x[i] != x[0]) {
				return "N";
			}
		}
		return x[0];
	}
	
}

function validate_arrs(Vals) {
	var Oboxes  = [];
	var Xboxes  = [];
	for(var i = 0; i < 9; i++) {
		if(Vals[i] == "X") {
			Xboxes[i] = true;
			Oboxes[i] = false;
		} else if (Vals[i] == "O") {
			Oboxes[i] = true;
			Xboxes[i] = false;
		} else {
			Oboxes[i] = false;
			Xboxes[i] = false;
 		} 
	}

	// check diagonals
	if((Oboxes[0] && Oboxes[4] && Oboxes[8]) || (Oboxes[2] && Oboxes[4] && Oboxes[6])) {
		return "O";
	}
	if((Xboxes[0] && Xboxes[4] && Xboxes[8]) || (Xboxes[2] && Xboxes[4] && Xboxes[6])) {
		return "X";
	}
	// check columns
	for(var i = 0; i < 3; i++) {

		if((Oboxes[i] && Oboxes[i+3] && Oboxes[i+6])) {
			return "O";
		}
		if((Xboxes[i] && Xboxes[i+3] && Xboxes[i+6])) {
			return "X";
		}	
	}
	// check rows
	for(var i = 0; i < 3; i++) {
		if((Oboxes[3*i] && Oboxes[3*i+1] && Oboxes[3*i+2])) {
			return "O";
		}
		if((Xboxes[3*i] && Xboxes[3*i+1] && Xboxes[3*i+2])) {
			return "X";
		}
	}
	return "N";
}