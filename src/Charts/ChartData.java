package Charts;

import Extras.OpenFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.data.jdbc.JDBCPieDataset;

public class ChartData implements ChartMouseListener {
    private Connection con;
    ChartPanel chartpanel;
    JDBCPieDataset data = null;

    public ChartData()  {

        try {

            con = (Connection) DriverManager.getConnection(
                   "jdbc:mysql://localhost:3306/jobfinder",
                   "giannis",
                   "giannis"
                   );
            
            data = new JDBCPieDataset(con);
            
            String sql = "SELECT cmpName Company,COUNT(pstId) AS 'Posts' " +
                    "FROM company,job_post WHERE cmpId = pstCmpId " +
                    "GROUP BY Company";
            data.executeQuery(sql);
            
            JFreeChart chart = ChartFactory.createPieChart(
            "Companies Posts", data, true, true, true);
           
            chartpanel = new ChartPanel(chart,false);
            chartpanel.addChartMouseListener(this);

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public ChartPanel getChartPanel() {
        return chartpanel;
    }

    public void chartMouseClicked(ChartMouseEvent cme) {
        ChartEntity chartentity = cme.getEntity();    
        if (chartentity.getURLText() != null) { 
           String query = OpenFile.getQueryMap( chartentity.getToolTipText() );
           new ChartPie(query);
        }
    }

    public void chartMouseMoved(ChartMouseEvent cme) {
        
    }
    
}