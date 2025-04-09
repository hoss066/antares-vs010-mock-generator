package de.arvato.exchange.antares.dto;

import jakarta.xml.bind.annotation.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "header",
        "serialNumbers",
        "errors"
})
@XmlRootElement(name = "itsXml")
public class SNResponse {
    @XmlAttribute(name = "interface", required = true)
    private String itsXmlInterface = "SerialsResponseMaster";
    @XmlElement(required = true)
    private SNResponse.Header header;
    @XmlElement(required = true)
    private SNResponse.SerialNumbers serialNumbers;

    @XmlElementWrapper(name = "errors")
    @XmlElement(name = "error", required = false)
    private List<Error> errors;

    public String getItsXmlInterface() {
        if (itsXmlInterface == null) {
            return "SerialsResponseMaster";
        }
        return itsXmlInterface;
    }

    public void setItsXmlInterface(String itsXmlInterface) {
        this.itsXmlInterface = itsXmlInterface;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public SerialNumbers getSerialNumbers() {
        return serialNumbers;
    }

    public void setSerialNumbers(SerialNumbers serialNumbers) {
        this.serialNumbers = serialNumbers;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "transactionTimestamp",
            "transactionTimeZone",
            "interfaceRevision",
            "messageId",
            "siteCode"
    })
    public static class Header {
        @XmlElement(required = true)
        @XmlSchemaType(name = "dateTime")
        private XMLGregorianCalendar transactionTimestamp;
        @XmlElement
        private String transactionTimeZone;
        @XmlElement(required = true)
        private String interfaceRevision;
        @XmlElement(required = true)
        private MessageId messageId;
        @XmlElement(required = true)
        private String siteCode;

        public XMLGregorianCalendar getTransactionTimestamp() {
            return transactionTimestamp;
        }

        public void setTransactionTimestamp(XMLGregorianCalendar transactionTimestamp) {
            this.transactionTimestamp = transactionTimestamp;
        }

        public String getTransactionTimeZone() {
            return transactionTimeZone;
        }

        public void setTransactionTimeZone(String transactionTimeZone) {
            this.transactionTimeZone = transactionTimeZone;
        }

        public String getInterfaceRevision() {
            return interfaceRevision;
        }

        public void setInterfaceRevision(String interfaceRevision) {
            this.interfaceRevision = interfaceRevision;
        }

        public MessageId getMessageId() {
            return messageId;
        }

        public void setMessageId(MessageId messageId) {
            this.messageId = messageId;
        }

        public String getSiteCode() {
            return siteCode;
        }

        public void setSiteCode(String siteCode) {
            this.siteCode = siteCode;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class MessageId {
        @XmlValue
        private String value;
        @XmlAttribute(name = "xref")
        private String xref;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getXref() {
            return xref;
        }

        public void setXref(String xref) {
            this.xref = xref;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class SerialNumbers {
        @XmlAttribute(name = "codingRule", required = true)
        private String codingRule;
        @XmlAttribute(name = "ntin", required = true)
        private String ntin;
        @XmlAttribute(name = "levelid")
        private String levelid;
        @XmlAttribute(name = "type")
        private String type;
        @XmlElement(name = "sn", required = true)
        private List<SN> snList;

        public String getCodingRule() {
            return codingRule;
        }

        public void setCodingRule(String codingRule) {
            this.codingRule = codingRule;
        }

        public String getNtin() {
            return ntin;
        }

        public void setNtin(String ntin) {
            this.ntin = ntin;
        }

        public String getLevelid() {
            return levelid;
        }

        public void setLevelid(String levelid) {
            this.levelid = levelid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<SN> getSnList() {
            return snList;
        }

        public void setSnList(List<SN> snList) {
            this.snList = snList;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        public static class SN {
            @XmlValue
            private String content;
            @XmlAttribute(name = "cryptokey")
            private String cryptokey;
            @XmlAttribute(name = "cryptocode")
            private String cryptocode;
            @XmlAttribute(name = "expiry")
            private String cryptoExpiry;

            public String getCryptokey() {
                return cryptokey;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setCryptokey(String cryptokey) {
                this.cryptokey = cryptokey;
            }

            public String getCryptocode() {
                return cryptocode;
            }

            public void setCryptocode(String cryptocode) {
                this.cryptocode = cryptocode;
            }

            public String getCryptoExpiry() {
                return cryptoExpiry;
            }

            public void setCryptoExpiry(String cryptoExpiry) {
                this.cryptoExpiry = cryptoExpiry;
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Error {
        @XmlValue
        private String content;
        @XmlAttribute(name = "code", required = false)
        private String code;

        @XmlAttribute(name = "level", required = false)
        private String level;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }
    }
}