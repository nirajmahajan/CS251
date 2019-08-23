import sys
import tarfile
import os

if len(sys.argv)<3:
	print("Too few arguments")
else:
	existscount=0
	notexist=[]
	outpath=str(sys.argv[1])
	tar = tarfile.open(outpath, "w:gz")
	fromlist=sys.argv[2:]
	for p in fromlist:
		#print(p)
		if os.path.exists(p):
			existscount+=1
			x=os.path.realpath(p)
			#print(x)
			if os.path.isfile(p):
				y=x.replace("/","-")
				#print(y)
				j=os.path.dirname(x)
				z=j+"/"+y
				#os.rename(x,z)
				tar.add(x,arcname=y)
			else:#p is a directory
				temp=os.listdir(p)
				temp2=[]
				for pqr in temp:
					if os.path.isfile(p+pqr):
						temp2.append(p+pqr)
					else:
						temp2.append(p+pqr+"/")
				print(temp2)
				print(fromlist)
				#fromlist.remove(p)
				for xyz in temp2:
					fromlist.append(xyz)
				# fromlist=fromlist.remove(p)
				# if fromlist:
				# 	fromlist=temp+fromlist
				# else:
				# 	fromlist=temp
				#print(fromlist)
				continue
		else:
			notexist.append(p)
	tar.close()
	if existscount>0:
		print("Successfully compressed")
		if notexist:
			for p in notexist:
				print(p)
	else:
		print("All files are missing")

		
