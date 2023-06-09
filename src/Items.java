import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Items {
    private String code;
    private String productName;
    private float price;

    public static ArrayList<Items> itemList;

    public static void loadItemFromDB() {
        itemList = new ArrayList<>();
        Items item;

        try {
            Statement stmt = DBConnector.connection.createStatement();

            String sql = "SELECT * from item";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                item = new Items();
                item.setCode(Integer.toString(rs.getInt("item_id")));
                item.setProductName(rs.getString("product_name"));
                item.setPrice(rs.getFloat("price"));

                itemList.add(item);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}