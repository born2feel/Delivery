import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleUI {
    private OrderManager orderManager;
    private Scanner scanner;
    private Courier testCourier; //тестовый курьер

    public ConsoleUI() {
        this.orderManager = new OrderManager();
        this.scanner = new Scanner(System.in);
        this.testCourier = new Courier("Иван");
    }

    public void start() {
        boolean running = true;

        while (running) {
            System.out.println("\n--- Меню Службы Доставки ---");
            System.out.println("1. Создать новый заказ");
            System.out.println("2. Показать список заказов");
            System.out.println("3. Выбрать способ доставки для заказа");
            System.out.println("4. Назначить курьера");
            System.out.println("5. Изменить статус заказа");
            System.out.println("6. Выход");
            System.out.print("Выберите действие: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        createOrderMenu();
                        break;
                    case 2:
                        showOrdersMenu();
                        break;
                    case 3:
                        selectDeliveryMenu();
                        break;
                    case 4:
                        assignCourierMenu();
                        break;
                    case 5:
                        changeStatusMenu();
                        break;
                    case 6:
                        running = false;
                        System.out.println("Завершение работы программы.");
                        break;
                    default:
                        System.out.println("Ошибка: Такого пункта меню нет.");
                }
            } catch (InputMismatchException e) {
                //если ввел букву
                System.out.println("Ошибка: Пожалуйста, введите цифру!");
                scanner.nextLine(); //очистка ввода
            }
        }
    }


    private void createOrderMenu() {
        System.out.print("Введите имя клиента: ");
        String name = scanner.nextLine();
        System.out.print("Введите телефон: ");
        String phone = scanner.nextLine();
        System.out.print("Введите адрес доставки: ");
        String address = scanner.nextLine();

        Client client = new Client(name, phone);
        Order order = orderManager.createOrder(client, address);

        System.out.print("Введите название товара: ");
        String itemName = scanner.nextLine();
        System.out.print("Введите цену: ");
        double price = scanner.nextDouble();
        order.addItem(new OrderItem(itemName, 1, price));

        System.out.println("Заказ #" + order.getId() + " успешно создан!");
    }

    private void showOrdersMenu() {
        System.out.println("--- Список заказов ---");
        for (Order order : orderManager.getAllOrders()) {
            System.out.println("Заказ #" + order.getId() + " | Статус: " + order.getStatus() +
                    " | Сумма: " + order.calculateTotalCost());
        }
    }

    private void selectDeliveryMenu() {
        System.out.print("Введите номер заказа: ");
        int id = scanner.nextInt();
        Order order = orderManager.getOrderById(id);

        if (order != null) {
            System.out.println("1 - Стандартная, 2 - Экспресс, 3 - Самовывоз");
            int delChoice = scanner.nextInt();
            if (delChoice == 1) order.setDeliveryMethod(new StandardDelivery());
            else if (delChoice == 2) order.setDeliveryMethod(new ExpressDelivery());
            else if (delChoice == 3) order.setDeliveryMethod(new PickupDelivery());

            System.out.println("Доставка обновлена! Ориентировочное время: " +
                    order.getDeliveryMethod().getEstimatedTime());
        } else {
            System.out.println("Заказ не найден.");
        }
    }

    private void assignCourierMenu() {
        System.out.print("Введите номер заказа для назначения курьера: ");
        int id = scanner.nextInt();
        Order order = orderManager.getOrderById(id);
        if (order != null) {
            order.assignCourier(testCourier);
        }
    }

    private void changeStatusMenu() {
        System.out.print("Введите номер заказа: ");
        int id = scanner.nextInt();
        Order order = orderManager.getOrderById(id);

        if (order != null) {

            System.out.println("Текущий статус заказа: " + order.getStatus());

            System.out.println("Выберите новый статус:");
            System.out.println("1 - В доставке (DELIVERING)");
            System.out.println("2 - Завершен (COMPLETED)");
            System.out.print("Ваш выбор: ");

            int statusChoice = scanner.nextInt();

            if (statusChoice == 1) {
                order.setStatus(OrderStatus.DELIVERING);
                System.out.println("Статус заказа #" + id + " изменен на " + order.getStatus());
            } else if (statusChoice == 2) {
                order.setStatus(OrderStatus.COMPLETED);
                //если заказ завершен то освободить курьера
                if (order.getCourier() != null) {
                    order.getCourier().setAvailable(true);
                }
                System.out.println("Заказ #" + id + " доставлен! Статус изменен на " + order.getStatus());
            } else {
                System.out.println("Ошибка: Неверный выбор статуса.");
            }
        } else {
            System.out.println("Заказ не найден.");
        }
    }
}