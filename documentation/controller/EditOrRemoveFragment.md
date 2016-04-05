# Class  EditOrRemoveFragment

## Methods

| Name                   | Return type      | Arguments                                                              | Description                                                                                                                           |
| ---------------------- | ---------------- | ---------------------------------------------------------------------- |-------------------------------------------------------------------------------------------------------------------------------------- |
| EditOrRemoveFragment   | Constructor      |                                                                        |                                                                                                                                       |
| onCreateView           | View             | LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState| Method to get view elements from XML view file                                                                                                                                      |
| onClick                | void             | View view                                                              | Method that calls editEventFragment when the user clicks in the Button which confirms that this is the event to be updated or deleted |

## Attributes

| Name               | Type             | Description                                                                                                                             | Default Value |
| ------------------ | ---------------- | ----------------------------------------------------------------------------------------------------------------------------------------|---------------|
| event              | Event            | Object of Event class used to get event information and show it to the user, so that him confirm if want to delete or update this event |               |
| eventCategoriesText| TextView         | TextView that shows the event categories                                                                                                |               |
| eventPriceText     | TextView         | TextView that shows the event price                                                                                                     |               |
| showEvent          | ShowEvent        | Object of ShowEvent class used to acess price converstion method                                                                        |               | 
| view               | View             | Object of View that receives the Fragment view through the XML                                                                          |               | 
