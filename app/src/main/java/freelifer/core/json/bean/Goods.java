package freelifer.core.json.bean;

import freelifer.core.json.annotations.LIMITJSON;
import freelifer.core.json.fliptables.FlipTable;

/**
 * @author kzhu on 2017/12/22.
 */
@LIMITJSON()
public class Goods {
    public int id;
    public String name;
    public String desc;
    public double price;
    public float rate;
    public String date;
    public String date1;

    /**
     * <pre>
     * ╔═════════════╗
     * ║ Goods       │
     * ╠═════════════╪═════════════════════════╤══════════════╗
     * ║ Name        │ Value                   │ Author       ║
     * ╠═════════════╪═════════════════════════╪══════════════╣
     * ║ Flip Tables │ Pretty-print a text ta  │ Jake Wharton ║
     * ╚═════════════╧═════════════════════════╧══════════════╝
     * </pre>
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Goods==>");
        builder.append("\n\tid:" + id);
        builder.append("\n\tname:" + name);
        builder.append("\n\tdesc:" + desc);
        builder.append("\n\tprice:" + price);
        builder.append("\n\trate:" + rate);
        builder.append("\n\tdate:" + date);
        builder.append("\n\tdate1:" + date1);
        builder.append("\n");
//        String[] headers = {"Name", "Value"};
//        String[][] data = {
//                {"id", Integer.toString(id)},
//                {"name", name},
//                {"desc", desc},
//                {"price", Double.toString(price)},
//                {"rate", Float.toString(rate)},
//                {"date", date}
//        };
//        return FlipTable.of(headers, data);
        return builder.toString();
    }
}
