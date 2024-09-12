package model;

import java.io.Serializable;
import java.util.Date;

public class Mutter implements Serializable {

	private int id;          // ID (データベースに対応したフィールドの追加)
	private String userName; // ユーザー名
	private String text;     // つぶやき内容
	private Date postDate;   // 投稿日時（Dateクラス）

	public Mutter(){}

	public Mutter(String userName, String text){
		this.userName = userName;
		this.text = text;
	}

	public Mutter(int id, String userName, String text, Date postDate){  // ID (データベースに対応したコンストラクタ)
		this.id = id;
		this.userName = userName;
		this.text = text;
		this.postDate = postDate;

	}

	public int getId() {  // (データベースに対応したフィールドの追加)
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public String getText() {
		return text;
	}

	public Date getPostDate() {
		return postDate;
	}


}
