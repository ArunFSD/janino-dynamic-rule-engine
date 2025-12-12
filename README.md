# Janino Dynamic Rule Engine Demo (Spring Boot + Java 8)

This project demonstrates how to use **Janino**, a lightweight Java runtime compiler, to dynamically evaluate business rules *without restarting the application*.

It compares two approaches:

- **Hardcoded Discount Logic** – Traditional Java method
- **Dynamic Discount Logic** – Rule loaded from an external file and recompiled at runtime using Janino

This is ideal for showing how legacy Java 8 applications can gain dynamic behavior without redeploying.

---

## 🚀 Features

✔ Hardcoded baseline logic  
✔ Dynamic rule evaluation using Janino  
✔ Auto-refresh rule every 5 seconds  
✔ Rule stored in `resources/rules/discount.rule`  
✔ Simple UI built with Thymeleaf  
✔ No application restart required

---