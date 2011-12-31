package extractor;
import fr.inria.acacia.corese.api.*;
import fr.inria.acacia.corese.exceptions.EngineException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Prop;

/**
 *
 * @author Desclaux Christophe <christophe@zouig.org>
 */
public class InseeSparqlRequest {
    
    public static IEngine engine = null;
    
    public static IEngine getEngine(){
        if(engine == null){
            engine = (new EngineFactory()).newInstance();
            String input = "resources/geographie/";
            try {
                engine.load(input);
            } catch (EngineException ex) {
                Logger.getLogger(InseeSparqlRequest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return engine;
    }
    
    public static IResults request(String queryString){
        try {
            IResults res = InseeSparqlRequest.getEngine().SPARQLQuery(queryString);
            return res;
        } catch (EngineException ex) {
            Logger.getLogger(InseeSparqlRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static ArrayList<Prop> getDimensions(String[] cities){
        ArrayList<Prop> result = new ArrayList<Prop>();
        for(int i=0; i < cities.length; i++){
            result.addAll(InseeSparqlRequest.getDimensions(cities[i]));
        }
        return result;
    }
    public static ArrayList<Prop> getDimensions(String city){
         /*String query="PREFIX geo: <http://rdf.insee.fr/geo/>"
                  + "\nSELECT  DISTINCT ?nom ?entite ?type WHERE{"
                  + "\n    ?entite geo:nom ?nom"
                  + "\n    ?entite rdf:type ?type"
                  + "\n    ?ville geo:nom ?nomVille"
                  + "\n    ?entite geo:subdivision* ?ville"
                  + "\n    FILTER(?type != geo:Pays_ou_Territoire && (FALSE";
                  for(int i=0; i < cities.length;i++){
                      query += "|| regex(str(?nomVille), '"+cities[i]+"', 'i') ";
                  }
        query += "))\n}";*/
        
        String query="PREFIX geo: <http://rdf.insee.fr/geo/>"
                  + "\nSELECT  DISTINCT ?nom ?entite ?type WHERE{"
                
                  + "\n    ?entite geo:nom ?nom"
                  + "\n    ?ville geo:nom '"+city+"'@fr"
                  + "\n    ?entite geo:subdivision* ?ville"
                  + "\n    ?entite rdf:type ?type"
                  + "\n    FILTER(?type != geo:Pays_ou_Territoire)\n}";
        
        
        
        IResults res = InseeSparqlRequest.request(query);
        
        ArrayList<Prop> dims = new ArrayList<Prop>();
        for (Enumeration<IResult> en = res.getResults(); en.hasMoreElements();) {
            IResult r = en.nextElement();
            dims.add(new Prop(r.getStringValue("?type"), r.getStringValue("?nom"),true));
            dims.add(new Prop(r.getStringValue("?type"), r.getStringValue("?entite"),false));
        }
        
        return dims;
    }
    
    public static void main(String[] args){
        System.out.println((new Date()).toString());
        IEngine engine = getEngine();
        System.out.println((new Date()).toString());
        System.out.println(getDimensions("Seyne"));
        System.out.println(getDimensions("La Seyne sur mer"));
        System.out.println(getDimensions("Toulon"));
        System.out.println((new Date()).toString());
        System.out.println("Done");
    }
}