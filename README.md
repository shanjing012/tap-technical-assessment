# tap-technical-assessment

## Setup Instructions

1. Use `createDbScripts.sql` to set up the database.
2. Navigate to project directory and use the `mvn spring-boot:run` to start the server
3. Endpoints can be viewed at http://localhost:8083/swagger-ui.html#/

## Assumptions

### Age assumptions:

- Strictly more than 50 years old: 1 day after their birthday
- Strictly less than 16 years old: 1 day before their birthday

### Assumptions about searching for households and recipients of grants:

#### Student Encouragement Bonus

- Returns only households with total annual income strictly less than the provided total annual income `(150000)`
- Returns only family members whose age is strictly less than the provided age `(16)`

#### Family Togetherness Scheme

- Returns only households and family members which fulfill the following requirements:
  - Has at least 1 married member who is married to another family member within the household
  - Has non married members, whose age is strictly less than the provided age `(18)`

#### Elder Bonus

- Returns only **HDB** households with family members whose age is strictly more than the provided value `(50)`
- Returns only family members whose age is strictly more than the provided value `(50)`

#### Baby Sunshine Grant

- Returns only households with family members whose age is strictly less than the provided value `(5)`
- Returns only family members whose age is strictly less than the provided value `(5)`

#### YOLO GST Grant

- Returns only **HDB** households with total annual income strictly less than the provided total annual income `(100000)`
- Returns all family members of the household

##### PS. Tests are just used to check for code logic, they are not meant to be comprehensive test cases for the entire project.
