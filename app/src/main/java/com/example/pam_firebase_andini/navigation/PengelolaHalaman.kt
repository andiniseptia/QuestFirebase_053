package com.example.pam_firebase_andini.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pam_firebase_andini.ui.view.DetailView
import com.example.pam_firebase_andini.ui.view.HomeScreen
import com.example.pam_firebase_andini.ui.view.InsertMhsView

@Composable
fun PengelolaHalaman(
    modifier: Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    ) {
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = {
                    navController.navigate(DestinasiInsert.route)
                },
                onDetailClick = { nim ->
                    navController.navigate("${DestinasiDetail.route}/$nim")
                    println("PengelolaHalaman: nim = $nim")
                }
            )
        }
        composable(DestinasiInsert.route) {
            InsertMhsView(
                onBack = { navController.popBackStack() },
                onNavigate = {
                    navController.navigate(DestinasiHome.route)
                }
            )
        }

        composable(
            DestinasiDetail.routesWithArg,
            arguments = listOf(navArgument(DestinasiDetail.NIM){})
        ) {
            navBackStackEntry ->
            val nim =
                navBackStackEntry.arguments?.getString(DestinasiDetail.NIM)
            nim?.let {
                DetailView(nim = nim, onBack = { navController.popBackStack() }, onEditClick = { // nim ->
                       // navController.navigate(DestinasiUpdate.routeWithArg.replace("{${DestinasiUpdate.NIM}}", nim))
                    },
                )
            }
        }
    }
}