package starbucks;

public class StrategyHandler {

    private static boolean isPortrait = true;
    private static final int TWO = 2, SIX = 6, TEN = 10, FIFTEEN = 15, THIRTYTWO = 32;

    public static void setIsPortrait(boolean isPortrait) {
        StrategyHandler.isPortrait = isPortrait;
    }
    public static boolean getIsPortrait(){
        return isPortrait;
    }
    public static int getWidth(){
        if( isPortrait )
            return FIFTEEN;
        else
            return THIRTYTWO;
    }
    public static int getLength(){
        if( isPortrait )
            return TEN;
        else
            return SIX;
    }

    public static String padSpaces(String str) {

        int length = str.length();
        int pad = (getWidth()-length)/TWO;
        int rem = (getWidth()-length)%TWO;

        StringBuffer out_bf = new StringBuffer();
        out_bf.append(moreSpaces(pad));
        out_bf.append(moreSpaces(rem));
        out_bf.append(str);
        out_bf.append(moreSpaces(pad));
        out_bf.append("\n");

        return out_bf.toString();

    }
    private static String moreSpaces(int spaces){
        StringBuffer out_bf = new StringBuffer();

        for ( int i = 0; i<spaces; i++ )
            out_bf.append(" ");
        return out_bf.toString() ;
    }

    public static String padLines(String str) {

        int length = countLines(str);
        int pad = (getLength()-length)/TWO;
        int rem = (getLength()-length)%TWO;

        StringBuffer out_bf = new StringBuffer();
        out_bf.append(moreLines(pad));
        out_bf.append(str);
        out_bf.append(moreLines(pad));
        out_bf.append(moreLines(rem));

        return out_bf.toString();
    }

    private static String moreLines(int lines) {
        StringBuffer out_bf = new StringBuffer();
        for (int i = 0; i < lines; ++i) {
            out_bf.append("\n");
        }
        return out_bf.toString();
    }

    private static int countLines(String count){

        int lines = 0;
        int position = 0;
        if (count == null || count.isEmpty()) {
            return lines;
        }
        while ((position = count.indexOf("\n", position) + 1) != 0) {
            lines++;
        }
        return lines ;
    }

    public static String getEqualLines(){
        String out ;
        if( isPortrait )
            out = "===============\n";
        else
            out = "================================\n";
        return out ;
    }

}
