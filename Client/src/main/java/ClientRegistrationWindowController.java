import Server.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ClientRegistrationWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField signUpFirstName;

    @FXML
    private TextField signUpLastName;

    @FXML
    private TextField signUpLogin;

    @FXML
    private TextField signUpMiddleName;

    @FXML
    private PasswordField signUpPassword;

    @FXML
    private PasswordField signUpPasswordConfirmation;


    /**
     * метод регистрации нового пользователя в базе данных.
     */
    public void registrationClientIntoDataBase() {
        System.out.println("ClientRegistrationWindowController.registrationClientIntoDataBase() - зарегистрировать пользователя");

        // проверка заполненности всех полей в окне регистрации нового пользователя
        if (signUpLogin.getText().trim().isEmpty() ||
                signUpPassword.getText().trim().isEmpty() ||
                signUpPasswordConfirmation.getText().trim().isEmpty() ||
                signUpFirstName.getText().trim().isEmpty() ||
                signUpLastName.getText().trim().isEmpty()) {
            System.err.println("ClientRegistrationWindowController.registrationClientIntoDataBase() - Обязательные поля не заполнены.");
            return;
        }

        // проверка совпадения новых паролей в окне регистрации нового пользователя
        if (!signUpPassword.getText().trim().equals(signUpPasswordConfirmation.getText().trim())) {
            System.err.println("ClientRegistrationWindowController.registrationClientIntoDataBase() - Пароли не свопадают.");
            return;
        }

        String insertNewUser = "INSERT INTO main (login, password, nickname, FirstName, LastName) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = AuthService.connect().prepareStatement(insertNewUser);
            preparedStatement.setString(1, signUpLogin.getText().trim());
            preparedStatement.setString(2, signUpPassword.getText().trim());
            preparedStatement.setString(3, signUpFirstName.getText().trim());
            preparedStatement.setString(4, signUpLastName.getText().trim());
            preparedStatement.setString(5, signUpMiddleName.getText().trim());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.err.println("ClientRegistrationWindowController.registrationClientIntoDataBase() - невозможно выполнить запрос на создание пользователя");
            e.getMessage();
        }

        AuthService.disconnect();

        // возвращяемся в главное окно
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        stage.close();

    }


}