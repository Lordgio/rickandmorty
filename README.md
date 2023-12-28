
# Rick and Morty sample project

This is a sample project using the [Rick and Morty GraphQL API](https://rickandmortyapi.com/graphql) 
and implementing a subset of the functionality to showcase its uses.
The app is developed in Kotlin with full Jetpack Compose ui and MVVM architecture.

## Features

- **Character List**: Shows a list of rick and Morty characters. The list data comes paginated from the GraphQL service
so the data loads as you scroll the list.
- **Character filter**: On the character list, you can use the filter row at the top to filter by character's gender. This
filter is sent to the service as part of the query.
- **Content navigation**: Pressing any character will take you to their detail screen, which shows more info.
- **Character detail**: The detail screen shows a bigger picture of the character and more information about it.
- **Favourites**: On the detail screen there is a toggle button to save the character as a user's favourite. This information
is saved locally and restored every time the user opens the app.
- **Deeplinking** You can access the full information of a character from anywhere in the system by tapping on an url with this
format: `https://alkimiirickandmorty.com/details/{characterId}`,
for example [this one](https://alkimiirickandmorty.com/details/1)

> Important information: Since Android 12 all deeplinks have to be verified to be able to open the app. As 
> this domain is not verified the link will always be open in the browser. One way to test the link is by using adb:
> `adb shell am start -W -a android.intent.action.VIEW -d "https://alkimiirickandmorty.com/details/1" dev.jorgeroldan.rickandmorty`

## Libraries used

| Library            | Description                                                                      |
|--------------------|----------------------------------------------------------------------------------|
| Apollo GraphQL     | Helps managing GraphQL resources and generates code for the schema and queries   |
| Paging             | Manages paging resources from the network or local and handles loading new pages |
| Arrow              | Adds functional programming helpers to manage business logic.                    |
| Koin               | Lightweight dependency injection framework easy to setup and work with           |
| Jetpack Compose    | The new UI framework for Android                                                 |
| Navigation compose | The official way of handling navigation in full Compose apps                     |
| Coil               | An image loading library written for Kotlin coroutines and with compose support. |

## Best practices applied

As small as this app is, it showcases a number of best practices considered the standard for Android development currently.
Some of these practices are:
- **Full Kotlin development**: The app is written 100% in Kotlin, resulting in code more concise and expressive. 
- **MVVM architecture**: This architecture is recommended by Google as it offers a clean separation of concerns between 
your ui and business logic.
- **Kotlin's coroutines for async programming**: Coroutines simplify handling async or concurrent operations and make the
code more readable and testable.
- **Follows Material Design guidelines**: Using standard design principles makes easier for the user to understand how to 
navigate your app and makes sure it is accessible for most people.
- **Dependency injection**: Dependency injection frameworks help separate concerns, reuse instances of components and makes
it easy to replace components for testing.
- **Testing**: Test help catch code issues early and improve its maintainability and reusability.