package kiosk.utils;

public enum MenuNumber {
    SELECT_BURGER(1),
    SELECT_CUSTARD(2),
    SELECT_DRINK(3),
    SELECT_BEER(4),
    SELECT_ORDER(5),
    SELECT_CANCEL(6);

    private final int selectedNumber;

    MenuNumber(int selectedNumber) {
        this.selectedNumber = selectedNumber;
    }

    public int getSelectedNumber() {
        return selectedNumber;
    }

    public static MenuNumber valueOf(int selectedNumber) {
        for (MenuNumber number : MenuNumber.values()) {
            if (number.getSelectedNumber() == selectedNumber) {
                return number;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 선택입니다.");
    }
}
