# tap-technical-assessment

## todo list for technical assessment

- [x] create/view household
- [x] update/view household
- [x] grants api
- [x] delete member (need reason, update spouse)
- [x] delete household
- [ ] member entity to member mapper (reuse code)
- [ ] readme documentation
- [ ] readme setup instruction
- [ ] testing?

## Setup Instructions

_To be done_

_mysql - run createDbScripts.sql for mysql_

_server - navigate to project directory and do run mvn spring-boot:run_

## Assumptions

Assumptions about searching for households and recipients of grants:

### Student Encouragement Bonus

- Returns only households with total annual income strictly less than the provided total annual income
- Returns only family members whose age is strictly less than the provided age

### Family Togetherness Scheme

- Returns only households and family members which fulfill the following requirements:
  - Has at least 1 married member who is married to another family member within the household
  - Non married members age is strictly less than the provided age

### Elder Bonus

- Returns only **HDB** households with family members whose age is strictly more than the provided value
- Returns only family members whose age is strictly more than the provided value

### Baby Sunshine Grant

- Returns only households with family members whose age is strictly less than the provided value
- Returns only family members whose age is strictly less than the provided value

### YOLO GST Grant

- Returns only **HDB** households with total annual income strictly less than the provided total annual income
- Returns all family members of the household
