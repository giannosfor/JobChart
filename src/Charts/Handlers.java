
package Charts;

import Extras.OpenFile;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.entity.ChartEntity;

public class Handlers implements ChartMouseListener{
//
//    private boolean flag;
//
//    public Handlers(boolean flag) {
//        this.flag = flag;
//    }

    public void chartMouseClicked(ChartMouseEvent cme) {
                ChartEntity chartentity = cme.getEntity();
        String tooltext = chartentity.getToolTipText();
        if ( tooltext != null) {
           String []params = OpenFile.getSplitTitles(tooltext);
           if (params[0].equals("Posts")) {
                ChartBar chartBar = new ChartBar(params[1]);
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
