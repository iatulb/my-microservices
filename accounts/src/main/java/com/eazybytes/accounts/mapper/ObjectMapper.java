package com.eazybytes.accounts.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ObjectMapper {
    private static final com.fasterxml.jackson.databind.ObjectMapper OBJECT_MAPPER = JsonMapper
            .builder()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
            .serializationInclusion(JsonInclude.Include.NON_NULL)
//			.addModule(module)
            .findAndAddModules()
            .build();

    public static <T, S> T mapUsingJson(Class<T> targetDTOClass, S srcPojo) {
        T targetDTO = null;
        try {
            targetDTO = targetDTOClass.newInstance();

            if (srcPojo != null) {
//				ObjectMapper om = new ObjectMapper();
//				om.registerModule(new Jdk8Module());
//				om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//				om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//				om.disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);

//				String json = OBJECT_MAPPER.writeValueAsString(srcPojo);
//				log.info("input json= [{}]", json);
//
//				targetDTO = OBJECT_MAPPER.readValue(json, targetDTOClass);
//				log.info("input json other way= [{}]", targetDTO);

                targetDTO = OBJECT_MAPPER.convertValue(srcPojo, targetDTOClass);
//				log.info("input json other way= [{}]", targetDTO);

//				logger.info("targetDTO="+targetDTO);
//				BeanUtils.copyProperties(targetDTO, srcPojo);
            } else {
//                log.warn("source object null");
            }
        } catch (Exception e) {
            String errMsg = "Error while Object mapping using JSON..";
//            log.error(errMsg, e);
            throw new RuntimeException(e);
        }
        return targetDTO;
    }

    public static <T, S> List<T> mapUsingJson(Class<T> targetDTOClass, List<S> srcPojo) {
        List<T> result = new ArrayList<>();
        if (Objects.nonNull(srcPojo) && !srcPojo.isEmpty()) {
            for (int i = 0; i < srcPojo.size(); i++)
                result.add(mapUsingJson(targetDTOClass, srcPojo.get(i)));
        } else {
//            log.warn("list source object null");
        }
        return result;
    }
}
