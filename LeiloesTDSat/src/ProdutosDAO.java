import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutosDAO {

    public Connection connectDB() {
        return new conectaDAO().connectDB();
    }

    public List<String> listarProdutos() throws SQLException {
        List<String> produtos = new ArrayList<>();
        String sql = "SELECT id, nome, preco, status FROM produtos";

        Connection conn = connectDB();
        PreparedStatement pstm = conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();

        while (rs.next()) {
            String produto = "ID: " + rs.getInt("id") +
                             ", Nome: " + rs.getString("nome") +
                             ", Preço: " + rs.getDouble("preco") +
                             ", Status: " + rs.getString("status");
            produtos.add(produto);
        }

        rs.close();
        pstm.close();
        conn.close();

        return produtos;
    }

    public boolean inserirProduto(String nome, double preco, String status) throws SQLException {
        String sql = "INSERT INTO produtos (nome, preco, status) VALUES (?, ?, ?)";

        Connection conn = connectDB();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, nome);
        pstm.setDouble(2, preco);
        pstm.setString(3, status);

        int rowsAffected = pstm.executeUpdate();

        pstm.close();
        conn.close();

        return rowsAffected > 0;
    }

    public boolean deletarProduto(int id) throws SQLException {
        String sql = "DELETE FROM produtos WHERE id = ?";

        Connection conn = connectDB();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);

        int rowsAffected = pstm.executeUpdate();

        pstm.close();
        conn.close();

        return rowsAffected > 0;
    }

    public boolean venderProduto(int id) throws SQLException {
        String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ? AND status != 'Vendido'";

        Connection conn = connectDB();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);

        int rowsAffected = pstm.executeUpdate();

        pstm.close();
        conn.close();

        return rowsAffected > 0;
    }
}
