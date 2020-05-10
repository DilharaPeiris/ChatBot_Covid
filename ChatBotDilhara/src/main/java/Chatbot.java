import java.io.File;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;
import org.apache.jena.rdf.model.Model;

public class Chatbot {
    private static final boolean TRACE_MODE = false;
    static String botName = "dilhara";

    public static void main(String[] args) {
        try {
            String resourcesPath = getResourcesPath();
            System.out.println(resourcesPath);
            MagicBooleans.trace_mode = TRACE_MODE;
            Bot bot = new Bot(botName, resourcesPath);
            bot.writeAIMLFiles();
            Chat chatSession = new Chat(bot);
            bot.brain.nodeStats();
            String textLine = "";

            while (true) {
                System.out.print("Human: ");
                textLine = IOUtils.readInputTextLine();
                if (textLine == null || textLine.length() < 1)
                    textLine = MagicStrings.null_input;
                if (textLine.equals("q")) {
                    System.exit(0);
                } else if (textLine.equals("wq")) {
                    bot.writeQuit();
                    System.exit(0);
                } else {
                    String request = textLine;
                    if (MagicBooleans.trace_mode)
                        System.out.println("STATE=" + request + ":THAT=" +
                                (History) chatSession.thatHistory.get(0).get(0) +
                                ":TOPIC=" + chatSession.predicates.get("topic"));
                    String response = chatSession.multisentenceRespond(request);
                    response =getResponse(request,response);

                    while (response.contains("&lt"))
                        response = response.replace("&lt;", "<");
                    while (response.contains("&gt"))
                        response = response.replace("&gt;", ">");
                    System.out.println("Robot :" + response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getResponse(String request ,String response){
        SPARQLService sPARQLService =new SPARQLService();
        Model model =sPARQLService.sprqlBuild(new File("D:/MSc/Sem2/DM/covidNewq.rdf"));

        StringBuilder finalResponse = new StringBuilder();
        switch (response){
            case "1":
                finalResponse = sPARQLService.getCoronaDeatils(model);
                break;
            case "2":
                finalResponse =sPARQLService.getCoronaSymptoms(model);
                break;
            case "3":
                finalResponse =sPARQLService.getCoronaPrecautions(model);
                break;
            case "4":
                finalResponse =sPARQLService.getRegionsWithLevelOfDanger(model);
                break;
            case "5":
                finalResponse =sPARQLService.getDangerFreeCountries(model);
                break;
            case "6":
                finalResponse =sPARQLService.getDeathRateOfSriLanka(model);
                break;
            case "7":
                finalResponse =sPARQLService.getDeathRateOfUSA(model);
                break;
            case "8":
                finalResponse =sPARQLService.getDeathRateOfItaly(model);
                break;
            case "9":
                finalResponse =sPARQLService.getDangerousCountries(model);
                break;
            default:finalResponse = sPARQLService.defaultResponse();
                break;
        }
        return finalResponse.toString();
    }
private  static StringBuilder nonSpecifeRquest(String request){
        StringBuilder StringBuilder = new StringBuilder();
     return StringBuilder.append(request);
}

    private static String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        System.out.println(path);
        String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        return resourcesPath;
    }
}
