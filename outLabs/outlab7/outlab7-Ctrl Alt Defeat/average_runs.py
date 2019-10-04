import sqlite3
mydb=sqlite3.connect('ipl.db')
c=mydb.cursor()

c.execute('''
	SELECT venue_name,AVG(runs_sum)
	FROM
	(
		SELECT MATCH.venue_name AS venue_name,SUM(runs_scored+extra_runs) AS runs_sum,
		FROM BALL_BY_BALL
		INNER JOIN MATCH ON MATCH.match_id=BALL_BY_BALL.match_id
	)
	GROUP BY venue_name;
	'''
	)
rows=c.fetchall()

for i in rows:
	print(i)