ByteMe! – CLI Based Food Ordering System
📌 Overview
ByteMe! is a Java-based Command Line Interface (CLI) food ordering system designed for a college canteen.
It provides two separate interfaces:
•	Admin Interface – For managing menu, orders, refunds, and reports
•	Customer Interface – For browsing food items, placing orders, reviewing items, and VIP membership
The system uses efficient Java data structures to handle priority-based order processing and item management.
________________________________________
🛠 Tech Stack
•	Java (JDK 8+)
•	IntelliJ IDEA
•	Core Java Collections (TreeMap, PriorityQueue, ArrayList)
________________________________________
⚙️ How to Run the Project (IntelliJ)
Prerequisites
•	Java JDK 8 or higher installed
•	IntelliJ IDEA installed
Steps
1.	Clone the repository
git clone https://github.com/spaswan-coder/ByteMe-Food-Ordering-System.git
2.	Open in IntelliJ
•	Open IntelliJ
•	Click Open → Select the project folder
•	Let IntelliJ index the project
3.	Run the Program
•	Open ByteMe.java
•	Right-click → Run 'ByteMe.main()'
________________________________________
📂 System Components
Class	Description
ByteMe	Main class that controls program flow
Customer	Manages customer info & VIP status
FoodItem	Stores menu item details & reviews
Order	Handles order processing & status
Review	Stores customer reviews
________________________________________
✨ Key Features
•	Dual Admin & Customer interfaces
•	VIP membership system with priority ordering
•	PriorityQueue-based VIP order processing
•	Menu management (add/update/remove items)
•	Order tracking & cancellation
•	Review and rating system
•	Sales & popular items report generation
________________________________________
📌 Assumptions
General
•	Data is not persisted between runs
•	Payment is assumed successful
•	No authentication/login system
Customer
•	Registration requires only name
•	VIP membership requires a one-time payment of $50
•	VIP status is permanent
Orders
•	Orders are marked as paid when placed
•	Orders can only be cancelled in Order Received status
•	VIP orders are always prioritized
Menu & Reviews
•	Each menu item has a unique name
•	Ratings range from 1 to 5
•	Customers cannot modify/delete reviews
________________________________________
🧠 Data Structures Used
Structure	Purpose
TreeMap	Sorted menu storage
PriorityQueue	VIP order priority handling
ArrayList	Carts, reviews, order history
________________________________________
🔄 System Flow
Admin
•	Manage menu
•	Process and update orders
•	Handle refunds
•	Generate sales & popular items reports
Customer
•	Register account
•	Browse menu
•	Add items to cart
•	Place & track orders
•	Submit reviews
•	Purchase VIP membership
________________________________________
📈 VIP Membership
•	One-time payment of $50
•	VIP orders receive processing priority
•	VIP status is displayed in order summaries
________________________________________

