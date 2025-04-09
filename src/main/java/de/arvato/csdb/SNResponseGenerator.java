package de.arvato.csdb;

import de.arvato.exchange.antares.dto.SNRequest;
import de.arvato.exchange.antares.dto.SNResponse;
import de.arvato.util.DateUtil;
import de.arvato.util.SNRequestParser;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SNResponseGenerator {
    public static void main(String[] args) throws IOException {
        processSNRequestAndGenerateSNResponse();
    }

    private static List<String> getListOfFiles(String dir) {
        return (List<String>)Stream.<File>of((new File(dir)).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .filter(fileName -> (fileName.endsWith(".xml") || fileName.endsWith(".XML") || fileName.endsWith(".txt")))
                .collect(Collectors.toList());
    }

    private static void processSNRequestAndGenerateSNResponse() {
        List<String> fileList = getListOfFiles(".");
        fileList.forEach(fileName -> {
            try {
                String snRequestStr = new String(Files.readAllBytes(Paths.get(fileName, new String[0])), StandardCharsets.UTF_8);
                SNRequest snRequest = SNRequestParser.parseSNRequest(snRequestStr);
                SNResponse snResponse = new SNResponse();
                SNResponse.Header snResponseHeader = new SNResponse.Header();
                snResponseHeader.setTransactionTimestamp(snRequest.getHeader().getTransactionTimestamp());
                snResponseHeader.setTransactionTimeZone(snRequest.getHeader().getTransactionTimeZone());
                snResponseHeader.setInterfaceRevision(snRequest.getHeader().getInterfaceRevision());

                SNResponse.MessageId snResponseMessageId = new SNResponse.MessageId();
                snResponseMessageId.setValue(snRequest.getHeader().getMessageId());
                snResponseMessageId.setXref(snRequest.getHeader().getMessageId());
                snResponseHeader.setMessageId(snResponseMessageId);

                snResponseHeader.setSiteCode(snRequest.getHeader().getSiteCode());
                snResponse.setHeader(snResponseHeader);
                SNResponse.SerialNumbers snResponseSerialNumbers = new SNResponse.SerialNumbers();
                snResponseSerialNumbers.setCodingRule(snRequest.getRequests().getSerialRequest().getCodingRule());
                snResponseSerialNumbers.setNtin(snRequest.getRequests().getSerialRequest().getNtin());
                long quantity = snRequest.getRequests().getSerialRequest().getQuantity();
                String ntin = snResponseSerialNumbers.getNtin();
                if (snResponseSerialNumbers.getCodingRule().equals("GS1_SGTIN_RU")) {
                    snResponseSerialNumbers.setSnList(getSNWithCryptoCode(quantity));
                } else {
                    snResponseSerialNumbers.setSnList(getSNWithoutCryptoCode(quantity));
                }
                snResponse.setSerialNumbers(snResponseSerialNumbers);
                StringWriter snResponseXmlHolder = new StringWriter();
                getSNRequestMarshaller().marshal(snResponse, snResponseXmlHolder);
                String folder = "VS010_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH.mm.ss"));
                if (!Files.exists(Paths.get(folder, new String[0]), new java.nio.file.LinkOption[0]))
                    Files.createDirectory(Paths.get(folder, new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
                String vs10FileName = fileName;
                try {
                    if (vs10FileName.contains("VS009"))
                        vs10FileName = vs10FileName.replaceAll("VS009", "VS010");
                    if (vs10FileName.contains("009"))
                        vs10FileName = vs10FileName.replaceAll("009", "010");
                    if (vs10FileName.contains("request"))
                        vs10FileName = vs10FileName.replaceAll("request", "Response");
                    if (vs10FileName.endsWith(".txt"))
                        vs10FileName = vs10FileName.replace(".txt", ".xml");
                } catch (Exception exception) {}
                Files.write(Paths.get(folder, new String[] { vs10FileName }), snResponseXmlHolder.toString().getBytes(), new java.nio.file.OpenOption[0]);
            } catch (Exception e) {
                System.out.println(fileName + " failed to process :" + fileName + "\n" + e.getMessage());
            }
        });
    }

    private static List<String> getUniqueSerialsUsingMap(long quantity) {
        Map<String, String> uniqueSerials = new HashMap<>();
        Random r = new Random(Instant.now().toEpochMilli());
        long toGenerate = quantity;
        while (toGenerate != 0L) {
            long count = toGenerate;
            for (; count > 0L; count--) {
                String serial = String.format("%014d", new Object[] { Integer.valueOf(getLong(r)) });
                uniqueSerials.put(serial, serial);
            }
            toGenerate = quantity - uniqueSerials.size();
        }
        return (List<String>)uniqueSerials.keySet().stream().collect(Collectors.toList());
    }

    private static List<String> getUniqueCryptoKeys(long quantity) {
        Map<String, String> keys = new HashMap<>();
        Random r = new Random(Instant.now().toEpochMilli());
        long toGenerate = quantity;
        while (toGenerate != 0L) {
            long count = toGenerate;
            for (; count > 0L; count--) {
                String[] keyAndCode = getRandomCryptoKeyAndCode(r);
                keys.put(keyAndCode[0], keyAndCode[0]);
            }
            toGenerate = quantity - keys.size();
        }
        return (List<String>)keys.keySet().stream().collect(Collectors.toList());
    }

    private static List<String> getUniqueExpiry(long quantity) {
        List<String> expiryDateList = new ArrayList<>();
        Random r = new Random(Instant.now().toEpochMilli());
        long toGenerate = quantity;
        GregorianCalendar gc = new GregorianCalendar();
        while (toGenerate != 0L) {
            if (expiryDateList.add(DateUtil.getRandomDateString(gc, r)))
                toGenerate--;
        }
        return expiryDateList;
    }

    private static List<String> getUniqueCryptoCodes(long quantity) {
        Map<String, String> codes = new HashMap<>();
        Random r = new Random(Instant.now().toEpochMilli());
        long toGenerate = quantity;
        while (toGenerate != 0L) {
            long count = toGenerate;
            for (; count > 0L; count--) {
                String[] keyAndCode = getRandomCryptoKeyAndCode(r);
                codes.put(keyAndCode[1], keyAndCode[1] + "==");
            }
            toGenerate = quantity - codes.size();
        }
        return (List<String>)codes.keySet().stream().collect(Collectors.toList());
    }

    private static List<SNResponse.SerialNumbers.SN> getSNWithoutCryptoCode(long quantity) {
        Random r = new Random(Instant.now().toEpochMilli());
        List<SNResponse.SerialNumbers.SN> snList = new ArrayList<>();
        List<String> serialList = getUniqueSerialsUsingMap(quantity);
        serialList.forEach(serial -> {
            SNResponse.SerialNumbers.SN sn = new SNResponse.SerialNumbers.SN();
            sn.setContent(serial);
            snList.add(sn);
        });
        return snList;
    }

    private static List<SNResponse.SerialNumbers.SN> getSNWithCryptoCode(long quantity) {
        Random r = new Random(Instant.now().toEpochMilli());
        List<SNResponse.SerialNumbers.SN> snList = new ArrayList<>();
        List<String> serialList = getUniqueSerialsUsingMap(quantity);
        Iterator<String> cryptoKeyList = getUniqueCryptoKeys(quantity).iterator();
        Iterator<String> cryptoCodeList = getUniqueCryptoCodes(quantity).iterator();
        Iterator<String> expiryList = getUniqueExpiry(quantity).iterator();
        serialList.forEach(serial -> {
            SNResponse.SerialNumbers.SN sn = new SNResponse.SerialNumbers.SN();
            sn.setContent(serial);
            sn.setCryptokey(cryptoKeyList.next());
            sn.setCryptocode(cryptoCodeList.next());
            sn.setCryptoExpiry(expiryList.next());
            snList.add(sn);
        });
        return snList;
    }

    private static String[] getRandomCryptoKeyAndCode(Random r) {
        String[] codeAndKey = new String[2];
        StringBuilder strBuilder = new StringBuilder();
        int capital = 25;
        int small = 25;
        int digit = 9;
        int i;
        for (i = 0; i < 4; i++)
            randomGenerator(r, strBuilder, capital, small, digit);
        codeAndKey[0] = strBuilder.toString();
        strBuilder = new StringBuilder();
        for (i = 0; i < 42; i++)
            randomGenerator(r, strBuilder, capital, small, digit);
        codeAndKey[1] = strBuilder.toString();
        return codeAndKey;
    }

    private static void randomGenerator(Random r, StringBuilder strBuilder, int capital, int small, int digit) {
        if (getInt(r) % 2 == 0) {
            if (r.nextInt() % 2 == 0) {
                strBuilder.append((char)(65 + getInt(r, capital)));
            } else {
                strBuilder.append((char)(48 + getInt(r, digit)));
            }
        } else if (r.nextInt() % 2 == 0) {
            strBuilder.append((char)(97 + getInt(r, small)));
        } else {
            strBuilder.append((char)(48 + getInt(r, digit)));
        }
    }

    private static Marshaller getSNRequestMarshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] { SNResponse.class });
        Marshaller marshallerObj = jaxbContext.createMarshaller();
        marshallerObj.setProperty("jaxb.fragment", Boolean.valueOf(true));
        marshallerObj.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
        marshallerObj.setProperty("jaxb.encoding", "UTF-8");
        return marshallerObj;
    }

    private static int getLong(Random r) {
        int l = r.nextInt();
        if (l < 0)
            l *= -1;
        return l;
    }

    private static int getInt(Random r) {
        int rInt = r.nextInt();
        if (rInt < 0)
            rInt *= -1;
        return rInt;
    }

    private static int getInt(Random r, int bound) {
        int rInt = r.nextInt(bound);
        if (rInt < 0)
            rInt *= -1;
        return rInt;
    }
}
