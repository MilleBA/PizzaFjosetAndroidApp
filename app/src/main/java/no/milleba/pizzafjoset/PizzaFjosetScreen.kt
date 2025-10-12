package no.milleba.pizzafjoset

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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import no.milleba.pizzafjoset.ui.AboutScreen
import no.milleba.pizzafjoset.ui.CartScreen
import no.milleba.pizzafjoset.ui.ContactScreen
import no.milleba.pizzafjoset.ui.FavoritesScreen
import no.milleba.pizzafjoset.ui.HomeScreen
import no.milleba.pizzafjoset.ui.MealListScreen
import no.milleba.pizzafjoset.ui.OrderViewModel
import no.milleba.pizzafjoset.ui.ProfileScreen
import no.milleba.pizzafjoset.ui.theme.errorContainerDark
import no.milleba.pizzafjoset.ui.theme.errorLightHighContrast
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
    modifier: Modifier = Modifier
) {

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
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = stringResource(R.string.cart),
                    tint = onSurfaceVariantDark
                )
            }
        )
    }

}

@Composable
fun PizzaFjosetApp(
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
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
            NavigationBottomBar(navController, currentScreen.route)
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
                HomeScreen()
            }
            composable(Screen.Meals.route) {
                val uiState by viewModel.uiState.collectAsState()
                MealListScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen()
            }

            composable(Screen.Cart.route) {
                CartScreen()
            }

            composable(Screen.Checkout.route) {
                // CheckoutScreen()
            }

            composable(Screen.Login.route) {
                // LoginScreen()
            }

            composable(Screen.Contact.route) {
                ContactScreen()
            }

            composable(Screen.About.route) {
                AboutScreen()
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


