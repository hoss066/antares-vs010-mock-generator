package de.arvato.exchange.antares.dto;

import jakarta.xml.bind.annotation.*;

import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "header",
        "requests"
})
@XmlRootElement(name = "itsXml")
public class SNRequest {
    @XmlAttribute(name = "interface", required = true)
    private String snReqInterface = "SerialsRequestMaster";
    @XmlElement(required = true)
    private Header header;
    @XmlElement(required = true)
    private Requests requests;

    public String getSnReqInterface() {
        if (snReqInterface == null) {
            return "SerialsResponseMaster";
        }
        return snReqInterface;
    }

    public void setSnReqInterface(String snReqInterface) {
        this.snReqInterface = snReqInterface;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Requests getRequests() {
        return requests;
    }

    public void setRequests(Requests requests) {
        this.requests = requests;
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
        private String messageId;
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

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
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
    @XmlType(name = "", propOrder = {
            "serialRequest"
    })
    public static class Requests {
        @XmlElement(required = true)
        private SerialRequest serialRequest;

        public SerialRequest getSerialRequest() {
            return serialRequest;
        }

        public void setSerialRequest(SerialRequest serialRequest) {
            this.serialRequest = serialRequest;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "requestType",
                "codingRule",
                "ntin",
                "levelId",
                "prefix",
                "quantity",
                "action"
        })
        public static class SerialRequest {
            @XmlElement(required = true, defaultValue = "serials")
            private String requestType;
            @XmlElement(required = true)
            private String codingRule;
            @XmlElement(required = true)
            private String ntin;
            private String levelId;
            private String prefix;
            @XmlElement(required = true)
            private long quantity;
            private String action;

            public String getRequestType() {
                return requestType;
            }

            public void setRequestType(String requestType) {
                this.requestType = requestType;
            }

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

            public String getLevelId() {
                return levelId;
            }

            public void setLevelId(String levelId) {
                this.levelId = levelId;
            }

            public String getPrefix() {
                return prefix;
            }

            public void setPrefix(String prefix) {
                this.prefix = prefix;
            }

            public long getQuantity() {
                return quantity;
            }

            public void setQuantity(long quantity) {
                this.quantity = quantity;
            }

            public String getAction() {
                return action;
            }

            public void setAction(String action) {
                this.action = action;
            }
        }
    }
}
