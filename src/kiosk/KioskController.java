package kiosk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class KioskController {
    private AtomicInteger waitingNumber = new AtomicInteger(1);
    List<Menu> menuList = Arrays.asList(
        new Menu("Burgers", "앵거스 비프 통살을 다져만든 버거"),
        new Menu("Frozen Custard", "매장에서 신선하게 만드는 아이스크림"),
        new Menu("Drinks", "매장에서 직접 만드는 음료"),
        new Menu("Beer", "뉴욕 브루클린 브루어리에서 양조한 맥주")
    );

    List<Menu> selectList = Arrays.asList(
        new Menu("Order", "장바구니를 확인 후 주문합니다."),
        new Menu("Cancel", "진행중인 주문을 취소합니다.")
    );

    List<Product> burgerList = Arrays.asList(
        new Product("ShackBurger", 6.9, "토마토, 양상추, 쉑소스가 토핑된 치즈버거"),
        new Product("SmokeShack", 8.9, "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거"),
        new Product("Shroom Burger", 9.4, "몬스터 치즈와 체다 치즈로 속을 채운 베지테리안 버거"),
        new Product("Cheeseburger", 6.9, "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거"),
        new Product("Hamburger", 5.4, "비프패티를 기반으로 야채가 들어간 기본버거")
    );

    List<Product> custardList = Arrays.asList(
        new Product("Shakes", 5.9, "바닐라, 초콜렛, 솔티드 카라멜, 블랙 & 화이트, 스트로베리, 피넛버터, 커피"),
        new Product("Shake of the Week", 6.5, "특별한 커스터드 플레이버"),
        new Product("Red Bean Shake", 6.5, "신선한 커스터드와 함께 우유와 레드빈이 블렌딩 된 시즈널 쉐이크"),
        new Product("Floats", 5.9, "루트 비어, 퍼플 카우, 크림시클"),
        new Product("Cups & Cones", 4.9, "바닐라, 초콜렛, Flavor of the Week")
    );

    List<Product> drinkList = Arrays.asList(
        new Product("Shack-made Lemonade", 3.9, "매장에서 직접 만드는 상큼한 레몬에이드"),
        new Product("Fresh Brewed Iced Tea", 3.4, "직접 유기농 홍차를 우려낸 아이스티"),
        new Product("Fifty/Fifty", 3.5, "레몬에이드와 아이스티의 만남"),
        new Product("Fountain Soda", 2.7, "코카콜라, 코카콜라 제로, 스트라이트, 환타 오렌지, 환타 그레이프"),
        new Product("Abita Root Beer", 4.4, "청량감 있는 독특한 미국식 무알콜 탄산음료"),
        new Product("Bottled Water", 1.0, "지리산 암반대수층으로 만든 프리미엄 생수")
    );

    List<Product> beerList = Arrays.asList(
        new Product("ShackMeister Ale", 9.8, "쉐이크쉑 버거를 위해 뉴욕 브루클린 브루어리에서 특별히 양조한 에일 맥주"),
        new Product("Magpie Brewing Co.", 6.8, "제주도 브루어리에서 전량 생산되는 맥파이 맥주")
    );

    List<Order> orderList = new ArrayList<>();


    public String MainMenu() {
        System.out.println("\"SHAKESHACK BURGER 에 오신걸 환영합니다.\"");
        System.out.println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.");
        System.out.println();

        System.out.println("[ SHAKESHACK MENU ]");

        AtomicInteger menuIndex = new AtomicInteger(1);
        menuList.stream().forEach(menu -> {
            int currentIndex = menuIndex.getAndIncrement();
            System.out.printf("%d. %-15s | %s\n", currentIndex, menu.getName(), menu.getDescription());
        });

        System.out.println();
        System.out.println("[ ORDER MENU ]");

        AtomicInteger selectIndex = new AtomicInteger(menuList.size()+1);
        selectList.stream().forEach(select -> {
            int currentIndex = selectIndex.getAndIncrement();
            System.out.printf("%d. %-11s | %s\n", currentIndex, select.getName(), select.getDescription());
        });

        Scanner scanner = new Scanner(System.in);
        System.out.print("선택하기 : ");
        int selectedMenu = scanner.nextInt();
        System.out.println();

        Map<Integer, Product> selectedCart;
        switch (selectedMenu) {
            case 1:
                selectedCart = ProductMenu("Burgers", burgerList);
                orderCart(selectedCart);
                break;
            case 2:
                selectedCart = ProductMenu("Frozen Custard", custardList);
                orderCart(selectedCart);
                break;
            case 3:
                selectedCart = ProductMenu("Drinks", drinkList);
                orderCart(selectedCart);
                break;
            case 4:
                selectedCart = ProductMenu("Beer", beerList);
                orderCart(selectedCart);
                break;
            case 5:
                orderMenu();
                break;
            case 6:
                orderCancel();
                break;
        }
        return null;
    }

    private Map<Integer, Product> ProductMenu(String selectedMenu, List<Product> productList) {
        System.out.println("\"SHAKESHACK BURGER 에 오신걸 환영합니다.\"");
        System.out.println("아래 상품메뉴판을 보시고 상품 골라 입력해주세요.");
        System.out.println();

        System.out.printf("[ %s MENU ]\n", selectedMenu);
        AtomicInteger selectedIndex = new AtomicInteger(1);
        productList.stream().forEach(product -> {
            int currentIndex = selectedIndex.getAndIncrement();
            int maxNameLength = productList.stream().mapToInt(p -> p.getName().length()).max().getAsInt() + 1;
            System.out.printf("%d. %-" + maxNameLength + "s | W %.1f | %s\n", currentIndex, product.getName(), product.getPrice(), product.getDescription());
        });
        System.out.println();

        Scanner scanner = new Scanner(System.in);
        System.out.print("선택하기 : ");
        int selectedProduct = scanner.nextInt();
        System.out.println();

        Product product = productList.get(selectedProduct - 1);
        int maxNameLength = productList.stream().mapToInt(p -> p.getName().length()).max().getAsInt() + 1;
        System.out.printf("\"%-" + maxNameLength + "s | W %.1f | %s\"\n", product.getName(), product.getPrice(), product.getDescription());
        System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인        2. 취소");

        System.out.print("선택하기 : ");
        int selectedCart = scanner.nextInt();
        System.out.println();

        Map<Integer, Product> menu = new HashMap<>();
        menu.put(selectedCart, product);
        return menu;
    }

    private void orderCart(Map<Integer,Product> menu) {
        if (menu.containsKey(1)) {
            System.out.printf("%s 가 장바구니에 추가되었습니다.\n", menu.get(1).getName());
            System.out.println();
            orderList.add(
                new Order(menu.get(1).getName(), menu.get(1).getPrice(), menu.get(1).getDescription())
            );
        }

        System.out.println();
        MainMenu();

    }

    private void orderMenu() {
        if (orderList != null && !orderList.isEmpty()) {
            System.out.println("아래와 같이 주문 하시겠습니까?");
            System.out.println();

            System.out.println("[ Orders ]");
            int maxNameLength = orderList.stream().mapToInt(p -> p.getName().length()).max().getAsInt() + 1;
            for (Order order: orderList) {
                System.out.printf("%-" + maxNameLength + "s | W %.1f | %s\n", order.getName(), order.getPrice(), order.getDescription());
            }
            System.out.println();

            System.out.println("[ Total ]");
            double totalPrice = orderList.stream().mapToDouble(Order::getPrice).sum();
            System.out.printf("W %.1f\n", totalPrice);
            System.out.println();
            System.out.println("1. 주문      2. 메뉴판");

            Scanner scanner = new Scanner(System.in);
            System.out.print("선택하기 : ");
            int selectedOrder = scanner.nextInt();
            System.out.println();

            if (selectedOrder == 1) {
                orderSuccess();
            } else {
                MainMenu();
            }
        } else {
            System.out.println("장바구니에 담긴 상품이 없습니다.");
            MainMenu();
        }
    }

    private void orderSuccess() {
        orderList.clear();
        System.out.println("주문이 완료되었습니다!");
        System.out.println();

        int currentWaitingNumber = waitingNumber.getAndIncrement();
        System.out.println("대기번호는 [ " + waitingNumber + " ] 번 입니다.");
        System.out.println("(3초후 메뉴판으로 돌아갑니다.)");

        new Thread(() -> {
            try {
                Thread.sleep(3000);
                MainMenu();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void orderCancel() {
        System.out.println("진행하던 주문을 취소하시겠습니까?");
        System.out.println("1. 확인        2. 취소");

        Scanner scanner = new Scanner(System.in);
        System.out.print("선택하기 : ");
        int selectedCancel = scanner.nextInt();
        System.out.println();

        if (selectedCancel == 1) {
            orderList.clear();
            System.out.println("진행하던 주문이 취소되었습니다.");
            System.out.println();
        }

        MainMenu();
    }
}
