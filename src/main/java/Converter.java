import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Converter {

    private static String INPUT_FILE_NAME = "src/main/resources/sample.xml";
    private static String OUTPUT_FILE_NAME = "src/main/resources/output.yml";

    public static void main (String[] args) throws JSONException, IOException {
        File xmlFile = new File(INPUT_FILE_NAME);
        String xmlStr = new String(
                Files.readAllBytes(xmlFile.toPath()), StandardCharsets.UTF_8);

        JSONObject xmlJSONObj = XML.toJSONObject(xmlStr);
        xmlJSONObj.getJSONObject("fsa:ResponseFsaType")
                .getJSONObject("fsa:RdcTr")
                .getJSONObject("tns:Product")
                .remove("tns:TechRegs");

        // Writing the output YAML / Сохраняем YAML файл
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        // Optional removing the dashes at start / Опционально убираем тире в начале файла
        // mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        mapper.writeValue(new File(OUTPUT_FILE_NAME), asYaml(xmlJSONObj.toString()));
    }

    private static String asYaml(String jsonString) throws IOException {
        JsonNode jsonNodeTree = new ObjectMapper().readTree(jsonString);
        String jsonAsYaml = new YAMLMapper().writeValueAsString(jsonNodeTree);
        return jsonAsYaml;
    }
}

