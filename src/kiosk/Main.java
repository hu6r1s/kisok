package kiosk;

import kiosk.repository.MenuRepository;
import kiosk.service.MenuService;
import kiosk.service.OrderService;
import kiosk.view.KioskView;

public class Main {
    public static void main(String[] args) {
        MenuRepository menuRepository = new MenuRepository();
        KioskView kioskView = new KioskView();

        OrderService orderService = new OrderService(menuRepository, kioskView);

        MenuService kiosk = new MenuService(menuRepository, kioskView, orderService);

        kiosk.MainMenu();
        System.out.println();
    }
}
