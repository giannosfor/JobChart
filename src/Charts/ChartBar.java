package Charts;

import Database.Database;
import Extras.OpenFile;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import org.jfree.chart.*;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RefineryUtilities;

public class ChartBar extends JFrame implements ChartMouseListener {

    DefaultPieDataset dataset;

    public ChartBar(String query)  {

            super("");
            
           /*  String fquery = "SELECT cmpName,COUNT(occuDscr) AS 'Posts' FROM " +
                    "(SELECT pstOccuId,cmpName FROM job_post LEFT JOIN " +
                    "company ON pstCmpId = cmpId) a LEFT JOIN occupation_field" +
                    " b ON a.pstOccuId = b.occuId WHERE occuDscr = '" + query + "' GROUP BY cmpName";
            
            try {
                    Connection con = Database.getConnection();            
                    dataset = new JDBCPieDataset(con);
                    dataset.executeQuery(fquery);
             
             */
 
            
              String fquery = "SELECT cmpName,COUNT(occuDscr) AS 'Posts' FROM " +
                    "(SELECT pstOccuId,cmpName FROM job_post LEFT JOIN " +
                    "company ON pstCmpId = cmpId) a LEFT JOIN occupation_field" +
                    " b ON a.pstOccuId = b.occuId WHERE occuDscr = ? GROUP BY cmpName";
        try {
             
            Connection con = Database.getConnection();

            PreparedStatement preparedStatement =
            (PreparedStatement) con.prepareStatement(fquery);

            preparedStatement.setString(1, query);

            ResultSet resultset = preparedStatement.executeQuery();
            dataset = new DefaultPieDataset();

            while( resultset.next() ) {
                dataset.setValue( resultset.getString(WIDTH) ,resultset.getDouble("Posts"));
            }    
            
            
      //---------------- ---------------------- //      
            
            
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
           pack();

           RefineryUtilities.centerFrameOnScreen(this);
           setVisible(true);

           con.close();

           } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
        }  catch (SQLException sqle) {
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



