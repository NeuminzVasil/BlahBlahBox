import Server.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClientReNickNameWindowController {

    @FXML
    ResourceBundle resources;

    @FXML
    URL location;

    @FXML
    TextField reNickNameConfirmField;

    @FXML
    TextField reNickNameNewField;

    @FXML
    TextField reNickNameCurrentField;

    @FXML
    Button reNickNameButtonCancel;

    @FXML
    Button reNickNameButtonOk;


    /**
     * метод изменения ника пользователя пользователя в базе данных.
     */
    public void ReNickName() {
        System.out.println("ClientReNickNameWindowController.ReNickName() - renick пользователя");
        // получить текущий ник пользователя

        reNickNameCurrentField.setText("test");

        // проверка заполненности всех полей в окне изменения ника
        if (reNickNameNewField.getText().trim().isEmpty() ||
                reNickNameConfirmField.getText().trim().isEmpty()) {
            System.err.println("ClientReNickNameWindowController.ReNickName() - Обязательные поля не заполнены.");
            return;
        }

        // проверка совпадения значения полей "Новй ник" и "подтверждение"
        if (!reNickNameNewField.getText().trim().equals(reNickNameConfirmField.getText().trim())) {
            System.err.println("ClientReNickNameWindowController.ReNickName() - Ники не свопадают.");
            return;
        }

        String reNickName = "UPDATE main SET nickname = ? WHERE nickname = ?";

        try {
            PreparedStatement preparedStatement = AuthService.connect().prepareStatement(reNickName);
            preparedStatement.setString(1, reNickNameNewField.getText().trim());
            preparedStatement.setString(2, reNickNameCurrentField.getText().trim());
            preparedStatement.execute();
            System.out.println("Обновление ника прошло успешно");
        } catch (SQLException e) {
            System.err.println("ClientReNickNameWindowController.ReNickName() - невозможно выполнить запрос обновления ника пользователя");
            e.getMessage();
        }

        AuthService.disconnect();

        // возвращяемся в главное окно
        reNickNameButtonCancelAction();

    }

    public void reNickNameButtonCancelAction() {
        // возвращяемся в главное окно
        Stage stage = (Stage) reNickNameButtonCancel.getScene().getWindow();
        stage.close();
    }

}