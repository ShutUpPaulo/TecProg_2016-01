# Class  Place

## Class Attributes

| Name                        | Type             | Description                                                                 | Default Value                                                            |
| --------------------------- | ---------------- | --------------------------------------------------------------------------- | ------------------------------------------------------------------------ |
| INVALID_NAME                | String           | Stores the error message of invalid place name                              | "Hey, nome invalido"                                                     |
| INVALID_LATITUDE            | String           | Stores the error message of invalid place latitude                          | "Hey, sem a latitude não é possível encontrar o lugar"                   |
| INVALID_LONGITUDE           | String           | Stores the error message of invalid place longitude                         | "Hey, sem a longitude não é possível encontrar o lugar"                  |
| INVALID_COMMENT             | String           | Stores the error message of invalid place comment                           | "Hey, o comentario não pode ser vazio"                                   |
| id                          | int              | Stores the place identifier                                                 |                                                                          |
| name                        | String           | Stores the place name                                                       |                                                                          |
| comment                     | ArrayList<String>| Stores the comments given to the place by the users                         |                                                                          |
| evaluate                    | Float            | Stores the average of place evaluations given by users                      |                                                                          |
| longitude                   | Double           | Stores the place longitude                                                  |                                                                          |
| latitude                    | Double           | Stores the place latitude                                                   |                                                                          |
| phone                       | String           | Stores the place phone                                                      |                                                                          |
| operation                   | String           | Stores the place operating hours                                            |                                                                          |
| description                 | String           | Stores the place description                                                |                                                                          |
| address                     | String           | Stores the place address                                                    |                                                                          |

## Methods

### Place

**Return type:** Constructor

**Arguments:** String name, String evaluate, String longitude, String latitude, String operation, String description, String address, String phone

**Description:** Creates a new place object with the given attributes

### setAddress

**Return type:** void

**Arguments:** String address;

**Description:** Method that sets the place address

### getName

**Return type:** String

**Arguments:** No arguments

**Description:** Method that returns the place name

### setName

**Return type:** void

**Arguments:** String name;

**Description:** Method that sets the place name

### getComment

**Return type:** String

**Arguments:** No arguments;

**Description:** Method that returns the place comments given by the people who visited it

### addComment

**Return type:** void

**Arguments:** String comment;

**Description:** Method that adds a place comment given by an user in ArrayList<String> of place comments

### getLongitude

**Return type:** Double

**Arguments:** No arguments;

**Description:** Method that returns the place longitude

### setLongitude

**Return type:** void

**Arguments:** Double longitude;

**Description:** Method that sets the place longitude

### setEvaluate

**Return type:** void

**Arguments:** String evaluate;

**Description:** Method that sets the average of the place evaluations

### getDescription

**Return type:** String

**Arguments:** No Arguments;

**Description:** Method that returns the place description

### setDescription

**Return type:** void

**Arguments:** String description;

**Description:** Method that sets the place description

### getPhone

**Return type:** String

**Arguments:** No arguments;

**Description:** Method that returns the place phone

### setPhone

**Return type:** void

**Arguments:** String phone;

**Description:** Method that sets the place phone

### getId

**Return type:** id

**Arguments:** No arguments;

**Description:** Method that returns the place identifier

### setId

**Return type:** void

**Arguments:** int id;

**Description:** Method that sets the place identifier
