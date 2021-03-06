# WindowControllerFx
Custom controller implementation to facilitate stage creation

## Usage 
1 - Download .jar and add to your project classpath

## Working with FXML

2 - Create your FXML layout on SceneBuilder or preffered IDE

3 - Extend your FXML controller of WindowControllerFx

```java

  public class MyController extends WindowControllerFx
  
```
4 - Override getFXML method passing the location of your FXML layout

```java

    @Override
    public String getFXML() {
        return "/com/example/view/my-layout.fxml";
    }
  
```
5 - Override initialize method if you want node initialization control 

6 - Instance your controller and use one of show methods to display your stage
```java

  // shows decorated stage
  new MyController().show();
  
  // shows modal decorated stage
  new MyController()
              .setParent(anParent)
              .showModal();
  
  // shows modal dialog stage
  new MyController()
              .setParent(anParent)
              .showAsDialog();
              
   // shows undecorated stage
  new MyController().showUndecorated(transparent?);
  
```

## Working with Region

If you don't work with FXML, just override the getRootPane method instead of getFXML, see below:

```java

    @Override
    public Region getRootPane() {
      BorderPane pane = new BorderPane();

       MenuBar bar = new MenuBar(
          new Menu("File"),
          new Menu("Edit"),
          new Menu("Exit")
       );

        pane.setTop(bar);

        pane.setPrefSize(500, 500);

        pane.setCenter(new TextArea("Some text"));

        return pane;
    }
  
```

Feel free to contribute. 

See example in sources to full usage.

## License

```
Copyright 2017 Fernando Andrauss

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
