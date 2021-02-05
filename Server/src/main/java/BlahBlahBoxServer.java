import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class BlahBlahBoxServer {

    public Vector<ClientHandler> clients;

    public BlahBlahBoxServer() {

        clients = new Vector<>();
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            AuthService.connect();
            //создаем серверную переменную с портом .
            serverSocket = new ServerSocket(8189);
            System.out.println("BlahBlahBoxServer.BlahBlahBoxServer(): Сервер запущен");

            while (true) {
                //создаем на сервере сокет для первичного подключения клиента
                clientSocket = serverSocket.accept();
                System.out.println("BlahBlahBoxServer.BlahBlahBoxServer(): Клиент подключился. Порт: " + clientSocket.getPort() + " isConnected(); " + clientSocket.isConnected());
                new ClientHandler(clientSocket, this);
            }
        } catch (IOException e) {
            System.out.println("BlahBlahBoxServer. BlahBlahBoxServer():. Ошибка запусак сервера или подключения клиента");
            e.getMessage();
        } finally {

            // пытаемся закрыть сокет.
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("BlahBlahBoxServer.BlahBlahBoxServer(): Ошибка закрытия сокета на стороне сервера");
                e.getMessage();
            }

            // пытаемся закрыть серевер.
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.out.println("BlahBlahBoxServer.BlahBlahBoxServer()Ошибка закрытия сервера");
                e.getMessage();
            }
            AuthService.disconnect();
        }
    }

    public boolean isNickBusy(String nick) {
        for (ClientHandler o : clients) {
            if (o.getNick().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    public void subscribe(ClientHandler client) {
        clients.add(client);
        System.out.println("BlahBlahBoxServer.subscribe()(client.getNic): " + client.getNick());
        broadcastClientList();
    }

    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
        System.out.println("BlahBlahBoxServer.UNsubscribe()(client.getNic): " + client.getNick());
        broadcastClientList();
    }

    void broadcastMsg(ClientHandler clientHandler, String message) {
        for (ClientHandler o : clients) {
            if (!o.checkBlackList(clientHandler.getNick())) {
                o.sendMsg(message);
                System.out.println("BlahBlahBoxServer.broadcastMsg()(message): " + message);
            }
        }
    }

    public void sendPersonalMsg(ClientHandler from, String nickTo, String message) {
        for (ClientHandler o : clients) {
            if (o.getNick().equals(nickTo)) {
                o.sendMsg("from " + from.getNick() + ": " + message);
                from.sendMsg("to " + nickTo + ": " + message);
                System.out.println("BlahBlahBoxServer.broadcastMsg()(message): " + message);
                return;
            }
        }
        from.sendMsg("sendPersonalMsg() Клиент с ником " + nickTo + " не найден в чате");
    }

    public void broadcastClientList() {
        StringBuilder sb = new StringBuilder();
        sb.append("/clientlist ");
        for (ClientHandler o : clients) {
            sb.append(o.getNick() + " ");
            System.out.println("BlahBlahBoxServer.broadcastClientList()(o.nick):" + o.nick);
        }

        String message = sb.toString();

        for (ClientHandler o : clients) {
            o.sendMsg(message);
            System.out.println("BlahBlahBoxServer.broadcastClientList()(message):" + message);
        }
    }
}
