# tap-technical-assessment

## todo list for technical assessment

- [ ] create/view household
- [ ] update/view household
- [ ] grants api
- [ ] delete member (need reason, update spouse)
- [ ] delete household
- [ ] readme documentation
- [ ] readme setup instruction
- [ ] testing?

## Assumptions

Assumptions about searching for households and recipients of grants:

1. Student Encouragement Bonus

- Returns only households with total annual income strictly less than the provided total annual income
- Returns only family members whose age is strictly less than the provided age

2. Family Togetherness Scheme

- Returns only households and family members which fulfill the following requirements:
  - Has at least 1 married member who is married to another family member within the household
  - Non married members age is strictly less than the provided age

3. Elder Bonus

- Returns only households with family members whose age is strictly more than the provided value
- Returns only family members whose age is strictly more than the provided value

4. Baby Sunshine Grant

- Returns only households with family members whose age is strictly more than the provided value
- Returns only family members whose age is strictly less than the provided value

5. YOLO GST Grant

- Returns only households with total annual income strictly less than the provided total annual income
- Returns all family members of the household
