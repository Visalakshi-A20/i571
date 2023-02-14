import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.io.FileNotFoundException;
import java.util.Scanner;
// str = "["+<id>+":"+<type>+"]"
public class Parser {
    private static final String VAR_KEYWORD = "var";
    private static final String NUMBER_KEYWORD = "number";
    private static final String STRING_KEYWORD = "string";
    private static final String RECORD_KEYWORD = "record";
    private static final String END_KEYWORD = "end";

    private static final String IDENTIFIER_PATTERN = "[a-zA-Z_][a-zA-Z0-9_]*";
    private static final String COMMENT_PATTERN = "#.*";
    private int isRecord=0;

    private String input;
    private int pos;


    public Parser(String input) {
        this.input = input;
        this.pos = 0;
    }

    private void skipWhitespace() {
        while (pos < input.length() && Character.isWhitespace(input.charAt(pos))) {
            pos++;
        }
    }

    private void skipComment() {
        Matcher commentMatcher = Pattern.compile(COMMENT_PATTERN).matcher(input.substring(pos));
        if (commentMatcher.find()) {
            pos += commentMatcher.end();
        }
    }

    private boolean match(String keyword) {
        skipWhitespace();
        skipComment();
        int keywordLength = keyword.length();
        if (input.regionMatches(pos, keyword, 0, keywordLength)) {
            pos += keywordLength;
            return true;
        }
        return false;
    }
    private boolean match2(String keyword) {
        skipWhitespace();
        skipComment();
        int keywordLength = keyword.length();
        if (input.regionMatches(pos, keyword, 0, keywordLength)) {
            // pos += keywordLength;
            return true;
        }
        return false;
    }
    //    private boolean lookForwardMatch(int pos, String keyword) {
    
    //     if (input.regionMatches(pos, keyword, 0, keywordLength)) {
    //         return true;
    //     }
    //     return false;
    // }

    private String matchIdentifier() {
        Matcher identifierMatcher = Pattern.compile(IDENTIFIER_PATTERN).matcher(input.substring(pos));
        if (identifierMatcher.find()) {
            pos += identifierMatcher.end();
            return identifierMatcher.group();
        }
        return null;
    }

    private void parseType(String indent) {
        skipWhitespace();
        skipComment();
        int total_len = input.length()-1;
        if (match(NUMBER_KEYWORD)) {
            System.out.print("\"NUMBER\""+"]");
        } else if (match(STRING_KEYWORD)) {
            System.out.print("\"STRING\""+"]");
        } else if (match(RECORD_KEYWORD)) {
            System.out.print("[");
            isRecord=1;
            while (!match(END_KEYWORD)) {
                String fieldIdentifier = matchIdentifier();
                if (fieldIdentifier == null) {
                    throw new IllegalStateException("Expected field identifier, but none found");
                }
                // System.out.println(indent + "  Field: {" + fieldIdentifier+"     }");
                System.out.print("\n[\"" + fieldIdentifier + "\",");
                if (!match(":")) {
                    throw new IllegalStateException("Expected colon after field identifier, but none found");
                }
                parseType(indent + "  ");
                if (!match(";")) {
                    throw new IllegalStateException("Expected semicolon after field declaration, but none found");
                }
                if(!match2(END_KEYWORD) && isRecord==1)
                    System.out.print(",");

           
            }
            isRecord=0;
            System.out.println("]");
            System.out.println("]");

        } else {
            throw new IllegalStateException("Expected type, but none found");
        }
                // System.out.println("check this=="+isRecord);

        if(pos != total_len-1 && isRecord==0 )
        System.out.print(",");
        // if(lookForwardMatch(pos,END_KEYWORD))
        //     System.out.print("ALERT!!!");

    }

    public void parse() {
        while (pos < input.length()-1) {
            if (!match(VAR_KEYWORD)) {
                throw new IllegalStateException("Expected var keyword, but none found");
            }
            String identifier = matchIdentifier();
            if (identifier == null) {
                throw new IllegalStateException("Expected identifier, but none found");
            }
            // System.out.println("CHECK HERE---------------"+pos +"  "+input.length());
            System.out.print("\n[\"" + identifier + "\",");
            if (!match(":")) {
                throw new IllegalStateException("Expected colon after identifier, but none found");
            }
            parseType("");
            if (!match(";")) {
                throw new IllegalStateException("Expected semicolon after declaration, but none found");
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner= new Scanner(System.in);
        String input= scanner.nextLine();
        //        PrintStream o = new PrintStream(new File("output.txt"));
 
    
        // System.setOut(o);
        //JSONObject jsonObject = new JSONObject();
        /* 
    File file = new File("input.txt");
    Scanner scanner = new Scanner(file);
    StringBuilder sb = new StringBuilder();
    while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (!line.trim().startsWith("#")) {
            sb.append(line + "\n");
        }
        
       
    }
    */
    //String input = sb.toString();
    // System.out.println("check:" + input);
    Parser parser = new Parser(input);
    System.out.println("[");
    parser.parse();
    System.out.println("\n]");

        /*String input = "var x: number;\n" +
                       "var y: string;\n" +
                       "var z: record\n" +
                       "  a: number;\n" +
                       "  b: string;\n" +
                       "end;";
        Parser parser = new Parser(input);
        parser.parse();
        */
    }
}

        

