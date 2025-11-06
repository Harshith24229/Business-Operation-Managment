package com.boms.config;

import com.boms.entity.User;
import com.boms.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class DataInitializer implements CommandLineRunner {
    
    private final UserService userService;
    
    public DataInitializer(UserService userService) {
        this.userService = userService;
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Create default admin user
        if (userService.findByUsername("admin").isEmpty()) {
            User admin = new User("admin", "admin123", "admin@boms.com", "System Administrator", User.Role.ADMIN);
            userService.createUser(admin);
            System.out.println("Default admin user created: admin/admin123");
        }
        
        // Create sample sales user
        if (userService.findByUsername("sales1").isEmpty()) {
            User sales = new User("sales1", "sales123", "sales1@boms.com", "Sales Executive", User.Role.SALES_TEAM);
            userService.createUser(sales);
            System.out.println("Sample sales user created: sales1/sales123");
        }
        
        // Create sample executive user
        if (userService.findByUsername("executive1").isEmpty()) {
            User executive = new User("executive1", "exec123", "executive1@boms.com", "Project Director", User.Role.EXECUTIVE_TEAM);
            userService.createUser(executive);
            System.out.println("Sample executive user created: executive1/exec123");
        }
        

    }
}