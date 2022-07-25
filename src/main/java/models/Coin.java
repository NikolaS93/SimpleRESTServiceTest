package models;

import java.util.Map;

public class Coin {

    private int sequenceNumber;
    private String value;
    //private Map<String, String> signatures;
    private Object signatures;

    public Coin() {
    }

    public Coin(int sequenceNumber, String value, Object signatures) {
        this.sequenceNumber = sequenceNumber;
        this.value = value;
        this.signatures = signatures;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

//    public Map<String, String> getSignatures() {
//        return signatures;
//    }
//
//    public void setSignatures(Map<String, String> signatures) {
//        this.signatures = signatures;
//    }

        public Object getSignatures() {
        return signatures;
    }

    public void setSignatures(Object signatures) {
        this.signatures = signatures;
    }

}
