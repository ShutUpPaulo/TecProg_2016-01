# Class  UserEvaluation

## Methods

| Name                        | Return type      | Arguments                                                              | Description     |
| --------------------------- | ---------------- | ---------------------------------------------------------------------- |---------------- |
| UserEvaluation              | Constructor      |                                                                        |                 |
| getRating                   | Float            | rating, userId, userEvaluationId                                       |                 |
| setRating                   | Float            |                                                                        |                 |
| getUserId                   | Integer          |                                                                        |                 |
| setUserId                   | Integer          | userId                                                                 |                 |
| getUserEvaluatedId          | Integer          |                                                                        |                 |

## Attributes

| Name                        | Type             | Description                                              | Default Value                                    |
| --------------------------- | ---------------- | -------------------------------------------------------- | ------------------------------------------------ |
| rating                      | Float            | Stores the user's rating                                 |                                                  |
| userId                      | Integer          | Stores the user's ID                                     |                                                  |
| userEvaluatedId             | Integer          | Stores the user's ID evaluated                           |                                                  |
| EVALUATION_IS_INVALID       | String           | Stores a message about an invalid evaluation             | "Hey, a avaliação deve estar entre 0 e 5"        |
| USER_ID_IS_INVALID          | String           | Stores a message of invalid user's ID                    | "O identificador do usuário é inválido"          |
| USER_EVALUATED_ID_IS_INVALID| String           | Stores a message about invalid evaluation of user's ID   | "O identificador do usuário avaliado é inválido" |
 

