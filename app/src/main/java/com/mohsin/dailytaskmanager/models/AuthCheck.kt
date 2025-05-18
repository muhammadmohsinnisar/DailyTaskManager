import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.mohsin.dailytaskmanager.Routes

@Composable
fun AuthCheck(navController: NavHostController) {
    val currentUser = FirebaseAuth.getInstance().currentUser

    LaunchedEffect(key1 = currentUser) {
        if (currentUser != null) {
            navController.navigate(Routes.MAIN) {
                popUpTo("login") { inclusive = true }
            }
        } else {
            navController.navigate("login") {
                popUpTo("home") { inclusive = true }
            }
        }
    }
}
