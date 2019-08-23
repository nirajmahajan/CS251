n = int(input())
lines = []
mydict={}
allplayers={}
for x in range(0,n):
	line = input()
	if(line==""):
		continue
	line=line.split(":")
	match=line[0]
	line=line[1].split(",")
	line=[x.split("-") for x in line]
	players={}
	for y in line:
		players[y[0]]=int(y[1])
	for z in players:
		if z in allplayers:
			allplayers[z]+=players[z]
		else:
			allplayers[z]=players[z]
	mydict[match]=players
playerlist = [(k, v) for k, v in allplayers.items()] 
playerlist.sort(key = lambda x: x[0], reverse=True)
playerlist.sort(key = lambda x: x[1], reverse=True)
print(mydict)
print(playerlist)