import sqlite3
mydb = sqlite3.connect('ipl.db')
c = mydb.cursor()

c.execute('''
	SELECT id, name, AVG(ball_count) as avrg
	FROM
	(
		SELECT PLAYER.player_id as id, PLAYER.player_name AS name,  COUNT(striker) AS ball_count
		FROM BALL_BY_BALL
		INNER JOIN PLAYER ON PLAYER.player_id=BALL_BY_BALL.striker
		GROUP BY BALL_BY_BALL.match_id	
	)
	GROUP BY id
	ORDER BY avrg DESC ;''')

row = c.fetchall()

for i in row[:10]:
    print("{},{},{}".format(i[0], i[1], round(i[2])))
ref = row[9]
j = 10
while row[j][-1] == ref[-1]:
    print("{},{},{}".format(row[j][0], row[j][1], round(row[j][2])))
    j += 1
