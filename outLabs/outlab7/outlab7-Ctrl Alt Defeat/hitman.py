import sqlite3
mydb = sqlite3.connect('ipl.db')
c = mydb.cursor()

c.execute('''
	SELECT striker, player, ball_count,sixes, 1.0*sixes/ball_count as avg
	FROM
	(
		SELECT striker, PLAYER.player_name as player, COUNT(striker) as ball_count, 
		COUNT(CASE runs_scored 
		when 6 then 1 
		else null 
		end) as sixes
		FROM BALL_BY_BALL
		INNER JOIN PLAYER ON PLAYER.player_id = striker
		GROUP BY striker
		ORDER BY sixes DESC
	)
	ORDER BY avg DESC;
''')
rows = c.fetchall()
mydb.close()

for elem in rows:
	print('{},{},{},{},{}'.format(elem[0],elem[1],elem[2],elem[3],elem[4]))
