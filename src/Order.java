import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private Client client;
    private String deliveryAddress;
    private List<OrderItem> items;
    private DeliveryMethod deliveryMethod;
    private Courier courier;
    private OrderStatus status;

    public Order(int id, Client client, String deliveryAddress) {
        this.id = id;
        this.client = client;
        this.deliveryAddress = deliveryAddress;
        this.items = new ArrayList<>();
        this.status = OrderStatus.CREATED;
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public void setDeliveryMethod(DeliveryMethod method) {
        this.deliveryMethod = method;
    }

    public void assignCourier(Courier courier) {
        if (deliveryMethod instanceof PickupDelivery) {
            System.out.println("Ошибка: самовывозу не требуется курьер");
            return;
        }
        if (!courier.isAvailable()) {
            System.out.println("Ошибка: выбранный курьер сейчас занят!");
            return;
        }
        this.courier = courier;
        courier.setAvailable(false);
        System.out.println("Курьер " + courier.getName() + " успешно нащначен на заказ #" + id);
    }

    public double calculateTotalCost() {
        double total = 0;

        for (OrderItem item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        if (deliveryMethod != null) {
            total += deliveryMethod.calculateCost();
        }
        return total;
    }

    public void setStatus(OrderStatus newStatus) {
        this.status = newStatus;
    }

    public int getId() {return id;}
    public OrderStatus getStatus() {return status;}
    public DeliveryMethod getDeliveryMethod() {return deliveryMethod;}
    public Courier getCourier() {return courier;}
}
