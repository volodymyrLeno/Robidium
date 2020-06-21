package com.robidium.demo.log;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing logs
     */
    private String logsLocation = "/home/ubuntu/Robidium/log-dir";


    /**
     * * Folder location for storing spmf input and output
     */
    private String spmfLocation = "/home/ubuntu/Robidium/spmf";

    /**
     * * Folder location for storing info about pattern for the compiler
     */
    private String patternInfoLocation = "/home/ubuntu/Robidium/compiler-input";


    public String getLogsLocation() {
        return logsLocation;
    }

    public void setLogsLocation(String logsLocation) {
        this.logsLocation = logsLocation;
    }

    public String getSpmfLocation() {
        return spmfLocation;
    }

    public void setSpmfLocation(String spmfLocation) {
        this.spmfLocation = spmfLocation;
    }

    public String getPatternInfoLocation() {
        return patternInfoLocation;
    }

    public void setPatternInfoLocation(String patternInfoLocation) {
        this.patternInfoLocation = patternInfoLocation;
    }
}
