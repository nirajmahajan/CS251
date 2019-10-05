import sqlite3
mydb = sqlite3.connect('ipl.db')
c = mydb.cursor()

c.execute('''
	SELECT 
	COUNT(case when battedfirst = match_winner AND battedfirst != 'NULL' then 1 else NULL end) ,
	COUNT(case when battedsecond = match_winner AND battedsecond != 'NULL' then 1 else NULL end) ,
	COUNT(case win_type when 'NA' then NULL else 1 end)
	FROM MATCH;
''')

i = c.fetchall()
mydb.close()
i = i[0]
print(round(i[0] / i[2], 3))
print(round(i[1] / i[2], 3))
