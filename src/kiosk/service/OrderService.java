package kiosk.service;

import java.util.List;
import kiosk.model.Order;
import kiosk.model.Product;
import kiosk.repository.MenuRepository;
import kiosk.view.KioskView;

public class OrderService {
    private MenuRepository menuRepository;
    private KioskView kioskView;

    public OrderService(MenuRepository menuRepository, KioskView kioskView) {
        this.menuRepository = menuRepository;
        this.kioskView = kioskView;
    }

    public int orderMenu(List<Order> orders) {
        kioskView.confirmOrder();

        System.out.println("[ Orders ]");
        kioskView.printOrder(orders);
        System.out.println();

        System.out.println("[ Total ]");
        double totalPrice = menuRepository.getTotalPrice();
        kioskView.printTotalPriceAndOrder(totalPrice);

        return kioskView.printInput();
    }

    public void orderCart(Product product) {
        menuRepository.setOrder(new Order(product));
        System.out.println();
    }

    public void orderSuccess(List<Order> orders) {
        orders.clear();
        kioskView.printOrderSuccess();
    }

    public void orderCancel(List<Order> orders) {
        kioskView.confirmCancel();
        int selectedCancel = kioskView.printInput();
        if (selectedCancel == 1) {
            orders.clear();
            kioskView.cancelSuccess();
        }
    }
}
