## Java Parser

-- This Java Parser project was developed as part of a university assignment. 

Sample imput/output:

Input:
```
set one "The cat";
set two "sat on the mat";
set sentence one + SPACE + two;
append sentence " by itself.";
print sentence;
printwordcount sentence;
printwords sentence;
printlength sentence;
list;
reverse	one;
print   one;
exit;
```

Output:
```
The cat sat on the mat by itself.
Wordcount: 8
Words:
The
cat
sat
on
the
mat
by
itself
Length: 33
Identifier List(3):
one : The cat
two : sat on the mat
sentence : The cat sat on the mat by itself.
cat The 
```

