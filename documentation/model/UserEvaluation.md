# Class  UserEvaluation

## Class Attributes

| Name                        | Type             | Description                                                                 | Default Value                                                            |
| --------------------------- | ---------------- | --------------------------------------------------------------------------- | ------------------------------------------------------------------------ |
| rating                      | Float            | Stores the rating given to the user being evaluated by te user evaluator    |                                                                          |
| userId                      | Integer          | Stores the identifier of the user evaluator                                 |                                                                          |
| userEvaluatedId             | Integer          | Stores the identifier of the user being evaluated                           |                                                                          |
| EVALUATION_IS_INVALID       | String           | Stores the message error for invalid evaluation value                       | "Hey, a avaliação deve estar entre 0 e 5"                                |
| USER_ID_IS_INVALID          | String           | Stores the message error for invalid user evaluator identifier              | "O identificador do usuário é inválido"                                  |
| USER_EVALUATED_ID_IS_INVALID| String           | Stores the message error for invalid identifier for user being evaluated    | "O identificador do usuário avaliado é inválido"                         |

## Methods

### UserEvaluation

**Return type:** Constructor

**Arguments:** Float rating, Integer userId, Integer userEvaluatedId

**Description:** Creates a new user UserEvaluation object with the given attributes

### getRating

**Return type:** Float

**Arguments:** No arguments

**Description:**  Method that returns the rating given to the user

### setRating

**Return type:** void

**Arguments:** Float rating

**Description:**  Method that sets the rating given to the user

### getUserId

**Return type:** Integer

**Arguments:** No arguments

**Description:**  Method that returns the identifier of the user evaluator

### setUserId

**Return type:** void

**Arguments:** Integer userId

**Description:**  Method that sets the identifier of the user evaluator

### getUserEvaluatedId

**Return type:** Integer

**Arguments:** No arguments

**Description:**  Method that returns the identifier of user being evaluated
