class test {
## sample test program for proj #4 (type checker) --
   may not execute correctly ##

int i = 0;
int I = 99; ## duplicate declaration##
int j = ~123;
int bigPos = 11111111;
int bigNeg = ~2222222;
int ii = '9';// Initializer type error
bool b = true;
bool ja = true;
bool nein = false;
char c = 'c';
char nl = '\n';
char tab = '\t';
char bslash = '\\';
char tic = '\'';
CONST ten = 10;
const Ten = "ten";
int ar[100];
int ar2[~100];
int ar3[10];
int ar4[10];
char ar5[4];

void jj(){int i; return;}

void test() {
  ten=9;
  i++;
  j--;
  ten++;
  ten--;
  ar++;
  ar--;
  test++;
  test--;
  j = 222;
  j = 'x';
  j = "x";
  j = test;
  ar=ar3;
  ar3=ar4;
  ar5=ar4;
  ar5 = "abcd";
  ar5 = "a\\cd";
  ar5 = "ad";
  ar5[2] = ar5[3];
  j[2] = ar5[3];
  ar5[2] = ar5[true];
  
}

void exprTest(){
 i = b+1;
 j = ar - 3;
 i = c + 1;
 j = test / 5;
 b = 1 > true;
 b = ar != 0;
 b = test == exprTest;
 b = 1 && true;
 b = 'c' || false;
 b = ar && 17;
 b = ! test;
 c = c +1;
 c = (char) (c - 1);
}

void IOtest() {
   int i; char c; int ar[10];
   char cc[80];
   const t=10;
   read(i,c);
   read(t,IOtest,ar);
   print(10,false,'c',"abcd",cc);
   print(Iotest,ar);
}

int f() {
	return 10;
	return;
	return false;
}

void f() {
	return 10;
	return;
	return false;
}

void pp(int i, char c[], bool b) {
	char cc[100];
	int ii[100];
	udef();
	c();
	pp(1,cc,false);
	pp();
	pp(1);
	pp(1,cc,false,1,1);
	f();
	pp(1,'c',false);
	pp(ii,'c',false);
	pp(1,cc,0);
}
   
int ff(int i, char c[], bool b) {
	char cc[100];
	int ii[100];
	ff(1,cc,false);
	i=ff(1,cc,false);
	i=ff();
	c=ff(1,cc,false);
	b=ff(1,cc,false);
	i=ff(1);
	i=ff(1,cc,false,1,1);
	i=ff(1,'c',false);
	i=ff(ii,'c',false);
	i=ff(1,cc,0);
}

void jj(int i,int j,int i[]) // Valid overloading of jj
   {int ii; int j; int i=10; ii=false; return;}

void p(INT j, BooL bb[]) {
	print("Ans = ", j+1, "\n");
}

int f(int i){
        ar[i] = 100;
	suspend();
	ReturN i+1;
}

void main(){
	int me;
	read(b);

	if (b || true) {
		bool local;
		bool me;
		me = 10;
		me = ! me;
		me = !10;
		i = me+10;
		local = b && me && ja;
	} else	b = !(b || true||local);
	local = false;

	L: while (i != true) {
		i = i*i/2; break L;
		i = (int) i/(i-2); continue L;
		i = (int) "abcde";
	}
	me: while (i != '0') {
		i = i*i/2; break main;
		i = (int) i/(i-2); continue L;
		break xxx; continue yyy;
	}

	if (i == 10 || i < 21 || i > ~17 || i != 123) {
		print( i);
		p(17);q(); return;
	}

	if (i >= f(~3,10,20) || i <= f(i-1))
		print( i, f(i), 'Z', ar[123], "\n");

}
} // That's all folks
