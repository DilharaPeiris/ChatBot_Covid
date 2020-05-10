import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SPARQLService {

    public Model sprqlBuild(File path){
        Model model = ModelFactory.createDefaultModel();
        try {
            model.read(new FileInputStream(path), null, "RDF/XML");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return model;
    }


    public ResultSet executeSparql(Model model, String query ){
        QueryExecution queryExecution = QueryExecutionFactory.create(query,model);
        ResultSet resultSet = queryExecution.execSelect();
        return resultSet;
    }

    public  StringBuilder getCoronaSymptoms(Model model){
        String query = "PREFIX covid: <http://www.semanticweb.org/dilhara/ontologies/2020/3/untitled-ontology-12#>" +
                "SELECT ?name" +
                " WHERE " +
                "{" +
                "?Symptom a covid:Symptom." +
                "?Symptom covid:name ?name." +
                "}";
        ResultSet resultSet =executeSparql(model,query);
        StringBuilder  symptoms=new StringBuilder();
        while(resultSet.hasNext()){
            symptoms.append(resultSet.next().getLiteral(resultSet.getResultVars().get(0)).toString()+"\n");
        }
        //System.out.println(symptoms);
        return symptoms;
    }

    public StringBuilder getCoronaDeatils(Model model){
        StringBuilder CoronaDetails = new StringBuilder("");
        CoronaDetails.append("Coronavirus disease (COVID-19) is an infectious disease caused by a newly discovered coronavirus. Coronavirus Death Toll" +
                "234,393 deaths as on 01st May 2020." +
                "\n" +
                "Most people infected with the COVID-19 virus will experience mild to moderate respiratory illness and recover without requiring special treatment.  Older people, and those with underlying medical problems like cardiovascular disease, diabetes, chronic respiratory disease, and cancer are more likely to develop serious illness." +
                "\n" +
                "The best way to prevent and slow down transmission is be well informed about the COVID-19 virus, the disease it causes and how it spreads. Protect yourself and others from infection by washing your hands or using an alcohol based rub frequently and not touching your face. " +
                "\n" +
                "The COVID-19 virus spreads primarily through droplets of saliva or discharge from the nose when an infected person coughs or sneezes, so it’s important that you also practice respiratory etiquette (for example, by coughing into a flexed elbow)." +
                "\n" +
                "At this time, there are no specific vaccines or treatments for COVID-19."+
                "\n" + "Reference : https://www.who.int/ "
        );
        return  CoronaDetails;
    }

    public  StringBuilder getCoronaPrecautions(Model model){
        String query = "PREFIX covid: <http://www.semanticweb.org/dilhara/ontologies/2020/3/untitled-ontology-12#>" +
                "SELECT ?name" +
                " WHERE " +
                "{" +
                "?Precaution a covid:Precaution." +
                "?Precaution covid:name ?name." +
                "}";
        ResultSet resultSet =executeSparql(model,query);
        StringBuilder  symptoms=new StringBuilder();
        while(resultSet.hasNext()){
            symptoms.append(resultSet.next().getLiteral(resultSet.getResultVars().get(0)).toString()+"\n");
        }
        return symptoms;
    }

    public  StringBuilder getRegionsWithLevelOfDanger(Model model){
        String query = "PREFIX covid: <http://www.semanticweb.org/dilhara/ontologies/2020/3/untitled-ontology-12#>" +
                "SELECT ?name ?danger" +
                " WHERE " +
                "{" +
                "?Region a covid:Region." +
                "?Region covid:name ?name." +
                "?Region covid:infectionPossibilities ?danger" +
                "}";
        ResultSet resultSet =executeSparql(model,query);
        ResultSet resultSet2 =executeSparql(model,query);
        StringBuilder  countries=new StringBuilder();
        while(resultSet.hasNext()){
           countries.append(resultSet.next().getLiteral(resultSet.getResultVars().get(0)) + " -> ");
           countries.append(resultSet2.next().getLiteral(resultSet2.getResultVars().get(1)).toString()+"\n");
        }
        return countries;
    }

    public  StringBuilder getDangerFreeCountries(Model model){
        String query = "PREFIX covid: <http://www.semanticweb.org/dilhara/ontologies/2020/3/untitled-ontology-12#>" +
                "SELECT ?name ?deaths ?infectionPossibilities ?precautionName" +
                " WHERE " +
                "{" +
                "?DangerFreeCountry a covid:DangerFreeCountry.\n" +
                "\t?DangerFreeCountry covid:name ?name.\n" +
                "\t?DangerFreeCountry covid:noOfDeaths ?deaths.\n" +
                "\t?DangerFreeCountry covid:infectionPossibilities ?infectionPossibilities.\n" +
                "\t?DangerFreeCountry covid:takes ?precaution.\n" +
                "\t?precaution covid:name ?precautionName."+
                "}";
        ResultSet resultSetname =executeSparql(model,query);
        ResultSet resultSetdeaths =executeSparql(model,query);
        ResultSet resultSetinfectionPossibilities =executeSparql(model,query);
        ResultSet resultSetprecautionName =executeSparql(model,query);
        StringBuilder  countryResults=new StringBuilder("Country -> ");
        while(resultSetname.hasNext()){
            countryResults.append(resultSetname.next().getLiteral(resultSetname.getResultVars().get(0)).toString()+" ; reported No of Deaths-");
            countryResults.append(resultSetdeaths.next().getLiteral(resultSetdeaths.getResultVars().get(1)).getInt()+" ; infection Posibility for citizens - " );
            countryResults.append(resultSetinfectionPossibilities.next().getLiteral(resultSetinfectionPossibilities.getResultVars().get(2)).toString()+" ; taken precautions as a Country -  ");
            countryResults.append(resultSetprecautionName.next().getLiteral(resultSetprecautionName.getResultVars().get(3)).toString()+"\n");
        }
        return countryResults;
    }

    public  StringBuilder getDangerousCountries(Model model){
        String query = "PREFIX covid: <http://www.semanticweb.org/dilhara/ontologies/2020/3/untitled-ontology-12#>\n" +
                "SELECT ?name ?deaths ?casualties ?infectionPossibilities ?precautionName \n" +
                "WHERE \n" +
                "{\n" +
                "\t?DangerousCountry a covid:DangerousCountry.\n" +
                "\t?DangerousCountry covid:name ?name.\n" +
                "\t?DangerousCountry covid:noOfDeaths ?deaths.\n" +
                "\t?DangerousCountry covid:noOfCasualties ?casualties.\n" +
                "\t?DangerousCountry covid:infectionPossibilities ?infectionPossibilities.\n" +
                "\t?DangerousCountry covid:takes ?precaution.\n" +
                "\t?precaution covid:name ?precautionName.\n" +
                "}";
        ResultSet resultSetname =executeSparql(model,query);
        ResultSet resultSetdeaths =executeSparql(model,query);
        ResultSet resultSetcasualties =executeSparql(model,query);
        ResultSet resultSetinfectionPossibilities =executeSparql(model,query);
        ResultSet resultSetprecautionName =executeSparql(model,query);
        StringBuilder  countryResults=new StringBuilder("Country -> ");
        while(resultSetname.hasNext()){
            countryResults.append(resultSetname.next().getLiteral(resultSetname.getResultVars().get(0)).toString()+" ; reported No of Deaths-");
            countryResults.append(resultSetdeaths.next().getLiteral(resultSetdeaths.getResultVars().get(1)).getInt()+" ; reported No of casualties - " );
            countryResults.append(resultSetcasualties.next().getLiteral(resultSetcasualties.getResultVars().get(2)).getInt()+" ; infection Posibility for citizens - " );
            countryResults.append(resultSetinfectionPossibilities.next().getLiteral(resultSetinfectionPossibilities.getResultVars().get(3)).toString()+" ; taken precautions as a Country -  ");
            countryResults.append(resultSetprecautionName.next().getLiteral(resultSetprecautionName.getResultVars().get(4)).toString()+"\n");
        }
        return countryResults;
    }

    public  StringBuilder getDeathRateOfSriLanka(Model model){
        String query = "PREFIX covid: <http://www.semanticweb.org/dilhara/ontologies/2020/3/untitled-ontology-12#>" +
                "SELECT ?deaths ?casualties" +
                " WHERE " +
                "{" +
                "?country covid:name 'SriLanka'." +
                "?country covid:noOfDeaths ?deaths." +
                "?country covid:noOfCasualties ?casualties." +
                "}";
        ResultSet resultSet =executeSparql(model,query);
        ResultSet resultSet1 =executeSparql(model,query);

        StringBuilder  country=new StringBuilder("Reported No of Deaths - ");
        while(resultSet.hasNext()){
           country.append(resultSet.next().getLiteral(resultSet.getResultVars().get(0)).getInt()+" ; Reported No of casualties - " );
           country.append(resultSet1.next().getLiteral(resultSet1.getResultVars().get(1)).getInt() );
        }
        return country;
    }

    public  StringBuilder getDeathRateOfUSA(Model model){
        String query = "PREFIX covid: <http://www.semanticweb.org/dilhara/ontologies/2020/3/untitled-ontology-12#>" +
                "SELECT ?deaths ?casualties" +
                " WHERE " +
                "{" +
                "?country covid:name 'USA'." +
                "?country covid:noOfDeaths ?deaths." +
                "?country covid:noOfCasualties ?casualties." +
                "}";

        ResultSet resultSet =executeSparql(model,query);
        ResultSet resultSet1 =executeSparql(model,query);

        StringBuilder  country=new StringBuilder("Reported No of Deaths - ");
        while(resultSet.hasNext()){
            country.append(resultSet.next().getLiteral(resultSet.getResultVars().get(0)).getInt()+" ; Reported No of casualties - " );
            country.append(resultSet1.next().getLiteral(resultSet1.getResultVars().get(1)).getInt() );
        }
        return country;
    }

    public  StringBuilder getDeathRateOfItaly(Model model){
        String query = "PREFIX covid: <http://www.semanticweb.org/dilhara/ontologies/2020/3/untitled-ontology-12#>" +
                "SELECT ?deaths ?casualties" +
                " WHERE " +
                "{" +
                "?country covid:name 'Italy'." +
                "?country covid:noOfDeaths ?deaths." +
                "?country covid:noOfCasualties ?casualties." +
                "}";
        ResultSet resultSet =executeSparql(model,query);
        ResultSet resultSet1 =executeSparql(model,query);

        StringBuilder  country=new StringBuilder("Reported No of Deaths - ");
        while(resultSet.hasNext()){
            country.append(resultSet.next().getLiteral(resultSet.getResultVars().get(0)).getInt()+" ; Reported No of casualties - " );
            country.append(resultSet1.next().getLiteral(resultSet1.getResultVars().get(1)).getInt() );
        }
        return country;
    }

    public StringBuilder defaultResponse(){
        StringBuilder response = new StringBuilder();
        response.append("I have no answer for your question."+ "\n" +
                "*********  Stay Strong. Follow the Precautions. Let's beat Covid-19  *********");
        return response;
    }

}
