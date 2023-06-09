import java.sql.PreparedStatement;
import java.util.ArrayList;

public class TransactionDetail {
    private int transactionID;

    public ArrayList<Integer> ItemIdList = new ArrayList();
    public ArrayList<Integer> ItemTotalList = new ArrayList();
    public ArrayList<Float> ItemPriceList = new ArrayList();


    public void insertTransactionDetail() {
        try {

            String sql = "INSERT INTO transaction_detail (transaction_id,item_id,quantity,price) VALUES (?,?,?,?);";
            PreparedStatement statement = DBConnector.connection.prepareStatement(sql);

            for (int i = 0; i < ItemIdList.size(); i++) {
                statement.setInt(1, transactionID);
                statement.setFloat(2, ItemIdList.get(i));
                statement.setFloat(3, ItemTotalList.get(i));
                statement.setFloat(4, ItemPriceList.get(i));

                int rowInserted = statement.executeUpdate();

                if (rowInserted > 0) {
                    System.out.println();
                }
            }

            statement.close();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public int getDetailTransactionID() {
        return transactionID;
    }

    public void setDetailTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }
}