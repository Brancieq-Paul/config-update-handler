package com.paulbrancieq.configupdatehandler.valuesystem.consumers.paths;

public class EntryPathConsumer extends PathConsumer {

    public EntryPathConsumer(String path) {
        super(".", path);
    }

    public EntryPathConsumer(EntryPathConsumer pathConsumer, Boolean resetPathPartsLeft) {
        super(pathConsumer, resetPathPartsLeft);
    }

    @Override
    protected void separePathParts() {
        String escapeChar = "\\\\";
        String regularExpressionSpecialChars = "/.*+?|()[]{}\\";

        // Prepare the separator for split by regex
        String usedSep = this.sep;
        if(regularExpressionSpecialChars.indexOf(usedSep) != -1) 
            usedSep = "\\" + usedSep;
        
        // Split the path by the non escaped separators
        String[] temp = path.split("(?<!" + escapeChar + ")" + this.sep, -1);

        // Remove the escape characters from the path parts
        String[] result = new String[temp.length];
        for(int i=0; i<temp.length; i++) {
            result[i] = temp[i].replaceAll(escapeChar + this.sep, this.sep);
        }
        // Set the path parts
        this.pathParts = result;
    }
}
