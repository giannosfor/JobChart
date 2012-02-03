package Charts;

import Database.Database;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

public class ChartPie extends JFrame {
    private ChartPanel chartpanel;
    private DefaultCategoryDataset dataset;

    public ChartPie(String query)  {

            super("");
            String fquery = "SELECT occuDscr Description , IFNULL(Quantity,0) Quantity" +
                    " FROM occupation_field LEFT JOIN (SELECT occuId Id,COUNT(pstOccuId) " +
                    "Quantity FROM company,job_post INNER JOIN occupation_field ON pstOccuId = " +
                    "occuId WHERE cmpId = pstCmpId AND cmpName = ? GROUP " +
                    "BY occuDscr) second ON occuId = Id";
  
           String []array = {query + " Posts","Occupation Field",null};

       try {
            Connection con = Database.getConnection();

            PreparedStatement preparedStatement =
            (PreparedStatement) con.prepareStatement(fquery);

            preparedStatement.setString(1, query);

            ResultSet resultset = preparedStatement.executeQuery();
            dataset = new DefaultCategoryDataset();

            while( resultset.next() ) {
                dataset.setValue( resultset.getDouble("Quantity"), query, resultset.getString("Description") );
            }
            
            JFreeChart chart = ChartFactory.createBarChart(
                    array[0], array[1], array[2], dataset,
                    PlotOrientation.VERTICAL, true, true, false); 

           chartpanel = new ChartPanel(chart, false);
           chartpanel.addChartMouseListener(new Handlers());
          
           add(chartpanel); 
           setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
           setSize(600, 400);
           
           RefineryUtilities.centerFrameOnScreen(this);
           setVisible(true);

           con.close(); 
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChartPie.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }     
           
    }
}