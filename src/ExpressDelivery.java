public class ExpressDelivery implements DeliveryMethod {
    @Override
    public double calculateCost() {
        return 800.0;
    }

    @Override
    public String getEstimatedTime() {
        return "2-4 часа";
    }
}
