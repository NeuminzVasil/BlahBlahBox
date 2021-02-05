/*public class MainBlahBlahBoxClient extends Application {

    ClientMainWindowController clientMainWindowController;

    *//**
     * существуют только для запуска Launch.
     * @param args - пробрасываемые параметы из запуска приложения
     *//*
    public static void main(String[] args) {
        launch(args);
    }

    *//**
     * Lunch из метода main вызывает метод start - такова жизнь.
     * поэтому необходимо переопределить метод start в котором описать внешний вид приложения.
     *
     * @param primaryStage - переменная основной сцены.
     *//*
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream("/FXML/ClientMainWindow.fxml"));
        clientMainWindowController = loader.getController();

        primaryStage.setTitle("BlahBlahBox - javaFX Application");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/CSS/Styles.css");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            System.out.println(event.getEventType());
            clientMainWindowController.dispose();
            Platform.exit();
            System.exit(0);
        });
    }


}*/
