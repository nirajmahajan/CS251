import sys
import tarfile
import os

if len(sys.argv) < 3:
	print("Too few arguments")
else:
	existscount = 0
	notexist = []
	outpath = str(sys.argv[1])
	tar = tarfile.open(outpath, "w:gz")
	fromlist = sys.argv[2:]
	for p in fromlist:
		if os.path.exists(p):
			existscount += 1
			x = os.path.realpath(p)
			if os.path.isfile(p):
				y = x.replace("/","-")
				j = os.path.dirname(x)
				z = j + "/" + y
				if y not in tar.getnames():
					tar.add(x,arcname=y)
			else:
				temp = os.listdir(p)
				temp2 = []
				for pqr in temp:
					if os.path.isfile(p + pqr):
						temp2.append(p+ pqr)
					else:
						temp2.append(p + pqr + "/")
				for xyz in temp2:
					fromlist.append(xyz)
				continue
		else:
			notexist.append(p)
	tar.close()
	if existscount > 0:
		print("Successfully compressed")
		if notexist:
			for p in notexist:
				print(p)
	else:
		print("All files are missing")

		

		
