/**********************************************
 Workshop #4-5
 Course:APD545 - S5
 Last Name:Araujo Carneiro
 First Name:Marcos Ian
 ID:153220223
 Section:NCC
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature
 Date:05-11-2025
 **********************************************/
package seneca.college.wk4_5.util;

import javafx.fxml.FXMLLoader;
import seneca.college.wk4_5.InventoryManagementApp;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

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
