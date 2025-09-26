package br.com.alura.AluraFake.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonReader {
    public static String read(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/test/resources/" + path)));
    }
}