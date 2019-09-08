package mercy.digital.transfer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
 class JsonToClass {

    private static ObjectMapper objectMapper = new ObjectMapper();

    <T> Object toClass(String content, Class aClass) {
        Object o1 = null;
        try {
            o1 = (T) objectMapper.readValue(content, aClass);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return o1;
    }
}