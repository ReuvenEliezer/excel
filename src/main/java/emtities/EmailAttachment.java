package emtities;

import javax.activation.DataHandler;

public class EmailAttachment {

    private DataHandler dataHandler;
    private String fileName;

    public EmailAttachment(DataHandler dataHandler, String fileName) {
        this.dataHandler = dataHandler;
        this.fileName = fileName;
    }

    public DataHandler getDataHandler() {
        return dataHandler;
    }

    public void setDataHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "EmailAttachment{" +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
