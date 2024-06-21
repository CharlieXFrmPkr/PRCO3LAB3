package com.example.frilab2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.*;

public class HelloController implements Initializable {
    public TableView<Suser> userTable;
    public TableColumn<Suser, Integer> id;
    public TableColumn<Suser, String> name;
    public TableColumn<Suser, String> email;
    public TableColumn<Suser, String> password;
    public TextField uid;
    public TextField uname;
    public TextField uemail;
    public TextField upassword;
    @FXML
    private Label welcomeText;

    ObservableList<Suser> list = FXCollections.observableArrayList();

    @FXML
    protected void onHelloButtonClick() {
        fetchData();
    }

    private void fetchData() {
        list.clear();

        String jdbcUrl = "jdbc:mysql://localhost:3306/Gfit";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM mac";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int sid = resultSet.getInt("Sid");
                String sname = resultSet.getString("Sname");
                String semail = resultSet.getString("Semail");
                String spassword = resultSet.getString("Spassword");
                list.add(new Suser(sid, sname, semail, spassword));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<Suser, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Suser, String>("name"));
        email.setCellValueFactory(new PropertyValueFactory<Suser, String>("email"));
        password.setCellValueFactory(new PropertyValueFactory<Suser, String>("password"));
        userTable.setItems(list);
    }

    public void InsertData(ActionEvent actionEvent) {
        String sname = uname.getText();
        String semail = uemail.getText();
        String spassword = upassword.getText();

        String jdbcUrl = "jdbc:mysql://localhost:3306/Gfit";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "INSERT INTO mac (Sname, Semail, Spassword) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, sname);
            statement.setString(2, semail);
            statement.setString(3, spassword);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UpdateData(ActionEvent actionEvent) {
        int sid = Integer.parseInt(uid.getText());
        String sname = uname.getText();
        String semail = uemail.getText();
        String spassword = upassword.getText();

        String jdbcUrl = "jdbc:mysql://localhost:3306/Gfit";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "UPDATE mac SET Sname = ?, Semail = ?, Spassword = ? WHERE Sid = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, sname);
            statement.setString(2, semail);
            statement.setString(3, spassword);
            statement.setInt(4, sid);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DeleteData(ActionEvent actionEvent) {
        int sid = Integer.parseInt(uid.getText());

        String jdbcUrl = "jdbc:mysql://localhost:3306/Gfit";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "DELETE FROM mac WHERE Sid = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, sid);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void LoadData(ActionEvent actionEvent) {
        int sid = Integer.parseInt(uid.getText());

        String jdbcUrl = "jdbc:mysql://localhost:3306/Gfit";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM mac WHERE Sid = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, sid);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String sname = resultSet.getString("Sname");
                String semail = resultSet.getString("Semail");
                String spassword = resultSet.getString("Spassword");

                uname.setText(sname);
                uemail.setText(semail);
                upassword.setText(spassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
