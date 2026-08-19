                    LibraryItem
                         |
              +----------+----------+
              |                     |
          PhysicalItem          DigitalItem
              |                     |
            Book                  EBook
              |
          BookCopy

                    +----------------+
                    |    Library     |
                    +----------------+
                    | books          |
                    | members        |
                    | loans          |
                    | reservations   |
                    +-------+--------+
                            |
             +--------------+--------------+
             |              |              |
        +----v----+    +----v-----+   +----v-------+
        |  Book   |    |  Member  |   |   Loan     |
        +---------+    +----------+   +------------+
        | isbn    |    | memberId |   | loanId     |
        | title   |    | name     |   | bookCopy   |
        | author  |    +----------+   | member     |
        +----+----+                   | issueDate  |
             |                         | dueDate    |
             v                         +------------+
       +-------------+
       |  BookCopy   |
       +-------------+
       | copyId      |
       | status      |
       +-------------+

Book
 └── BookCopy

Member
 └── Loan

Library
 ├── Book
 ├── Member
 ├── Loan
 └── Reservation

                   ┌───────────────┐
                   │ API Gateway   │
                   └───────┬───────┘
                           │
             ┌─────────────┴─────────────┐
             │                           │
      ┌──────▼───────┐           ┌──────▼───────┐
      │ Library      │           │ Member       │
      │ Service      │           │ Service      │
      └──────┬───────┘           └──────┬───────┘
             │                          │
             └────────────┬─────────────┘
                          │
                    ┌─────▼─────┐
                    │ PostgreSQL│
                    └───────────┘
                          │
                    ┌─────▼─────┐
                    │   Redis   │
                    └───────────┘

                    ┌───────────┐
                    │   Kafka   │
                    └─────┬─────┘
                          │
              ┌───────────┴───────────┐
              │                       │
       Notification Service     Fine Service

             LibraryItem
             /         \
            /           \
         Book           EBook
          |
      BookCopy
                  LibraryItem
                /        \
               /          \
            Book           EBook
             |               |
      PhysicalBorrowing   EBookBorrowing
          Policy             Policy
              
 11. Your next interview-level exercises

Don't stop at the above implementation. Modify it yourself in this order:

Level 1 — Basic LLD

Add EBook
Add Magazine
Add DVD
Support different borrowing periods
Add maximum books per member
Add book categories
Search by title/author/category
Add/remove members
Add/remove books

Level 2 — Design Patterns

Implement FineCalculator using Strategy Pattern
Implement notification using Observer Pattern
Implement book search using Specification Pattern
Implement payment using Strategy Pattern
Add Factory for creating different library items
Add Repository interfaces

Level 3 — Concurrency

Multiple entry gates
Multiple return counters
Thread-safe book allocation
Prevent double borrowing
Prevent double return
Handle two users attempting to borrow the last copy simultaneously

Level 4 — Real-world requirements

Lost book
Damaged book
Fine payment
Partial fine payment
Reservation expiry
Reservation priority
Member suspension
Membership types
Different borrowing limits