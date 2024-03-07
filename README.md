# MobiquityTest
Take home task for Mobiquity


About the solution presented here:


App has a services/domain layer, a data layer, and a UI layer.
These are contained in packages data, services, and UI respectively.

The Dependency Injection framework instantiates the app services, and provides them to domain layer.
Since the number of services are limited, Service Locator pattern seems apt for use here.

The Navhost takes care of navigation, screen transitions, and handling backstack.
I have omitted the back arrow navigation on the Detain screen, although user can go back to previous screens,
using the android device back button.

The oroducts fetched is represented in the UIState within MainViewModel.

I have omitted moving all string literals in strings.xml, and creating PreviewParameterProviders for each screen composable,
but that would the standard practice while working on a production app.

App Services

ProductCatalogService - fetches the data

Testing

Unit Tests -
    Mockito used for mocking
    ProductCatalogService, MainViewModel have been unit tested.

Integration Tests -

    I planned to do 2 basic integration tests, for checking the happy path
    and error path Product detain Flows, but it's probably going to delay my assignment.

    ComposeUiTest can be used to achieve that.

    Happy to talk about it later.
