package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Mutter;

public class MuttersDAO {

	// ドライバー名を格納
	// MySQL用
	// private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	// Azure SQL Server用
	private static final String DRIVER_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	// Connection型変数
	private Connection connection;

	// データベースの接続文字列
	// MySQL用
	// private final String url = "jdbc:mysql://localhost:8889/docotsubu"; // データベースのパスを指定
	// private final String user = "morikawa";
	// private final String password = "00830080gG";
	// Azure SQL Server用
	private final String url = "jdbc:sqlserver://reviewsgembb.database.windows.net:1433;database=reviewsgembb;user=morikawasusumu@reviewsgembb;password=00830080gG;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

	public List<Mutter> findAll(int offset, int limit) {

		// returnするリストを用意
		List<Mutter> mutterList = new ArrayList<>();

		try {
            // JDBCドライバを読み込み
			Class.forName(DRIVER_NAME);

            // DriverManagerデータベースに接続
			// MySQL用
			// connection = DriverManager.getConnection(url, user, password);
			// Azure SQL Server用
			connection = DriverManager.getConnection(url);

			// String型変数にクエリ(問い合わせ)を用意
			// MySQL用
			// String sql = "SELECT id, name, text, post_date FROM mutters ORDER BY ID DESC LIMIT ? OFFSET ?";
			// Azure SQL Server用
			String sql = "SELECT * FROM mutters ORDER BY post_date DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

			// SQLをDBに届けるPreparedStatementインスタンスを取得
			PreparedStatement ps = connection.prepareStatement(sql);

			// MySQL用
			// ps.setInt(1, limit);
			// ps.setInt(2, offset);
	        // Azure SQL Server用
			ps.setInt(1, offset);
			ps.setInt(2, limit);

            // SELECTが実行され、ResultSetインスタンスに結果が格納される
			ResultSet rs = ps.executeQuery();

			// 結果（）に格納されたレコードの内容を表示
			// next() 対象レコードを一つ進める
			// 取得結果が存在するかどうかを表すbool値)で条件分岐や繰り返しを行い、ResultSetから情報を引き出す
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String text = rs.getString("text");
				java.util.Date postDate = rs.getTimestamp("post_date"); // getTimestamp()で投稿日時を取得（投稿時に自動で生成されている）

				// 取り出した値をコンストラクタに入れインスタンス生成
				Mutter mutter = new Mutter(id, name, text, postDate);
				// リストに格納
				mutterList.add(mutter);
			}
		  } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	        return mutterList;

	} // findAll


    public int countAll() {
        int total = 0;
        try  {
			Class.forName(DRIVER_NAME);

			// MySQL用
			// connection = DriverManager.getConnection(url, user, password);
			// Azure SQL Server用
			connection = DriverManager.getConnection(url);

            String sql = "SELECT COUNT(*) AS total FROM mutters";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }


	public boolean create(Mutter mutter) {

		try {
			Class.forName(DRIVER_NAME);

			// MySQL用
			// connection = DriverManager.getConnection(url, user, password);
			// Azure SQL Server用
			connection = DriverManager.getConnection(url);

			// プレースホルダによるSQL分の組み立て
			// SQL文の中にクエスチョンマーク（？）の形でパラメータを埋め込んで仮のSQL文を作る
			// 利点・・・SQL文の見易さ・不正な検索を防止
			String sql = "INSERT INTO mutters(NAME , TEXT) VALUES(?,?)";

			PreparedStatement ps = connection.prepareStatement(sql);

			// SQLの文にあった(？)の位置に値を入れる
			// 第一引数・・・ (？)の場所 , 第二引数・・・値
			ps.setString(1, mutter.getUserName());
			ps.setString(2, mutter.getText());

			// SELECT文の際に使用したexecuteQuery()の戻り値は
			// 検索の結果が格納されているResultSetオブジェクト
			// executeUpdate()の戻り値は更新した行数(int)
			// INSERT文，UPDATE文，DELETE文の際に使用
			int result = ps.executeUpdate();

			// １行更新できたら
			if (result == 1) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} // try

		return false;

	}// create

	public void delete(int id) {

		try {
			Class.forName(DRIVER_NAME);

			// MySQL用
			// connection = DriverManager.getConnection(url, user, password);
			// Azure SQL Server用
			connection = DriverManager.getConnection(url);

			String sql = "DELETE FROM mutters WHERE id = ? ";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
