package kiosk.view;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import kiosk.model.Menu;
import kiosk.model.Order;
import kiosk.model.Product;

public class KioskView {
    private AtomicInteger waitingNumber = new AtomicInteger(1);
    public void printWelcome() {
        System.out.println("\"SHAKESHACK BURGER 에 오신걸 환영합니다.\"");
        System.out.println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.");
        System.out.println();
    }
    public int printMenu(List<Menu> menus, int currentIndex) {
        for (Menu menu: menus) {
            System.out.println(currentIndex++ + ". " + menu.getName() + "  | " + menu.getDescription());
        }
        System.out.println();

        return currentIndex;
    }

    public int printInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("선택하기 : ");
        int selectedMenu = scanner.nextInt();
        System.out.println();
        return selectedMenu;
    }

    public void printProduct(List<Product> products) {
        AtomicInteger selectedIndex = new AtomicInteger(1);
        products.stream().forEach(product -> {
            int currentIndex = selectedIndex.getAndIncrement();
            System.out.println(currentIndex + ". " + product.getName() + "  | " + " | W" + product.getPrice() + " | " + product.getDescription());
        });
    }

    public void printSelectedProduct(Product product) {
        System.out.println("\"" + product.getName() + "  | W" + product.getPrice() + " | " + product.getDescription() + "\"");
    }

    public void confirmAddToCart() {
        System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인        2. 취소");
    }

    public void addToCartSuccess(Product product) {
        System.out.printf("%s 가 장바구니에 추가되었습니다.\n", product.getName());
        System.out.println();
    }

    public void addToCartCancel() {
        System.out.println("장바구니에 추가가 취소되었습니다.");
        System.out.println();
    }

    public void notInCart() {
        System.out.println("장바구니에 담긴 상품이 없습니다.");
        System.out.println();
    }

    public void confirmOrder() {
        System.out.println("아래와 같이 주문 하시겠습니까?");
        System.out.println();
    }

    public void printOrder(List<Order> orders) {
        orders.stream().forEach(order -> {
            System.out.println(order.getName() + "  | " + " | W" + order.getPrice() + " | " + order.getDescription());
        });
    }

    public void printTotalPriceAndOrder(double totalPrice) {
        System.out.printf("W %.1f\n", totalPrice);
        System.out.println();
        System.out.println("1. 주문      2. 메뉴판");
    }

    public void printOrderSuccess() {
        System.out.println("주문이 완료되었습니다!");
        System.out.println();

        int currentWaitingNumber = waitingNumber.getAndIncrement();
        System.out.println("대기번호는 [ " + currentWaitingNumber + " ] 번 입니다.");
        System.out.println("(3초후 메뉴판으로 돌아갑니다.)");
    }

    public void confirmCancel() {
        System.out.println("진행하던 주문을 취소하시겠습니까?");
        System.out.println("1. 확인        2. 취소");
    }

    public void cancelSuccess() {
        System.out.println("진행하던 주문이 취소되었습니다.");
        System.out.println();
    }

    public void invalidSelected() {
        System.out.println("유효하지 않은 선택입니다.");
        System.out.println();
    }
}
