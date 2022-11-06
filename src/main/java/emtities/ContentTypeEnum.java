package emtities;

public enum ContentTypeEnum {
    //    https://cloud.google.com/appengine/docs/standard/php/mail/mail-with-headers-attachments
//    https://docs.microsoft.com/en-us/microsoft-365/compliance/supported-filetypes-datainvestigations?view=o365-worldwide
    EXCEL("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    CSV("text/csv");

    private final String value;

    ContentTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
