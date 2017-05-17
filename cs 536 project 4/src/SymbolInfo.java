/**************************************************
*  class used to hold information associated w/
*  Symbs (which are stored in SymbolTables)
*  Update to handle arrays and methods
*
*  Dustin Maiden Project 4
****************************************************/

import java.util.ArrayList;

class SymbolInfo extends Symb {
public ArrayList<ArrayList<Pinfo>> parameters;
public ASTNode.Kinds kind;
public ASTNode.Types type;
	
int arrSize; //Used by arrays
private exprNode[] items;   //used by arrays

public SymbolInfo(String id, ASTNode.Kinds k, ASTNode.Types t){    
super(id);
	arrSize = 0;
	type = t;
	kind = k; 
	parameters = new ArrayList<ArrayList<Pinfo>>();
};

public void setArraysize(int size){
	arrSize = size;
}

public void addMethodParms(ArrayList<Pinfo> parms){
	parameters.add(parms);
}

public void addItem(int index, exprNode e){
	items[index] = e;
}
	
public exprNode getItem(int index){
	return items[index];
}
	
	//detect bad OVERLOADING
public boolean containsParms(ArrayList<Pinfo> parms)
{
	boolean duplicate = false;
	for(int i = 0; i < parameters.size(); i++){
		
	if(parms.size() == parameters.get(i).size()){
		if(parms.size() == 0)
		return true; //empty
		duplicate = true;
				
		for(int j = 0; j < parameters.get(i).size(); j++){
			duplicate = parms.get(j).isEqual(parameters.get(i).get(j)); // if a match
			if(duplicate == false) // if not a match
			break;					
			}
			if(duplicate)
				return true; //duplicates
			}
		}
		return false; //No match 
	}

	public String toString(){
	return "("+name()+": kind=" + kind+ ", type="+  type+")";};
}
