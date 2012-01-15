package Charts;

import javax.swing.JFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.ui.RefineryUtilities;

public class ChartPie extends JFrame {

    public ChartPie(String query)  {
            super("");
            String fquery = "SELECT occuDscr Description , IFNULL(Quantity,0) Quantity" +
                    " FROM occupation_field LEFT JOIN (SELECT occuId Id,COUNT(pstOccuId) " +
                    "Quantity FROM company,job_post INNER JOIN occupation_field ON pstOccuId = " +
                    "occuId WHERE cmpId = pstCmpId AND cmpName = '" + query + "' GROUP " +
                    "BY occuDscr) second ON occuId = Id";
  
           String []array = {query + " Posts","Occupation Field","Quantity"};
           
           ChartPanel chartpanel = new BarChart(fquery , array ).getChartPanel();
           add(chartpanel);

           setVisible(true);
           setSize(600, 400);
           setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

           RefineryUtilities.centerFrameOnScreen(this);

    }
}