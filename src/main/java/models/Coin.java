package models;


public class Coin {

    private int sequenceNumber;
    private String value;

    private Signature[] signatures;

    public Coin() {
    }

    public Coin(int sequenceNumber, String value,  Signature[] signatures) {
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

        public Signature[] getSignatures() {
        return signatures;
    }

}
