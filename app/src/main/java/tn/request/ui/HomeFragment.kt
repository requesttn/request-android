package tn.request.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import org.koin.androidx.compose.get
import tn.request.R
import tn.request.ui.theme.RequestTheme

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(
            requireContext()
        ).apply {
            setContent {
                RequestTheme {
                    val homeViewModel: HomeViewModel = get()
                    HomeScreen(homeViewModel) {
                        homeViewModel.logout()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container_view, LoginFragment())
                            ?.commit()
                    }
                }
            }
        }
    }
}