import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Credits extends Items{
    private float nominal;
    private String operator;
    private String phoneNumber;
    private int item_id;
    private int transaction_id;

    public void insertCredits(Credits credit) {
        try  {
            String sql = "INSERT INTO credits (item_id, transaction_id, operator, phone_number, price, nominal) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = DBConnector.connection.prepareStatement(sql);

            statement.setInt(1, credit.getItem_id());
            statement.setInt(2, credit.getTransaction_id());
            statement.setString(3, credit.getOperator());
            statement.setString(4, credit.getPhoneNumber());
            statement.setFloat(5, credit.getPrice());
            statement.setFloat(6, credit.getNominal());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public float getNominal() {
        return nominal;
    }

    public void setNominal(float nominal) {
        this.nominal = nominal;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }
}
