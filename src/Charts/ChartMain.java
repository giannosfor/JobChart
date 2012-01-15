
package Charts;

import Extras.OpenFile;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


public class ChartMain extends ApplicationFrame {
    
    public ChartMain() {

        super(null);
        setLayout(new GridLayout(2, 1));
        add(new ChartData().getChartPanel());
        String []array =  { "Synopsys", "Posts/Replys", "Quantity" };

        try {

            add(new BarChart( OpenFile.getContent("database/query1.sql") , array ).getChartPanel() );
            setVisible(true);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(new Dimension(900, 750));
            RefineryUtilities.centerFrameOnScreen(this);

       } catch (FileNotFoundException fe) {
             fe.printStackTrace();
             System.exit(0);
       } catch (IOException ie) {
             ie.printStackTrace();
             System.exit(0);
       }
}

     public static void main(String[] args)  {

        new ChartMain();
     }
}
