# Class  UserEvaluation

## Methods

| Name                        | Return type      | Arguments                                                              | Description                                                |
| --------------------------- | ---------------- | ---------------------------------------------------------------------- |----------------------------------------------------------- |
| UserEvaluation              | Constructor      | Float rating, Integer userId, Integer userEvaluatedId                  |                                                            |
| getRating                   | Float            |                                                                        | Method that returns the rating given to the user           |
| setRating                   | Float            | Float rating                                                           | Method that set the rating given to the user               |
| getUserId                   | Integer          |                                                                        | Method that returns the identifier of the user evaluator   |
| setUserId                   | Integer          | Integer userId                                                         | Method that set the identifier of the user evaluator       |
| getUserEvaluatedId          | Integer          |                                                                        | Method that returns the identifier of user being evaluated |

## Attributes

| Name                        | Type             | Description                                                                 | Default Value                                                            |
| --------------------------- | ---------------- | --------------------------------------------------------------------------- | ------------------------------------------------------------------------ |
| rating                      | Float            | Stores the rating given to the user being evaluated by te user evaluator    |                                                                          |
| userId                      | Integer          | Stores the identifier of the user evaluator                                 |                                                                          |
| userEvaluatedId             | Integer          | Stores the identifier of the user being evaluated                           |                                                                          |
| EVALUATION_IS_INVALID       | String           | Stores the message error for invalid evaluation value                       | "Hey, a avaliação deve estar entre 0 e 5"                                |
| USER_ID_IS_INVALID          | String           | Stores the message error for invalid user evaluator identifier              | "O identificador do usuário é inválido"                                  |
| USER_EVALUATED_ID_IS_INVALID| String           | Stores the message error for invalid identifier for user being evaluated    | "O identificador do usuário avaliado é inválido"                         |
 

