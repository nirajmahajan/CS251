import sqlite3
mydb = sqlite3.connect('ipl.db')
c = mydb.cursor()

c.execute('''
	SELECT striker, player, sixes, ball_count, 1.0*sixes/ball_count AS avg
	FROM
	(
		SELECT striker, PLAYER.player_name AS player, COUNT(striker) AS ball_count, 
		COUNT(CASE runs_scored 
		when 6 then 1 
		else NULL 
		end) AS sixes
		FROM BALL_BY_BALL
		INNER JOIN PLAYER ON PLAYER.player_id = striker
		GROUP BY striker
	)
	ORDER BY avg DESC, sixes DESC, ball_count DESC;
''')
rows = c.fetchall()
mydb.close()

for elem in rows:
	print('{},{},{},{},{}'.format(elem[0],elem[1],elem[2],elem[3],elem[4]))
