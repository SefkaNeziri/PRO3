package dk.via.pro3y.semesterassignment.model;

public class ProductData {

    private int productId;
    private int[] registrationNumbers;

    public ProductData(int productId, int[] registrationNumbers) {
        this.productId = productId;
        this.registrationNumbers = registrationNumbers;
    }

    public int getProductId() {
        return productId;
    }

    public int[] getRegistrationNumbers() {
        return registrationNumbers;
    }
}