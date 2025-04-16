Printing Press Management System


Overview

This project simulates a printing press that produces various publications—including books, posters, newspapers, and more. Each publication is uniquely defined by its title, the number of pages, and the page size (A1, A2, A3, A4, A5). The simulation models every stage of the printing process, from paper selection and pricing to production, cost tracking, and revenue calculation.


Features

Publications and Paper Selection
- Publications: Every publication includes a title, the count of pages, and a specified page size (ranging from A1 to A5).
- Paper Types: Each publication can be printed using one of three paper types:
  - Standard paper
  - Glossy paper
  - Newspaper printing paper
- Paper Pricing Strategy: The cost of a sheet of paper depends on its size and type.
  - Base prices are defined for the smallest paper size (A5) for each type.
  - For larger sizes, the price increases by a predetermined percentage.
  - Importantly, paper pricing must be distinct for each printing press within the application.

Employee Management and Payroll
- Employee Roles: The printing press employs two types of workers:
  - Printing machine operators
  - Managers
- Salary Details: Both roles receive the same base salary. However, managers are eligible for an additional bonus—a percentage on their base salary—if the printing press’s revenue exceeds a specified threshold.

Cost and Revenue Calculations
- Expenses:
  - Paper Costs: Calculated by multiplying the number of sheets purchased by the cost per sheet.
  - Salaries: Determined based on the list of employees and their individual base salaries.
- Revenue: Revenue is generated through the printed editions.
  - A set printing price is applied for each copy of a publication that customers pay.
  - If the number of copies printed surpasses a predefined quantity, a discount percentage is applied to the per-copy printing price.

Printing Machines
- Mechanical Specs: The press is equipped with several printing machines. Each machine has:
  - A maximum paper capacity.
  - The ability to print in color or black and white, with a specific speed (pages per minute) when loaded with paper.
- Operational Functions:
  - A dedicated method to load paper into a machine.
  - Tracking for each machine should include a record of which publications are printed along with the number of copies produced.
  - When initiating a printing job, the system must determine whether the output should be in color or black and white. If the machine is not configured for the requested print type, it should throw an exception.
  - A method is required to return the total number of pages printed on the machine.

Data Persistence and Testing
- File-Based Recording: Data for printed publications, revenue, and expenses should be recorded in a file, with the ability to read and load these records back into the system.
- Robust Exception Handling and Testing: The project utilizes exception management to handle errors gracefully and incorporates comprehensive unit tests to verify that individual components function correctly.


Objective

Implement a complete simulation of a printing press that manages the process of printing various editions while accurately calculating revenues and expenses according to the rules outlined above. The solution should employ object-oriented design principles, robust exception handling, and thorough unit testing to create a reliable and maintainable system.
