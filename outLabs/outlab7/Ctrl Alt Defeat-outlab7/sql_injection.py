import sqlite3
mydb = sqlite3.connect('ipl.db')
c = mydb.cursor()

table_id = int(input())
add_statement = ""

if(table_id == 1):
	table_name = 'TEAM'
	identifier = 'team_name'
elif(table_id == 2):
	table_name = 'PLAYER'
	identifier = 'player_name'
elif(table_id == 3):
	table_name = 'MATCH'
	identifier = 'match_id'

mode = int(input())
delete_statement_common = 'DELETE FROM ' + table_name + ' WHERE ' + identifier + ' = '
third_parameter_value = input()

if(mode == 0):
	c.execute(delete_statement_common + "\"" + third_parameter_value + "\"")
elif (mode == 1):
	c.execute((delete_statement_common + '?'), (third_parameter_value, ))

mydb.commit()
mydb.close()