import re
import sys

regex_mail = re.compile(r'((?!.*[\._][_\.].*)\b[0-9a-zA-Z][0-9a-zA-Z_.]*@[0-9a-zA-Z.]*[a-zA-Z]\b)')
regex_phone = re.compile(r'(\b[1-9][0-9]{9}\b)')

diary = open(sys.argv[1])

mails = regex_mail.findall(diary.read())
diary.seek(0)
phones = regex_phone.findall(diary.read())

contacts = {}

for a in phones:
	a = a.strip()
	if a in contacts:
		contacts[a] += 1
	else:
		contacts[a] = 1

for a in mails:
	if a in contacts:
		contacts[a] += 1
	else:
		contacts[a] = 1

if (not sys.argv[2] in contacts):
	contacts[sys.argv[2]] = 0

for a in contacts.keys():
	if a == sys.argv[2]:
		continue

	if contacts[a] > contacts[sys.argv[2]]:
		print("Cheater alert! {} {}".format(a, contacts[a]))


# (?!.*[\._][_\.].*)\b[0-9a-zA-Z][0-9a-zA-Z_.]*@[0-9a-zA-Z.]*[a-zA-Z]\b