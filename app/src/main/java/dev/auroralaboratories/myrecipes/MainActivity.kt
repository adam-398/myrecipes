package dev.auroralaboratories.myrecipes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dev.auroralaboratories.myrecipes.loginframes.ForgotPassword
import dev.auroralaboratories.myrecipes.loginframes.LoginScreen
import dev.auroralaboratories.myrecipes.loginframes.RegisterUser
import dev.auroralaboratories.myrecipes.loginframes.ResetNewPasswordScreen
import dev.auroralaboratories.myrecipes.preferences.SettingsScreen
import dev.auroralaboratories.myrecipes.preferences.ThemePreferences
import dev.auroralaboratories.myrecipes.ui.theme.MyRecipesTheme
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.parseSessionFromFragment
import kotlinx.coroutines.launch
import dev.auroralaboratories.myrecipes.databasefunctions.SupabaseClient
import dev.auroralaboratories.myrecipes.databasefunctions.SupabaseClient.supabase

class MainActivity : ComponentActivity() {

    private var pendingResetPasswordLink by mutableStateOf(false)

    private var pendingSharedListId by mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SupabaseClient.initialize(this)
        ThemePreferences.initialize(this)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        handleIncomingIntent(intent)
        enableEdgeToEdge()
        setContent {
            MyRecipesTheme {

                val navController = rememberNavController()
                var startDestination by remember { mutableStateOf<String?>(null) }

                LaunchedEffect(Unit) {
                    supabase.auth.awaitInitialization()
                    startDestination =
                        if (supabase.auth.currentSessionOrNull() != null) "landing" else "login"
                }

                LaunchedEffect(pendingResetPasswordLink) {
                    if (pendingResetPasswordLink) {
                        navController.navigate("resetNewPassword")
                        pendingResetPasswordLink = false
                    }
                }

                LaunchedEffect(pendingSignupConfirmed) {
                    if (pendingSignupConfirmed) {
                        navController.navigate("landing") { popUpTo(0) }
                        pendingSignupConfirmed = false
                    }
                }

                LaunchedEffect(pendingSharedListId) {
                    pendingSharedListId?.let {
                        navController.navigate("sharedList/$it")
                        pendingSharedListId = null
                    }
                }

                if (startDestination != null) {
                    NavHost(
                        navController = navController,
                        startDestination = startDestination!!
                    ) {
                        composable("landing") {
                            Landing(navController)
                        }
                        composable("login") {
                            LoginScreen(navController)
                        }
                        composable("register") {
                            RegisterUser(navController)
                        }
                        composable("forgotPassword") {
                            ForgotPassword(navController)
                        }
                        composable("settings") {
                            SettingsScreen(navController)
                        }
                        composable("resetNewPassword") {
                            ResetNewPasswordScreen(navController)
                        }
                    }
                }
            }
        }
    }

    /**
     * Handles new intents.
     * @param intent The new intent.
     */
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIncomingIntent(intent)
    }

    private var pendingSignupConfirmed by mutableStateOf(false)

    /**
     * Handles incoming intents.
     * @param intent The intent to handle.
     */
    private fun handleIncomingIntent(intent: Intent) {
        Log.d("DeepLinkDebug", "Received intent data: ${intent.data}")
        val data = intent.data
        when {
            data?.scheme == "trailweight" && data.host == "reset-password" -> {
                pendingResetPasswordLink = true
                val fragment = data.encodedFragment
                if (fragment != null) {
                    lifecycleScope.launch {
                        try {
                            val session = supabase.auth.parseSessionFromFragment(fragment)
                            supabase.auth.importSession(session)
                        } catch (e: Exception) {
                            FirebaseCrashlytics.getInstance().recordException(e)
                        }
                    }
                }
            }
            data?.scheme == "trailweight" && data.host == "confirm-signup" -> {
                val fragment = data.encodedFragment
                if (fragment != null) {
                    lifecycleScope.launch {
                        try {
                            val session = supabase.auth.parseSessionFromFragment(fragment)
                            supabase.auth.importSession(session)
                            pendingSignupConfirmed = true
                        } catch (e: Exception) {
                            FirebaseCrashlytics.getInstance().recordException(e)
                        }
                    }
                }
            }
            data?.scheme == "trailweight" && data.host == "list" -> {
                val shareId = data.lastPathSegment
                if (shareId != null) {
                    pendingSharedListId = shareId
                }
            }
        }
    }
}

