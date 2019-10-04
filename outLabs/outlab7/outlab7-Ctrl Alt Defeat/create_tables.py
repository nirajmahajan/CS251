import sqlite3 as sq

mydb = sq.connect('ipl.db')
curs = mydb.cursor()

curs.execute(
	'''DROP TABLE IF EXISTS TEAM;''')
curs.execute(
	'''DROP TABLE IF EXISTS PLAYER;''')
curs.execute(
	'''DROP TABLE IF EXISTS MATCH;''')
curs.execute(
    '''DROP TABLE IF EXISTS PLAYER_MATCH;''')
curs.execute(
    '''DROP TABLE IF EXISTS BALL_BY_BALL;''')

curs.execute(
    '''CREATE TABLE IF NOT EXISTS TEAM (
    team_id INTEGER NOT NULL PRIMARY KEY, 
    team_name TEXT NOT NULL);'''
)
curs.execute(
    '''CREATE TABLE IF NOT EXISTS PLAYER (
    player_id INTEGER NOT NULL PRIMARY KEY, 
    player_name TEXT NOT NULL, 
    dob TIMESTAMP NOT NULL, 
    batting_hand TEXT NOT NULL, 
    bowling_skill TEXT, 
    country_name TEXT NOT NULL);'''
)
curs.execute(
    '''CREATE TABLE IF NOT EXISTS MATCH (
    match_id INTEGER PRIMARY KEY,
    season_year INTEGER,
    team1 INTEGER REFERENCES TEAM (team_id),
    team2 INTEGER REFERENCES TEAM (team_id),
    battedfirst INTEGER REFERENCES TEAM (team_id),
    battedsecond INTEGER REFERENCES TEAM (team_id),
    venue_name TEXT,
    city_name TEXT,
    country_name TEXT,
    toss_winner TEXT,
    match_winner TEXT,
    toss_name TEXT,
    win_type TEXT,
    man_of_match INTEGER,
    win_margin INTEGER);'''
)
curs.execute(
    '''CREATE TABLE IF NOT EXISTS PLAYER_MATCH (
    playermatch_key BIGINT NOT NULL PRIMARY KEY,
    match_id INTEGER NOT NULL REFERENCES MATCH (match_id),
    player_id INTEGER NOT NULL REFERENCES PLAYER (player_id),
    batting_hand TEXT NOT NULL,
    bowling_skill TEXT,
    role_desc TEXT NOT NULL,
    team_id INTEGER NOT NULL REFERENCES TEAM (team_id));'''
)
curs.execute(
    '''CREATE TABLE IF NOT EXISTS BALL_BY_BALL (
    match_id INTEGER NOT NULL REFERENCES MATCH (match_id),
    innings_no INTEGER NOT NULL,
    over_id INTEGER NOT NULL,
    ball_id INTEGER NOT NULL,
    striker_batting_position INTEGER,
    runs_scored INTEGER NOT NULL,
    extra_runs INTEGER NOT NULL,
    out_type TEXT,
    striker INTEGER NOT NULL REFERENCES PLAYER (player_id),
    non_striker INTEGER NOT NULL REFERENCES PLAYER (player_id),
    bowler INTEGER NOT NULL REFERENCES PLAYER (player_id),
    PRIMARY KEY (match_id, innings_no, over_id, ball_id));'''
)
mydb.commit()
mydb.close()