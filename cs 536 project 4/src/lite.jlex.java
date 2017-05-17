import java_cup.runtime.*;
/*  Expand this into your solution for project 2 */
// DUSTIN MAIDEN  PROJECT 4 - I DID NOT WRITE THIS, WAS GIVEN A BETTER WORKING COPY!!!
class CSXToken {
	int linenum;
	int colnum;
	CSXToken(int line,int col){
		linenum=line;colnum=col;};
}
class CSXIntLitToken extends CSXToken {
	int intValue;
	CSXIntLitToken(int val,int line,int col){
	   super(line,col);intValue=val; 
        };
}
class CSXIdentifierToken extends CSXToken {
	String identifierText;
	CSXIdentifierToken(String text,int line,int col){
		super(line,col);identifierText=text;};
}
class CSXCharLitToken extends CSXToken {
	char charValue;
	CSXCharLitToken(char val,int line,int col){
		super(line,col);charValue=val;};
}
class CSXStringLitToken extends CSXToken {
	String stringText; // Full text of string literal,
                          //  including quotes & escapes
	CSXStringLitToken(String text,int line,int col){
		super(line,col);
		stringText=text;
	};
}
// This class is used to track line and column numbers
// Feel free to change to extend it
class Pos {
	static int  linenum = 1; /* maintain this as line number current
                                 token was scanned on */
	static int  colnum = 1; /* maintain this as column number current
                                 token began at */
	static int  line = 1; /* maintain this as line number after
					scanning current token  */
	static int  col = 1; /* maintain this as column number after
					scanning current token  */
	static void setpos() { // set starting position for current token
		linenum = line;
		colnum = col;
	}
        static int countEOLs(String s) {
		char[] c = s.toCharArray();
		int cnt=0;
                for(int i=0; i < c.length; i++) {
			cnt+=(c[i]=='\n'?1:0);
                }
		return cnt;
	}
        static int distFromLastEOL(String s) {
		char[] c = s.toCharArray();
		int dist=1;
                for(int i= c.length-1; i >= 0; i--) {
			if (c[i] == '\n')
				return dist;
			else	dist++;
                }
		return 0;
	}
}


class Yylex {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final char YYEOF = '\uFFFF';
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yy_lexical_state;

	Yylex (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Yylex (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Yylex () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private char yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YYEOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YYEOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_start () {
		++yy_buffer_start;
	}
	private void yy_pushback () {
		--yy_buffer_end;
	}
	private void yy_mark_start () {
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int yy_acpt[] = {
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR
	};
	private int yy_cmap[] = {
		0, 0, 0, 0, 0, 0, 0, 0,
		0, 1, 2, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0,
		3, 4, 5, 6, 7, 7, 8, 9,
		10, 11, 12, 13, 14, 15, 7, 16,
		17, 17, 17, 17, 17, 17, 17, 17,
		17, 17, 18, 19, 20, 21, 22, 7,
		7, 23, 24, 25, 26, 27, 28, 29,
		30, 31, 29, 32, 33, 29, 34, 35,
		36, 29, 37, 38, 39, 40, 41, 42,
		29, 29, 29, 43, 44, 45, 7, 7,
		7, 23, 24, 25, 26, 27, 28, 29,
		30, 31, 29, 32, 33, 29, 46, 35,
		36, 29, 37, 38, 47, 40, 41, 42,
		29, 29, 29, 48, 49, 50, 51, 0
		
	};
	private int yy_rmap[] = {
		0, 1, 1, 1, 1, 2, 1, 1,
		1, 3, 1, 4, 5, 6, 1, 1,
		7, 8, 9, 10, 1, 1, 1, 1,
		1, 1, 1, 1, 1, 1, 1, 1,
		10, 1, 11, 1, 1, 10, 1, 10,
		10, 10, 10, 10, 10, 10, 10, 10,
		10, 10, 10, 10, 10, 12, 12, 13,
		14, 15, 16, 17, 18, 19, 20, 21,
		22, 23, 24, 25, 26, 27, 28, 29,
		30, 27, 31, 32, 33, 34, 35, 36,
		37, 38, 39, 40, 41, 42, 43, 44,
		45, 46, 47, 48, 49, 50, 51, 52,
		53, 54, 55, 56, 57, 58, 59, 60,
		61, 62, 63, 64, 65, 66, 67, 68,
		69, 70, 71 
	};
	private int yy_nxt[][] = {
		{ 1, 2, 3, 4, 5, 54, 57, 1,
			60, 63, 6, 7, 8, 9, 10, 11,
			12, 13, 14, 15, 16, 17, 18, 19,
			97, 98, 19, 99, 111, 19, 19, 55,
			19, 19, 19, 19, 112, 100, 19, 101,
			19, 102, 113, 20, 1, 21, 19, 101,
			22, 66, 23, 69 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, 24, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, 27, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, 28,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			68, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 13, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, 29, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, 30, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, 31, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 34, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, 73 
		},
		{ -1, -1, -1, 53, 53, 25, 53, 53,
			53, 53, 53, 53, 53, 53, 53, 53,
			53, 53, 53, 53, 53, 53, 53, 53,
			53, 53, 53, 53, 53, 53, 53, 53,
			53, 53, 53, 53, 53, 53, 53, 53,
			53, 53, 53, 53, 56, 53, 53, 53,
			53, 53, 53, 53 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 32, 19, 19, 19,
			19, 19, 58, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 58, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, 53, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, 53, -1, 53, 53,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, 59, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 19, 19, 37,
			19, 19, 19, -1, -1, -1, 19, 37,
			-1, -1, -1, -1 
		},
		{ 59, 59, 59, 59, 59, 59, 71, 59,
			59, 59, 59, 59, 59, 59, 59, 59,
			59, 59, 59, 59, 59, 59, 59, 59,
			59, 59, 59, 59, 59, 59, 59, 59,
			59, 59, 59, 59, 59, 59, 59, 59,
			59, 59, 59, 59, 59, 59, 59, 59,
			59, 59, 59, 59 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			26, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 39, 19, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, 35, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, 62, 62, 62, 62, 62,
			62, -1, 62, 62, 62, 62, 62, 62,
			62, 62, 62, 62, 62, 62, 62, 62,
			62, 62, 62, 62, 62, 62, 62, 62,
			62, 62, 62, 62, 62, 62, 62, 62,
			62, 62, 62, 62, 65, 62, 62, 62,
			62, 62, 62, 62 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 40, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, 62, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, 62, -1, 62, 62,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 33, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 41, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ 68, 68, 36, 68, 68, 68, 68, 68,
			68, 68, 68, 68, 68, 68, 68, 68,
			68, 68, 68, 68, 68, 68, 68, 68,
			68, 68, 68, 68, 68, 68, 68, 68,
			68, 68, 68, 68, 68, 68, 68, 68,
			68, 68, 68, 68, 68, 68, 68, 68,
			68, 68, 68, 68 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 34, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 42, 19, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ 59, 59, 59, 59, 59, 59, 38, 59,
			59, 59, 59, 59, 59, 59, 59, 59,
			59, 59, 59, 59, 59, 59, 59, 59,
			59, 59, 59, 59, 59, 59, 59, 59,
			59, 59, 59, 59, 59, 59, 59, 59,
			59, 59, 59, 59, 59, 59, 59, 59,
			59, 59, 59, 59 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 43, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 44, 19, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			45, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 19, 46, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 19, 19, 47,
			19, 19, 19, -1, -1, -1, 19, 47,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 48, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 19, 19, 49,
			19, 19, 19, -1, -1, -1, 19, 49,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 50, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 51, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 51, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 52, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, 61, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 64,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 19, 67, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 70,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 19, 19, 109,
			19, 19, 19, -1, -1, -1, 19, 109,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			72, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 74,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 75,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 19, 76, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 19, 77, 114,
			19, 19, 19, -1, -1, -1, 19, 114,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 19, 78, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 79, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 79, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 80, 19, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 81, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			82, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, 83, 19, 103, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 84, 19,
			19, 104, 19, 105, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 85, 19, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 86, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 87, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, 88, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 89, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 90,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 91, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 91, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 92, 19, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 93,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 94,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			95, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 96, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 96, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 106,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, 19, 19, 107, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 108, 19,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, 19, -1, -1, -1, -1, -1, 19,
			19, 19, 19, 19, 19, 19, 19, 110,
			19, 19, 19, 19, 19, 19, 19, 19,
			19, 19, 19, -1, -1, -1, 19, 19,
			-1, -1, -1, -1 
		}
	};
	public Symbol yylex ()
		throws java.io.IOException {
		char yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			if (YYEOF != yy_lookahead) {
				yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YYEOF == yy_lookahead && true == yy_initial) {

return new Symbol(sym.EOF, new  CSXToken(0,0));
				}
				else if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_to_mark();
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_pushback();
					}
					if (0 != (YY_START & yy_anchor)) {
						yy_move_start();
					}
					switch (yy_last_accept_state) {
					case 1:
						{System.out.println("Illegal token ("+yytext()+ ") ignored.");}
					case -2:
						break;
					case 2:
						{Pos.col +=1;}
					case -3:
						break;
					case 3:
						{Pos.line +=1; Pos.col = 1;}
					case -4:
						break;
					case 4:
						{Pos.col +=1;}
					case -5:
						break;
					case 5:
						{Pos.setpos(); Pos.col +=1;
		return new Symbol(sym.NOT,
			new CSXToken(Pos.linenum,Pos.colnum));}
					case -6:
						break;
					case 6:
						{Pos.setpos(); Pos.col +=1;
		return new Symbol(sym.LPAREN,
			new CSXToken(Pos.linenum,Pos.colnum));}
					case -7:
						break;
					case 7:
						{Pos.setpos(); Pos.col +=1;
		return new Symbol(sym.RPAREN,
			new CSXToken(Pos.linenum,Pos.colnum));}
					case -8:
						break;
					case 8:
						{Pos.setpos(); Pos.col +=1;
		return new Symbol(sym.TIMES,
			new CSXToken(Pos.linenum,Pos.colnum));}
					case -9:
						break;
					case 9:
						{Pos.setpos(); Pos.col +=1;
		return new Symbol(sym.PLUS,
			new CSXToken(Pos.linenum,Pos.colnum));}
					case -10:
						break;
					case 10:
						{Pos.setpos(); Pos.col +=1;
		return new Symbol(sym.COMMA,
			new CSXToken(Pos.linenum,Pos.colnum));}
					case -11:
						break;
					case 11:
						{Pos.setpos(); Pos.col +=1;
		return new Symbol(sym.MINUS,
			new CSXToken(Pos.linenum,Pos.colnum));}
					case -12:
						break;
					case 12:
						{Pos.setpos(); Pos.col +=1;
		return new Symbol(sym.SLASH,
			new CSXToken(Pos.linenum,Pos.colnum));}
					case -13:
						break;
					case 13:
						{ Pos.setpos(); Pos.col += yytext().length();
                  int i; double d = (new Double(yytext())).doubleValue();
                  if (d > Integer.MAX_VALUE){
                     System.out.println("Error: Integer literal "+
                         yytext()+" too large; replaced with "+
                         Integer.MAX_VALUE);
                     i=Integer.MAX_VALUE; }
                  else i = new Integer(yytext()).intValue(); 
		  return new Symbol(sym.INTLIT,
				new CSXIntLitToken(
		                    	i,Pos.linenum,Pos.colnum));}
					case -14:
						break;
					case 14:
						{Pos.setpos(); Pos.col +=1;
		return new Symbol(sym.COLON,
			new CSXToken(Pos.linenum,Pos.colnum));}
					case -15:
						break;
					case 15:
						{Pos.setpos(); Pos.col +=1;
		return new Symbol(sym.SEMI,
			new CSXToken(Pos.linenum,Pos.colnum));}
					case -16:
						break;
					case 16:
						{Pos.setpos(); Pos.col +=1;
		return new Symbol(sym.LT,
			new CSXToken(Pos.linenum,Pos.colnum));}
					case -17:
						break;
					case 17:
						{Pos.setpos(); Pos.col +=1;
		return new Symbol(sym.ASG,
			new CSXToken(Pos.linenum,Pos.colnum));}
					case -18:
						break;
					case 18:
						{Pos.setpos(); Pos.col +=1;
		return new Symbol(sym.GT,
			new CSXToken(Pos.linenum,Pos.colnum));}
					case -19:
						break;
					case 19:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -20:
						break;
					case 20:
						{Pos.setpos(); Pos.col +=1;
		return new Symbol(sym.LBRACKET,
			new CSXToken(Pos.linenum,Pos.colnum));}
					case -21:
						break;
					case 21:
						{Pos.setpos(); Pos.col +=1;
		return new Symbol(sym.RBRACKET,
			new CSXToken(Pos.linenum,Pos.colnum));}
					case -22:
						break;
					case 22:
						{Pos.setpos(); Pos.col +=1;
		return new Symbol(sym.LBRACE,
			new CSXToken(Pos.linenum,Pos.colnum));}
					case -23:
						break;
					case 23:
						{Pos.setpos(); Pos.col +=1;
		return new Symbol(sym.RBRACE,
			new CSXToken(Pos.linenum,Pos.colnum));}
					case -24:
						break;
					case 24:
						{Pos.setpos(); Pos.col +=2;
		return new Symbol(sym.NOTEQ,
			new CSXToken(Pos.linenum,Pos.colnum));}
					case -25:
						break;
					case 25:
						{ Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.STRLIT,
                                new CSXStringLitToken(yytext(),
                                     Pos.linenum,Pos.colnum));}
					case -26:
						break;
					case 26:
						{Pos.setpos(); Pos.col +=2;
		return new Symbol(sym.CAND,
			new CSXToken(Pos.linenum,Pos.colnum));}
					case -27:
						break;
					case 27:
						{Pos.setpos(); Pos.col +=2;
		return new Symbol(sym.INCREMENT,
			new CSXToken(Pos.linenum,Pos.colnum));}
					case -28:
						break;
					case 28:
						{Pos.setpos(); Pos.col +=2;
		return new Symbol(sym.DECREMENT,
			new CSXToken(Pos.linenum,Pos.colnum));}
					case -29:
						break;
					case 29:
						{Pos.setpos(); Pos.col +=2;
		return new Symbol(sym.LEQ,
			new CSXToken(Pos.linenum,Pos.colnum));}
					case -30:
						break;
					case 30:
						{Pos.setpos(); Pos.col +=2;
		return new Symbol(sym.EQ,
			new CSXToken(Pos.linenum,Pos.colnum));}
					case -31:
						break;
					case 31:
						{Pos.setpos(); Pos.col +=2;
		return new Symbol(sym.GEQ,
			new CSXToken(Pos.linenum,Pos.colnum));}
					case -32:
						break;
					case 32:
						{Pos.setpos(); Pos.col +=2;
                        return new Symbol(sym.rw_IF,
                            new CSXToken(Pos.linenum,Pos.colnum));}
					case -33:
						break;
					case 33:
						{Pos.setpos(); Pos.col +=2;
		return new Symbol(sym.COR,
			new CSXToken(Pos.linenum,Pos.colnum));}
					case -34:
						break;
					case 34:
						{ Pos.setpos(); Pos.col += yytext().length();
                  String unsignedVal = yytext().substring(1);
                  int i; double d = -1.0*(new Double(unsignedVal).doubleValue());
                  if (d < Integer.MIN_VALUE){
                     System.out.println("Error: Integer literal "+
                         yytext()+" too small; replaced with "+
                         Integer.MIN_VALUE);
                     i=Integer.MIN_VALUE; }
                  else i = -1*(new Integer(unsignedVal).intValue()); 
		  return new Symbol(sym.INTLIT,
				new CSXIntLitToken(
		                    	i,Pos.linenum,Pos.colnum));}
					case -35:
						break;
					case 35:
						{ Pos.setpos(); Pos.col += yytext().length();
			String val=yytext();
			char cval =  val.charAt(val.length()-2);
                        if (val.length()==4)
                           if (val.charAt(2)=='n')
                               cval = '\n';
                           else if (val.charAt(2)== 't')
                               cval = '\t';
                        return new Symbol(sym.CHARLIT,
                                new CSXCharLitToken(cval,
                                     Pos.linenum,Pos.colnum));}
					case -36:
						break;
					case 36:
						{Pos.line +=1; Pos.col = 1;}
					case -37:
						break;
					case 37:
						{Pos.setpos(); Pos.col +=3;
                        return new Symbol(sym.rw_INT,
                            new CSXToken(Pos.linenum,Pos.colnum));}
					case -38:
						break;
					case 38:
						{if (Pos.countEOLs(yytext()) > 0) {
                              Pos.line +=Pos.countEOLs(yytext());
                              Pos.col = Pos.distFromLastEOL(yytext());}
	                 else Pos.col += yytext().length(); }
					case -39:
						break;
					case 39:
						{Pos.setpos(); Pos.col +=4;
                    return new Symbol(sym.rw_BOOL,
                        new CSXToken(Pos.linenum,Pos.colnum));}
					case -40:
						break;
					case 40:
						{Pos.setpos(); Pos.col +=4;
                    return new Symbol(sym.rw_CHAR,
                            new CSXToken(Pos.linenum,Pos.colnum));}
					case -41:
						break;
					case 41:
						{Pos.setpos(); Pos.col +=4;
                    return new Symbol(sym.rw_ELSE,
                        new CSXToken(Pos.linenum,Pos.colnum));}
					case -42:
						break;
					case 42:
						{Pos.setpos(); Pos.col +=4;
                    return new Symbol(sym.rw_READ,
                        new CSXToken(Pos.linenum,Pos.colnum));}
					case -43:
						break;
					case 43:
						{Pos.setpos(); Pos.col +=4;
                    return new Symbol(sym.rw_TRUE,
                        new CSXToken(Pos.linenum,Pos.colnum));}
					case -44:
						break;
					case 44:
						{Pos.setpos(); Pos.col +=4;
                    return new Symbol(sym.rw_VOID,
                        new CSXToken(Pos.linenum,Pos.colnum));}
					case -45:
						break;
					case 45:
						{Pos.setpos(); Pos.col +=5;
                        return new Symbol(sym.rw_BREAK,
                            new CSXToken(Pos.linenum,Pos.colnum));}
					case -46:
						break;
					case 46:
						{Pos.setpos(); Pos.col +=5;
                        return new Symbol(sym.rw_CLASS,
                            new CSXToken(Pos.linenum,Pos.colnum));}
					case -47:
						break;
					case 47:
						{Pos.setpos(); Pos.col +=5;
                        return new Symbol(sym.rw_CONST,
                            new CSXToken(Pos.linenum,Pos.colnum));}
					case -48:
						break;
					case 48:
						{Pos.setpos(); Pos.col +=5;
                        return new Symbol(sym.rw_FALSE,
                            new CSXToken(Pos.linenum,Pos.colnum));}
					case -49:
						break;
					case 49:
						{Pos.setpos(); Pos.col +=5;
                        return new Symbol(sym.rw_PRINT,
                            new CSXToken(Pos.linenum,Pos.colnum));}
					case -50:
						break;
					case 50:
						{Pos.setpos(); Pos.col +=5;
                        return new Symbol(sym.rw_WHILE,
                            new CSXToken(Pos.linenum,Pos.colnum));}
					case -51:
						break;
					case 51:
						{Pos.setpos(); Pos.col +=6;
                    return new Symbol(sym.rw_RETURN,
                        new CSXToken(Pos.linenum,Pos.colnum));}
					case -52:
						break;
					case 52:
						{Pos.setpos(); Pos.col +=8;
                        return new Symbol(sym.rw_CONTINUE,
                            new CSXToken(Pos.linenum,Pos.colnum));}
					case -53:
						break;
					case 54:
						{System.out.println("Illegal token ("+yytext()+ ") ignored.");}
					case -54:
						break;
					case 55:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -55:
						break;
					case 57:
						{System.out.println("Illegal token ("+yytext()+ ") ignored.");}
					case -56:
						break;
					case 58:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -57:
						break;
					case 60:
						{System.out.println("Illegal token ("+yytext()+ ") ignored.");}
					case -58:
						break;
					case 61:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -59:
						break;
					case 63:
						{System.out.println("Illegal token ("+yytext()+ ") ignored.");}
					case -60:
						break;
					case 64:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -61:
						break;
					case 66:
						{System.out.println("Illegal token ("+yytext()+ ") ignored.");}
					case -62:
						break;
					case 67:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -63:
						break;
					case 69:
						{System.out.println("Illegal token ("+yytext()+ ") ignored.");}
					case -64:
						break;
					case 70:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -65:
						break;
					case 72:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -66:
						break;
					case 74:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -67:
						break;
					case 75:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -68:
						break;
					case 76:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -69:
						break;
					case 77:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -70:
						break;
					case 78:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -71:
						break;
					case 79:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -72:
						break;
					case 80:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -73:
						break;
					case 81:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -74:
						break;
					case 82:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -75:
						break;
					case 83:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -76:
						break;
					case 84:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -77:
						break;
					case 85:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -78:
						break;
					case 86:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -79:
						break;
					case 87:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -80:
						break;
					case 88:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -81:
						break;
					case 89:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -82:
						break;
					case 90:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -83:
						break;
					case 91:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -84:
						break;
					case 92:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -85:
						break;
					case 93:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -86:
						break;
					case 94:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -87:
						break;
					case 95:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -88:
						break;
					case 96:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -89:
						break;
					case 97:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -90:
						break;
					case 98:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -91:
						break;
					case 99:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -92:
						break;
					case 100:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -93:
						break;
					case 101:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -94:
						break;
					case 102:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -95:
						break;
					case 103:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -96:
						break;
					case 104:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -97:
						break;
					case 105:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -98:
						break;
					case 106:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -99:
						break;
					case 107:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -100:
						break;
					case 108:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -101:
						break;
					case 109:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -102:
						break;
					case 110:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -103:
						break;
					case 111:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -104:
						break;
					case 112:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -105:
						break;
					case 113:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -106:
						break;
					case 114:
						{Pos.setpos(); Pos.col += yytext().length();
                    return new Symbol(sym.IDENTIFIER,
                      new CSXIdentifierToken(yytext(),Pos.linenum,Pos.colnum));}
					case -107:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
					}
				}
			}
		}
	}
}
