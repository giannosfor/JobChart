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

    public ChartData()  {

        try { 
            
         Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(
                   "jdbc:mysql://localhost:3306/jobfinder",
                   "giannis",
                   "giannis"
                   );
            
            con.createStatement();       
            
            JDBCPieDataset data = null;  
            
            data = new JDBCPieDataset(con);
            
            String sql = "SELECT cmpName Company,COUNT(pstId) AS 'Posts' " +
                    "FROM company,job_post WHERE cmpId = pstCmpId " +
                    "GROUP BY Company";
            data.executeQuery(sql);
            
            JFreeChart chart = ChartFactory.createPieChart(
            "Companies", data, true, true, true);
           
            chartpanel = new ChartPanel(chart,false);
            chartpanel.addChartMouseListener(this);

            con.close();  
            
        }catch(ClassNotFoundException cnfe){
            cnfe.printStackTrace();
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