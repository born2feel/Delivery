import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    //список со всеми заказами программы
    private List<Order> orders;
    private int nextOrderId;

    public OrderManager() {
        this.orders = new ArrayList<>();
        this.nextOrderId = 1; //первый заказ будет номер один
    }

    //метод для создания нового заказа и добавления его в список
    public Order createOrder(Client client, String address) {
        Order newOrder = new Order(nextOrderId, client, address);
        nextOrderId++;
        orders.add(newOrder);
        return newOrder;
    }

    //метод для получения списка всех заказов
    public List<Order> getAllOrders() {
        return orders;
    }

    //конкретный заказ по айди
    public Order getOrderById(int id) {
        for (Order order : orders) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }
}
