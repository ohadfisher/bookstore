# Welcome To Bookstore:)
At Bookstore you can add books to warehouse and order books for customer.

Bookstore build as  3 microservices system:
1. bookstore-service
2. bookstore-warehouse-service
3. bookstore-crm-service

All the services "dockerized" and upload to DockerHub as images,
 "docker-compose.yml" will pull the images, combined and run them together.

**HOW TO USE:**
**Download** "docker-compose.yml" 
**Open** the terminal at the location of the file.
**Type** "docker-compose up" (to start)
**Goto**  [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)
**Functionality** use the functionality of Bookstore (by using swagger-ui) 
**Open** the terminal at the location of the file.
**Type** "docker-compose down" (to stop)




**Important to know abut Bookstore system:**
-The system is key sensitive.
-Every customer can order book just if the book exist at warehouse.
-If you ask the books that was order for specific customer, you get the "books", and the orders deleted (because orderד executed and the book supplied).

**Functionality of bookstore:**
 - /bookstore/addBook
 - /bookstore/addCustomer/{customerName}
 - /bookstore/getBookById/{id}
 - /bookstore/getBookOrdersByCustomerName/{customerName}
 - bookstore/getBooksByAuthor/{author}
 - /bookstore/getBooksByName/{name}
 - /bookstore/getCustomerByName/{customerName}
 - /bookstore/getOrder/{customerName}/{orderId}
 - /bookstore/orderBook/{customerName}/{bookId}
 - /bookstore/retrieveBooksByIds
 
