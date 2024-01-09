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
        menuRepository.setOrderHistory(orders);
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

    public void printOrderHistory() {
        System.out.println("[ 총 판매상품 목록 현황 ]");
        System.out.println("현재까지 총 판매된 상품 목록은 아래와 같습니다.");
        for (Order history : menuRepository.getOrderHistory()) {

            for (int i = 0; i < history.getCount(); i++) {
                System.out.println("- " + history.getProduct().getName() + "  | W " + history.getProduct().getPrice());
            }
        }
        System.out.println();
        System.out.println("[ 총 판매금액 현황 ]");
        System.out.println("현재까지 총 판매된 금액은 [ W " + menuRepository.getSalePrice() + " ] 입니다.");
        System.out.println("1. 돌아가기");
    }
}
