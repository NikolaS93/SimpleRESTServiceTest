package models;

public class Signature {

    private String signature;
    private String publicKey;

    public Signature(String signature, String publicKey){
        this.signature = signature;
        this.publicKey = publicKey;

    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
