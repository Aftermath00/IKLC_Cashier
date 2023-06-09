import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Token extends Items{
    private String tokenCode;
    private float nominal;
    private String electricity_id;
    private int transaction_id;
    private int item_id;

    public void insertToken(Token token) {
        try  {
            String sql = "INSERT INTO token (item_id, transaction_id, token_code, electricity_id, price, nominal) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = DBConnector.connection.prepareStatement(sql);

            statement.setInt(1, token.getItem_id());
            statement.setInt(2, token.getTransaction_id());
            statement.setString(3, token.getTokenCode());
            statement.setString(4, token.getElectricity_id());
            statement.setFloat(5, token.getPrice());
            statement.setFloat(6, token.getNominal());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String getTokenCode() {
        return tokenCode;
    }

    public void setTokenCode(String tokenCode) {
        this.tokenCode = tokenCode;
    }

    public float getNominal() {
        return nominal;
    }

    public void setNominal(float nominal) {
        this.nominal = nominal;
    }

    public String getElectricity_id() {
        return electricity_id;
    }

    public void setElectricity_id(String electricity_id) {
        this.electricity_id = electricity_id;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }
}
