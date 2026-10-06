public class PickupDelivery implements DeliveryMethod {
    @Override
    public double calculateCost() {
        return 0.0;
    }

    @Override
    public String getEstimatedTime() {
        return "В любое время работы пункта выдачи";
    }
}
