To test with BloomRPC:
Open the .proto file, and in
AnimalService -> GetProducts

{
  "registrationNumber": 101
}
possible data points: 101, 102, 103

In ProductService -> GetRegistrationNumbers
{
  "productId": 1
}
possible data points:
1, 2, 3.

See src/main/resources/data.sql.
