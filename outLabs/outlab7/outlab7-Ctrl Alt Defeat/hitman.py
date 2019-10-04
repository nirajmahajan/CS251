import sqlite3
mydb = sqlite3.connect('ipl.db')
c = mydb.cursor()

c.execute('''
	SELECT id, name, sixes, ball_count, sixes*1.0/ball_count AS frac
	FROM
	(
		SELECT PLAYER.player_id as id, PLAYER.player_name AS name,  COUNT(striker) AS ball_count, COUNT(case runs_scored when 6 then 1 else NULL end) as sixes
		FROM BALL_BY_BALL
		INNER JOIN PLAYER ON PLAYER.player_id=BALL_BY_BALL.striker
		GROUP BY BALL_BY_BALL.striker	
		ORDER BY ball_count
	)
	ORDER BY frac DESC;
''')

rows = c.fetchall()

for i in rows:
    print("{},{},{},{},{}".format(i[0], i[1], i[2], i[3], i[4]))