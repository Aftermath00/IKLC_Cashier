import java.sql.PreparedStatement;
import java.util.Random;

public class Transaction {

    private float total_paid;
    private float changes;
    private int transaction_id;
    private float total_price;

    public int generateRandomTransactionID() {
        Random rand = new Random();
        return rand.nextInt(900000) + 100000;
    }

    public void insertTransactionData() {
        try {
            String sql = "INSERT INTO transaction (transaction_id, total_price, total_paid ,changes) VALUES (?,?,?,?);";
            PreparedStatement statement = DBConnector.connection.prepareStatement(sql);
            statement.setInt(1, getTransaction_id());
            statement.setFloat(2, getTotal_price());
            statement.setFloat(3, getTotal_paid());
            statement.setFloat(4, getChanges());

            int rowInserted = statement.executeUpdate();
            if (rowInserted > 0) {
                System.out.println();
            }

            statement.close();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public float getTotal_paid() {
        return total_paid;
    }

    public void setTotal_paid(float total_paid) {
        this.total_paid = total_paid;
    }

    public float getChanges() {
        return changes;
    }

    public void setChanges(float changes) {
        this.changes = changes;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }
}