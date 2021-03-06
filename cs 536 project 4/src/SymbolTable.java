//
//You may use this symbol table implementation or your own (from project 1)
//
import java.util.*;
import java.io.*;
class SymbolTable {
class Scope {
		   
ArrayList<Pinfo> parameters; // Array
		   
Hashtable<String,Symb> currentScope;
Scope next;
Scope() {
currentScope = new Hashtable<String,Symb>();
next = null;
//added
parameters = new ArrayList<Pinfo>();
}

Scope(Scope scopes) {
currentScope = new Hashtable<String,Symb>();
next = scopes;
//added         
parameters = new ArrayList<Pinfo>();
}
}

private Scope top;

SymbolTable() {top = new Scope();}

public void openScope() {
top = new Scope(top); 
}

public ArrayList<Pinfo> getParms(){
return top.parameters;
}

public void addParm(Pinfo p){
top.parameters.add(p);
}

public void closeScope() throws EmptySTException {
if (top == null)
throw new EmptySTException();
else top = top.next;
}

public void insert(Symb s)
throws DuplicateException, EmptySTException {
String key = (s.name().toLowerCase());
if (top == null)
	throw new EmptySTException();
if (localLookup(key) != null)
	 throw new DuplicateException();
else top.currentScope.put(key,s);
}

public Symb localLookup(String s) {
String key = s.toLowerCase();
if (top == null) return null;
Symb ans =top.currentScope.get(key);
return ans;
}

public Symb globalLookup(String s) {
String key = s.toLowerCase();
Scope top = this.top;
while (top != null) {
Symb ans = top.currentScope.get(key);
if (ans != null) return ans;
else top = top.next;
}
return null;
}

public String toString() {
String ans = "";
Scope top = this.top;
while (top != null) {
ans = ans +  top.currentScope.toString()+"\n";
top = top.next;
}
return ans;
}

void dump(PrintStream ps) {
ps.print(toString());
}
}