import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler {
    String nick;
    List<String> blackList;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private BlahBlahBoxServer server;

    /**
     * Конструктор, создающий нить подключения для нового пользователя
     */
    public ClientHandler(Socket socket, BlahBlahBoxServer server) {

        try {
            this.blackList = new ArrayList<>();
            this.socket = socket;
            this.server = server;
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("ClientHandler.ClientHandler(): hashCode клиента: " + this + "порт : " + socket.getPort());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String message = dataInputStream.readUTF();
                            System.out.println("ClientHandler.ClientHandler() вошел в цикл нити. (message):" + message);
                            if (message.startsWith("/auth")) {
                                String[] tokens = message.split(" ");
                                String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                                if (newNick != null) {
                                    if (!server.isNickBusy(newNick)) {
                                        sendMsg("/authok");
                                        nick = newNick;
                                        server.subscribe(ClientHandler.this);
                                        break;
                                    } else sendMsg("Учетная запись занята");
                                } else sendMsg("Неверный логин или пароль!");
                            }
                            server.broadcastMsg(ClientHandler.this, message);
                            System.out.println("ClientHandler.ClientHandler() broadcastMsg-ok. (message):" + message);
                        }

                        while (true) {
                            // получаем следующую строку
                            System.out.println("ClientHandler.ClientHandler() вошел в цикл сообщений");
                            String message = dataInputStream.readUTF();
                            System.out.println("ClientHandler.ClientHandler() dataInputStream. (message)" + message);
                            if (message.startsWith("/")) {
                                if (message.equals("/stop")) {
                                    dataOutputStream.writeUTF("/serverclosed");
                                    break;
                                }
                                if (message.startsWith("/w ")) {
                                    String[] tokens = message.split(" ", 3);
                                    server.sendPersonalMsg(ClientHandler.this, tokens[1], tokens[2]);
                                }
                                if (message.equals("/blacklist ")) {
                                    String[] tokens = message.split(" ");
                                    blackList.add(tokens[1]);
                                    sendMsg("Вы добавили пользователя" + tokens[1] + "в черный список");
                                }
                            } else {
                                server.broadcastMsg(ClientHandler.this, nick + ": " + message);
                            }
                        }
                    } catch (SocketException e) {
                        System.out.println("ClientHandler.ClientHandler() Клиент отключился. \n\t" + socket.getPort());
                        //UserListCorrecting(socket, server);
                        // e.getMessage();
                    } catch (IOException e) {
                        System.out.println("ClientHandler.ClientHandler() Ошибка вещания IOException e");
                        e.getMessage();
                    } finally {
                        try {
                            dataInputStream.close();
                        } catch (IOException e) {
                            System.out.println("ClientHandler.ClientHandler() Ошибка закрытия dataInputStream.close();");
                            e.getMessage();
                        }
                        try {
                            dataOutputStream.close();
                        } catch (IOException e) {
                            System.out.println("ClientHandler.ClientHandler() Ошибка закрытия dataOutputStream.close();");
                            e.getMessage();
                        }
                    }
                    server.unsubscribe(ClientHandler.this);
                }
            }).start();
        } catch (IOException e) {
            System.out.println("ClientHandler.ClientHandler() Ошибка создания dataInputStream или dataOutputStream");
            e.getMessage();
        }

    }

    public String getNick() {
        return nick;
    }

    public boolean checkBlackList(String nick) {
        return blackList.contains(nick);
    }

    /**
     * метод рассылки сообщений со стороны сервера всем клиентам
     *
     * @param message - текст сообщения.
     */
    void sendMsg(String message) {
        try {
            dataOutputStream.writeUTF(message);
        } catch (IOException e) {
            System.out.println("ClientHandler.sendMsg(). Ошибка отправки сообщения на стороне сервера");
            e.getMessage();
        }
    }
}
