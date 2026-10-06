public class StandardDelivery implements DeliveryMethod{
    @Override
    public double calculateCost() {
        return 300.0;
    }

    @Override
    public String getEstimatedTime() {
        return "1-2 дня";
    }
}
