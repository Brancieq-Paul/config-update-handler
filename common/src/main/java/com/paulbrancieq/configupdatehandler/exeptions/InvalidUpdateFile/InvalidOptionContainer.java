package com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile;

public class InvalidOptionContainer extends UpdateFileExeption {
    
        public InvalidOptionContainer(String expected_option_type, String option_type) {
            super("Can't create option container: " + option_type + " should be " + expected_option_type);
        }
}
