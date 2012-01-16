
package Extras;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class OpenFile {

        public static String getContent(String filename) throws FileNotFoundException, IOException
        {
                BufferedReader buffer = new BufferedReader(new FileReader(filename));
                StringBuilder strbuilder = new StringBuilder();
                String text = "";
                while( (text = buffer.readLine()) != null ) {
                    strbuilder.append(text + "\n");
                }
                return strbuilder.toString();
        }

        public static String getQueryMap(String query)
        {
             String[] params = query.split(":");
             return params[0].trim();
        }

        public static String[] getSplitTitles(String query)
        {
            
             int comma = query.lastIndexOf(",");
             int pare = query.lastIndexOf(")");

             String []params = new String[2];
             params[0] = query.substring(1, comma );
             params[1] = query.substring(comma + 2 , pare );

             return params;
        }
}
