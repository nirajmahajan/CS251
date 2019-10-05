import sqlite3
mydb = sqlite3.connect('ipl.db')
c = mydb.cursor()

table_id = int(input())
add_statement = ""

if(table_id == 1):
	table_name = 'TEAM'
	add_statement='INSERT OR REPLACE INTO TEAM (team_id, team_name) VALUES (?, ?)'
elif(table_id == 2):
	table_name = 'PLAYER'
	add_statement='INSERT OR REPLACE INTO PLAYER (player_id, player_name, dob, batting_hand, bowling_skill, country_name) VALUES (?, ?, ?, ?, ?, ?)'
elif(table_id == 3):
	table_name = 'MATCH'
	add_statement='INSERT OR REPLACE INTO MATCH (match_id, season_year, team1, team2, battedfirst, battedsecond, venue_name, city_name, country_name, toss_winner, match_winner, toss_name, win_type, man_of_match, win_margin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)'
elif(table_id == 4):
	table_name = 'PLAYER_MATCH'
	add_statement='INSERT OR REPLACE INTO PLAYER_MATCH (playermatch_key, match_id, player_id, batting_hand, bowling_skill, role_desc, team_id) VALUES (?, ?, ?, ?, ?, ?, ?)'
elif(table_id == 5):
	table_name = 'BALL_BY_BALL'
	add_statement='INSERT OR REPLACE INTO BALL_BY_BALL (match_id, innings_no, over_id, ball_id, striker_batting_position, runs_scored, extra_runs, out_type, striker, non_striker, bowler) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)'


c.execute('''
	SELECT *
	FROM ''' + table_name)

col_names = list(map(lambda x: x[0], c.description))

input_vals = []

for a in col_names:
	temp = input()
	input_vals.append(temp)

print(input_vals)
c.execute(add_statement, input_vals)
mydb.commit()
mydb.close()

