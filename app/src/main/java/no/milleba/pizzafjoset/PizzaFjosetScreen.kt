package no.milleba.pizzafjoset

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import no.milleba.pizzafjoset.ui.screens.AboutScreen
import no.milleba.pizzafjoset.ui.screens.CartScreen
import no.milleba.pizzafjoset.ui.screens.ContactScreen
import no.milleba.pizzafjoset.ui.screens.FavoritesScreen
import no.milleba.pizzafjoset.ui.screens.HomeScreen
import no.milleba.pizzafjoset.ui.screens.MealListScreen
import no.milleba.pizzafjoset.ui.viewModels.MealsViewModel
import no.milleba.pizzafjoset.ui.viewModels.OrderViewModel
import no.milleba.pizzafjoset.ui.screens.ProfileScreen
import no.milleba.pizzafjoset.ui.theme.errorContainerDark
import no.milleba.pizzafjoset.ui.theme.inverseOnSurfaceDark
import no.milleba.pizzafjoset.ui.theme.onSurfaceVariantDark

sealed class Screen(val route: String, val titleRes: Int) {

    data object Home : Screen("home", R.string.app_name)
    data object About : Screen("about", R.string.about)
    data object Meals : Screen("meals", R.string.menu)
    data object Details : Screen("details", R.string.details)

    data object Profile : Screen("profile", R.string.profile)
    data object Contact : Screen("contact", R.string.contact)
    data object Cart : Screen("cart", R.string.cart)

    data object Checkout : Screen("checkout", R.string.checkout)

    data object Login : Screen("login", R.string.login)
    data object Favorites : Screen("favorites", R.string.favorites)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PizzaFjosetTopAppBar(
    title: String,
    currentScreen: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var expanded by remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        title = { Text(title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = inverseOnSurfaceDark,
            titleContentColor = onSurfaceVariantDark
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                        tint = onSurfaceVariantDark
                    )
                }
            }
        },
        actions = {
            Box {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = stringResource(R.string.menu),
                        tint = onSurfaceVariantDark
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(260.dp)
                ) {
                    DropdownMenuItem(
                        text = {
                            DropDownMenuItemText(
                                stringResource(R.string.home),
                                Screen.Home,
                                navController,
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = stringResource(R.string.home),
                                tint = onSurfaceVariantDark
                            )
                        },
                        onClick = {
                            expanded = false
                            navController.navigate(Screen.Home.route)
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            DropDownMenuItemText(
                                stringResource(R.string.about),
                                Screen.About,
                                navController
                            )
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.contact),
                                contentDescription = stringResource(R.string.about),
                                tint = onSurfaceVariantDark
                            )
                        },
                        onClick = {
                            expanded = false
                            navController.navigate(Screen.About.route)
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            DropDownMenuItemText(
                                stringResource(R.string.contact),
                                Screen.Contact,
                                navController
                            )
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.call),
                                contentDescription = stringResource(R.string.contact),
                                tint = onSurfaceVariantDark
                            )
                        },
                        onClick = {
                            expanded = false
                            navController.navigate(Screen.Contact.route)
                        }
                    )
                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                    DropdownMenuItem(
                        text = {
                            DropDownMenuItemText(
                                stringResource(R.string.cart),
                                Screen.Cart,
                                navController
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = stringResource(R.string.cart),
                                tint = onSurfaceVariantDark
                            )
                        },
                        onClick = {
                            expanded = false
                            navController.navigate(Screen.Cart.route)
                        }
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
    )
}


@Composable
fun NavigationBottomBar(
    navController: NavHostController,
    currentScreen: String,
    modifier: Modifier = Modifier,
    orderViewModel: OrderViewModel
) {
    val state by orderViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.totalCount) {
        Log.d("BottomBar", "badge = ${state.totalCount}")
    }


    NavigationBar(modifier = modifier, windowInsets = NavigationBarDefaults.windowInsets) {
        NavigationBarItem(
            selected = currentScreen == Screen.Meals.route,
            onClick = { navController.navigate(Screen.Meals.route) },
            // label = { Text(stringResource(Screen.Meals.titleRes)) },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.fastfood),
                    contentDescription = stringResource(R.string.menu),
                    tint = onSurfaceVariantDark
                )
            }
        )
        NavigationBarItem(
            selected = currentScreen == Screen.Profile.route,
            onClick = { navController.navigate(Screen.Profile.route) },
            // label = { Text(stringResource(Screen.Profile.titleRes)) },
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = stringResource(R.string.profile),
                    tint = onSurfaceVariantDark
                )
            }
        )

        NavigationBarItem(
            selected = currentScreen == Screen.Favorites.route,
            onClick = { navController.navigate(Screen.Favorites.route) },
            // label = { Text(stringResource(Screen.Favorites.titleRes)) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(R.string.favorites),
                    tint = onSurfaceVariantDark
                )
            }
        )

        NavigationBarItem(
            selected = currentScreen == Screen.Cart.route,
            onClick = { navController.navigate(Screen.Cart.route) },
            // label = { Text(stringResource(Screen.Cart.titleRes)) },
            icon = {
                BadgedBox(
                    badge = {
                        if (state.totalCount > 0) {
                            Badge(
                                containerColor = errorContainerDark,
                                contentColor = onSurfaceVariantDark
                            ) { Text("${state.totalCount}") }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = stringResource(R.string.cart),
                        tint = onSurfaceVariantDark
                    )
                }

            }
        )
    }

}

@Composable
fun PizzaFjosetApp(
    navController: NavHostController = rememberNavController()
) {
    val orderViewModel: OrderViewModel = viewModel()
    val mealViewModel: MealsViewModel = viewModel()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: Screen.Meals.route
    val screenForRoute = remember {
        mapOf(
            Screen.Home.route to Screen.Home,
            Screen.Meals.route to Screen.Meals,
            Screen.Details.route to Screen.Details,
            Screen.Profile.route to Screen.Profile,
            Screen.Contact.route to Screen.Contact,
            Screen.Cart.route to Screen.Cart,
            Screen.Checkout.route to Screen.Checkout,
            Screen.Login.route to Screen.Login,
            Screen.Favorites.route to Screen.Favorites,
            Screen.About.route to Screen.About
        )
    }
    val currentScreen = screenForRoute[currentRoute] ?: Screen.Meals
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Scaffold(
        topBar = {
            PizzaFjosetTopAppBar(
                stringResource(currentScreen.titleRes),
                currentScreen.route,
                navController.previousBackStackEntry != null,
                { navController.navigateUp() },
                navController
            )
        },
        bottomBar = {
            NavigationBottomBar(navController, currentScreen.route, orderViewModel = orderViewModel)
        }

    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(isLandscape)
            }
            composable(Screen.Meals.route) {
                MealListScreen(orderViewModel, mealViewModel, isLandscape)
            }
            composable(Screen.Profile.route) {
                ProfileScreen(orderViewModel, isLandscape)
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen(orderViewModel)
            }

            composable(Screen.Cart.route) {
                CartScreen(orderViewModel)
            }

            composable(Screen.Checkout.route) {
                // CheckoutScreen()
            }

            composable(Screen.Login.route) {
                // LoginScreen()
            }

            composable(Screen.Contact.route) {
                ContactScreen(isLandscape)
            }

            composable(Screen.About.route) {
                AboutScreen(isLandscape)
            }
        }

    }

}

@Composable
fun DropDownMenuItemText(
    text: String,
    screen: Screen,
    navHostController: NavHostController
) {
    val currentDestination = navHostController.currentBackStackEntryAsState().value?.destination

    val isSelected = currentDestination?.route == screen.route

    Text(
        text = text,
        color = if (isSelected) errorContainerDark else onSurfaceVariantDark,
        fontWeight = FontWeight.Bold
    )
}


