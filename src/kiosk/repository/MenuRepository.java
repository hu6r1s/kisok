package kiosk.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import kiosk.model.Menu;
import kiosk.model.Order;
import kiosk.model.Product;

public class MenuRepository {
    private Map<String, List<Menu>> menus;
    private Map<String, List<Product>> products;
    List<Order> orders;

    public MenuRepository() {
        menus = new HashMap<>();
        products = new HashMap<>();
        orders = new ArrayList<>();
        initialize();
    }

    private void initialize() {
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

        menus.put("Main", menuList);
        menus.put("Order", selectList);

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

        products.put("Burgers", burgerList);
        products.put("Frozen Custard", custardList);
        products.put("Drinks", drinkList);
        products.put("Beer", beerList);
    }

    public List<Menu> getMenus(String key) {
        return menus.get(key);
    }

    public List<Product> getProducts(String key) {
        return products.get(key);
    }

    public List<Order> getOrderList() {
        return orders;
    }
    public void setOrder(Order newOrder) {
        Optional<Order> existingOrder = orders.stream()
                .filter(order -> order.getProduct().getName().equals(newOrder.getProduct().getName()))
                .findFirst();

        if (existingOrder.isPresent()) {
            existingOrder.get().setCount(existingOrder.get().getCount() + newOrder.getCount());
        } else {
            orders.add(newOrder);
        }
    }

    public void increaseCount(Product product) {
        orders.stream().filter(order -> order.getProduct().getName().equals(product.getName()))
                    .findFirst()
                    .ifPresent(order -> order.setCount(order.getCount() + 1));
    }

    public double getTotalPrice() {
        return orders.stream().mapToDouble(order -> order.getProduct().getPrice() * order.getCount()).sum();
    }
}
