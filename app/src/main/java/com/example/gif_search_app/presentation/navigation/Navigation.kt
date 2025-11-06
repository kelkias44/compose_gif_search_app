package com.example.gif_search_app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gif_search_app.domain.entity.Gif
import com.example.gif_search_app.presentation.ui.gifDetail.GifDetailScreen
import com.example.gif_search_app.presentation.ui.gifList.GifGridScreen
import com.google.gson.Gson
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class Screen(val route: String) {
    object GifGrid : Screen("gif_grid")
    object GifDetail : Screen("gif_detail")
    
    fun createDetailRoute(gif: Gif): String {
        val gifJson = URLEncoder.encode(Gson().toJson(gif), StandardCharsets.UTF_8.toString())
        return "${GifDetail.route}/$gifJson"
    }
}

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.GifGrid.route,
        modifier = modifier
    ) {
        composable(Screen.GifGrid.route) {
            GifGridScreen(
                onGifClick = { gif ->
                    navController.navigate(Screen.GifDetail.createDetailRoute(gif))
                }
            )
        }

        composable(
            route = "${Screen.GifDetail.route}/{gifJson}",
            arguments = listOf(
                navArgument("gifJson") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val gifJson = backStackEntry.arguments?.getString("gifJson")?.let {
                URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
            }
            val gif = Gson().fromJson(gifJson, Gif::class.java)

            GifDetailScreen(
                gif = gif,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

