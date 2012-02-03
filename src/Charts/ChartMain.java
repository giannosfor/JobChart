
package Charts;

import Extras.OpenFile;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFrame;
import org.jfree.ui.RefineryUtilities;


public class ChartMain extends JFrame {
    
    public ChartMain() {

        super("");
        setLayout(new GridLayout(2, 1));
        add(new ChartData().getChartPanel());
        String []array =  { "Occupation Fields", null, null };

        try {
            
            add(new BarChart( OpenFile.getContent("database/query1.sql") , array ).getChartPanel() );
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(new Dimension(800, 600));
            RefineryUtilities.centerFrameOnScreen(this);
            setVisible(true);

       } catch (FileNotFoundException fe) {
             fe.printStackTrace();
             System.exit(0);
       } catch (IOException ie) {
             ie.printStackTrace();
             System.exit(0);
       }
    }
}
