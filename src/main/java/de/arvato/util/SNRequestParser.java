package de.arvato.util;

import de.arvato.exchange.antares.dto.SNRequest;
import de.arvato.exchange.antares.dto.SNResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SNRequestParser {
    public static SNRequest parseSNRequest(String snRequestStr) throws JAXBException, InboundErrorException {
        JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] { SNRequest.class });
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        SNRequest snRequest = (SNRequest)unmarshaller.unmarshal(new StringReader(snRequestStr));
        return snRequest;
    }

    public static void main(String[] args) throws IOException, InboundErrorException, JAXBException {
        byte[] bytes = Files.readAllBytes(Paths.get("C:\\shazzat\\code\\develop\\Tools\\csdb\\out\\V4\\VS010_2024_02_05_16.21.40\\8712617621289-VS010-1234567823454-20230321T052724Z-612.xml", new String[0]));
        String content = new String(bytes);
        System.out.println(content);
        SNResponse snResponse = SNResponseParser.parseAndValidateSNResponse(content);
        snResponse.getSerialNumbers().getSnList().forEach(sn -> System.out.println(sn.getCryptokey() + "\t" + sn.getCryptokey() + "\t" + sn.getCryptocode() + "\t" + sn.getCryptoExpiry()));
    }
}
