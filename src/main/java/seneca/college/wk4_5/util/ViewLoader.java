package seneca.college.wk4_5.util;

import javafx.fxml.FXMLLoader;
import seneca.college.wk4_5.InventoryManagementApp;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ViewLoader {
    private final com.google.inject.Injector injector;

    @Inject
    public ViewLoader(com.google.inject.Injector injector) {
        this.injector = injector;
    }

    public FXMLLoader load(String resourcePath) throws java.io.IOException {
        FXMLLoader loader = new FXMLLoader(InventoryManagementApp.class.getResource(resourcePath));
        loader.setControllerFactory(injector::getInstance);
        loader.load();
        return loader;
    }
}
