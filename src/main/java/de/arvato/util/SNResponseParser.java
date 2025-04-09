package de.arvato.util;

import de.arvato.exchange.antares.dto.SNResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.StringReader;

public class SNResponseParser {
    public static SNResponse parseAndValidateSNResponse(String snResponseXml) throws JAXBException, InboundErrorException {
        JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] { SNResponse.class });
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        SNResponse snResponse = (SNResponse)unmarshaller.unmarshal(new StringReader(snResponseXml));
        return snResponse;
    }
}
