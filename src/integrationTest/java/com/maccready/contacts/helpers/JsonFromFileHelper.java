package com.maccready.contacts.helpers;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class JsonFromFileHelper {
    public static String getJson(String fileName) {
        try {
            URL resource = JsonFromFileHelper.class.getResource("/" + fileName);
            Path path = Paths.get(resource.toURI());
            return Files.lines(path).collect(Collectors.joining("\n"));
        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
