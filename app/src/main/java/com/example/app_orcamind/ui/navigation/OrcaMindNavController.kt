package com.example.app_orcamind.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


//Destinos da navegação
object MainDestinations {
    const val HOME_ROUTE = "home"
}


//Lembra e cria uma instância de OrcaMindController
@Composable
fun rememberOrcaMindNavController(
    navController: NavHostController = rememberNavController()
): OrcaMindNavController = remember(navController) {
    OrcaMindNavController(navController)
}

//Responsável por manter a lógica de navegação da interface do usuário.
@Stable
class OrcaMindNavController(
    val navController: NavHostController
) {


    //implementar o comportamento do botão de voltar em uma barra de navegação.
    fun upPress() {
        navController.navigateUp()
    }

    fun navigateToBottomBarRoute(route: String) {
        if (route != navController.currentDestination?.route) {
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true

                popUpTo(findStartDestination(navController.graph).id) {
                    saveState = true
                }
            }
        }
    }

    fun navigationToOrcaDetails(orcaId: Long, origin: String, from: NavBackStackEntry) {

        if (from.lifecycleIsResumed()) {
            navController.navigate("${MainDestinations.HOME_ROUTE}/$orcaId?origin=$origin")
        }
    }
}

//Se o ciclo de vida não for retomado, significa que este NavBackStackEntry já processou um evento nav.
//*
//* Isto é usado para desduplicar eventos de navegação.
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)


private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}