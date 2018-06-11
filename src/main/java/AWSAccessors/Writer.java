package AWSAccessors;

public class Writer {

    public static void main(String[] args) {
        RDSHandler db = new RDSHandler();

        db.closeConnection();
    }
}
