## Project Structure

```
api-gateway/
├── src/main/java/com/example/api_gateway/
│   ├── ApiGatewayApplication.java           ← Main class
│   ├── config/
│   │   ├── GatewayConfig.java              ← Routes & load balancing
│   │   └── SecurityConfig.java             ← Security & CORS
│   ├── controller/
│   │   └── GatewayController.java          ← Health/info endpoints
│   ├── filter/
│   │   └── JwtAuthFilter.java              ← JWT validation
│   ├── dto/
│   │   └── ApiResponse.java                ← Response wrapper
│   └── util/
│       └── JwtTokenProvider.java           ← JWT utilities
├── src/main/resources/
│   └── application.properties               ← Configuration
├── src/test/java/com/example/api_gateway/
├── pom.xml                                  ← Maven dependencies
└── application-example.yml                  ← Profile examples
```


## Public Endpoints (No Auth Required)

```
GET  /api/restaurants          - List restaurants
GET  /api/restaurants/{id}     - Get restaurant details
GET  /api/menu/{restaurantId}  - Get menu
POST /api/users/register       - Register
.....
```

## Protected Endpoints (Auth Required)

```
GET/PUT/DELETE /api/users/**           - User operations
POST /api/orders                        - Create order
GET  /api/orders/{id}                   - Get order
GET  /api/delivery/**                   - Delivery status
.....
```
