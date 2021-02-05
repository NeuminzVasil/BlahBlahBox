import Server.AuthService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientMainWindowController {

    private final String IP_ADDRESS = "localhost";
    private final int PORT = 8189;
    @FXML
    TextArea textArea;
    @FXML
    TextFlow textFlow;
    @FXML
    TextField textField;
    @FXML
    HBox upperpanel;
    @FXML
    VBox buttompanel;
    @FXML
    TextField logingField;
    @FXML
    PasswordField passwordField;
    @FXML
    MenuItem menuMainRegistrionNewUser;
    @FXML
    MenuItem menuMainReNickName;
    @FXML
    TreeView treeView;
    String nickName;
    private boolean isAuthorised;
    private Socket clientSocket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    /**
     * Метод подключения к серверу
     */
    public void connect() {
        try {
            AuthService.connect();
            clientSocket = new Socket(IP_ADDRESS, PORT);
            dataInputStream = new DataInputStream(clientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            Thread.currentThread().setName("Client" + clientSocket.getLocalPort());
                            System.out.println("Conrtoller.connect(): Вошел в первый цикл нити (имя нити)" + Thread.currentThread().getName());
                            String message = dataInputStream.readUTF();
                            System.out.println("Conrtoller.connect(): message: " + message);
                            if (message.startsWith("/authok")) {
                                setAuthorised(true);
                                System.out.println("Conrtoller.connect(). я подключился(" + clientSocket + ").\n\tgetLocalPort; " + clientSocket.getLocalPort());
                                System.out.println("Conrtoller.connect(). setAuthorised(true) - ok (message:)" + message);
                                break;
                            } else {
                                System.out.println("Conrtoller.connect(). setAuthorised(false) - false" + message);
                                textArea.appendText(message + "\n");
                            }
                        }

                        while (true) {
                            System.out.println("Conrtoller.connect(): Вошел во второй цикл нити (имя нити)" + Thread.currentThread().getName());
                            String message = dataInputStream.readUTF();
                            textArea.appendText(message + "\n");
                            System.out.println(message + "\n");
//                            textFlow.getChildren().add(new Text("\n")); FIXME - зараза такая! почему выводишь ты текст в бесполезный textArea, а в красивчый TextFlow не хочешь! разрисованный ты пляж :(
                            if (message.equals("/serverClosed")) break;
                        }
                    } catch (IOException e) {
                        System.out.println("Conrtoller.connect().Ошибка чтения dataInputStream.readUTF");
                        //e.getMessage();
                    } finally {
                        try {
                            clientSocket.close();
                        } catch (IOException e) {
                            e.getMessage();
                        }
                        setAuthorised(false);
                    }
                    System.out.println("Conrtoller.connect(). я завершил работу");
                }
            }).start();
        } catch (IOException e) {
            System.out.println("Conrtoller.connect().Ошибка инициализации клиента");
            //System.out.println(e.getMessage());
        }
    }

    /**
     * Метод обработки отправки сообщения
     */
    public void sendMSG() {
        try {
            dataOutputStream.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            System.out.println("Conrtoller.sendMSG(). Ошибка отправки сообщения");
            e.getMessage();
        }
    }

    public void tryToAuthorisation() {

        if (logingField.getText().trim().isEmpty()) {
            System.out.println("пустой логин недопустим");
            return;
        }

        if (passwordField.getText().trim().isEmpty()) {
            System.out.println("пустой пароль недопустим");
            return;
        }


        if (clientSocket == null || clientSocket.isClosed()) {
            connect();
        }
        try {
            String string = "/auth " + logingField.getText().trim() + " " + passwordField.getText().trim();
            dataOutputStream.writeUTF(string);

            System.out.println("Conrtoller.tryToAuthorisation() " + string);

            logingField.clear();
            passwordField.clear();
        } catch (IOException e) {
            System.err.println("Conrtoller.tryToAuthorisation() ошибка авторизации.");
            e.getMessage();

        }
    }

    public void setAuthorised(boolean isAuthorised) {
        this.isAuthorised = isAuthorised;
        System.out.println("Conrtoller.setAuthorised() this.isAuthorised: " + this.isAuthorised);

        if (!isAuthorised) {
            upperpanel.setVisible(true);
            upperpanel.setManaged(true);
            buttompanel.setVisible(false);
            buttompanel.setManaged(false);
        } else {
            upperpanel.setVisible(false);
            upperpanel.setManaged(false);
            buttompanel.setVisible(true);
            buttompanel.setManaged(true);
        }
    }


    public void dispose() {
        System.out.println("Conrtoller.dispose(). отправляем сообщение о закрытиии");
        try {
            if (dataOutputStream != null) {
                dataOutputStream.writeUTF("/stop");
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    /**
     * Метод вызова окна регистрации нового пользователя
     */
    public void registrationClientWindowOpening() {
        System.out.println("открыть окно регистрации");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/ClientRegistrationWindow.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            //e.getMessage();
            System.out.printf("не удалось загрузить окно регистрации");
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Метод вызова окна замены ника пользователя
     */
    public void reNickNameWindowOpening() {
        System.out.println("открыть окно изменения ника пользователя");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/ClientReNickNameWindow.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.getMessage();
            System.out.printf("не удалось загрузить окно изменения ника пользователя");
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
