import java.util.ArrayList;

// Dustin Maiden Project 4 Type Checker
// The following methods type check  AST nodes used in CSX Lite
//  You will need to complete the methods after line 238 to type check the
//   rest of CSX
//  Note that the type checking done for CSX lite may need to be extended to
//   handle full CSX (for example binaryOpNode).

public class TypeChecking extends Visitor { 
	
	int typeErrors;     // Total number of type errors found 
	
	ASTNode.Types curReturnT; 		//current return type
	boolean mainDeclared = false; 	//main -> declared
	
	SymbolTable st;	

	TypeChecking(){
		typeErrors = 0;
		st = new SymbolTable(); 
		curReturnT = ASTNode.Types.Void;//default
	}
//unchanged
	boolean isTypeCorrect(csxLiteNode n) {
		this.visit(n);
		return (typeErrors == 0);
	}
//unchanged
	boolean isTypeCorrect(classNode n) {
		this.visit(n);
		return (typeErrors == 0);
	}
//unchanged
	static void assertCondition(boolean assertion){  
		if (! assertion)
			throw new RuntimeException();
	}
//unchanged
	void typeMustBe(ASTNode.Types testType,ASTNode.Types requiredType,String errorMsg) {
		if ((testType != ASTNode.Types.Error) && (testType != requiredType)) {
			System.out.println(errorMsg);
			typeErrors++;
		}
	}

	void typeMustBe(ASTNode.Types testType, ASTNode.Types option1, ASTNode.Types option2, String errorMsg){
		if ((testType != ASTNode.Types.Error) && !((testType == option1) || (testType == option2)))
		{
			System.out.println(errorMsg);
			typeErrors++;
		}
	}

	void typesMustBeEqual(ASTNode.Types type1,ASTNode.Types type2,String errorMsg) {
		if ((type1 != ASTNode.Types.Error) && (type2 != ASTNode.Types.Error) && (type1 != type2)) {
			System.out.println(errorMsg);
			typeErrors++;
		}
	}

//Array | Scalar | Var 
boolean assignable(ASTNode.Kinds testKind){
return (testKind == ASTNode.Kinds.Array) || (testKind == ASTNode.Kinds.Array) || (testKind == ASTNode.Kinds.ScalarParm) || (testKind == ASTNode.Kinds.Var);
}

//Var, Value, ScalarParm
boolean scalar(ASTNode.Kinds testKind){
return (testKind == ASTNode.Kinds.ScalarParm) || (testKind == ASTNode.Kinds.Value) || (testKind == ASTNode.Kinds.Var);
}

//builds an array of to store type and kind
ArrayList<Pinfo> buildArgList(argsNodeOption firstArg){
ArrayList<Pinfo> args = new ArrayList<Pinfo>();
argsNodeOption currentArg = firstArg;

//while processing	
while (!currentArg.isNull()){ 
argsNode temp = (argsNode)currentArg;
Pinfo pi = new Pinfo(temp.argVal.kind, temp.argVal.type);
args.add(pi);
currentArg = temp.moreArgs;
}
return args;
}
	
//comparisons
void isComparable(ASTNode.Types type1,ASTNode.Types type2,String errorMsg) {
if ((type1 == ASTNode.Types.Error) || (type2 == ASTNode.Types.Error))
return;
if ((type1 == ASTNode.Types.Boolean) && (type2 == ASTNode.Types.Boolean))
return;
if (((type1 == ASTNode.Types.Integer) || (type1 == ASTNode.Types.Character)) && ((type2 == ASTNode.Types.Integer) || (type2 == ASTNode.Types.Character)))
return;
System.out.println(errorMsg);
typeErrors++;
}

int countingCs(String str){
int count = 0;
str = str.substring(1,str.length()-1);//removing ""s

while(str.contains("\\")){
str = str.replace("\\", "");
count++;
}
while(str.contains("\\'")){
str = str.replace("\\'", "");
count++;
}
while(str.contains("\\n")){
str = str.replace("\\n", "");
count++;
}
while(str.contains("\\t")){
str = str.replace("\\t", "");
count++;
}
return count+=str.length(); //add remaining
}
//unchanged
String error(ASTNode n) {return "Error (line " + n.linenum + "): ";}

//filled out opToString
static String opToString(int op) {
switch (op) {
case sym.CAND:return(" && ");
case sym.COR:return(" || ");
case sym.EQ:return(" == ");
case sym.GEQ:return(" >= ");
case sym.GT:return(" > ");
case sym.LEQ:return(" <= ");
case sym.LT:return(" < ");
case sym.PLUS:return(" + ");
case sym.MINUS:return(" - ");
case sym.NOTEQ:return(" != ");
case sym.SLASH:return(" / ");
case sym.TIMES:return(" * ");
default:
assertCondition(false);return "";
}
}

//filled out printOp
static void printOp(int op) {
switch (op) {
case sym.CAND:System.out.print(" && ");break;
case sym.COR:System.out.print(" || ");break;
case sym.EQ:System.out.print(" == ");break;
case sym.GEQ:System.out.print(" >= ");break;
case sym.GT:System.out.print(" > ");break;
case sym.LEQ:System.out.print(" <= ");break;
case sym.LT:System.out.print(" < ");break;
case sym.PLUS:System.out.print(" + ");break;
case sym.MINUS:System.out.print(" - ");break;
case sym.NOTEQ:System.out.print(" != ");break;
case sym.SLASH:System.out.print(" / ");break;
case sym.TIMES:
System.out.print(" * ");break;
default:
throw new Error();
}
}
//unchanged
	void visit(csxLiteNode n){
		this.visit(n.progDecls);
		this.visit(n.progStmts);
	}
//unchanged
	void visit(fieldDeclsNode n){
		this.visit(n.thisField);
		this.visit(n.moreFields);
	}
//unchanged
	void visit(nullFieldDeclsNode n){}
//unchanged
	void visit(stmtsNode n){
		this.visit(n.thisStmt);
		this.visit(n.moreStmts);
	}
//unchanged
	void visit(nullStmtsNode n){}

//fill out VarDeclNode
void visit(varDeclNode n){
	
SymbolInfo     id;
id = (SymbolInfo) st.localLookup(n.varName.idname);

// Make sure declaration hasn't already happen
if (id != null) {
System.out.println(error(n) + id.name()+ " is already declared.");
typeErrors++;
n.varName.type = ASTNode.Types.Error;
} 
else{
//initialize
if(!n.initValue.isNull()){
// Type check initial value expression.
visit(n.initValue);

// Check that the initial value's type is typeNode.type
typesMustBeEqual(n.varType.type, ((exprNode)n.initValue).type, error(n)+"Initializer must be of type "+n.varType.type);

// Make sure initial value's scalar
try{
assertCondition(scalar(((exprNode)n.initValue).kind));
} 
catch (RuntimeException r){
System.out.println(error(n)+"Initial value must be scalar");
}
				
// Enter symbol table
id = new SymbolInfo(n.varName.idname,ASTNode.Kinds.Var, n.varType.type);

n.varName.type = n.varType.type;
try {
st.insert(id);
} 
catch (DuplicateException d) 
{ /* can't happen */ }
catch (EmptySTException e) 
{ /* can't happen */ }
n.varName.idinfo=id;
} 
else{ 

// Enter symbol table
id = new SymbolInfo(n.varName.idname,ASTNode.Kinds.Var, n.varType.type);
n.varName.type = n.varType.type;
try {
st.insert(id);
} 
catch (DuplicateException d) 
{ /* can't happen */ }
catch (EmptySTException e) 
{ /* can't happen */ }
n.varName.idinfo=id;
}
}
}
//unchanged
void visit(nullTypeNode n){}
//unchanged
void visit(intTypeNode n){
//no type checking needed}
}
//unchanged
void visit(boolTypeNode n){
//no type checking needed}
}

// look in symbol table -> copy type and kind -> store
void visit(identNode n){
SymbolInfo    id;
id =  (SymbolInfo) st.globalLookup(n.idname);
if (id == null) {
System.out.println(error(n) +  n.idname + " is not declared.");
typeErrors++;
n.type = ASTNode.Types.Error;
} 
else{
n.type = id.type; 
n.kind = id.kind;
n.idinfo = id; // Save ptr to correct symbol table entry
}
}

// Extend nameNode's method to handle subscripts
void visit(nameNode n){
this.visit(n.varName);    	 // type check
if(n.subscriptVal.isNull()){ // if null node, copy
n.type = n.varName.type;
n.kind = n.varName.kind;
return;
}
		
this.visit(n.subscriptVal);  // type check
try{
assertCondition(n.varName.kind == ASTNode.Kinds.Array); // array
} catch (RuntimeException e) {
typeErrors++;
System.out.println(error(n) + "Only arrays can be subscripted.");
return;
}

exprNode temp = (exprNode)n.subscriptVal;
try{
assertCondition(scalar(temp.kind)); // int
assertCondition((temp.type == ASTNode.Types.Integer) ||(temp.type == ASTNode.Types.Character));
} catch (RuntimeException e) {
typeErrors++;
System.out.println(error(n) + "subscript value should be an int or" +" char, but " + temp.type + " was found.");
}
n.type=n.varName.type;    // set type and kind
}

void visit(asgNode n){

this.visit(n.target); // type check
this.visit(n.source); // type check
try{
assertCondition(assignable(n.target.kind)); // check is assignable
} catch (RuntimeException e) {
typeErrors++;
System.out.println(error(n) + "Target of kind " + n.target.kind +" is not assignable.");
return;
} 
if(scalar(n.target.kind)){ // if scalar
try{
//can't assign
assertCondition(n.target.kind != ASTNode.Kinds.Value);
} catch (RuntimeException e) {
typeErrors++;
System.out.println(error(n) + "Cannot assign to a const.");
}
try{
//must have same type
assertCondition(n.target.type == n.source.type);
} catch (RuntimeException e) {
typeErrors++;
System.out.println(error(n)+"Both of the left and right sides must be same type.");
}
return;
}

// arrays
if((n.target.varName.kind == ASTNode.Kinds.Array) && (n.source.kind == ASTNode.Kinds.Array) && (n.target.varName.type == n.source.type)){
			
//get array info
SymbolInfo id_s = (SymbolInfo)st.globalLookup(n.target.varName.idname);
nameNode temp = (nameNode)n.source; //array => nameNode
SymbolInfo id_t = (SymbolInfo)st.globalLookup(temp.varName.idname);
try{
assertCondition(id_s.arrSize == id_t.arrSize);
} catch (RuntimeException e) {
typeErrors++;
System.out.println(error(n) + "Arrays must be same length.");
}
return;			
}
// array and char
if(n.target.kind == ASTNode.Kinds.Array && n.target.type == ASTNode.Types.Character && n.source.kind == ASTNode.Kinds.String){
SymbolInfo id_s = (SymbolInfo)st.globalLookup(n.target.varName.idname);
strLitNode temp2 = (strLitNode)n.source; //string->strLit
try{
assertCondition(id_s.arrSize == countingCs(temp2.strval));
} catch (RuntimeException e) {
typeErrors++;
System.out.println(error(n) + "char array and string must have same length.");
}
return;
}
System.out.println(error(n) + "Incorrect assignment.");
}

// Extend ifThenNode's method to handle else parts
void visit(ifThenNode n){
this.visit(n.condition);
typeMustBe(n.condition.type, ASTNode.Types.Boolean,error(n) + "The expression of an if must be a bool.");
this.visit(n.thenPart);
this.visit(n.elsePart);
}

//print int, bool, chars v's, char arrays, string lits
void visit(printNode n){
this.visit(n.outputValue);
try{
assertCondition((n.outputValue.type == ASTNode.Types.Integer || n.outputValue.type == ASTNode.Types.Boolean ||
				 n.outputValue.type == ASTNode.Types.Character ) || (n.outputValue.type == ASTNode.Types.Character &&
				 n.outputValue.kind == ASTNode.Kinds.Array ) || (n.outputValue.kind == ASTNode.Kinds.String));
} catch (RuntimeException e) {
typeErrors++;
System.out.println(error(n) + "Can only print int's, bool's, and char's, strings, or arrays of chars.");
}
this.visit(n.morePrints);
}
//unchanged
	void visit(blockNode n){
		// open a new local scope for the block body
		st.openScope();
		this.visit(n.decls);
		this.visit(n.stmts);
		// close this block's local scope
		try { st.closeScope();
		}  catch (EmptySTException e) 
		{ /* can't happen */ }
	}

	void visit(binaryOpNode n){

//Extend binaryopNode
assertCondition(
n.operatorCode == sym.CAND  ||
n.operatorCode == sym.COR   ||				
n.operatorCode == sym.EQ    ||
n.operatorCode == sym.GEQ   ||
n.operatorCode == sym.GT    ||
n.operatorCode == sym.LEQ   ||
n.operatorCode == sym.LT    ||
n.operatorCode == sym.MINUS || 
n.operatorCode == sym.NOTEQ ||
n.operatorCode == sym.PLUS  ||
n.operatorCode == sym.SLASH ||
n.operatorCode == sym.TIMES);

this.visit(n.leftOperand);
this.visit(n.rightOperand);
		
// Check that both scalar.
try{
assertCondition(scalar(n.leftOperand.kind));
assertCondition(scalar(n.rightOperand.kind));
} catch (RuntimeException r){
System.out.println(error(n)+"Operands of"+opToString(n.operatorCode)+ "must be scalar.");
typeErrors++;
}
//Check + - / *
if (n.operatorCode== sym.PLUS  || n.operatorCode== sym.MINUS || n.operatorCode== sym.SLASH || n.operatorCode== sym.TIMES){
// Arithmetic can be int or char values;
n.type = ASTNode.Types.Integer;

//Check (int or char)
typeMustBe(n.leftOperand.type, ASTNode.Types.Integer, ASTNode.Types.Character, error(n) + "Left operand of" + opToString(n.operatorCode) + "must be arithmetic.");
			
typeMustBe(n.rightOperand.type, ASTNode.Types.Integer, ASTNode.Types.Character, error(n) + "Right operand of" + opToString(n.operatorCode) + "must be arithmetic.");
} 
//Check == < > != <= >=
else if (n.operatorCode == sym.EQ  || n.operatorCode == sym.NOTEQ || n.operatorCode == sym.GEQ || n.operatorCode == sym.GT || n.operatorCode == sym.LEQ || n.operatorCode == sym.LT) {
			
// Relational -> pair of arithmetic or pair of bool 
n.type = ASTNode.Types.Boolean;

String errorMsg = error(n)+"operands of"+ opToString(n.operatorCode)+"must be arithmetic or must be boolean.";
			
// must be arithmetic, or booleans
isComparable(n.leftOperand.type, n.rightOperand.type,errorMsg);
} 
else {
//Logical -> bool values
n.type = ASTNode.Types.Boolean;

//Check boolean type. 
typeMustBe(n.leftOperand.type, ASTNode.Types.Boolean, error(n) + "Left operand of" + opToString(n.operatorCode) + "must be bool.");
typeMustBe(n.rightOperand.type, ASTNode.Types.Boolean, error(n) + "Right operand of" + opToString(n.operatorCode) + "must be boolean.");
}
}

void visit(intLitNode n){
n.kind = ASTNode.Kinds.Var;
n.type = ASTNode.Types.Integer;
}

void visit(classNode n){

SymbolInfo	id;
id = new SymbolInfo(n.className.idname, ASTNode.Kinds.VisibleLabel, ASTNode.Types.Void);
try {
st.insert(id);
st.openScope();
			
//Type check 
this.visit(n.members); 
st.closeScope();
} catch (DuplicateException e) {
// Can't occur
} catch (EmptySTException e) {
// Can't occur
}
}

void  visit(memberDeclsNode n){
//Type check 
this.visit(n.fields);
//Type check 
this.visit(n.methods);

//if a main method declared
if(mainDeclared == false){
System.out.println("Error: No main method was declared.");
}
}

void  visit(methodDeclsNode n){
this.visit(n.thisDecl);
this.visit(n.moreDecls);
}
//unchanged
	void visit(nullStmtNode n){}
//unchanged
	void visit(nullReadNode n){}
//unchanged
	void visit(nullPrintNode n){}
//unchanged
	void visit(nullExprNode n){}
//unchanged
	void visit(nullMethodDeclsNode n){}


void visit(methodDeclNode n){	
		
SymbolInfo     id;
id = (SymbolInfo) st.localLookup(n.name.idname);

//if main already declared.
if(mainDeclared){
System.out.println(error(n) + "No method can be declared after main.");
typeErrors++;
n.name.type = ASTNode.Types.Error;
}

//If main, check it is void with no args
if(n.name.idname.equals("main")){
mainDeclared = true;
if(!n.args.isNull()){
System.out.println(error(n)+"main must take 0 arguments.");
typeErrors++;
n.name.type = ASTNode.Types.Error;
}

if(n.returnType.type != ASTNode.Types.Void){
System.out.println(error(n)+"main must have return type void.");
typeErrors++;
n.name.type = ASTNode.Types.Error;
}
}

// if already in symbol table
if (id != null) {
//OVERLOADING
			
if(id.kind != ASTNode.Kinds.Method){
System.out.println(error(n) + n.name.idname + " is already declared.");
typeErrors++;
n.name.type = ASTNode.Types.Error;
}

// Create new scope in symbol table.
st.openScope();

//Type check args subtree - list of symbol table nodes is created
this.visit(n.args);

//If in the table, check for unique params
if((id.kind == ASTNode.Kinds.Method) && id.containsParms(st.getParms()))
{
System.out.println(error(n) + n.name.idname + " is already "+ "declared. Invalid overloading.");
typeErrors++;
n.name.type = ASTNode.Types.Error;
} 
//Overloading
else if (id.kind == ASTNode.Kinds.Method) {
id.addMethodParms(st.getParms());
}

//Check type
if(id.type != n.returnType.type){
System.out.println(error(n) + n.name.idname + " must be of type " + id.type);
n.name.type = ASTNode.Types.Error;
typeErrors++;
}
			
curReturnT = n.returnType.type;
			
//Type check
this.visit(n.decls);
			
//Type check 
this.visit(n.stmts);

try {
st.closeScope();
} catch (EmptySTException e) {
}

n.name.idinfo=id;
} else {
//Add to symbol table
id = new SymbolInfo(n.name.idname, ASTNode.Kinds.Method, n.returnType.type);

try {
st.insert(id);
} catch (DuplicateException d) 
{ /* can't happen */ }
catch (EmptySTException e) 
{ /* can't happen */ }

//New scope
st.openScope();

//Type check
this.visit(n.args);

// Add the parameters
id.addMethodParms(st.getParms());
curReturnT = n.returnType.type;
			
//Type check 
this.visit(n.decls);
			
//Type check 
this.visit(n.stmts);

//Close scope
try { st.closeScope();
} catch (EmptySTException e)
{ /* can't happen */ }
n.name.idinfo=id;
}
}

// int or char may be ++/--
void visit(incrementNode n){
this.visit(n.target);
try{
assertCondition((
n.target.kind == ASTNode.Kinds.Var || n.target.kind == ASTNode.Kinds.ScalarParm || n.target.kind == ASTNode.Kinds.ArrayParm) &&
(n.target.type == ASTNode.Types.Character || n.target.type == ASTNode.Types.Integer));
} catch (RuntimeException e ){
typeErrors++;
System.out.println(error(n) + "Target of ++ can't be changed.");
}
}

// int or char may be ++/--
void visit(decrementNode n){
this.visit(n.target);
try{
assertCondition((
n.target.kind == ASTNode.Kinds.Var || n.target.kind == ASTNode.Kinds.ScalarParm || n.target.kind == ASTNode.Kinds.ArrayParm) &&
(n.target.type == ASTNode.Types.Character ||n.target.type == ASTNode.Types.Integer));
} catch (RuntimeException e) {
typeErrors++;
System.out.println(error(n) + "Target of -- can't be changed.");
}
}

void visit(argDeclsNode n){
this.visit(n.thisDecl);
this.visit(n.moreDecls);
}
//unchanged
void visit(nullArgDeclsNode n){}

void visit(valArgDeclNode n){
SymbolInfo	id;

// Check if already in symbol table
id = (SymbolInfo) st.localLookup(n.argName.idname);
if (id != null) {
System.out.println(error(n) + id.name()+ " is already declared.");
typeErrors++;
n.argName.type = ASTNode.Types.Error;
			
//Inserts node into list
st.addParm(new Pinfo(ASTNode.Kinds.ScalarParm,n.argType.type));
} else {
			
//Enter into symbol table
id = new SymbolInfo(n.argName.idname,ASTNode.Kinds.ScalarParm, n.argType.type);
n.argName.type = n.argType.type;
try {
st.insert(id);
} catch (DuplicateException d) 
{ /* can't happen */ }
catch (EmptySTException e) 
{ /* can't happen */ }
n.argName.idinfo=id;

// Insert list 
st.addParm(new Pinfo(ASTNode.Kinds.ScalarParm,n.argType.type));
}
}

void visit(arrayArgDeclNode n){
SymbolInfo	id;

// Check if already in symbol table.
id = (SymbolInfo) st.localLookup(n.argName.idname);
if (id != null) {
System.out.println(error(n) + id.name()+ " is already declared.");
typeErrors++;
n.argName.type = ASTNode.Types.Error;

// Insert into list
st.addParm(new Pinfo(ASTNode.Kinds.ArrayParm, n.elementType.type));
} else {		
			
// Add to symbol table 
id = new SymbolInfo(n.argName.idname,ASTNode.Kinds.ArrayParm, n.argName.type);
n.argName.type = n.elementType.type;
try {
st.insert(id);
} catch (DuplicateException d) 
{ /* can't happen */ }
catch (EmptySTException e) 
{ /* can't happen */ }
n.argName.idinfo=id;

//Add to list
st.addParm(new Pinfo(ASTNode.Kinds.ArrayParm, n.elementType.type));
}
}

void visit(constDeclNode n){
SymbolInfo	id;

// Check if already in symbol table
id = (SymbolInfo) st.localLookup(n.constName.idname);
if (id != null) {
System.out.println(error(n) + id.name()+ " is already declared.");
typeErrors++;
n.constName.type = ASTNode.Types.Error;
} else {

// Type check .
visit(n.constValue);	

// Check if scalar
try{
assertCondition(scalar(n.constValue.kind));
} catch (RuntimeException r){
typeErrors++;
System.out.println(error(n)+"Only scalars can be made consts");
}
//Add to symbol table with 
id = new SymbolInfo(n.constName.idname,ASTNode.Kinds.Value, n.constValue.type);

//type of a const is type of exp 
n.constName.type = n.constValue.type;
try {
st.insert(id);
} catch (DuplicateException d) 
{ /* can't happen */ }
catch (EmptySTException e) 
{ /* can't happen */ }
n.constName.idinfo=id;
}	
}

void visit(arrayDeclNode n){
SymbolInfo	id;

// Check if already in symbol table.
id = (SymbolInfo) st.localLookup(n.arrayName.idname);
if (id != null) {
System.out.println(error(n) + id.name()+ " is already declared.");
typeErrors++;
n.arrayName.type = ASTNode.Types.Error;
} else {

id = new SymbolInfo(n.arrayName.idname,ASTNode.Kinds.Array, n.elementType.type);

//size -> 0
if(n.arraySize.intval <= 0){
System.out.println(error(n) + n.arrayName.idname + " must have more than 0 elements.");
typeErrors++;
n.arrayName.type = ASTNode.Types.Error;
id.arrSize = 1;
} else {
id.arrSize = n.arraySize.intval;
n.arrayName.type = n.elementType.type;
}

try {
st.insert(id);
} catch (DuplicateException d) 
{ /* can't happen */ }
catch (EmptySTException e) 
{ /* can't happen */ }
n.arrayName.idinfo=id;
}
return;
}

void visit(charTypeNode n){
//No type check needed
}

void visit(voidTypeNode n){
//No type check needed
}

void visit(whileNode n){
//check
this.visit(n.condition);
try{
//check for boolean
assertCondition(n.condition.type == ASTNode.Types.Boolean && scalar(n.condition.kind));
} catch (RuntimeException e) {
typeErrors++;
System.out.println(error(n) + "Condition must be a scalar boolean.");
}
//if null
if(n.label.isNull()){ 
this.visit(n.loopBody);
return;
//otherwise, a label
} else { 
identNode temp = (identNode)n.label;
//if already in symbol table
SymbolInfo id = (SymbolInfo) st.localLookup(temp.idname); 
if (id != null) {
System.out.println(error(n)+id.name()+" label is already declared.");
typeErrors++;
temp.type = ASTNode.Types.Error;
}else { 
//add to symbol table
id = new SymbolInfo(temp.idname, ASTNode.Kinds.VisibleLabel,ASTNode.Types.Void);
try {
st.insert(id);
} catch (DuplicateException d) 
{ System.out.println("here"); }
catch (EmptySTException e) 
{ System.out.println("here1"); }
}
//check type
this.visit(n.loopBody); 
//change type
id.kind = ASTNode.Kinds.HiddenLabel; 
}
}

// check that in symbol table and check kind is Visible 
void visit(breakNode n){
SymbolInfo id;
id = (SymbolInfo) st.globalLookup(n.label.idname);
if (id == null) {
System.out.println(error(n) + n.label.idname + " isn't a valid label.");
typeErrors++;
return;
}
try{
assertCondition(id.kind == ASTNode.Kinds.VisibleLabel);
} catch (RuntimeException e) {
typeErrors++;
System.out.println(error(n) + "Label "+n.label.idname+" out of scope.");
}
}

void visit(continueNode n){
SymbolInfo id;
id = (SymbolInfo) st.globalLookup(n.label.idname);
if (id == null) {
System.out.println(error(n) + n.label.idname + " isn't a valid label.");
typeErrors++;
return;
}
try{
assertCondition(id.kind == ASTNode.Kinds.VisibleLabel);
} catch (RuntimeException e) {
typeErrors++;
System.out.println(error(n) + "Label "+n.label.idname+" out of scope.");
}
}

void visit(callNode n){

SymbolInfo id;
//check if in symbol table
id = (SymbolInfo) st.globalLookup(n.methodName.idname); 
if (id == null) {
System.out.println(error(n) + n.methodName.idname+"()"+ " isn't declared.");
typeErrors++;
return;
}
try{
assertCondition(id.type == ASTNode.Types.Void && id.kind == ASTNode.Kinds.Method);
} catch (RuntimeException e){
typeErrors++;
System.out.println(error(n) + n.methodName.idname +" requires a return value.");
return;
}
//check type
this.visit(n.args); 
		
//build list
ArrayList<Pinfo> args = buildArgList(n.args); 
try{
assertCondition(id.containsParms(args)); 
} catch (RuntimeException e){
typeErrors++;
n.methodName.type = ASTNode.Types.Error;
if(id.parameters.size() == 0){
System.out.println(error(n)+n.methodName.idname+" requires 0 parameters");
} else if (id.parameters.size() == 1){
if(id.parameters.get(0).size() != args.size()){
System.out.println(error(n)+n.methodName.idname+" requires "+id.parameters.get(0).size()+" parameters");
}
else
for(int i = 0; i < id.parameters.get(0).size(); i++){
if(i == args.size())break;
if(!args.get(i).isEqual(id.parameters.get(0).get(i))){	
System.out.println(error(n)+"In the call to "+n.methodName.idname+" parameter "+(i+1)+" has incorrect type.");
}
}
} else{
System.out.println(error(n)+"None of the "+id.parameters.size() +" definitions of "+n.methodName.idname+" match the parameters in this call.");
}
}
}

//only int and char 
void visit(readNode n){
if(n.targetVar.varName.linenum != -1){ 
this.visit(n.targetVar);
try{
assertCondition((n.targetVar.type == ASTNode.Types.Integer || n.targetVar.type == ASTNode.Types.Character));
} catch (RuntimeException e){
typeErrors++;
System.out.println(error(n) + "Only int and char values may be read.");
return;
}
}
this.visit(n.moreReads);
}

void visit(returnNode n){
// if null, check that void
if(n.returnVal.isNull()){ 
try{
assertCondition(curReturnT == ASTNode.Types.Void);
} catch (RuntimeException e){
System.out.println(error(n) + "Missing return value of type " +curReturnT+".");
}
//if not null, check that scalar
} else { 
exprNode temp = (exprNode)n.returnVal;
try{
assertCondition(scalar(temp.kind) &&(temp.type == curReturnT));
} catch (RuntimeException e){
System.out.println(error(n) + "Return type mismatch; found "+ temp.type+" but expected "+curReturnT+".");
}
}
}

void visit(argsNode n){
this.visit(n.argVal);
this.visit(n.moreArgs);
}
//unchanged
void visit(nullArgsNode n){}

//can only cast int, char, or bool to type int, char, or bool.
void visit(castNode n){
this.visit(n.operand);
try{
assertCondition(!(n.resultType instanceof voidTypeNode) && (n.operand.type == ASTNode.Types.Integer ||
				  n.operand.type == ASTNode.Types.Character || n.operand.type == ASTNode.Types.Boolean));
} catch (RuntimeException e){
typeErrors++;
System.out.println(error(n) + "Can only cast ints, chars, and bools"+" to int, char, or bool.");
}
if(n.resultType instanceof boolTypeNode){
n.type = ASTNode.Types.Boolean;
} else if (n.resultType instanceof charTypeNode){
n.type = ASTNode.Types.Character;
}else if (n.resultType instanceof intTypeNode){
n.type = ASTNode.Types.Integer;
}
n.kind = n.operand.kind;
}

void visit(fctCallNode n){
SymbolInfo id;
id = (SymbolInfo) st.globalLookup(n.methodName.idname); // lookup method
if (id == null) {
System.out.println(error(n) + n.methodName.idname+"() is not " + "declared.");
typeErrors++;
n.methodName.type = ASTNode.Types.Error;
} else if (id.kind != ASTNode.Kinds.Method) {
System.out.println(error(n) + n.methodName.idname+" is not a method.");
typeErrors++;
n.methodName.type = ASTNode.Types.Error;			
} else {
			
//Assign type and kind of the method
n.type = id.type;
n.kind = ASTNode.Kinds.ScalarParm;
try{
assertCondition(!(id.type == ASTNode.Types.Void));
} catch (RuntimeException e){
System.out.println(error(n) + n.methodName.idname +" is called as a procedure and must return void.");
typeErrors++;
}
this.visit(n.methodArgs); // type check
ArrayList<Pinfo> args = buildArgList(n.methodArgs); // build list
try{
assertCondition(id.containsParms(args)); // check compatibility
} catch (RuntimeException e){
typeErrors++;
if(id.parameters.size() == 0){
System.out.println(error(n)+n.methodName.idname+" requires 0 parameters");
} else if (id.parameters.size() == 1){
if(id.parameters.get(0).size() != args.size()){
System.out.println(error(n)+n.methodName.idname+" requires "+id.parameters.get(0).size()+" parameters");
}
else
for(int i = 0; i < id.parameters.get(0).size(); i++){
if(i == args.size())break;
if(!args.get(i).isEqual(id.parameters.get(0).get(i)))
System.out.println(error(n)+"In the call to "+n.methodName.idname+" parameter "+(i+1)+" has incorrect type.");
}
} else{
System.out.println(error(n)+"None of the "+id.parameters.size()+" definitions of "+n.methodName.idname+" match the parameters in this call.");
}
}
}
}

void visit(unaryOpNode n){
this.visit(n.operand);
try{
assertCondition(n.operand.type == ASTNode.Types.Boolean);
} catch (RuntimeException e){
System.out.println(error(n)+"Operand of ! must be bool.");
}
n.type = ASTNode.Types.Boolean;
}

void visit(charLitNode n){
n.kind = ASTNode.Kinds.Var;
n.type = ASTNode.Types.Character;
}

void visit(strLitNode n){
n.kind = ASTNode.Kinds.String;
}

void visit(trueNode n){
}

void visit(falseNode n){
}
}