# INTE HT23 GROUP 12
- Stefan Lundell
- Minna Qvarnström

## Project: Checkout system
### Classes:
- Customer
- Product
  - VAT
- Purchase
    - Receipt
- CashRegister
  - Money
  - CashMoney
- InventoryBalance
- Membership
    - MembershipType

### Basic flow:
A `Customer` starts scanning `Products`, adding them to their current
`Purchase`. The customer can be part of a `Membership`if he or she 
chooses. When the customer has finished scanning products, that purchase 
is sent to the `CashRegister`, where the customer can choose to pay by 
either `Money` or `CashMoney`. If the payment is completed a `Receipt` 
is created and the `InventoryBalance` is updated.

### Test case naming convention
- expectedBehavior_when_stateUnderTest 
- For example: throwsException_when_ageLessThan18
