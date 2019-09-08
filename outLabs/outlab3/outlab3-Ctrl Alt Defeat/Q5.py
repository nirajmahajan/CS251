import argparse
import csv
import sys
import os.path

class MyArgParse(argparse.ArgumentParser):
	"""docstring for MyArgParse"""
	def __init__(self):
		super(MyArgParse, self).__init__()

	def error(self, mess):
		sys.stderr.write(mess + '\n')
		exit(2)
		
parser = MyArgParse()
parser.add_argument("--first_name", help="display a square of a given number",
                    required=True)
parser.add_argument("--last_name", help="display a square of a given number",
                    required=True)
parser.add_argument("--roll_no", help="display a square of a given number",
                    required=True)
parser.add_argument("--gender", help="display a square of a given number",
                    required=True)
parser.add_argument("--mobile", help="display a square of a given number",
                    required=True)
parser.add_argument("--dept", help="display a square of a given number",
                    required=True)
parser.add_argument("--CGPA", help="display a square of a given number",
                    required=True)
args = parser.parse_args()

my_dic = {'First Name': args.first_name, 'Last Name': args.last_name, 'Roll Number': args.roll_no, 'Gender': args.gender, 'Mobile':args.mobile, 'Dept':args.dept, 'CGPA': args.CGPA}

createdNew = True

if (os.path.isfile("student_database.csv")):
	createdNew = False

out_csv = open("student_database.csv", "a", newline = '')
writer = csv.DictWriter(out_csv, list(my_dic.keys()))

if createdNew:
	writer.writeheader()
writer.writerow(my_dic)

print("Successfully Added!!")