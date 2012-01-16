package Charts;

import Extras.OpenFile;
import java.awt.Color;
import java.sql.SQLException;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.jdbc.JDBCPieDataset;
import org.jfree.ui.RefineryUtilities;

public class ChartBar extends JFrame implements ChartMouseListener {

    JDBCPieDataset dataset;
    
    public ChartBar(String query)  {

            super("");
            String fquery = "SELECT cmpName,COUNT(occuDscr) AS 'Posts' FROM " +
                    "(SELECT pstOccuId,cmpName FROM job_post LEFT JOIN " +
                    "company ON pstCmpId = cmpId) a LEFT JOIN occupation_field" +
                    " b ON a.pstOccuId = b.occuId WHERE occuDscr = '" + query + "' " +
                    "GROUP BY cmpName";

           String []array = { query + " Posts","Companies","Posts"};
        try {

           dataset = new JDBCPieDataset("jdbc:mysql://localhost:3306/jobfinder",
            "com.mysql.jdbc.Driver", "giannis", "giannis");
           
            dataset.executeQuery(fquery);
            JFreeChart chart = ChartFactory.createPieChart3D(
                    query + " Posts", dataset, true, true, false);


           ChartPanel chartpanel = new ChartPanel(chart, false);
           chartpanel.addChartMouseListener(this);
           
// ---------------------- Coloring            

            PiePlot plot = (PiePlot)chart.getPlot();
            plot.setSectionPaint(0, Color.DARK_GRAY);
            plot.setSectionPaint(1, Color.LIGHT_GRAY);
            plot.setBackgroundPaint(Color.WHITE);
            
// --------------------- end
           
           add(chartpanel);
           setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
           //setSize(600, 400);
           pack();

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
                if (chartentity.getToolTipText() != null) {
                    String query = OpenFile.getQueryMap( chartentity.getToolTipText() );
                    System.out.println(query);
                }
            }

            public void chartMouseMoved(ChartMouseEvent cme) {
            }

       }


    
