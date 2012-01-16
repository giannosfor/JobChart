package Charts;

import Extras.OpenFile;
import java.sql.SQLException;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.jdbc.JDBCCategoryDataset;
import org.jfree.ui.RefineryUtilities;

public class ChartPie extends JFrame  implements ChartMouseListener {
    private ChartPanel chartpanel;
    private JDBCCategoryDataset dataset;

    public ChartPie(String query)  {

            super("");
            String fquery = "SELECT occuDscr Description , IFNULL(Quantity,0) Quantity" +
                    " FROM occupation_field LEFT JOIN (SELECT occuId Id,COUNT(pstOccuId) " +
                    "Quantity FROM company,job_post INNER JOIN occupation_field ON pstOccuId = " +
                    "occuId WHERE cmpId = pstCmpId AND cmpName = '" + query + "' GROUP " +
                    "BY occuDscr) second ON occuId = Id";
  
           String []array = {query + " Posts","Occupation Field",null};

       try {
               
            dataset = new JDBCCategoryDataset("jdbc:mysql://localhost:3306/jobfinder",
            "com.mysql.jdbc.Driver", "giannis", "giannis");
            dataset.executeQuery(fquery);
            
            JFreeChart chart = ChartFactory.createBarChart(
                    array[0] , array[1], array[2] , dataset,
                    PlotOrientation.VERTICAL, true, true, false); 

//           
//           ChartPanel chartpanel = barchart.getChartPanel();
//           barchart.geRenderer().setSeriesPaint(0 , Color.BLUE );
//           
           chartpanel = new ChartPanel(chart, false);
           chartpanel.addChartMouseListener( this);  
           
           
           add(chartpanel); 
           setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
           setSize(600, 400);
           
           RefineryUtilities.centerFrameOnScreen(this);
           setVisible(true);

           
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }     
           
    }
    
        public void chartMouseClicked(ChartMouseEvent cme) {
        ChartEntity chartentity = cme.getEntity();
        String tooltext = chartentity.getToolTipText();
        if ( tooltext != null) {
           String []params = OpenFile.getSplitTitles(tooltext);
           if (params[0].equals("Posts")) {     
               new ChartBar(params[1]);
           } else {
               for (String param : params) {
                    System.out.println(param.toString());
               }
           }
        }     
    }

    public void chartMouseMoved(ChartMouseEvent cme) {

    }
}