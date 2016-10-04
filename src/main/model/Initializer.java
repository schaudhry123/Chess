package main.model;

import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by samir on 9/12/16.
 */
public class Initializer {
    /**
     * ####################################
     *         Initialization Methods
     * ####################################
     */
    public static void initializeBoard(Board board) {
        try {
            String filePath = new File("").getAbsolutePath();
            JsonReader jsonReader = new JsonReader(new FileReader(filePath.concat("/src/main/model/BoardSetup.json")));
            readJSONHelper(jsonReader, board);
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     *
     * @param jsonReader
     * @throws IOException
     */
    private static void readJSONHelper(JsonReader jsonReader, Board board) throws IOException {
        jsonReader.beginObject();
        jsonReader.nextName();
        jsonReader.beginArray();

        while (jsonReader.hasNext()) {
            jsonReader.beginObject();

            while (jsonReader.hasNext()) {
                jsonReader.nextName();
                String pieceType = jsonReader.nextString();
                jsonReader.nextName();
                int x = jsonReader.nextInt();
                jsonReader.nextName();
                int y = jsonReader.nextInt();
                jsonReader.nextName();
                String color = jsonReader.nextString();
                board.initializePiece(pieceType, x, y, color);
            }

            jsonReader.endObject();
        }

        jsonReader.endArray();
    }
}
