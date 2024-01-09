package kiosk.service;

import java.util.List;
import kiosk.Main;
import kiosk.model.Menu;
import kiosk.model.Order;
import kiosk.model.Product;
import kiosk.repository.MenuRepository;
import kiosk.utils.MenuNumber;
import kiosk.view.KioskView;

public class MenuService {
    private MenuRepository menuRepository;
    private KioskView kioskView;
    private OrderService orderService;

    public MenuService(MenuRepository menuRepository, KioskView kioskView, OrderService orderService) {
        this.menuRepository = menuRepository;
        this.kioskView = kioskView;
        this.orderService = orderService;
    }

    public void MainMenu() {
        kioskView.printWelcome();
        List<Menu> menus = menuRepository.getMenus("Main");

        System.out.println("[ SHAKESHACK MENU ]");
        int currentIndex = kioskView.printMenu(menus, 1);

        System.out.println("[ ORDER MENU ]");
        List<Menu> orders = menuRepository.getMenus("Order");
        kioskView.printMenu(orders, currentIndex);

        MenuNumber menuNumber = MenuNumber.valueOf(kioskView.printInput());
        List<Order> orderList = menuRepository.getOrderList();
        switch (menuNumber) {
            case SELECT_BURGER:
                ProductMenu("Burgers");
                break;
            case SELECT_CUSTARD:
                ProductMenu("Frozen Custard");
                break;
            case SELECT_DRINK:
                ProductMenu("Drinks");
                break;
            case SELECT_BEER:
                ProductMenu("Beer");
                break;
            case SELECT_ORDER:
                int selectedOrder = 0;
                if (orderList != null && !orderList.isEmpty()) {
                    selectedOrder = orderService.orderMenu(orderList);
                } else {
                    kioskView.notInCart();
                    MainMenu();
                }
                if (selectedOrder == 1) {
                    orderService.orderSuccess(orderList);

                    new Thread(() -> {
                        try {
                            Thread.sleep(3000);
                            MainMenu();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
                } else if (selectedOrder == 2) {
                    MainMenu();
                } else {
                    kioskView.invalidSelected();
                }
                break;
            case SELECT_CANCEL:
                orderService.orderCancel(orderList);
                MainMenu();
                break;
            case SELECT_HIDDEN:
                orderService.printOrderHistory();
                int returnMenu = kioskView.printInput();
                if (returnMenu == 1) {
                    MainMenu();
                }
                break;
            default:
                kioskView.invalidSelected();
                break;
        }
    }

    private void ProductMenu(String selectedMenu) {
        kioskView.printWelcome();

        System.out.printf("[ %s MENU ]\n", selectedMenu);
        List<Product> productList = menuRepository.getProducts(selectedMenu);
        kioskView.printProduct(productList);
        System.out.println();

        int selectedProduct = kioskView.printInput();
        if (selectedProduct >= 1 && selectedProduct <= productList.size()) {
            Product selectedProductInfo = productList.get(selectedProduct - 1);
            kioskView.printSelectedProduct(selectedProductInfo);

            kioskView.confirmAddToCart();
            int selectedCart = kioskView.printInput();

            if (selectedCart == 1) {
                kioskView.addToCartSuccess(selectedProductInfo);
                orderService.orderCart(selectedProductInfo);
                MainMenu();
            } else if (selectedCart == 2) {
                kioskView.addToCartCancel();
            }
        } else {
            kioskView.invalidSelected();
        }
    }
}
