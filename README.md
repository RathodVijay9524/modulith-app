# Spring Modulith Application

A comprehensive Spring Boot application demonstrating the Spring Modulith architecture with multiple business modules.

## Project Structure

```
modulith-app/                  <-- Parent POM (packaging: pom)
├─ pom.xml                     <-- Parent POM configuration
├─ src/main/java/com/vijay/    <-- Main application class
├─ common/
│  └─ pom.xml
│  └─ src/main/java/com.vijay.common/
│      └─ BaseEntity.java      <-- Shared base entity
├─ user-management/
│  └─ pom.xml
│  └─ src/main/java/com.vijay.usermgmt/
│      ├─ User.java            <-- User entity
│      ├─ UserService.java     <-- User business logic
│      ├─ UserRepository.java  <-- User data access
│      └─ UserCreatedEvent.java <-- Domain event
├─ chat/
│  └─ pom.xml
│  └─ src/main/java/com.vijay.chat/
│      ├─ ChatMessage.java     <-- Chat message entity
│      ├─ ChatService.java     <-- Chat business logic
│      └─ ChatMessageRepository.java
├─ hotel-ordering/
│  └─ pom.xml
│  └─ src/main/java/com.vijay.hotel/
│      ├─ HotelBooking.java    <-- Booking entity
│      ├─ HotelBookingService.java
│      ├─ HotelBookingRepository.java
│      └─ BookingCreatedEvent.java
├─ notification/
│  └─ pom.xml
│  └─ src/main/java/com.vijay.notification/
│      └─ NotificationService.java <-- Email/SMS notifications
└─ payments/
   └─ pom.xml
   └─ src/main/java/com.vijay.payments/
       ├─ Payment.java         <-- Payment entity
       ├─ PaymentService.java  <-- Payment processing
       ├─ PaymentRepository.java
       └─ PaymentCompletedEvent.java
```

## Key Features

### 1. **Modulith Architecture**
- **@Modulith** annotation on main application class
- Each module is a separate Maven module with its own POM
- Clear module boundaries and dependencies
- Event-driven communication between modules

### 2. **Module Dependencies**
- **Common Module**: Shared utilities (BaseEntity, common configurations)
- **User Management**: User authentication and management + Spring Security
- **Chat**: Real-time messaging with WebSocket support
- **Hotel Ordering**: Booking management system
- **Notification**: Email/SMS notifications using Spring Mail
- **Payments**: Payment processing with event publishing

### 3. **Event-Driven Communication**
- **UserCreatedEvent**: Published when new user registers
- **BookingCreatedEvent**: Published when hotel booking is created
- **PaymentCompletedEvent**: Published when payment is processed
- Cross-module communication via Spring Application Events

## How to Use This Modulith

### Prerequisites
- Java 24
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, VS Code)

### Setup Instructions

1. **Clone and Build**
   ```bash
   git clone <your-repo>
   cd modulith-app
   mvn clean install
   ```

2. **Database Setup**
   - Install MySQL and create database:
   ```sql
   CREATE DATABASE modulith_db;
   ```
   - Update `src/main/resources/application.properties` with your MySQL credentials

3. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```
   Or run `ModulithApplication.java` from your IDE

### Module Communication Examples

#### 1. **User Registration Flow**
```java
// 1. User registers via UserService
User user = userService.createUser(newUser);

// 2. UserCreatedEvent is published
// 3. ChatService listens and sends welcome message
// 4. NotificationService listens and sends welcome email
```

#### 2. **Hotel Booking Flow**
```java
// 1. User creates booking via HotelBookingService
HotelBooking booking = bookingService.createBooking(newBooking);

// 2. BookingCreatedEvent is published
// 3. PaymentService listens and processes payment
// 4. NotificationService listens and sends confirmation
```

#### 3. **Payment Processing Flow**
```java
// 1. Payment is processed via PaymentService
Payment payment = paymentService.processPayment(paymentData);

// 2. PaymentCompletedEvent is published
// 3. Other modules can listen for post-payment operations
```

### Testing the Modulith

#### 1. **Module Structure Verification**
```java
@Test
void verifyModulithStructure() {
    ApplicationModules modules = ApplicationModules.of(ModulithApplication.class);
    modules.verify();
}
```

#### 2. **Integration Testing**
```java
@ModulithTest
class UserManagementIntegrationTest {
    // Test cross-module interactions
}
```

### Key Modulith Concepts

#### 1. **Module Boundaries**
- Each package under `com.vijay.*` represents a module
- Modules communicate via events, not direct method calls
- Clear separation of concerns

#### 2. **Event Publishing**
```java
@Service
public class UserService {
    private final ApplicationEventPublisher eventPublisher;
    
    public User createUser(User user) {
        User saved = repository.save(user);
        eventPublisher.publishEvent(new UserCreatedEvent(saved.getId()));
        return saved;
    }
}
```

#### 3. **Event Listening**
```java
@Service
public class NotificationService {
    @EventListener
    public void handleUserCreated(UserCreatedEvent event) {
        sendWelcomeEmail(event.userId());
    }
}
```

### Development Guidelines

#### 1. **Adding New Modules**
1. Create new Maven module directory
2. Add module to parent POM's `<modules>` section
3. Create module POM with parent reference
4. Follow package naming: `com.vijay.{module-name}`
5. Add dependencies on common module if needed

#### 2. **Inter-Module Communication**
- **DO**: Use Application Events for cross-module communication
- **DON'T**: Direct method calls between modules
- **DO**: Keep module interfaces clean and minimal
- **DON'T**: Share entities directly between modules

#### 3. **Testing Strategy**
- Unit tests within each module
- Integration tests using `@ModulithTest`
- Module structure verification tests
- Event flow testing

### Configuration Files

#### application.properties
```properties
# Database configuration for MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/modulith_db
spring.jpa.hibernate.ddl-auto=update

# Enable Modulith event store
spring.modulith.events.jdbc.schema-initialization.enabled=true

# Debug logging for development
logging.level.org.springframework.modulith=DEBUG
```

### Benefits of This Architecture

1. **Modularity**: Clear separation of business domains
2. **Maintainability**: Each module can be developed independently
3. **Testability**: Easy to test modules in isolation
4. **Scalability**: Modules can be extracted to microservices later
5. **Event-Driven**: Loose coupling between modules
6. **Documentation**: Self-documenting module structure

### Next Steps

1. **Add REST Controllers** for each module
2. **Implement Security** in user-management module
3. **Add WebSocket** configuration for real-time chat
4. **Integrate Payment Gateway** (Stripe, PayPal)
5. **Add Email Templates** for notifications
6. **Create Docker Configuration** for easy deployment
7. **Add API Documentation** with OpenAPI/Swagger

### Troubleshooting

#### Common Issues
1. **Module not found**: Check package naming and POM configuration
2. **Events not firing**: Ensure `@EnableJpaAuditing` is present
3. **Database connection**: Verify MySQL is running and credentials are correct
4. **Build failures**: Run `mvn clean install` from root directory

#### Useful Commands
```bash
# Build all modules
mvn clean install

# Run specific module tests
mvn test -pl user-management

# Generate module documentation
mvn spring-modulith:document-modules

# Verify module structure
mvn spring-modulith:verify
```

This modulith architecture provides a solid foundation for building scalable, maintainable applications while keeping the benefits of a monolithic deployment model.
