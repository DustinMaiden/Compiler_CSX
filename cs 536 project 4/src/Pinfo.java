public class Pinfo {
	ASTNode.Kinds kind;
	ASTNode.Types type;
	
Pinfo(ASTNode.Kinds k, ASTNode.Types t){
this.kind = k;
this.type = t;
}
	
public boolean isEqual(Pinfo p){
	
if(this.type != p.type) // if not equal
return false;
		
if(this.kind == ASTNode.Kinds.ArrayParm ||this.kind == ASTNode.Kinds.Array ||this.kind == ASTNode.Kinds.String)
return (p.kind == ASTNode.Kinds.Array || p.kind == ASTNode.Kinds.ArrayParm ||p.kind == ASTNode.Kinds.String);
		
if(this.kind == ASTNode.Kinds.ScalarParm ||this.kind == ASTNode.Kinds.Value ||this.kind == ASTNode.Kinds.Var)
return (p.kind == ASTNode.Kinds.ScalarParm || p.kind == ASTNode.Kinds.Value ||p.kind == ASTNode.Kinds.Var);
return false;
}
	
	public String toString(){
return "Kind: "+kind+" Type: "+type;
}
}