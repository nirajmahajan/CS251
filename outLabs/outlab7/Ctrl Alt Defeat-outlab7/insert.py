import sqlite3 as sq
import csv

mydb = sq.connect('ipl.db')
curs = mydb.cursor()

with open('team.csv', 'r') as f:
    dictread=csv.DictReader(f)
    to = [(i['team_id'], i['team_name']) for i in dictread]
curs.executemany('''INSERT OR REPLACE INTO TEAM (team_id, team_name) VALUES (?, ?);''', to)

with open('player.csv', 'r') as f:
    dictread=csv.DictReader(f)
    to = [(i['player_id'], i['player_name'], i['dob'], i['batting_hand'], i['bowling_skill'], i['country_name']) for i in dictread]
curs.executemany('''INSERT OR REPLACE INTO PLAYER (player_id, player_name, dob, batting_hand, bowling_skill, country_name) VALUES (?, ?, ?, ?, ?, ?);''', to)

with open('match.csv', 'r') as f:
    dictread=csv.DictReader(f)
    to = [(i['match_id'], i['season_year'], i['team1'], i['team2'], i['battedfirst'], i['battedsecond'], i['venue_name'], i['city_name'], i['country_name'], i['toss_winner'], i['match_winner'], i['toss_name'], i['win_type'], i['man_of_match'], i['win_margin']) for i in dictread]
curs.executemany('''INSERT OR REPLACE INTO MATCH (match_id, season_year, team1, team2, battedfirst, battedsecond, venue_name, city_name, country_name, toss_winner, match_winner, toss_name, win_type, man_of_match, win_margin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);''', to)

with open('player_match.csv', 'r') as f:
    dictread=csv.DictReader(f)
    to = [(i['playermatch_key'], i['match_id'], i['player_id'], i['batting_hand'], i['bowling_skill'], i['role_desc'], i['team_id']) for i in dictread]
curs.executemany('''INSERT OR REPLACE INTO PLAYER_MATCH (playermatch_key, match_id, player_id, batting_hand, bowling_skill, role_desc, team_id) VALUES (?, ?, ?, ?, ?, ?, ?);''', to)

with open('ball_by_ball.csv', 'r') as f:
    dictread=csv.DictReader(f)
    to = [(i['match_id'], i['innings_no'], i['over_id'], i['ball_id'], i['striker_batting_position'], i['runs_scored'], i['extra_runs'], i['out_type'], i['striker'], i['non_striker'], i['bowler']) for i in dictread]
curs.executemany('''INSERT OR REPLACE INTO BALL_BY_BALL (match_id, innings_no, over_id, ball_id, striker_batting_position, runs_scored, extra_runs, out_type, striker, non_striker, bowler) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);''', to)

mydb.commit()
mydb.close()